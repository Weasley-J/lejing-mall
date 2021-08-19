let api = [];
api.push({
    alias: 'AttrAttrgroupRelationController',
    order: '1',
    link: '属性&属性分组关联controller',
    desc: '属性&属性分组关联Controller',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '查询属性&属性分组关联列表',
});
api[0].list.push({
    order: '2',
    desc: '获取属性&属性分组关联详情',
});
api[0].list.push({
    order: '3',
    desc: '新增属性&属性分组关联',
});
api[0].list.push({
    order: '4',
    desc: '修改属性&属性分组关联',
});
api[0].list.push({
    order: '5',
    desc: '批量删除属性&属性分组关联',
});
api.push({
    alias: 'AttrController',
    order: '2',
    link: '商品属性controller',
    desc: '商品属性Controller',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '获取spu规格',
});
api[1].list.push({
    order: '2',
    desc: '查询属性base/salelist',
});
api[1].list.push({
    order: '3',
    desc: '查询商品属性列表',
});
api[1].list.push({
    order: '4',
    desc: '获取商品属性详情',
});
api[1].list.push({
    order: '5',
    desc: '新增商品属性',
});
api[1].list.push({
    order: '6',
    desc: '修改商品属性',
});
api[1].list.push({
    order: '7',
    desc: '根据spuId修改商品属性',
});
api[1].list.push({
    order: '8',
    desc: '批量删除商品属性',
});
api.push({
    alias: 'AttrGroupController',
    order: '3',
    link: '属性分组controller',
    desc: '属性分组Controller',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '获取当属性分组id的关联关系',
});
api[2].list.push({
    order: '2',
    desc: '获取分类下所有分组&关联属性',
});
api[2].list.push({
    order: '3',
    desc: '获取当分组id的没有关联关系的属性',
});
api[2].list.push({
    order: '4',
    desc: '查询属性分组列表',
});
api[2].list.push({
    order: '5',
    desc: '根据catelogId查询属性分组列表',
});
api[2].list.push({
    order: '6',
    desc: '获取属性分组详情',
});
api[2].list.push({
    order: '7',
    desc: '新增属性分组',
});
api[2].list.push({
    order: '8',
    desc: '修改属性分组',
});
api[2].list.push({
    order: '9',
    desc: '批量删除属性分组',
});
api[2].list.push({
    order: '10',
    desc: '删除属性分组关联关系',
});
api[2].list.push({
    order: '11',
    desc: '新增属性分组关联关系',
});
api.push({
    alias: 'BrandController',
    order: '4',
    link: '品牌controller',
    desc: '品牌Controller',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '查询品牌列表',
});
api[3].list.push({
    order: '2',
    desc: '获取品牌详情',
});
api[3].list.push({
    order: '3',
    desc: '批量获取品牌信息',
});
api[3].list.push({
    order: '4',
    desc: '新增品牌',
});
api[3].list.push({
    order: '5',
    desc: '修改品牌',
});
api[3].list.push({
    order: '6',
    desc: '修改品牌状态',
});
api[3].list.push({
    order: '7',
    desc: '批量删除品牌',
});
api.push({
    alias: 'CategoryBrandRelationController',
    order: '5',
    link: '品牌分类关联controller',
    desc: '品牌分类关联Controller',
    list: []
})
api[4].list.push({
    order: '1',
    desc: '根据分类id获取品牌列表',
});
api[4].list.push({
    order: '2',
    desc: '查询品牌分类关联列表',
});
api[4].list.push({
    order: '3',
    desc: '获取当前品牌关联的分类列表',
});
api[4].list.push({
    order: '4',
    desc: '获取品牌分类关联详情',
});
api[4].list.push({
    order: '5',
    desc: '新增品牌分类关联',
});
api[4].list.push({
    order: '6',
    desc: '修改品牌分类关联',
});
api[4].list.push({
    order: '7',
    desc: '批量删除品牌分类关联',
});
api.push({
    alias: 'CategoryController',
    order: '6',
    link: '商品三级分类controller',
    desc: '商品三级分类Controller',
    list: []
})
api[5].list.push({
    order: '1',
    desc: '查询商品三级分类列表',
});
api[5].list.push({
    order: '2',
    desc: '查出所有分类及其子分类，树形结构组装',
});
api[5].list.push({
    order: '3',
    desc: '获取商品三级分类详情',
});
api[5].list.push({
    order: '4',
    desc: '新增商品三级分类',
});
api[5].list.push({
    order: '5',
    desc: '级联更新-商品三级分类',
});
api[5].list.push({
    order: '6',
    desc: '批量修改三级分类排序',
});
api[5].list.push({
    order: '7',
    desc: '批量删除商品三级分类',
});
api.push({
    alias: 'CommentReplayController',
    order: '7',
    link: '商品评价回复关系controller',
    desc: '商品评价回复关系Controller',
    list: []
})
api[6].list.push({
    order: '1',
    desc: '查询商品评价回复关系列表',
});
api[6].list.push({
    order: '2',
    desc: '获取商品评价回复关系详情',
});
api[6].list.push({
    order: '3',
    desc: '新增商品评价回复关系',
});
api[6].list.push({
    order: '4',
    desc: '修改商品评价回复关系',
});
api[6].list.push({
    order: '5',
    desc: '批量删除商品评价回复关系',
});
api.push({
    alias: 'ProductAttrValueController',
    order: '8',
    link: 'spu属性值controller',
    desc: 'spu属性值Controller',
    list: []
})
api[7].list.push({
    order: '1',
    desc: '查询spu属性值列表',
});
api[7].list.push({
    order: '2',
    desc: '获取spu属性值详情',
});
api[7].list.push({
    order: '3',
    desc: '新增spu属性值',
});
api[7].list.push({
    order: '4',
    desc: '修改spu属性值',
});
api[7].list.push({
    order: '5',
    desc: '批量删除spu属性值',
});
api.push({
    alias: 'SkuImagesController',
    order: '9',
    link: 'sku图片controller',
    desc: 'sku图片Controller',
    list: []
})
api[8].list.push({
    order: '1',
    desc: '查询sku图片列表',
});
api[8].list.push({
    order: '2',
    desc: '获取sku图片详情',
});
api[8].list.push({
    order: '3',
    desc: '新增sku图片',
});
api[8].list.push({
    order: '4',
    desc: '修改sku图片',
});
api[8].list.push({
    order: '5',
    desc: '批量删除sku图片',
});
api.push({
    alias: 'SkuInfoController',
    order: '10',
    link: 'sku信息controller',
    desc: 'sku信息Controller',
    list: []
})
api[9].list.push({
    order: '1',
    desc: '查询sku信息列表',
});
api[9].list.push({
    order: '2',
    desc: '获取sku信息详情',
});
api[9].list.push({
    order: '3',
    desc: '新增sku信息',
});
api[9].list.push({
    order: '4',
    desc: '修改sku信息',
});
api[9].list.push({
    order: '5',
    desc: '批量删除sku信息',
});
api.push({
    alias: 'SkuSaleAttrValueController',
    order: '11',
    link: 'sku销售属性&值controller',
    desc: 'sku销售属性&值Controller',
    list: []
})
api[10].list.push({
    order: '1',
    desc: '查询sku销售属性&值列表',
});
api[10].list.push({
    order: '2',
    desc: '根据skuId获取商品的销售属性',
});
api[10].list.push({
    order: '3',
    desc: '获取sku销售属性&值详情',
});
api[10].list.push({
    order: '4',
    desc: '新增sku销售属性&值',
});
api[10].list.push({
    order: '5',
    desc: '修改sku销售属性&值',
});
api[10].list.push({
    order: '6',
    desc: '批量删除sku销售属性&值',
});
api.push({
    alias: 'SpuCommentController',
    order: '12',
    link: '商品评价controller',
    desc: '商品评价Controller',
    list: []
})
api[11].list.push({
    order: '1',
    desc: '查询商品评价列表',
});
api[11].list.push({
    order: '2',
    desc: '获取商品评价详情',
});
api[11].list.push({
    order: '3',
    desc: '新增商品评价',
});
api[11].list.push({
    order: '4',
    desc: '修改商品评价',
});
api[11].list.push({
    order: '5',
    desc: '批量删除商品评价',
});
api.push({
    alias: 'SpuImagesController',
    order: '13',
    link: 'spu图片controller',
    desc: 'spu图片Controller',
    list: []
})
api[12].list.push({
    order: '1',
    desc: '查询spu图片列表',
});
api[12].list.push({
    order: '2',
    desc: '获取spu图片详情',
});
api[12].list.push({
    order: '3',
    desc: '新增spu图片',
});
api[12].list.push({
    order: '4',
    desc: '修改spu图片',
});
api[12].list.push({
    order: '5',
    desc: '批量删除spu图片',
});
api.push({
    alias: 'SpuInfoController',
    order: '14',
    link: 'spu信息controller',
    desc: 'spu信息Controller',
    list: []
})
api[13].list.push({
    order: '1',
    desc: '查询spu信息列表',
});
api[13].list.push({
    order: '2',
    desc: '获取spu信息详情',
});
api[13].list.push({
    order: '3',
    desc: '新增spu信息',
});
api[13].list.push({
    order: '4',
    desc: '上架商品',
});
api[13].list.push({
    order: '5',
    desc: '修改spu信息',
});
api[13].list.push({
    order: '6',
    desc: '批量删除spu信息',
});
api.push({
    alias: 'SpuInfoDescController',
    order: '15',
    link: 'spu信息介绍controller',
    desc: 'spu信息介绍Controller',
    list: []
})
api[14].list.push({
    order: '1',
    desc: '查询spu信息介绍列表',
});
api[14].list.push({
    order: '2',
    desc: '获取spu信息介绍详情',
});
api[14].list.push({
    order: '3',
    desc: '新增spu信息介绍',
});
api[14].list.push({
    order: '4',
    desc: '修改spu信息介绍',
});
api[14].list.push({
    order: '5',
    desc: '批量删除spu信息介绍',
});
api.push({
    alias: 'UndoLogController',
    order: '16',
    link: '撤销日志表controller',
    desc: '撤销日志表Controller',
    list: []
})
api[15].list.push({
    order: '1',
    desc: '查询撤销日志表列表',
});
api[15].list.push({
    order: '2',
    desc: '获取撤销日志表详情',
});
api[15].list.push({
    order: '3',
    desc: '新增撤销日志表',
});
api[15].list.push({
    order: '4',
    desc: '修改撤销日志表',
});
api[15].list.push({
    order: '5',
    desc: '批量删除撤销日志表',
});
api.push({
    alias: 'IndexController',
    order: '17',
    link: '首页controller',
    desc: '首页Controller',
    list: []
})
api[16].list.push({
    order: '1',
    desc: '进入首页返回所有1级分类',
});
api[16].list.push({
    order: '2',
    desc: 'api视图',
});
api[16].list.push({
    order: '3',
    desc: '&lt;b&gt;查出三级分类&lt;/b&gt;key-1级分类,value-2级分类List',
});
api[16].list.push({
    order: '4',
    desc: '你好，WeasleyJ！',
});
api.push({
    alias: 'ItemController',
    order: '18',
    link: '商品sku详情页controller',
    desc: '商品sku详情页Controller',
    list: []
})
api[17].list.push({
    order: '1',
    desc: '根据skuId获取商品详情页',
});
api[17].list.push({
    order: '2',
    desc: '根据skuId获取商品详情',
});
api.push({
    alias: 'error',
    order: '19',
    link: 'error_code_list',
    desc: '错误码列表',
    list: []
})
api.push({
    alias: 'dict',
    order: '20',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[19].list.push({
    order: '1',
    desc: '响应http状态码字典',
});
api[19].list.push({
    order: '2',
    desc: '验证码请求来源字典',
});
api[19].list.push({
    order: '3',
    desc: '电子入场券状态字典',
});
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchArr = [];
        for (let i = 0; i < api.length; i++) {
            let apiData = api[i];
            const desc = apiData.desc;
            if (desc.indexOf(searchValue) > -1) {
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
                    if (methodDesc.indexOf(searchValue) > -1) {
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
        let html;
        if (searchValue == '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchArr,liClass,display);
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

function buildAccordion(apiData, liClass, display) {
    let html = "";
    let doc;
    if (apiData.length > 0) {
        for (let j = 0; j < apiData.length; j++) {
            html += '<li class="'+liClass+'">';
            html += '<a class="dd" href="#_' + apiData[j].link + '">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
            html += '<ul class="sectlevel2" style="'+display+'">';
            doc = apiData[j].list;
            for (let m = 0; m < doc.length; m++) {
                html += '<li><a href="#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + doc[m].desc + '</a> </li>';
            }
            html += '</ul>';
            html += '</li>';
        }
    }
    return html;
}