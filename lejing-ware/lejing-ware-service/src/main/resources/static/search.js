let api = [];
api.push({
    alias: 'HelloRabbitController',
    order: '1',
    link: '测试rabbit_-_controller',
    desc: '测试Rabbit-Controller',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '',
});
api[0].list.push({
    order: '2',
    desc: '',
});
api[0].list.push({
    order: '3',
    desc: '',
});
api.push({
    alias: 'PurchaseController',
    order: '2',
    link: '采购信息controller',
    desc: '采购信息Controller',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '查询w未领取的采购单列表',
});
api[1].list.push({
    order: '2',
    desc: '查询采购信息列表',
});
api[1].list.push({
    order: '3',
    desc: '获取采购信息详情',
});
api[1].list.push({
    order: '4',
    desc: '新增采购信息',
});
api[1].list.push({
    order: '5',
    desc: '合并仓储采购表',
});
api[1].list.push({
    order: '6',
    desc: '领取采购单',
});
api[1].list.push({
    order: '7',
    desc: '完成采购',
});
api[1].list.push({
    order: '8',
    desc: '修改采购信息',
});
api[1].list.push({
    order: '9',
    desc: '批量删除采购信息',
});
api.push({
    alias: 'PurchaseDetailController',
    order: '3',
    link: '仓储采购表controller',
    desc: '仓储采购表Controller',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '查询仓储采购表列表',
});
api[2].list.push({
    order: '2',
    desc: '获取仓储采购表详情',
});
api[2].list.push({
    order: '3',
    desc: '新增仓储采购表',
});
api[2].list.push({
    order: '4',
    desc: '修改仓储采购表',
});
api[2].list.push({
    order: '5',
    desc: '批量删除仓储采购表',
});
api.push({
    alias: 'UndoLogController',
    order: '4',
    link: '撤销日志表controller',
    desc: '撤销日志表Controller',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '查询撤销日志表列表',
});
api[3].list.push({
    order: '2',
    desc: '获取撤销日志表详情',
});
api[3].list.push({
    order: '3',
    desc: '新增撤销日志表',
});
api[3].list.push({
    order: '4',
    desc: '修改撤销日志表',
});
api[3].list.push({
    order: '5',
    desc: '批量删除撤销日志表',
});
api.push({
    alias: 'WareInfoController',
    order: '5',
    link: '仓库信息controller',
    desc: '仓库信息Controller',
    list: []
})
api[4].list.push({
    order: '1',
    desc: '获取运费信息',
});
api[4].list.push({
    order: '2',
    desc: '查询仓库信息列表',
});
api[4].list.push({
    order: '3',
    desc: '获取仓库信息详情',
});
api[4].list.push({
    order: '4',
    desc: '新增仓库信息',
});
api[4].list.push({
    order: '5',
    desc: '修改仓库信息',
});
api[4].list.push({
    order: '6',
    desc: '批量删除仓库信息',
});
api.push({
    alias: 'WareOrderTaskController',
    order: '6',
    link: '库存工作单controller',
    desc: '库存工作单Controller',
    list: []
})
api[5].list.push({
    order: '1',
    desc: '查询库存工作单列表',
});
api[5].list.push({
    order: '2',
    desc: '获取库存工作单详情',
});
api[5].list.push({
    order: '3',
    desc: '新增库存工作单',
});
api[5].list.push({
    order: '4',
    desc: '修改库存工作单',
});
api[5].list.push({
    order: '5',
    desc: '批量删除库存工作单',
});
api.push({
    alias: 'WareOrderTaskDetailController',
    order: '7',
    link: '库存工作单controller',
    desc: '库存工作单Controller',
    list: []
})
api[6].list.push({
    order: '1',
    desc: '查询库存工作单列表',
});
api[6].list.push({
    order: '2',
    desc: '获取库存工作单详情',
});
api[6].list.push({
    order: '3',
    desc: '新增库存工作单',
});
api[6].list.push({
    order: '4',
    desc: '修改库存工作单',
});
api[6].list.push({
    order: '5',
    desc: '批量删除库存工作单',
});
api.push({
    alias: 'WareSkuController',
    order: '8',
    link: '商品库存controller',
    desc: '商品库存Controller',
    list: []
})
api[7].list.push({
    order: '1',
    desc: '下单锁定库存',
});
api[7].list.push({
    order: '2',
    desc: '查看是否有库存',
});
api[7].list.push({
    order: '3',
    desc: '查询商品库存列表',
});
api[7].list.push({
    order: '4',
    desc: '查询商品库存列表',
});
api[7].list.push({
    order: '5',
    desc: '获取商品库存详情',
});
api[7].list.push({
    order: '6',
    desc: '新增商品库存',
});
api[7].list.push({
    order: '7',
    desc: '修改商品库存',
});
api[7].list.push({
    order: '8',
    desc: '批量删除商品库存',
});
api.push({
    alias: 'error',
    order: '9',
    link: 'error_code_list',
    desc: '错误码列表',
    list: []
})
api.push({
    alias: 'dict',
    order: '10',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[9].list.push({
    order: '1',
    desc: '响应http状态码字典',
});
api[9].list.push({
    order: '2',
    desc: '验证码请求来源字典',
});
api[9].list.push({
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