let api = [];
api.push({
    alias: 'AppSiteReserveController',
    order: '1',
    link: 'app场地预约controller',
    desc: 'App场地预约Controller',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '预约场地-分页（当前往后推7日）',
});
api[0].list.push({
    order: '2',
    desc: '获取XX场地的7日内预约详情',
});
api[0].list.push({
    order: '3',
    desc: '获取场地yyyy-MM-dd号的场次信息',
});
api[0].list.push({
    order: '4',
    desc: '确认订单提交预约',
});
api[0].list.push({
    order: '5',
    desc: '场地预约订单列表-分页',
});
api[0].list.push({
    order: '6',
    desc: '查询订单详情',
});
api[0].list.push({
    order: '7',
    desc: '查看入场券',
});
api[0].list.push({
    order: '8',
    desc: '下载入场券',
});
api[0].list.push({
    order: '9',
    desc: '申请退款',
});
api[0].list.push({
    order: '10',
    desc: '退款详情',
});
api.push({
    alias: 'OrderMasterController',
    order: '2',
    link: '主订单表controller',
    desc: '主订单表Controller',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '查询主订单表列表',
});
api[1].list.push({
    order: '2',
    desc: '获取主订单表详情',
});
api[1].list.push({
    order: '3',
    desc: '新增主订单表',
});
api[1].list.push({
    order: '4',
    desc: '修改主订单表',
});
api[1].list.push({
    order: '5',
    desc: '批量删除主订单表',
});
api.push({
    alias: 'OrderReimburseController',
    order: '3',
    link: '订单退款表controller',
    desc: '订单退款表Controller',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '查询订单退款表列表',
});
api[2].list.push({
    order: '2',
    desc: '获取订单退款表详情',
});
api[2].list.push({
    order: '3',
    desc: '新增订单退款表',
});
api[2].list.push({
    order: '4',
    desc: '修改订单退款表',
});
api[2].list.push({
    order: '5',
    desc: '批量删除订单退款表',
});
api.push({
    alias: 'OrderSiteCouponController',
    order: '4',
    link: '场地预约入场券卷号表controller',
    desc: '场地预约入场券卷号表Controller',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '查询场地预约入场券卷号表列表',
});
api[3].list.push({
    order: '2',
    desc: '获取场地预约入场券卷号表详情',
});
api[3].list.push({
    order: '3',
    desc: '新增场地预约入场券卷号表',
});
api[3].list.push({
    order: '4',
    desc: '修改场地预约入场券卷号表',
});
api[3].list.push({
    order: '5',
    desc: '批量删除场地预约入场券卷号表',
});
api.push({
    alias: 'OrderSnapDetailController',
    order: '5',
    link: '订单快照表controller',
    desc: '订单快照表Controller',
    list: []
})
api[4].list.push({
    order: '1',
    desc: '查询订单快照表列表',
});
api[4].list.push({
    order: '2',
    desc: '获取订单快照表详情',
});
api[4].list.push({
    order: '3',
    desc: '新增订单快照表',
});
api[4].list.push({
    order: '4',
    desc: '修改订单快照表',
});
api[4].list.push({
    order: '5',
    desc: '批量删除订单快照表',
});
api.push({
    alias: 'OrderStockController',
    order: '6',
    link: '订单库存表controller',
    desc: '订单库存表Controller',
    list: []
})
api[5].list.push({
    order: '1',
    desc: '查询订单库存表列表',
});
api[5].list.push({
    order: '2',
    desc: '获取订单库存表详情',
});
api[5].list.push({
    order: '3',
    desc: '新增订单库存表',
});
api[5].list.push({
    order: '4',
    desc: '修改订单库存表',
});
api[5].list.push({
    order: '5',
    desc: '批量删除订单库存表',
});
api.push({
    alias: 'SiteInvalidSessionController',
    order: '7',
    link: '场地预约不可用场次表controller',
    desc: '场地预约不可用场次表Controller',
    list: []
})
api[6].list.push({
    order: '1',
    desc: '查询场地预约不可用场次表列表',
});
api[6].list.push({
    order: '2',
    desc: '获取场地预约不可用场次表详情',
});
api[6].list.push({
    order: '3',
    desc: '新增场地预约不可用场次表',
});
api[6].list.push({
    order: '4',
    desc: '修改场地预约不可用场次表',
});
api[6].list.push({
    order: '5',
    desc: '批量删除场地预约不可用场次表',
});
api.push({
    alias: 'SiteOrderRuleController',
    order: '8',
    link: '场地预约订单规则表controller',
    desc: '场地预约订单规则表Controller',
    list: []
})
api[7].list.push({
    order: '1',
    desc: '查询场地预约订单规则表列表',
});
api[7].list.push({
    order: '2',
    desc: '获取场地预约订单规则表详情',
});
api[7].list.push({
    order: '3',
    desc: '新增场地预约订单规则表',
});
api[7].list.push({
    order: '4',
    desc: '修改场地预约订单规则表',
});
api[7].list.push({
    order: '5',
    desc: '批量删除场地预约订单规则表',
});
api.push({
    alias: 'SiteReserveController',
    order: '9',
    link: '场地预约主表controller',
    desc: '场地预约主表Controller',
    list: []
})
api[8].list.push({
    order: '1',
    desc: '查询场地预约主表列表',
});
api[8].list.push({
    order: '2',
    desc: '获取场地预约主表详情',
});
api[8].list.push({
    order: '3',
    desc: '新增场地预约主表',
});
api[8].list.push({
    order: '4',
    desc: '修改场地预约主表',
});
api[8].list.push({
    order: '5',
    desc: '批量删除场地预约主表',
});
api.push({
    alias: 'SiteReserveDetailController',
    order: '10',
    link: '场地详情表controller',
    desc: '场地详情表Controller',
    list: []
})
api[9].list.push({
    order: '1',
    desc: '查询场地详情表列表',
});
api[9].list.push({
    order: '2',
    desc: '获取场地详情表详情',
});
api[9].list.push({
    order: '3',
    desc: '新增场地详情表',
});
api[9].list.push({
    order: '4',
    desc: '修改场地详情表',
});
api[9].list.push({
    order: '5',
    desc: '批量删除场地详情表',
});
api.push({
    alias: 'SiteReserveSessionController',
    order: '11',
    link: '场地预约场次表controller',
    desc: '场地预约场次表Controller',
    list: []
})
api[10].list.push({
    order: '1',
    desc: '查询场地预约场次表列表',
});
api[10].list.push({
    order: '2',
    desc: '获取场地预约场次表详情',
});
api[10].list.push({
    order: '3',
    desc: '新增场地预约场次表',
});
api[10].list.push({
    order: '4',
    desc: '修改场地预约场次表',
});
api[10].list.push({
    order: '5',
    desc: '批量删除场地预约场次表',
});
api.push({
    alias: 'SiteReserveStatusController',
    order: '12',
    link: '场地状态表controller',
    desc: '场地状态表Controller',
    list: []
})
api[11].list.push({
    order: '1',
    desc: '查询场地状态表列表',
});
api[11].list.push({
    order: '2',
    desc: '获取场地状态表详情',
});
api[11].list.push({
    order: '3',
    desc: '新增场地状态表',
});
api[11].list.push({
    order: '4',
    desc: '修改场地状态表',
});
api[11].list.push({
    order: '5',
    desc: '批量删除场地状态表',
});
api.push({
    alias: 'SysConfigController',
    order: '13',
    link: '参数配置表controller',
    desc: '参数配置表Controller',
    list: []
})
api[12].list.push({
    order: '1',
    desc: '查询参数配置表列表',
});
api[12].list.push({
    order: '2',
    desc: '获取参数配置表详情',
});
api[12].list.push({
    order: '3',
    desc: '新增参数配置表',
});
api[12].list.push({
    order: '4',
    desc: '修改参数配置表',
});
api[12].list.push({
    order: '5',
    desc: '批量删除参数配置表',
});
api.push({
    alias: 'SysDictDataController',
    order: '14',
    link: '字典数据表controller',
    desc: '字典数据表Controller',
    list: []
})
api[13].list.push({
    order: '1',
    desc: '查询字典数据表列表',
});
api[13].list.push({
    order: '2',
    desc: '获取字典数据表详情',
});
api[13].list.push({
    order: '3',
    desc: '新增字典数据表',
});
api[13].list.push({
    order: '4',
    desc: '修改字典数据表',
});
api[13].list.push({
    order: '5',
    desc: '批量删除字典数据表',
});
api.push({
    alias: 'SysDictTypeController',
    order: '15',
    link: '字典类型表controller',
    desc: '字典类型表Controller',
    list: []
})
api[14].list.push({
    order: '1',
    desc: '查询字典类型表列表',
});
api[14].list.push({
    order: '2',
    desc: '获取字典类型表详情',
});
api[14].list.push({
    order: '3',
    desc: '新增字典类型表',
});
api[14].list.push({
    order: '4',
    desc: '修改字典类型表',
});
api[14].list.push({
    order: '5',
    desc: '批量删除字典类型表',
});
api.push({
    alias: 'error',
    order: '16',
    link: 'error_code_list',
    desc: '错误码列表',
    list: []
})
api.push({
    alias: 'dict',
    order: '17',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[16].list.push({
    order: '1',
    desc: '响应http状态码字典',
});
api[16].list.push({
    order: '2',
    desc: '验证码请求来源字典',
});
api[16].list.push({
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