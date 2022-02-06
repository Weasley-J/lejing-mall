let api = [];
const apiDocListSize = 1
api.push({
    name: 'default',
    order: '1',
    list: []
})
api[0].list.push({
    alias: 'HomeAdvController',
    order: '1',
    link: '首页轮播广告controller',
    desc: '首页轮播广告Controller',
    list: []
})
api[0].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/homeadv/list',
    desc: '查询首页轮播广告列表',
});
api[0].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/homeadv/info/{id}',
    desc: '获取首页轮播广告详情',
});
api[0].list[0].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/homeadv/save',
    desc: '新增首页轮播广告',
});
api[0].list[0].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/homeadv/update',
    desc: '修改首页轮播广告',
});
api[0].list[0].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/homeadv/delete/{ids}',
    desc: '批量删除首页轮播广告',
});
api[0].list.push({
    alias: 'SkuFullReductionController',
    order: '2',
    link: '商品满减信息controller',
    desc: '商品满减信息Controller',
    list: []
})
api[0].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/skufullreduction/list',
    desc: '查询商品满减信息列表',
});
api[0].list[1].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/skufullreduction/info/{id}',
    desc: '获取商品满减信息详情',
});
api[0].list[1].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/skufullreduction/save',
    desc: '新增商品满减信息',
});
api[0].list[1].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/skufullreduction/update',
    desc: '修改商品满减信息',
});
api[0].list[1].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/skufullreduction/delete/{ids}',
    desc: '批量删除商品满减信息',
});
api[0].list[1].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/skufullreduction/saveinfo',
    desc: '保存满减、优惠信息',
});
api[0].list.push({
    alias: 'SeckillSkuNoticeController',
    order: '3',
    link: '秒杀商品通知订阅controller',
    desc: '秒杀商品通知订阅Controller',
    list: []
})
api[0].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillskunotice/list',
    desc: '查询秒杀商品通知订阅列表',
});
api[0].list[2].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillskunotice/info/{id}',
    desc: '获取秒杀商品通知订阅详情',
});
api[0].list[2].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillskunotice/save',
    desc: '新增秒杀商品通知订阅',
});
api[0].list[2].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillskunotice/update',
    desc: '修改秒杀商品通知订阅',
});
api[0].list[2].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillskunotice/delete/{ids}',
    desc: '批量删除秒杀商品通知订阅',
});
api[0].list.push({
    alias: 'CouponSpuRelationController',
    order: '4',
    link: '优惠券与产品关联controller',
    desc: '优惠券与产品关联Controller',
    list: []
})
api[0].list[3].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/couponspurelation/list',
    desc: '查询优惠券与产品关联列表',
});
api[0].list[3].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/couponspurelation/info/{id}',
    desc: '获取优惠券与产品关联详情',
});
api[0].list[3].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/couponspurelation/save',
    desc: '新增优惠券与产品关联',
});
api[0].list[3].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/couponspurelation/update',
    desc: '修改优惠券与产品关联',
});
api[0].list[3].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/couponspurelation/delete/{ids}',
    desc: '批量删除优惠券与产品关联',
});
api[0].list.push({
    alias: 'CouponHistoryController',
    order: '5',
    link: '优惠券领取历史记录controller',
    desc: '优惠券领取历史记录Controller',
    list: []
})
api[0].list[4].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/couponhistory/list',
    desc: '查询优惠券领取历史记录列表',
});
api[0].list[4].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/couponhistory/info/{id}',
    desc: '获取优惠券领取历史记录详情',
});
api[0].list[4].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/couponhistory/save',
    desc: '新增优惠券领取历史记录',
});
api[0].list[4].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/couponhistory/update',
    desc: '修改优惠券领取历史记录',
});
api[0].list[4].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/couponhistory/delete/{ids}',
    desc: '批量删除优惠券领取历史记录',
});
api[0].list.push({
    alias: 'SeckillSkuRelationController',
    order: '6',
    link: '秒杀活动商品关联controller',
    desc: '秒杀活动商品关联Controller',
    list: []
})
api[0].list[5].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillskurelation/list',
    desc: '查询秒杀活动商品关联列表',
});
api[0].list[5].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillskurelation/info/{id}',
    desc: '获取秒杀活动商品关联详情',
});
api[0].list[5].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillskurelation/save',
    desc: '新增秒杀活动商品关联',
});
api[0].list[5].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillskurelation/update',
    desc: '修改秒杀活动商品关联',
});
api[0].list[5].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillskurelation/delete/{ids}',
    desc: '批量删除秒杀活动商品关联',
});
api[0].list.push({
    alias: 'UndoLogController',
    order: '7',
    link: '撤销日志表controller',
    desc: '撤销日志表Controller',
    list: []
})
api[0].list[6].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/undolog/list',
    desc: '查询撤销日志表列表',
});
api[0].list[6].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/undolog/info/{id}',
    desc: '获取撤销日志表详情',
});
api[0].list[6].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/undolog/save',
    desc: '新增撤销日志表',
});
api[0].list[6].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/undolog/update',
    desc: '修改撤销日志表',
});
api[0].list[6].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/undolog/delete/{ids}',
    desc: '批量删除撤销日志表',
});
api[0].list.push({
    alias: 'HomeSubjectController',
    order: '8',
    link: '首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】controller',
    desc: '首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】Controller',
    list: []
})
api[0].list[7].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/homesubject/list',
    desc: '查询首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】列表',
});
api[0].list[7].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/homesubject/info/{id}',
    desc: '获取首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】详情',
});
api[0].list[7].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/homesubject/save',
    desc: '新增首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】',
});
api[0].list[7].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/homesubject/update',
    desc: '修改首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】',
});
api[0].list[7].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/homesubject/delete/{ids}',
    desc: '批量删除首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】',
});
api[0].list.push({
    alias: 'SpuBoundsController',
    order: '9',
    link: '商品spu积分设置controller',
    desc: '商品spu积分设置Controller',
    list: []
})
api[0].list[8].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/spubounds/list',
    desc: '查询商品spu积分设置列表',
});
api[0].list[8].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/spubounds/info/{id}',
    desc: '获取商品spu积分设置详情',
});
api[0].list[8].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/spubounds/save',
    desc: '新增商品spu积分设置',
});
api[0].list[8].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/spubounds/update',
    desc: '修改商品spu积分设置',
});
api[0].list[8].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/spubounds/delete/{ids}',
    desc: '批量删除商品spu积分设置',
});
api[0].list.push({
    alias: 'CouponController',
    order: '10',
    link: '优惠券信息controller',
    desc: '优惠券信息Controller',
    list: []
})
api[0].list[9].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/coupon/list',
    desc: '查询优惠券信息列表',
});
api[0].list[9].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/coupon/info/{id}',
    desc: '获取优惠券信息详情',
});
api[0].list[9].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/coupon/save',
    desc: '新增优惠券信息',
});
api[0].list[9].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/coupon/update',
    desc: '修改优惠券信息',
});
api[0].list[9].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/coupon/delete/{ids}',
    desc: '批量删除优惠券信息',
});
api[0].list.push({
    alias: 'MemberPriceController',
    order: '11',
    link: '商品会员价格controller',
    desc: '商品会员价格Controller',
    list: []
})
api[0].list[10].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/memberprice/list',
    desc: '查询商品会员价格列表',
});
api[0].list[10].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/memberprice/info/{id}',
    desc: '获取商品会员价格详情',
});
api[0].list[10].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/memberprice/save',
    desc: '新增商品会员价格',
});
api[0].list[10].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/memberprice/update',
    desc: '修改商品会员价格',
});
api[0].list[10].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/memberprice/delete/{ids}',
    desc: '批量删除商品会员价格',
});
api[0].list.push({
    alias: 'SkuLadderController',
    order: '12',
    link: '商品阶梯价格controller',
    desc: '商品阶梯价格Controller',
    list: []
})
api[0].list[11].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/skuladder/list',
    desc: '查询商品阶梯价格列表',
});
api[0].list[11].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/skuladder/info/{id}',
    desc: '获取商品阶梯价格详情',
});
api[0].list[11].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/skuladder/save',
    desc: '新增商品阶梯价格',
});
api[0].list[11].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/skuladder/update',
    desc: '修改商品阶梯价格',
});
api[0].list[11].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/skuladder/delete/{ids}',
    desc: '批量删除商品阶梯价格',
});
api[0].list.push({
    alias: 'HomeSubjectSpuController',
    order: '13',
    link: '专题商品controller',
    desc: '专题商品Controller',
    list: []
})
api[0].list[12].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/homesubjectspu/list',
    desc: '查询专题商品列表',
});
api[0].list[12].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/homesubjectspu/info/{id}',
    desc: '获取专题商品详情',
});
api[0].list[12].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/homesubjectspu/save',
    desc: '新增专题商品',
});
api[0].list[12].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/homesubjectspu/update',
    desc: '修改专题商品',
});
api[0].list[12].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/homesubjectspu/delete/{ids}',
    desc: '批量删除专题商品',
});
api[0].list.push({
    alias: 'CouponSpuCategoryRelationController',
    order: '14',
    link: '优惠券分类关联controller',
    desc: '优惠券分类关联Controller',
    list: []
})
api[0].list[13].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/couponspucategoryrelation/list',
    desc: '查询优惠券分类关联列表',
});
api[0].list[13].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/couponspucategoryrelation/info/{id}',
    desc: '获取优惠券分类关联详情',
});
api[0].list[13].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/couponspucategoryrelation/save',
    desc: '新增优惠券分类关联',
});
api[0].list[13].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/couponspucategoryrelation/update',
    desc: '修改优惠券分类关联',
});
api[0].list[13].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/couponspucategoryrelation/delete/{ids}',
    desc: '批量删除优惠券分类关联',
});
api[0].list.push({
    alias: 'SeckillSessionController',
    order: '15',
    link: '秒杀活动场次controller',
    desc: '秒杀活动场次Controller',
    list: []
})
api[0].list[14].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillsession/latest/3days/seckill/session',
    desc: '获取最近3天的秒杀场次列表',
});
api[0].list[14].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillsession/list/no/args',
    desc: '查询秒杀活动场次列表',
});
api[0].list[14].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillsession/batch/update',
    desc: '批量更新',
});
api[0].list[14].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillsession/list',
    desc: '查询秒杀活动场次列表',
});
api[0].list[14].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillsession/info/{id}',
    desc: '获取秒杀活动场次详情',
});
api[0].list[14].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillsession/save',
    desc: '新增秒杀活动场次',
});
api[0].list[14].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillsession/update',
    desc: '修改秒杀活动场次',
});
api[0].list[14].list.push({
    order: '8',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillsession/delete/{ids}',
    desc: '批量删除秒杀活动场次',
});
api[0].list.push({
    alias: 'SeckillPromotionController',
    order: '16',
    link: '秒杀活动controller',
    desc: '秒杀活动Controller',
    list: []
})
api[0].list[15].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillpromotion/list',
    desc: '查询秒杀活动列表',
});
api[0].list[15].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillpromotion/info/{id}',
    desc: '获取秒杀活动详情',
});
api[0].list[15].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillpromotion/save',
    desc: '新增秒杀活动',
});
api[0].list[15].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillpromotion/update',
    desc: '修改秒杀活动',
});
api[0].list[15].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:7000/coupon/seckillpromotion/delete/{ids}',
    desc: '批量删除秒杀活动',
});
api[0].list.push({
    alias: 'error',
    order: '17',
    link: 'error_code_list',
    desc: '错误码列表',
    list: []
})
api[0].list.push({
    alias: 'dict',
    order: '18',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[0].list[17].list.push({
    order: '1',
    deprecated: 'false',
    url: '',
    desc: '响应http状态码字典',
});
api[0].list[17].list.push({
    order: '2',
    deprecated: 'false',
    url: '',
    desc: '验证码请求来源字典',
});
api[0].list[17].list.push({
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