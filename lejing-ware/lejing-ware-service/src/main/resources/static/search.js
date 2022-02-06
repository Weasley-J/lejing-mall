let api = [];
const apiDocListSize = 1
api.push({
    name: 'default',
    order: '1',
    list: []
})
api[0].list.push({
    alias: 'HelloRabbitController',
    order: '1',
    link: '测试rabbit_-_controller',
    desc: '测试Rabbit - Controller',
    list: []
})
api[0].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:11000/app/mq/send/msg',
    desc: '',
});
api[0].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:11000/app/status',
    desc: '',
});
api[0].list[0].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:11000/app/skuInfo',
    desc: '',
});
api[0].list.push({
    alias: 'UndoLogController',
    order: '2',
    link: '撤销日志表controller',
    desc: '撤销日志表Controller',
    list: []
})
api[0].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/undolog/list',
    desc: '查询撤销日志表列表',
});
api[0].list[1].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/undolog/info/{id}',
    desc: '获取撤销日志表详情',
});
api[0].list[1].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/undolog/save',
    desc: '新增撤销日志表',
});
api[0].list[1].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/undolog/update',
    desc: '修改撤销日志表',
});
api[0].list[1].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/undolog/delete/{ids}',
    desc: '批量删除撤销日志表',
});
api[0].list.push({
    alias: 'WareInfoController',
    order: '3',
    link: '仓库信息controller',
    desc: '仓库信息Controller',
    list: []
})
api[0].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/wareinfo/postage/info',
    desc: '获取运费信息',
});
api[0].list[2].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/wareinfo/list',
    desc: '查询仓库信息列表',
});
api[0].list[2].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/wareinfo/info/{id}',
    desc: '获取仓库信息详情',
});
api[0].list[2].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/wareinfo/save',
    desc: '新增仓库信息',
});
api[0].list[2].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/wareinfo/update',
    desc: '修改仓库信息',
});
api[0].list[2].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/wareinfo/delete/{ids}',
    desc: '批量删除仓库信息',
});
api[0].list.push({
    alias: 'PurchaseDetailController',
    order: '4',
    link: '仓储采购表controller',
    desc: '仓储采购表Controller',
    list: []
})
api[0].list[3].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/purchasedetail/list',
    desc: '查询仓储采购表列表',
});
api[0].list[3].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/purchasedetail/info/{id}',
    desc: '获取仓储采购表详情',
});
api[0].list[3].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/purchasedetail/save',
    desc: '新增仓储采购表',
});
api[0].list[3].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/purchasedetail/update',
    desc: '修改仓储采购表',
});
api[0].list[3].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/purchasedetail/delete/{ids}',
    desc: '批量删除仓储采购表',
});
api[0].list.push({
    alias: 'WareOrderTaskDetailController',
    order: '5',
    link: '库存工作单controller',
    desc: '库存工作单Controller',
    list: []
})
api[0].list[4].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/wareordertaskdetail/list',
    desc: '查询库存工作单列表',
});
api[0].list[4].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/wareordertaskdetail/info/{id}',
    desc: '获取库存工作单详情',
});
api[0].list[4].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/wareordertaskdetail/save',
    desc: '新增库存工作单',
});
api[0].list[4].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/wareordertaskdetail/update',
    desc: '修改库存工作单',
});
api[0].list[4].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/wareordertaskdetail/delete/{ids}',
    desc: '批量删除库存工作单',
});
api[0].list.push({
    alias: 'PurchaseController',
    order: '6',
    link: '采购信息controller',
    desc: '采购信息Controller',
    list: []
})
api[0].list[5].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/purchase/unreceive/list',
    desc: '查询w未领取的采购单列表',
});
api[0].list[5].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/purchase/list',
    desc: '查询采购信息列表',
});
api[0].list[5].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/purchase/info/{id}',
    desc: '获取采购信息详情',
});
api[0].list[5].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/purchase/save',
    desc: '新增采购信息',
});
api[0].list[5].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/purchase/merge',
    desc: '合并仓储采购表',
});
api[0].list[5].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/purchase/received',
    desc: '领取采购单',
});
api[0].list[5].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/purchase/done',
    desc: '完成采购',
});
api[0].list[5].list.push({
    order: '8',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/purchase/update',
    desc: '修改采购信息',
});
api[0].list[5].list.push({
    order: '9',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/purchase/delete/{ids}',
    desc: '批量删除采购信息',
});
api[0].list.push({
    alias: 'WareSkuController',
    order: '7',
    link: '商品库存controller',
    desc: '商品库存Controller',
    list: []
})
api[0].list[6].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/waresku/order/lock/stock',
    desc: '下单锁定库存',
});
api[0].list[6].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/waresku/skuHasStock',
    desc: '查看是否有库存',
});
api[0].list[6].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/waresku/list',
    desc: '查询商品库存列表',
});
api[0].list[6].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/waresku/list/{skuId}',
    desc: '查询商品库存列表',
});
api[0].list[6].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/waresku/info/{id}',
    desc: '获取商品库存详情',
});
api[0].list[6].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/waresku/save',
    desc: '新增商品库存',
});
api[0].list[6].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/waresku/update',
    desc: '修改商品库存',
});
api[0].list[6].list.push({
    order: '8',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/waresku/delete/{ids}',
    desc: '批量删除商品库存',
});
api[0].list.push({
    alias: 'WareOrderTaskController',
    order: '8',
    link: '库存工作单controller',
    desc: '库存工作单Controller',
    list: []
})
api[0].list[7].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/wareordertask/list',
    desc: '查询库存工作单列表',
});
api[0].list[7].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/wareordertask/info/{id}',
    desc: '获取库存工作单详情',
});
api[0].list[7].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/wareordertask/save',
    desc: '新增库存工作单',
});
api[0].list[7].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/wareordertask/update',
    desc: '修改库存工作单',
});
api[0].list[7].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:11000/ware/wareordertask/delete/{ids}',
    desc: '批量删除库存工作单',
});
api[0].list.push({
    alias: 'error',
    order: '9',
    link: 'error_code_list',
    desc: '错误码列表',
    list: []
})
api[0].list.push({
    alias: 'dict',
    order: '10',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[0].list[9].list.push({
    order: '1',
    deprecated: 'false',
    url: '',
    desc: '响应http状态码字典',
});
api[0].list[9].list.push({
    order: '2',
    deprecated: 'false',
    url: '',
    desc: '验证码请求来源字典',
});
api[0].list[9].list.push({
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