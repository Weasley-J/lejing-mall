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
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilterBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedHashSet;
import java.util.List;
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
        NativeSearchQuery nativeSearchQuery = buildNativeSearchQuery(param);

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
        // 自定查询构建器
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();

        // 1、对key进行全文检索查询
        // QueryBuilder basicQuery = QueryBuilders.matchQuery("all", key).operator(Operator.AND);
        BoolQueryBuilder basicQuery = buildBoolSearchQuery(param);
        searchQueryBuilder.withQuery(null);

        //2、通过sourceFilter设置返回的结果字段,我们只需要id、skus、subTitle
        SourceFilter sourceFilter2 = new FetchSourceFilterBuilder().withIncludes("id", "skus", "subTitle").withExcludes().build();
        searchQueryBuilder.withSourceFilter(sourceFilter2);

        //2.1 可以是多个高亮字段
        searchQueryBuilder.withHighlightFields(
                new HighlightBuilder.Field("all").preTags("<span style='color:red'>").postTags("</span>")
        );

        //3.1 分页, 分页页码默认从0开始
        Integer page = 1;
        Integer size = 2;
        searchQueryBuilder.withPageable(PageRequest.of(page - 1, size));

        //3.2 添加分类和商品聚合
        String categoryAggName = "categories", brandAggName = "brands";
        searchQueryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        searchQueryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));

        //3.3 排序
        boolean desc = false;
        searchQueryBuilder.withSort(SortBuilders.fieldSort(null).order(desc ? SortOrder.DESC : SortOrder.ASC));

        NativeSearchQuery nativeSearchQuery = searchQueryBuilder.build();
        QueryBuilder queryBuilder = nativeSearchQuery.getQuery();
        log.info("ES DQL查询语句:\n {}", queryBuilder);
        return nativeSearchQuery;
    }

    /**
     * 根据请求参数封装构建布尔查询构建器
     *
     * @param param 搜索结果页实体对象
     * @return BoolQueryBuilder 布尔查询构建器
     */
    private BoolQueryBuilder buildBoolSearchQuery(SearchParam param) {
        String keyword = param.getKeyword();
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        // 查询关键字
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery(ReflectUtil.propertyName(SkuModel::getSkuTitle), keyword).operator(Operator.AND));
        /*param.getFilter().forEach((k, v) -> {
            System.out.println(k + ":" + v);
            // 搜索的过滤字段
            switch (k) {
                case "品牌":
                    k = "brandId";
                    break;
                case "分类":
                    k = "cid3";
                    break;
                default:
                    k = "specs." + k + ".keyword";
                    break;
            }
            boolQuery.filter(QueryBuilders.termQuery(k, v));
        });*/
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
        SearchHits<SearchResult> searchHits = restTemplate.search(nativeSearchQuery, SearchResult.class, IndexCoordinates.of(indexNames));
        return null;
    }
}
