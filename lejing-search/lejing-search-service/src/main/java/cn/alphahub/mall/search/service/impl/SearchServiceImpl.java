package cn.alphahub.mall.search.service.impl;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.exception.BizException;
import cn.alphahub.common.reflect.ReflectUtil;
import cn.alphahub.mall.product.domain.Brand;
import cn.alphahub.mall.product.vo.AttrRespVO;
import cn.alphahub.mall.search.domain.SkuModel;
import cn.alphahub.mall.search.feign.AttrClient;
import cn.alphahub.mall.search.feign.BrandClient;
import cn.alphahub.mall.search.pojo.SearchParam;
import cn.alphahub.mall.search.pojo.SearchResult;
import cn.alphahub.mall.search.repository.ProductRepository;
import cn.alphahub.mall.search.service.SearchService;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.AggregatorFactories;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.clients.elasticsearch7.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <b>商品搜索业务实现</b>
 *
 * @author Weasley
 * @version 1.0
 * @date 2021/03/07
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {
    /**
     * 导航链接
     */
    public static final String LINK = "http://search.lejing.com/list.html";

    /**
     * 商品-Elasticsearch持久层
     */
    @Resource
    private ProductRepository repository;
    /**
     * The ElasticsearchRestTemplate is an implementation of the ElasticsearchOperations interface using the High Level REST Client.
     *
     * @link https://docs.spring.io/spring-data/elasticsearch/docs/4.1.5/reference/html/#elasticsearch.operations.resttemplate
     */
    @Resource
    private ElasticsearchRestTemplate restTemplate;
    /**
     * 商品属性-feign远程调用客户端
     */
    @Resource
    private AttrClient attrClient;
    /**
     * 品牌信息-feign客户端
     */
    @Resource
    private BrandClient brandClient;

    /**
     * 使用spring提供的repository模板方法保存数据至es中
     *
     * @param skuModels 商品SKU信息数据
     * @return true|false
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveProduct(List<SkuModel> skuModels) {
        Set<SkuModel> oldRecords = new LinkedHashSet<>(skuModels);
        Set<SkuModel> newRecords = new LinkedHashSet<>();
        if (CollectionUtils.isEmpty(skuModels)) {
            return false;
        }
        // res返回的是保存成功的SkuModel
        Iterable<SkuModel> res = restTemplate.save(skuModels);
        for (SkuModel skuModel : res) {
            log.info("保存成功: {}", skuModel);
            newRecords.add(skuModel);
        }
        Set<SkuModel> failRecords = oldRecords.stream().filter(skuModel -> !newRecords.contains(skuModel)).collect(Collectors.toCollection(LinkedHashSet::new));
        for (SkuModel skuModel : failRecords) {
            log.info("保存失败：{}", skuModel);
        }
        return oldRecords.size() == newRecords.size();
    }

    /**
     * 删除商品
     *
     * @param skuId 商品sku id
     * @return true|false
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteProductById(Long skuId) {
        try {
            repository.deleteById(skuId);
            return true;
        } catch (Exception e) {
            log.error("删除商品失败：{}\n", e.getClass(), e);
            return false;
        }
    }

    /**
     * 获取搜索结果列表
     *
     * @param param 搜索请求参数实体
     * @return 获取搜索结果
     */
    @Override
    public SearchResult search(SearchParam param) {
        // 1. 判断是否有搜索条件，如果没有，直接返回null。不允许搜索全部商品
        if (ObjectUtils.isEmpty(param)) {
            return null;
        }
        // 2. 获取Elasticsearch QueryBuilder instances
        NativeSearchQuery nativeSearchQuery = this.buildNativeSearchQuery(param);
        // 3. 从ES从查询数据, 封装查询结果返回
        return this.buildSearchResult(nativeSearchQuery, param);
    }

    /**
     * 动态构建Elasticsearch查询条件
     *
     * @param param 搜索请求参数实体
     * @return a query created from Elasticsearch QueryBuilder instances
     */
    private NativeSearchQuery buildNativeSearchQuery(SearchParam param) {
        // 初始化一个查询构建器
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();

        // 1 对key进行全文检索查询
        BoolQueryBuilder basicQuery = buildBoolSearchQuery(param);
        searchQueryBuilder.withQuery(basicQuery);

        /* 2 通过sourceFilter设置返回的结果字段,我们只需要id、skus、subTitle */

        /**
         SourceFilter sourceFilter2 = new FetchSourceFilterBuilder().withIncludes("id", "skus", "subTitle").withExcludes().build();
         searchQueryBuilder.withSourceFilter(sourceFilter2);
         */

        //3 添加聚合条件: 品牌聚合，分类聚合，属性聚合
        // 品牌聚合
        TermsAggregationBuilder termsBrandAgg = AggregationBuilders.terms("brand_agg").field(ReflectUtil.property(SkuModel::getBrandId)).size(60);
        // 商品品牌子聚合: brand_name_agg, brand_img_agg
        termsBrandAgg.subAggregations(AggregatorFactories.builder()
                .addAggregator(AggregationBuilders.terms("brand_name_agg").field(ReflectUtil.property(SkuModel::getBrandName)))
                .addAggregator(AggregationBuilders.terms("brand_img_agg").field(ReflectUtil.property(SkuModel::getBrandImg)))
        );
        // 3.2 分类聚合
        TermsAggregationBuilder termsCategoryAgg = AggregationBuilders.terms("category_agg").field(ReflectUtil.property(SkuModel::getCatalogId));
        // 分类子聚合: category_name_agg
        termsCategoryAgg.subAggregation(AggregationBuilders.terms("category_name_agg").field(ReflectUtil.property(SkuModel::getCatalogName)));

        // 3.3 属性聚合(嵌入式聚合)
        String attrs = ReflectUtil.property(SkuModel::getAttrs);
        // 属性子聚合,创建一个嵌入式的子聚合
        NestedAggregationBuilder nestedTermsAttrAgg = AggregationBuilders.nested("attr_agg", attrs)
                .subAggregations(AggregatorFactories.builder().addAggregator(
                                AggregationBuilders.terms("attr_id_agg").field(attrs + "." + ReflectUtil.property(SkuModel.Attrs::getAttrId))
                                        // 3.3.1 聚合出attr_id对应的attr_name
                                        // 3.3.2 聚合出attr_id对应的所有可能值attr_value
                                        .subAggregations(AggregatorFactories.builder()
                                                .addAggregator(AggregationBuilders.terms("attr_name_agg").field(attrs + "." + ReflectUtil.property(SkuModel.Attrs::getAttrName)))
                                                .addAggregator(AggregationBuilders.terms("attr_value_agg").field(attrs + "." + ReflectUtil.property(SkuModel.Attrs::getAttrValue)).size(50))
                                        )
                        )
                );

        searchQueryBuilder.addAggregation(termsBrandAgg);
        searchQueryBuilder.addAggregation(termsCategoryAgg);
        searchQueryBuilder.addAggregation(nestedTermsAttrAgg);

        // 4 排序
        if (StringUtils.isNotBlank(param.getSort())) {
            String[] sortArray = StringUtils.split(param.getSort(), "_");
            SortOrder order = StringUtils.equalsIgnoreCase(sortArray[1], SortOrder.ASC.toString()) ? SortOrder.ASC : SortOrder.DESC;
            FieldSortBuilder sortBuilder = SortBuilders.fieldSort(sortArray[0]).order(order);
            System.out.println("ES 排序查询语句:\n" + JSONUtil.toJsonStr(sortBuilder));
            searchQueryBuilder.withSort(sortBuilder);
        }

        // 5 分页, 分页页码默认从0开始
        Integer pageNum = param.getPageNum();
        Integer pageSize = param.getPageSize();
        int page = Objects.nonNull(pageNum) && pageNum > 0 ? pageNum : 1;
        pageSize = Objects.nonNull(pageSize) && pageSize > 0 ? pageSize : 1;
        searchQueryBuilder.withPageable(PageRequest.of(page - 1, pageSize));

        // 6 高亮字段
        if (StringUtils.isNotBlank(param.getKeyword())) {
            searchQueryBuilder.withHighlightBuilder(buildHighlightBuilder());
        }

        NativeSearchQuery nativeSearchQuery = searchQueryBuilder.build();
        QueryBuilder queryBuilder = nativeSearchQuery.getQuery();
        System.err.println("ES DQL查询语句:\n" + queryBuilder + "\n");
        return nativeSearchQuery;
    }

    /**
     * <p>构建高亮字段</p>
     * <em>Settings can control how large fields are summarized to show only selected snippets ("fragments") containing search terms.</em>
     *
     * @return 搜索突出显示的高亮构建器
     */
    private HighlightBuilder buildHighlightBuilder() {
        return new HighlightBuilder()
                .preTags("<span style='color:red'>")
                .field(ReflectUtil.property(SkuModel::getSkuTitle))
                .postTags("</span>");
    }

    /**
     * 根据请求参数封装构建布尔查询构建器
     *
     * @param param 搜索结果页实体对象
     * @return BoolQueryBuilder 布尔查询构建器
     */
    private BoolQueryBuilder buildBoolSearchQuery(SearchParam param) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 1.1 must - 模糊匹配(skuTitle商品名称)
        if (StringUtils.isNotBlank(param.getKeyword())) {
            boolQuery.must(QueryBuilders.matchQuery(ReflectUtil.property(SkuModel::getSkuTitle), param.getKeyword()).operator(Operator.AND));
        }
        // 1.2 filter - 3级分类id查询(catalogId)
        if (ObjectUtils.isNotEmpty(param.getCatalog3Id())) {
            boolQuery.filter(QueryBuilders.termQuery(ReflectUtil.property(SkuModel::getCatalogId), param.getCatalog3Id()));
        }
        // 1.3 filter - 品牌id查询(brandId)
        if (ObjectUtils.isNotEmpty(param.getBrandId())) {
            boolQuery.filter(QueryBuilders.termsQuery(ReflectUtil.property(SkuModel::getBrandId), param.getBrandId()));
        }
        // 1.4 filter - 是否有库存查询(hasStock)
        Integer hasStock = param.getHasStock();
        if (ObjectUtils.isNotEmpty(hasStock)) {
            boolQuery.filter(QueryBuilders.termQuery(ReflectUtil.property(SkuModel::getHasStock), Objects.equals(hasStock, 1)));
        }

        // 1.5 filter - 商品属性查询(attrs)
        List<String> attrList = param.getAttrs();
        if (CollectionUtils.isNotEmpty(attrList)) {
            String attrsPropertyName = ReflectUtil.property(SkuModel::getAttrs);
            for (String attr : attrList) {
                // 切割属性: attr = "attrs=1_5寸:8寸&attrs=3_4核:8核&attrs=3_8G:12G"
                String[] attrArray = attr.split("_");
                String attrId = attrArray[0];
                String[] attrValues = attrArray[1].split(":");
                // 构建nested查询构造条件
                BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
                nestedBoolQuery.must(QueryBuilders.termQuery(attrsPropertyName + "." + ReflectUtil.property(SkuModel.Attrs::getAttrId), attrId));
                nestedBoolQuery.must(QueryBuilders.termsQuery(attrsPropertyName + "." + ReflectUtil.property(SkuModel.Attrs::getAttrValue), attrValues));
                // 每个attr都必须生成一个与之对应的nested查询条件
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery(attrsPropertyName, nestedBoolQuery, ScoreMode.None);
                boolQuery.filter(nestedQuery);
            }
        }

        // 1.6 filter - 价格区间查询(skuPrice): 前台传来格式: 1_500 | _500 | 500_
        String skuPrice = param.getSkuPrice();
        if (StringUtils.isNotBlank(skuPrice)) {
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(ReflectUtil.property(SkuModel::getSkuPrice));
            // 处理价格区间
            String[] prices = StringUtils.split(skuPrice, "_");
            int length = prices.length;
            if (length == 2) {
                rangeQuery.gte(prices[0]).lte(prices[1]);
            }
            if (length == 1) {
                if (StringUtils.startsWith(skuPrice, "_")) {
                    rangeQuery.gt(BigDecimal.ZERO).lte(prices[0]);
                }
                if (StringUtils.endsWith(skuPrice, "_")) {
                    rangeQuery.gte(prices[0]).lt(Long.MAX_VALUE);
                }
            }
            boolQuery.filter(rangeQuery);
        }

        return boolQuery;
    }

    /**
     * <p>从ES从查询数据</p>
     * <em>包含: 模糊匹配，过滤（按照属性、分类、品牌，价格区间，库存），完成排序、分页、高亮,聚合分析功能</em>
     *
     * @param nativeSearchQuery Elasticsearch QueryBuilder instances
     * @return 搜索结果响应数据实体
     */
    private SearchResult buildSearchResult(NativeSearchQuery nativeSearchQuery, SearchParam param) {
        // 初始化一个搜索结果响应数据实体
        SearchResult result = new SearchResult();
        // Tips: restTemplate会根据实体类的注解获取索引信息
        SearchHits<SkuModel> searchHits = restTemplate.search(nativeSearchQuery, SkuModel.class);
        ElasticsearchAggregations hitsAggregations = (ElasticsearchAggregations) searchHits.getAggregations();
        if (null == hitsAggregations) {
            throw new BizException("Elasticsearch7 聚合的 AggregationsContainer 实现为空");
        }
        Aggregations aggregations = hitsAggregations.aggregations();

        /* 保存商品分类 */
        List<SearchResult.CatalogVO> catalogVos = new ArrayList<>();
        /* 保存商品属性 */
        List<SearchResult.AttrVO> attrVos = Lists.newArrayList();
        /* 保存商品品牌 */
        List<SearchResult.BrandVO> brandVos = Lists.newArrayList();
        /* 保存商品分页页码集合 */
        List<Integer> pageNavs = Lists.newArrayList();
        /* 保存商品面包屑导航 */
        List<SearchResult.NavVO> navVos = Lists.newArrayList();

        // 断言聚合结果集不为空,否则抛出异常
        assert aggregations != null;
        for (Aggregation aggregation : aggregations) {
            String name = aggregation.getName();
            String type = aggregation.getType();
            log.info("aggregation name:" + name + ", aggregation type:" + type);
            // 从聚合结果中解析分类聚合: category_agg
            if (StringUtils.equals("category_agg", name)) {
                parsedCategoryAgg(catalogVos, (ParsedLongTerms) aggregation);
            }
            // 从聚合结果中解析品牌聚合: brand_agg
            if (StringUtils.equals("brand_agg", name)) {
                parsedBrandAgg(brandVos, (Terms) aggregation);
            }
            // 从聚合结果中解析属性聚合: attr_agg
            if (StringUtils.equals("attr_agg", name)) {
                parsedAttrNestedAgg(attrVos, (Nested) aggregation, result);
            }
        }

        List<SkuModel> skuModels = searchHits.stream().map(hit -> {
            SkuModel skuModel = hit.getContent();
            // 替换高亮字段处理
            Map<String, List<String>> highlightFields = hit.getHighlightFields();
            highlightFields.forEach((key, values) -> {
                String value = values.get(0);
                if (StringUtils.equals(key, ReflectUtil.property(SkuModel::getSkuTitle))) {
                    skuModel.setSkuTitle(value);
                }
            });
            System.out.println(skuModel);
            return skuModel;
        }).collect(Collectors.toList());

        Integer currentPage = param.getPageNum();
        Integer pageSize = param.getPageSize();
        currentPage = Objects.nonNull(currentPage) && currentPage > 0 ? pageSize : 1;
        pageSize = Objects.nonNull(pageSize) && pageSize > 0 ? pageSize : 1;
        // 总记录数
        long totalRecord = searchHits.getTotalHits();
        // 分页总页数
        int totalPage = Math.toIntExact((totalRecord + pageSize - 1) / pageSize);
        // 添加分页页码
        for (int i = 1; i <= totalPage; i++) {
            pageNavs.add(i);
        }

        // 构建面包屑导航
        navVos.addAll(buildNavVoListOfAttr(param, result));
        navVos.addAll(buildNavVoListOfBrand(param));
        navVos.addAll(buildNavVoListOfCategory(param));

        // 封装结果数据
        result.setProduct(skuModels);
        result.setPageNum(currentPage);
        result.setTotal(totalRecord);
        result.setTotalPages(totalPage);
        result.setPageNavs(pageNavs);
        result.setBrands(brandVos);
        result.setAttrs(attrVos);
        result.setCatalogs(catalogVos);
        result.setNavs(navVos);
        return result;
    }

    /**
     * 构建属性面包屑导航
     *
     * @param param  请求参数
     * @param result 搜索结果响应数据实
     * @return 页面面包屑导航列表
     */
    private List<SearchResult.NavVO> buildNavVoListOfAttr(SearchParam param, SearchResult result) {
        List<SearchResult.NavVO> navVos = new ArrayList<>();
        List<String> attrs = param.getAttrs();
        if (CollectionUtils.isNotEmpty(attrs)) {
            navVos = attrs.stream().map(attr -> {
                SearchResult.NavVO navVO = new SearchResult.NavVO();
                // attr=2_5寸:6寸
                String[] attrsArray = attr.split("_");
                Long attrId = Long.parseLong(attrsArray[0]);

                result.getAttrIds().add(attrId);

                String navValue = attrsArray[1];
                // 远程调用商品服务查询属性元数据信息
                BaseResult<AttrRespVO> baseResult = attrClient.info(attrId);
                if (baseResult.getSuccess()) {
                    AttrRespVO attrRespVO = baseResult.getData();
                    log.info("远程调用商品服务成功,响应数据{}", attrRespVO);
                    String attrName = attrRespVO.getAttrName();
                    navVO.setNavName(attrName);
                } else {
                    log.info("远程调用商品服务失败");
                }
                // 设置跳转，要跳转的页面，将请求的url里面当前的查询条件置空，无吊当前
                String replace = replaceQueryString(param, "attr", attr);
                navVO.setLink(LINK + "?" + replace);
                navVO.setNavValue(navValue);
                // 返回数据
                return navVO;
            }).collect(Collectors.toList());
        }
        return navVos;
    }

    /**
     * 构建品牌面包屑导航
     *
     * @param param 请求参数
     * @return 面包屑导航列表
     */
    private List<SearchResult.NavVO> buildNavVoListOfBrand(SearchParam param) {
        List<SearchResult.NavVO> navVos = new ArrayList<>();
        // 品牌
        if (CollectionUtils.isNotEmpty(param.getBrandId())) {
            BaseResult<List<Brand>> baseResult = brandClient.brandsInfo(param.getBrandId());
            if (baseResult.getSuccess()) {
                List<Brand> brands = baseResult.getData();
                SearchResult.NavVO navVO = new SearchResult.NavVO();
                log.info("远程调用商品服务查询品牌信息成功,响应数据:\n{}", JSONUtil.toJsonPrettyStr(brands));
                AtomicReference<String> replace = new AtomicReference<>("");
                String brandNames = brands.stream().map(brand -> {
                    replace.set(replaceQueryString(param, ReflectUtil.property(Brand::getBrandId), brand.getBrandId().toString()));
                    return brand.getName();
                }).collect(Collectors.joining(";"));
                navVO.setNavName("品牌");
                navVO.setNavValue(brandNames);
                navVO.setLink(LINK + "?" + replace);
                navVos.add(navVO);
            } else {
                log.info("远程调用商品服务查询品牌信息失败");
            }
        }
        return navVos;
    }

    /**
     * 构建分类面包屑导航
     *
     * @param param 请求参数
     * @return 面包屑导航列表
     */
    private List<SearchResult.NavVO> buildNavVoListOfCategory(SearchParam param) {
        List<SearchResult.NavVO> navVos = new ArrayList<>();
        // 分类
        if (ObjectUtils.isNotEmpty(param.getCatalog3Id())) {
            // TODO 构建分类面包屑导航
        }
        return navVos;
    }

    /**
     * 替换跳转的URL字符串
     *
     * @param param 搜索请求参数实体
     * @param key   要替换的字符串
     * @param value 要替换的内容
     * @return 替换后的URL字符串
     */
    private String replaceQueryString(SearchParam param, String key, String value) {
        String queryString = param.getQueryString();
        String encodedAttr = URLEncoder.encode(value, StandardCharsets.UTF_8);
        // 浏览器对空格的编码和Java不一样，差异化处理
        encodedAttr = StringUtils.replaceChars(encodedAttr, "+", "%20");
        return queryString.replace("&" + key + "=" + encodedAttr, "");
    }

    /**
     * 从聚合结果中解析属性聚合: attr_agg
     *
     * @param attrVos     商品属性列表
     * @param aggregation 聚合结果
     * @param result      搜索结果响应数据实体
     */
    private void parsedAttrNestedAgg(List<SearchResult.AttrVO> attrVos, Nested aggregation, SearchResult result) {
        List<Long> attrIds = Lists.newArrayList();
        Terms attrIdTerms = aggregation.getAggregations().get("attr_id_agg");
        List<? extends Terms.Bucket> attrIdBuckets = attrIdTerms.getBuckets();
        for (Terms.Bucket bucket : attrIdBuckets) {
            Terms attrNameTerms = bucket.getAggregations().get("attr_name_agg");
            Terms attrValueTerms = bucket.getAggregations().get("attr_value_agg");
            // 商品属性id
            Long attrId = bucket.getKeyAsNumber().longValue();
            // 商品属性名
            String attrName = attrNameTerms.getBuckets().stream().map(MultiBucketsAggregation.Bucket::getKeyAsString).collect(Collectors.joining(""));
            // 商品属性值
            List<String> attrValue = attrValueTerms.getBuckets().stream().map(MultiBucketsAggregation.Bucket::getKeyAsString).collect(Collectors.toList());
            // 封装商品属性数据
            attrVos.add(SearchResult.AttrVO.builder()
                    .attrId(attrId)
                    .attrName(attrName)
                    .attrValue(attrValue)
                    .build()
            );
            // 相当于改集合被使用
            attrIds.add(attrId);
        }
        result.setAttrIds(attrIds);
    }

    /**
     * 从聚合结果中解析品牌聚合: brand_agg
     *
     * @param brandVos    商品品牌列表
     * @param aggregation 聚合结果
     */
    private void parsedBrandAgg(List<SearchResult.BrandVO> brandVos, Terms aggregation) {
        List<? extends Terms.Bucket> brandBuckets = aggregation.getBuckets();
        for (Terms.Bucket brandBucket : brandBuckets) {
            // 1. 获取品品牌id
            Long brandId = brandBucket.getKeyAsNumber().longValue();
            // 2. 获取品品牌名称,品牌图片
            Terms brandNameTerms = brandBucket.getAggregations().get("brand_name_agg");
            Terms brandImgTerms = brandBucket.getAggregations().get("brand_img_agg");
            List<String> nameList = brandNameTerms.getBuckets().stream().map(MultiBucketsAggregation.Bucket::getKeyAsString).collect(Collectors.toList());
            List<String> imgList = brandImgTerms.getBuckets().stream().map(MultiBucketsAggregation.Bucket::getKeyAsString).collect(Collectors.toList());
            brandVos.add(SearchResult.BrandVO.builder()
                    .brandId(brandId)
                    .brandName(CollectionUtils.isNotEmpty(nameList) ? nameList.get(0) : "")
                    .brandImg(CollectionUtils.isNotEmpty(imgList) ? imgList.get(0) : "")
                    .build());
        }
    }

    /**
     * 从聚合结果中解析分类聚合: category_agg
     *
     * @param catalogVos  商品分类
     * @param aggregation 聚合结果
     */
    private void parsedCategoryAgg(List<SearchResult.CatalogVO> catalogVos, ParsedLongTerms aggregation) {
        List<? extends Terms.Bucket> buckets = aggregation.getBuckets();
        buckets.forEach(bucket -> {
            ParsedStringTerms catalogNameTerms = bucket.getAggregations().get("category_name_agg");
            List<? extends Terms.Bucket> bucketList = catalogNameTerms.getBuckets();
            // 获取商品分类id
            long catalogId = bucket.getKeyAsNumber().longValue();
            // 获取商品分类名称
            String catalogName = CollectionUtils.isNotEmpty(bucketList) ? bucketList.get(0).getKeyAsString() : "";
            // 封装商品分类数据
            SearchResult.CatalogVO catalogVO = new SearchResult.CatalogVO();
            catalogVO.setCatalogId(catalogId);
            catalogVO.setCatalogName(catalogName);
            catalogVos.add(catalogVO);
        });
    }
}
