let api = [];
const apiDocListSize = 1
api.push({
    name: 'default',
    order: '1',
    list: []
})
api[0].list.push({
    alias: 'AttrController',
    order: '1',
    link: '商品属性controller',
    desc: '商品属性Controller',
    list: []
})
api[0].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attr/base/listforspu/{spuId}',
    desc: '获取spu规格',
});
api[0].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attr/{attrType}/list/{catelogId}',
    desc: '查询属性base/sale list',
});
api[0].list[0].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attr/list',
    desc: '查询商品属性列表',
});
api[0].list[0].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attr/info/{attrId}',
    desc: '获取商品属性详情',
});
api[0].list[0].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attr/save',
    desc: '新增商品属性',
});
api[0].list[0].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attr/update',
    desc: '修改商品属性',
});
api[0].list[0].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attr/update/{spuId}',
    desc: '根据spuId修改商品属性',
});
api[0].list[0].list.push({
    order: '8',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attr/delete/{attrIds}',
    desc: '批量删除商品属性',
});
api[0].list.push({
    alias: 'SpuInfoController',
    order: '2',
    link: 'spu信息controller',
    desc: 'spu信息Controller',
    list: []
})
api[0].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spuinfo/list',
    desc: '查询spu信息列表',
});
api[0].list[1].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spuinfo/info/{id}',
    desc: '获取spu信息详情',
});
api[0].list[1].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spuinfo/save',
    desc: '新增spu信息',
});
api[0].list[1].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spuinfo/{spuId}/up',
    desc: '上架商品',
});
api[0].list[1].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spuinfo/update',
    desc: '修改spu信息',
});
api[0].list[1].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spuinfo/delete/{ids}',
    desc: '批量删除spu信息',
});
api[0].list.push({
    alias: 'AttrGroupController',
    order: '3',
    link: '属性分组controller',
    desc: '属性分组Controller',
    list: []
})
api[0].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attrgroup/{attrGroupId}/attr/relation',
    desc: '获取当属性分组id的关联关系',
});
api[0].list[2].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attrgroup/{catelogId}/withattr',
    desc: '获取分类下所有分组&关联属性',
});
api[0].list[2].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attrgroup/{attrGroupId}/noattr/relation',
    desc: '获取当分组id的没有关联关系的属性',
});
api[0].list[2].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attrgroup/list',
    desc: '查询属性分组列表',
});
api[0].list[2].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attrgroup/list/{catelogId}',
    desc: '根据catelogId查询属性分组列表',
});
api[0].list[2].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attrgroup/info/{attrGroupId}',
    desc: '获取属性分组详情',
});
api[0].list[2].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attrgroup/save',
    desc: '新增属性分组',
});
api[0].list[2].list.push({
    order: '8',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attrgroup/update',
    desc: '修改属性分组',
});
api[0].list[2].list.push({
    order: '9',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attrgroup/delete/{attrGroupIds}',
    desc: '批量删除属性分组',
});
api[0].list[2].list.push({
    order: '10',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attrgroup/attr/relation/delete',
    desc: '删除属性分组关联关系',
});
api[0].list[2].list.push({
    order: '11',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attrgroup/attr/relation',
    desc: '新增属性分组关联关系',
});
api[0].list.push({
    alias: 'ProductAttrValueController',
    order: '4',
    link: 'spu属性值controller',
    desc: 'spu属性值Controller',
    list: []
})
api[0].list[3].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/product/productattrvalue/list',
    desc: '查询spu属性值列表',
});
api[0].list[3].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/product/productattrvalue/info/{id}',
    desc: '获取spu属性值详情',
});
api[0].list[3].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/product/productattrvalue/save',
    desc: '新增spu属性值',
});
api[0].list[3].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/product/productattrvalue/update',
    desc: '修改spu属性值',
});
api[0].list[3].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:10000/product/productattrvalue/delete/{ids}',
    desc: '批量删除spu属性值',
});
api[0].list.push({
    alias: 'SpuCommentController',
    order: '5',
    link: '商品评价controller',
    desc: '商品评价Controller',
    list: []
})
api[0].list[4].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spucomment/list',
    desc: '查询商品评价列表',
});
api[0].list[4].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spucomment/info/{id}',
    desc: '获取商品评价详情',
});
api[0].list[4].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spucomment/save',
    desc: '新增商品评价',
});
api[0].list[4].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spucomment/update',
    desc: '修改商品评价',
});
api[0].list[4].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spucomment/delete/{ids}',
    desc: '批量删除商品评价',
});
api[0].list.push({
    alias: 'SkuInfoController',
    order: '6',
    link: 'sku信息controller',
    desc: 'sku信息Controller',
    list: []
})
api[0].list[5].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/product/skuinfo/list',
    desc: '查询sku信息列表',
});
api[0].list[5].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/product/skuinfo/info/{skuId}',
    desc: '获取sku信息详情',
});
api[0].list[5].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/product/skuinfo/save',
    desc: '新增sku信息',
});
api[0].list[5].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/product/skuinfo/update',
    desc: '修改sku信息',
});
api[0].list[5].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:10000/product/skuinfo/delete/{skuIds}',
    desc: '批量删除sku信息',
});
api[0].list.push({
    alias: 'BrandController',
    order: '7',
    link: '品牌controller',
    desc: '品牌Controller',
    list: []
})
api[0].list[6].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/product/brand/list',
    desc: '查询品牌列表',
});
api[0].list[6].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/product/brand/info/{brandId}',
    desc: '获取品牌详情',
});
api[0].list[6].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/product/brand/infos/{brandIds}',
    desc: '批量获取品牌信息',
});
api[0].list[6].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/product/brand/save',
    desc: '新增品牌',
});
api[0].list[6].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:10000/product/brand/update',
    desc: '修改品牌',
});
api[0].list[6].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:10000/product/brand/update/status',
    desc: '修改品牌状态',
});
api[0].list[6].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:10000/product/brand/delete/{brandIds}',
    desc: '批量删除品牌',
});
api[0].list.push({
    alias: 'UndoLogController',
    order: '8',
    link: '撤销日志表controller',
    desc: '撤销日志表Controller',
    list: []
})
api[0].list[7].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/product/undolog/list',
    desc: '查询撤销日志表列表',
});
api[0].list[7].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/product/undolog/info/{id}',
    desc: '获取撤销日志表详情',
});
api[0].list[7].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/product/undolog/save',
    desc: '新增撤销日志表',
});
api[0].list[7].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/product/undolog/update',
    desc: '修改撤销日志表',
});
api[0].list[7].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:10000/product/undolog/delete/{ids}',
    desc: '批量删除撤销日志表',
});
api[0].list.push({
    alias: 'CategoryBrandRelationController',
    order: '9',
    link: '品牌分类关联controller',
    desc: '品牌分类关联Controller',
    list: []
})
api[0].list[8].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/product/categorybrandrelation/brands/list',
    desc: '根据分类id获取品牌列表',
});
api[0].list[8].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/product/categorybrandrelation/list',
    desc: '查询品牌分类关联列表',
});
api[0].list[8].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/product/categorybrandrelation/catelog/list',
    desc: '获取当前品牌关联的分类列表',
});
api[0].list[8].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/product/categorybrandrelation/info/{id}',
    desc: '获取品牌分类关联详情',
});
api[0].list[8].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:10000/product/categorybrandrelation/save',
    desc: '新增品牌分类关联',
});
api[0].list[8].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:10000/product/categorybrandrelation/update',
    desc: '修改品牌分类关联',
});
api[0].list[8].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:10000/product/categorybrandrelation/delete/{ids}',
    desc: '批量删除品牌分类关联',
});
api[0].list.push({
    alias: 'CommentReplayController',
    order: '10',
    link: '商品评价回复关系controller',
    desc: '商品评价回复关系Controller',
    list: []
})
api[0].list[9].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/product/commentreplay/list',
    desc: '查询商品评价回复关系列表',
});
api[0].list[9].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/product/commentreplay/info/{id}',
    desc: '获取商品评价回复关系详情',
});
api[0].list[9].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/product/commentreplay/save',
    desc: '新增商品评价回复关系',
});
api[0].list[9].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/product/commentreplay/update',
    desc: '修改商品评价回复关系',
});
api[0].list[9].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:10000/product/commentreplay/delete/{ids}',
    desc: '批量删除商品评价回复关系',
});
api[0].list.push({
    alias: 'SkuSaleAttrValueController',
    order: '11',
    link: 'sku销售属性&值controller',
    desc: 'sku销售属性&值Controller',
    list: []
})
api[0].list[10].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/product/skusaleattrvalue/list',
    desc: '查询sku销售属性&值列表',
});
api[0].list[10].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/product/skusaleattrvalue/skuAttrValues/{skuId}',
    desc: '根据skuId获取商品的销售属性',
});
api[0].list[10].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/product/skusaleattrvalue/info/{id}',
    desc: '获取sku销售属性&值详情',
});
api[0].list[10].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/product/skusaleattrvalue/save',
    desc: '新增sku销售属性&值',
});
api[0].list[10].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:10000/product/skusaleattrvalue/update',
    desc: '修改sku销售属性&值',
});
api[0].list[10].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:10000/product/skusaleattrvalue/delete/{ids}',
    desc: '批量删除sku销售属性&值',
});
api[0].list.push({
    alias: 'CategoryController',
    order: '12',
    link: '商品三级分类controller',
    desc: '商品三级分类Controller',
    list: []
})
api[0].list[11].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/product/category/list',
    desc: '查询商品三级分类列表',
});
api[0].list[11].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/product/category/list/tree',
    desc: '查出所有分类及其子分类， 树形结构组装',
});
api[0].list[11].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/product/category/info/{catId}',
    desc: '获取商品三级分类详情',
});
api[0].list[11].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/product/category/save',
    desc: '新增商品三级分类',
});
api[0].list[11].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:10000/product/category/update',
    desc: '级联更新-商品三级分类',
});
api[0].list[11].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:10000/product/category/update/sort',
    desc: '批量修改三级分类排序',
});
api[0].list[11].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:10000/product/category/delete/{catIds}',
    desc: '批量删除商品三级分类',
});
api[0].list.push({
    alias: 'SpuImagesController',
    order: '13',
    link: 'spu图片controller',
    desc: 'spu图片Controller',
    list: []
})
api[0].list[12].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spuimages/list',
    desc: '查询spu图片列表',
});
api[0].list[12].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spuimages/info/{id}',
    desc: '获取spu图片详情',
});
api[0].list[12].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spuimages/save',
    desc: '新增spu图片',
});
api[0].list[12].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spuimages/update',
    desc: '修改spu图片',
});
api[0].list[12].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spuimages/delete/{ids}',
    desc: '批量删除spu图片',
});
api[0].list.push({
    alias: 'SkuImagesController',
    order: '14',
    link: 'sku图片controller',
    desc: 'sku图片Controller',
    list: []
})
api[0].list[13].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/product/skuimages/list',
    desc: '查询sku图片列表',
});
api[0].list[13].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/product/skuimages/info/{id}',
    desc: '获取sku图片详情',
});
api[0].list[13].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/product/skuimages/save',
    desc: '新增sku图片',
});
api[0].list[13].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/product/skuimages/update',
    desc: '修改sku图片',
});
api[0].list[13].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:10000/product/skuimages/delete/{ids}',
    desc: '批量删除sku图片',
});
api[0].list.push({
    alias: 'AttrAttrgroupRelationController',
    order: '15',
    link: '属性&属性分组关联controller',
    desc: '属性&属性分组关联Controller',
    list: []
})
api[0].list[14].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attrattrgrouprelation/list',
    desc: '查询属性&属性分组关联列表',
});
api[0].list[14].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attrattrgrouprelation/info/{id}',
    desc: '获取属性&属性分组关联详情',
});
api[0].list[14].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attrattrgrouprelation/save',
    desc: '新增属性&属性分组关联',
});
api[0].list[14].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attrattrgrouprelation/update',
    desc: '修改属性&属性分组关联',
});
api[0].list[14].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:10000/product/attrattrgrouprelation/delete/{ids}',
    desc: '批量删除属性&属性分组关联',
});
api[0].list.push({
    alias: 'SpuInfoDescController',
    order: '16',
    link: 'spu信息介绍controller',
    desc: 'spu信息介绍Controller',
    list: []
})
api[0].list[15].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spuinfodesc/list',
    desc: '查询spu信息介绍列表',
});
api[0].list[15].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spuinfodesc/info/{spuId}',
    desc: '获取spu信息介绍详情',
});
api[0].list[15].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spuinfodesc/save',
    desc: '新增spu信息介绍',
});
api[0].list[15].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spuinfodesc/update',
    desc: '修改spu信息介绍',
});
api[0].list[15].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:10000/product/spuinfodesc/delete/{spuIds}',
    desc: '批量删除spu信息介绍',
});
api[0].list.push({
    alias: 'IndexController',
    order: '17',
    link: '首页controller',
    desc: '首页Controller',
    list: []
})
api[0].list[16].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/;	http:/localhost:10000/index;	http:/localhost:10000/index.html',
    desc: '进入首页返回所有1级分类',
});
api[0].list[16].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/api;	http:/localhost:10000/api.html',
    desc: 'api视图',
});
api[0].list[16].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:10000/index/catalog.json',
    desc: '&lt;b&gt;查出三级分类&lt;/b&gt; key-1级分类,value-2级分类List',
});
api[0].list[16].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:10000/hello',
    desc: '你好，Weasley J！',
});
api[0].list.push({
    alias: 'ItemController',
    order: '18',
    link: '商品sku详情页controller',
    desc: '商品sku详情页Controller',
    list: []
})
api[0].list[17].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:10000/{skuId}.html',
    desc: '根据skuId获取商品详情页',
});
api[0].list[17].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:10000/item/{skuId}',
    desc: '根据skuId获取商品详情',
});
api[0].list.push({
    alias: 'error',
    order: '19',
    link: 'error_code_list',
    desc: '错误码列表',
    list: []
})
api[0].list.push({
    alias: 'dict',
    order: '20',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[0].list[19].list.push({
    order: '1',
    deprecated: 'false',
    url: '',
    desc: '响应http状态码字典',
});
api[0].list[19].list.push({
    order: '2',
    deprecated: 'false',
    url: '',
    desc: '验证码请求来源字典',
});
api[0].list[19].list.push({
    order: '3',
    deprecated: 'false',
    url: '',
    desc: '电子入场券状态字典',
});
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        const search = document.getElementById('search');
        const searchValue = search.value.toLocaleLowerCase();

        let searchGroup = [];
        for (let i = 0; i < api.length; i++) {

            let apiGroup = api[i];

            let searchArr = [];
            for (let i = 0; i < apiGroup.list.length; i++) {
                let apiData = apiGroup.list[i];
                const desc = apiData.desc;
                if (desc.toLocaleLowerCase().indexOf(searchValue) > -1) {
                    searchArr.push({
                        order: apiData.order,
                        desc: apiData.desc,
                        link: apiData.link,
                        list: apiData.list
                    });
                } else {
                    let methodList = apiData.list || [];
                    let methodListTemp = [];
                    for (let j = 0; j < methodList.length; j++) {
                        const methodData = methodList[j];
                        const methodDesc = methodData.desc;
                        if (methodDesc.toLocaleLowerCase().indexOf(searchValue) > -1) {
                            methodListTemp.push(methodData);
                            break;
                        }
                    }
                    if (methodListTemp.length > 0) {
                        const data = {
                            order: apiData.order,
                            desc: apiData.desc,
                            link: apiData.link,
                            list: methodListTemp
                        };
                        searchArr.push(data);
                    }
                }
            }
            if (apiGroup.name.toLocaleLowerCase().indexOf(searchValue) > -1) {
                searchGroup.push({
                    name: apiGroup.name,
                    order: apiGroup.order,
                    list: searchArr
                });
                continue;
            }
            if (searchArr.length === 0) {
                continue;
            }
            searchGroup.push({
                name: apiGroup.name,
                order: apiGroup.order,
                list: searchArr
            });
        }
        let html;
        if (searchValue == '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchGroup,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiGroups, liClass, display) {
    let html = "";
    let doc;
    if (apiGroups.length > 0) {
         if (apiDocListSize == 1) {
            let apiData = apiGroups[0].list;
            for (let j = 0; j < apiData.length; j++) {
                html += '<li class="'+liClass+'">';
                html += '<a class="dd" href="#_' + apiData[j].link + '">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
                html += '<ul class="sectlevel2" style="'+display+'">';
                doc = apiData[j].list;
                for (let m = 0; m < doc.length; m++) {
                    let spanString;
                    if (doc[m].deprecated == 'true') {
                        spanString='<span class="line-through">';
                    } else {
                        spanString='<span>';
                    }
                    html += '<li><a href="#_1_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + spanString + doc[m].desc + '<span></a> </li>';
                }
                html += '</ul>';
                html += '</li>';
            }
        } else {
            for (let i = 0; i < apiGroups.length; i++) {
                let apiGroup = apiGroups[i];
                html += '<li class="'+liClass+'">';
                html += '<a class="dd" href="#_' + apiGroup.name + '">' + apiGroup.order + '.&nbsp;' + apiGroup.name + '</a>';
                html += '<ul class="sectlevel1">';

                let apiData = apiGroup.list;
                for (let j = 0; j < apiData.length; j++) {
                    html += '<li class="'+liClass+'">';
                    html += '<a class="dd" href="#_'+apiGroup.order+'_'+ apiData[j].order + '_'+ apiData[j].link + '">' +apiGroup.order+'.'+ apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
                    html += '<ul class="sectlevel2" style="'+display+'">';
                    doc = apiData[j].list;
                    for (let m = 0; m < doc.length; m++) {
                       let spanString;
                       if (doc[m].deprecated == 'true') {
                           spanString='<span class="line-through">';
                       } else {
                           spanString='<span>';
                       }
                       html += '<li><a href="#_'+apiGroup.order+'_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">'+apiGroup.order+'.' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + spanString + doc[m].desc + '<span></a> </li>';
                   }
                    html += '</ul>';
                    html += '</li>';
                }

                html += '</ul>';
                html += '</li>';
            }
        }
    }
    return html;
}