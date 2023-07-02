package cn.alphahub.mall.product.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.Category;
import cn.alphahub.mall.product.mapper.CategoryMapper;
import cn.alphahub.mall.product.service.CategoryBrandRelationService;
import cn.alphahub.mall.product.service.CategoryService;
import cn.alphahub.mall.product.vo.SecondCategoryVO;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 商品三级分类Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@Slf4j
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * 商品三级分类Redis缓存数据的key前缀
     */
    public static final String KEY_PREFIX = "product:category";

    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private CategoryBrandRelationService categoryBrandRelationService;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 查询商品三级分类分页列表
     *
     * @param pageDomain 分页数据
     * @param category   分页对象
     * @return 商品三级分类分页数据
     */
    @Override
    public PageResult<Category> queryPage(PageDomain pageDomain, Category category) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>(category);
        PageResult<Category> pageResult = new PageResult<>();
        pageResult.startPage(pageDomain);
        List<Category> categoryList = this.list(wrapper);
        return pageResult.getPage(categoryList);
    }

    /**
     * 查出所有1级分类
     *
     * @return 1级分类
     */
    @Override
    @Cacheable(value = {KEY_PREFIX}, key = "#root.methodName", sync = true)
    public List<Category> getFirstLevelCategories() {
        long start = System.currentTimeMillis();
        Wrapper<Category> wrapper = new QueryWrapper<Category>().lambda().eq(Category::getParentCid, 0);
        List<Category> categories = this.baseMapper.selectList(wrapper);
        log.info("查出1级分类消耗时间：{} 毫秒", DateUtil.spendMs(start));
        return categories;
    }

    /**
     * <b>从Redis查出三级分类</b>
     * key-1级分类,value-2级分类List
     *
     * @return 一级分类+二级分类列表集合
     * @link <a href="https://github.com/redisson/redisson/wiki/8.-%E5%88%86%E5%B8%83%E5%BC%8F%E9%94%81%E5%92%8C%E5%90%8C%E6%AD%A5%E5%99%A8"></a>
     */
    @Override
    @Cacheable(value = {KEY_PREFIX}, key = "#root.methodName")
    public Map<String, List<SecondCategoryVO>> getAllLevelCategories() {
        Map<String, List<SecondCategoryVO>> categoryMap = new LinkedHashMap<>();
        // 获取分布式锁，细化锁的力度
        RLock lock = redissonClient.getLock(KEY_PREFIX + ":all-level-lock");
        lock.lock();
        // redis中取值
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String key = KEY_PREFIX + ":getAllLevelCategories";
        String value = operations.get(key);
        // 处理业务
        try {
            if (StringUtils.isEmpty(value)) {
                log.info("从数据库中获取三级分类数据......");
                categoryMap = getCatalogJsonFromDatabase();
                value = objectMapper.writeValueAsString(categoryMap);
                operations.set(key, value, 1, TimeUnit.DAYS);
            } else {
                log.info("从缓存中获取三级分类数据......");
                categoryMap = objectMapper.readValue(value, new TypeReference<>() {
                });
            }
        } catch (JsonProcessingException e) {
            log.error("JSON序列化异常, 异常信息：{}\n", e.getCause(), e);
        } finally {
            // 释放分布式锁
            lock.unlock();
        }
        log.info("三级分类列表集合 {}", JSONUtil.toJsonStr(categoryMap));
        return categoryMap;
    }


    /**
     * <b>从数据库查出三级分类</b>
     * key-1级分类,value-2级分类List
     *
     * @return 一级分类+二级分类列表集合
     */
    @Override
    public Map<String, List<SecondCategoryVO>> getCatalogJsonFromDatabase() {
        long start = System.currentTimeMillis();
        // 一次查出所有分类, 减少db查询次数
        List<Category> categoryList = baseMapper.selectList(null);
        // 查找所有1级分类
        List<Category> firstLevelCategories = getCategoriesByParentCid(categoryList, 0L);
        //封装数据
        Map<String, List<SecondCategoryVO>> map = firstLevelCategories.stream().collect(Collectors.toMap(key -> key.getCatId().toString(), value -> {
            // 遍历1级分类,查找1级分类子级-2级分类菜单
            List<Category> secondLevelCategories = getCategoriesByParentCid(categoryList, value.getCatId());
            List<SecondCategoryVO> secondCategoryVos = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(secondLevelCategories)) {
                // 遍历2级分类, 查找2级分类子级-3级分类
                secondCategoryVos = secondLevelCategories.stream().map(secondCategory -> {
                    // 查找3级分类
                    List<Category> thirdLevelCategories = getCategoriesByParentCid(categoryList, secondCategory.getCatId());
                    List<SecondCategoryVO.ThirdCategoryVO> thirdCategoryVos = new ArrayList<>();
                    // 封装3级分类
                    if (CollectionUtils.isNotEmpty(thirdLevelCategories)) {
                        thirdCategoryVos = thirdLevelCategories.stream().map(thirdCategory ->
                                SecondCategoryVO.ThirdCategoryVO.builder()
                                        .catalog2Id(secondCategory.getCatId())
                                        .id(thirdCategory.getCatId())
                                        .name(thirdCategory.getName())
                                        .build()).collect(Collectors.toList());
                    }
                    return SecondCategoryVO.builder()
                            .catalog1Id(value.getCatId())
                            .catalog3List(thirdCategoryVos)
                            .id(secondCategory.getCatId())
                            .name(secondCategory.getName())
                            .build();
                }).collect(Collectors.toList());
            }
            return secondCategoryVos;
        }));
        log.info("查出三级分类耗时: {} 毫秒", DateUtil.spendMs(start));
        return map;
    }

    /**
     * 获根据分类父id获取三级分类列表
     *
     * @param catId        当前分类id
     * @param categoryList 从这个列表里面获取分类父id
     * @return 分类列表
     */
    private List<Category> getCategoriesByParentCid(List<Category> categoryList, Long catId) {
        return categoryList.stream().filter(item -> Objects.equals(item.getParentCid(), catId)).collect(Collectors.toList());
    }

    /**
     * 查出所有分类及其子分类， 树形结构组装
     *
     * @return 分类及其子分类树形数据
     */
    @Override
    public List<Category> listWithTree() {
        // 1. 查出所有分类
        List<Category> categories = categoryMapper.selectList(null);
        // 一级类目
        // 2. 组装父子结构的数据
        return categories.stream()
                .filter(category -> category.getParentCid() == 0)
                .peek(menu -> menu.setChildren(getChildrenList(menu, categories)))
                .sorted(Comparator.comparingInt(Category::getSort)).collect(Collectors.toList());
    }

    @Override
    public boolean removeMenusByIds(List<Long> ids) {
        //TODO 1. 检查当前删除的菜单是否被别的地方引用
        int batchIds = categoryMapper.deleteBatchIds(ids);
        return batchIds >= 1;
    }

    /**
     * 找到catelogId所属分类的完整路径
     *
     * @param catelogId 所属分类id
     * @return [父, 子, 孙], [2,25,166]
     */
    @Override
    public Long[] getCategoryFullPath(Long catelogId) {
        List<Long> list = new ArrayList<>();
        List<Long> parentPath = getParentPath(catelogId, list);
        return parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 级联更新-商品三级分类
     *
     * @param category 商品三级分类,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    /*
    @Caching(evict = {
            @CacheEvict(value = {KEY_PREFIX}, key = "'getFirstLevelCategories'"),
            @CacheEvict(value = {KEY_PREFIX}, key = "'getAllLevelCategories'")
    })
    // 二选一
    @CacheEvict(value = {KEY_PREFIX}, allEntries = true)
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {KEY_PREFIX}, allEntries = true)
    public boolean updateCasecade(Category category) {
        boolean b1 = this.updateById(category);
        boolean b2 = this.categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        return b1 && b2;
    }

    /**
     * 递归方法找父路径
     *
     * @param catelogId 分类id
     * @return 分类id集合
     */
    private List<Long> getParentPath(Long catelogId, List<Long> initialList) {
        // 收集当前节点的id
        initialList.add(catelogId);
        Category category = this.getById(catelogId);
        if (!Objects.equals(category.getParentCid(), 0L)) {
            getParentPath(category.getParentCid(), initialList);
        }
        // 找到的是逆序的,使用Collections转换一下: [225,25,2] -> [2,25,225]
        Collections.reverse(initialList);
        return initialList;
    }

    /**
     * 递归查找所有菜单的子菜单
     *
     * @param rootCategory  当前菜单
     * @param allCategories 所有菜单
     * @return 菜单的子菜单
     */
    private List<Category> getChildrenList(@Nonnull Category rootCategory, @Nonnull List<Category> allCategories) {
        return allCategories.stream()
                .filter(category -> category.getParentCid().equals(rootCategory.getCatId()))
                //找子菜单
                .peek(category -> category.setChildren(getChildrenList(category, allCategories)))
                //菜单排序,sort越小越靠前
                .sorted(Comparator.comparingInt(value -> ObjectUtils.isNull(value.getSort()) ? 0 : value.getSort()))
                .collect(Collectors.toList());
    }
}
