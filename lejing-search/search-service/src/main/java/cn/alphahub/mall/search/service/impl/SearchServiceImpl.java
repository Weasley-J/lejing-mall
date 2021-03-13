package cn.alphahub.mall.search.service.impl;

import cn.alphahub.common.reflect.ReflectUtil;
import cn.alphahub.mall.search.domain.SkuModel;
import cn.alphahub.mall.search.pojo.SearchParam;
import cn.alphahub.mall.search.pojo.SearchResult;
import cn.alphahub.mall.search.repository.ProductRepository;
import cn.alphahub.mall.search.service.SearchService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
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
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
     * 索引名称
     */
    @Value("${spring.elasticsearch.rest.index-names}")
    private String[] indexNames;

    @Resource
    private ProductRepository repository;
    @Resource
    private ElasticsearchRestTemplate restTemplate;

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
        System.out.println("\n");
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
        // 2 获取Elasticsearch QueryBuilder instances
        NativeSearchQuery nativeSearchQuery = this.buildNativeSearchQuery(param);

        // 3. 从ES从查询数据, 封装查询结果返回
        return this.buildSearchResult(nativeSearchQuery);
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

        // 2 通过sourceFilter设置返回的结果字段,我们只需要id、skus、subTitle
        /*
        SourceFilter sourceFilter2 = new FetchSourceFilterBuilder().withIncludes("id", "skus", "subTitle").withExcludes().build();
        searchQueryBuilder.withSourceFilter(sourceFilter2);
        */

        // 3 添加分类和商品聚合
        /*
        String categoryAggName = "categories", brandAggName = "brands";
        searchQueryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        searchQueryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));
        */

        // 4 排序
        if (StringUtils.isNotBlank(param.getSort())) {
            String[] sortArray = StringUtils.split(param.getSort(), "_");
            SortOrder order = StringUtils.equalsIgnoreCase(sortArray[1], SortOrder.ASC.toString()) ? SortOrder.ASC : SortOrder.DESC;
            FieldSortBuilder sortBuilder = SortBuilders.fieldSort(sortArray[0]).order(order);
            searchQueryBuilder.withSort(sortBuilder);
        }

        // 5 分页, 分页页码默认从0开始
        int page = 1, size = 10;
        searchQueryBuilder.withPageable(PageRequest.of(page - 1, size));

        // 6 高亮字段
        if (StringUtils.isNotBlank(param.getKeyword())) {
            searchQueryBuilder.withHighlightBuilder(buildHighlightBuilder());
        }

        NativeSearchQuery nativeSearchQuery = searchQueryBuilder.build();
        QueryBuilder queryBuilder = nativeSearchQuery.getQuery();
        log.info("ES DQL查询语句:\n {}", queryBuilder);

        return nativeSearchQuery;
    }

    /**
     * <p>构建高亮字段</p>
     * <em>Settings can control how large fields are summarized to show only selected snippets ("fragments") containing search terms.</em>
     *
     * @return 搜索突出显示的高亮构建器
     */
    private HighlightBuilder buildHighlightBuilder() {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.field(ReflectUtil.propertyName(SkuModel::getSkuTitle));
        highlightBuilder.postTags("</span>");
        return highlightBuilder;
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
            boolQuery.must(QueryBuilders.matchQuery(ReflectUtil.propertyName(SkuModel::getSkuTitle), param.getKeyword()).operator(Operator.AND));
        }
        // 1.2 filter - 3级分类id查询(catalogId)
        if (ObjectUtils.isNotEmpty(param.getCatalog3Id())) {
            boolQuery.filter(QueryBuilders.termQuery(ReflectUtil.propertyName(SkuModel::getCatalogId), param.getCatalog3Id()));
        }
        // 1.3 filter - 品牌id查询(brandId)
        if (ObjectUtils.isNotEmpty(param.getBrandId())) {
            boolQuery.filter(QueryBuilders.termQuery(ReflectUtil.propertyName(SkuModel::getBrandId), param.getBrandId()));
        }
        // 1.4 filter - 是否有库存查询(hasStock)
        Integer hasStock = param.getHasStock();
        if (ObjectUtils.isNotEmpty(hasStock)) {
            boolQuery.filter(QueryBuilders.termQuery(ReflectUtil.propertyName(SkuModel::getHasStock), Objects.equals(hasStock, 1)));
        }

        // 1.5 filter - 商品属性查询(attrs)
        List<String> attrList = param.getAttrs();
        if (CollectionUtils.isNotEmpty(attrList)) {
            String attrsPropertyName = ReflectUtil.propertyName(SkuModel::getAttrs);
            for (String attr : attrList) {
                // 切割属性: attr = "attrs=1_5寸:8寸&attrs=3_4核:8核&attrs=3_8G:12G"
                String[] attrArray = attr.split("_");
                String attrId = attrArray[0];
                String[] attrValues = attrArray[1].split(":");
                // 构建nested查询构造条件
                BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
                nestedBoolQuery.must(QueryBuilders.termQuery(attrsPropertyName + "." + ReflectUtil.propertyName(SkuModel.Attrs::getAttrId), attrId));
                nestedBoolQuery.must(QueryBuilders.termQuery(attrsPropertyName + "." + ReflectUtil.propertyName(SkuModel.Attrs::getAttrValue), attrValues));
                // 每个attr都必须生成一个与之对应的nested查询条件
                NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery(attrsPropertyName, nestedBoolQuery, ScoreMode.None);
                boolQuery.filter(nestedQuery);
            }
        }

        // 1.6 filter - 价格区间查询(skuPrice): 前台传来格式: 1_500 | _500 | 500_
        String skuPrice = param.getSkuPrice();
        if (StringUtils.isNotBlank(skuPrice)) {
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(ReflectUtil.propertyName(SkuModel::getSkuPrice));
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
    private SearchResult buildSearchResult(NativeSearchQuery nativeSearchQuery) {
        // SearchHits<SearchResult> searchHits = restTemplate.search(nativeSearchQuery, SearchResult.class, IndexCoordinates.of(indexNames));
        return null;
    }
}
