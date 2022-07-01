let api = [];
const apiDocListSize = 1
api.push({
    name: 'default',
    order: '1',
    list: []
})
api[0].list.push({
    alias: 'OrderEasypoiController',
    order: '1',
    link: '主订单数据excel导入导出controller',
    desc: '主订单数据excel导入导出Controller',
    list: []
})
api[0].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:88/api/order/public/easypoi/download/order',
    desc: '导出订单数据',
});
api[0].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:88/api/order/public/easypoi/upload/order',
    desc: '导入订单数据',
});
api[0].list.push({
    alias: 'BillDetailController',
    order: '2',
    link: '账单明细controller  &lt;p&gt;这个controller主要展示&lt;/p&gt;',
    desc: '账单明细Controller  &lt;p&gt;这个controller主要展示&lt;/p&gt;',
    list: []
})
api[0].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:88/api/order/public/easypoi/bill/detail/download/bills',
    desc: '账单明细 - 下载',
});
api[0].list[1].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:88/api/order/public/easypoi/bill/detail/preview/html',
    desc: '账单明细excel预览',
});
api[0].list.push({
    alias: 'OrderItemEasypoiController',
    order: '3',
    link: '子订单订单导入导出controller',
    desc: '子订单订单导入导出Controller',
    list: []
})
api[0].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:88/api/order/public/easypoi/download/order/item',
    desc: '下载订单的excel文件',
});
api[0].list[2].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:88/api/order/public/easypoi/upload/order/item',
    desc: '上传订单的excel文件',
});
api[0].list.push({
    alias: 'EasypoiValidSheetController',
    order: '4',
    link: 'easypoi基于jsr303注解校验excel表格内容',
    desc: 'Easypoi基于JSR303注解校验Excel表格内容',
    list: []
})
api[0].list[3].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:88/api/order/public/easypoi/valid/sheet/person/download',
    desc: '下载excel文件',
});
api[0].list[3].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:88/api/order/public/easypoi/valid/sheet/person/upload',
    desc: '文件上传（校验excel的元数据）',
});
api[0].list[3].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:88/api/order/public/easypoi/valid/sheet/preview/html',
    desc: '预览excel内容',
});
api[0].list.push({
    alias: 'LejingCustomValidationController',
    order: '5',
    link: '自定义注解校验示例controller',
    desc: '自定义注解校验示例Controller',
    list: []
})
api[0].list[4].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:88/api/order/public/valid/save',
    desc: '保存用户的虚拟币',
});
api[0].list[4].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:88/api/order/public/valid/list',
    desc: '获取会员的虚拟货币列表',
});
api[0].list[4].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:88/api/order/public/valid/info/{memberId}',
    desc: '获取会员的虚拟货币明细',
});
api[0].list[4].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:88/api/order/public/valid/edit',
    desc: '修改用户的虚拟币信息',
});
api[0].list[4].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:88/api/order/public/valid/edit/status',
    desc: '修改用户的虚拟币扎状态',
});
api[0].list[4].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:88/api/order/public/valid/delete/{memberIds}',
    desc: '批量删除用户虚拟币',
});
api[0].list.push({
    alias: 'error',
    order: '6',
    link: 'error_code_list',
    desc: '错误码列表',
    list: []
})
api[0].list.push({
    alias: 'dict',
    order: '7',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[0].list[6].list.push({
    order: '1',
    deprecated: 'false',
    url: '',
    desc: '响应http状态码字典',
});
api[0].list[6].list.push({
    order: '2',
    deprecated: 'false',
    url: '',
    desc: '订单状态字典',
});
api[0].list[6].list.push({
    order: '3',
    deprecated: 'false',
    url: '',
    desc: '支付方式字典',
});
api[0].list[6].list.push({
    order: '4',
    deprecated: 'false',
    url: '',
    desc: '验证码请求来源字典',
});
api[0].list[6].list.push({
    order: '5',
    deprecated: 'false',
    url: '',
    desc: '电子入场券状态字典',
});
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code === 13) {
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
        if (searchValue === '') {
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
            let $this = $(this), $next = $this.next();
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
    if (apiGroups.length > 0) {
        if (apiDocListSize === 1) {
            let apiData = apiGroups[0].list;
            let order = apiGroups[0].order;
            for (let j = 0; j < apiData.length; j++) {
                html += '<li class="'+liClass+'">';
                html += '<a class="dd" href="#_'+order+'_'+apiData[j].order+'_' + apiData[j].link + '">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
                html += '<ul class="sectlevel2" style="'+display+'">';
                let doc = apiData[j].list;
                for (let m = 0; m < doc.length; m++) {
                    let spanString;
                    if (doc[m].deprecated === 'true') {
                        spanString='<span class="line-through">';
                    } else {
                        spanString='<span>';
                    }
                    html += '<li><a href="#_'+order+'_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + spanString + doc[m].desc + '<span></a> </li>';
                }
                html += '</ul>';
                html += '</li>';
            }
        } else {
            for (let i = 0; i < apiGroups.length; i++) {
                let apiGroup = apiGroups[i];
                html += '<li class="'+liClass+'">';
                html += '<a class="dd" href="#_'+apiGroup.order+'_' + apiGroup.name + '">' + apiGroup.order + '.&nbsp;' + apiGroup.name + '</a>';
                html += '<ul class="sectlevel1">';

                let apiData = apiGroup.list;
                for (let j = 0; j < apiData.length; j++) {
                    html += '<li class="'+liClass+'">';
                    html += '<a class="dd" href="#_'+apiGroup.order+'_'+ apiData[j].order + '_'+ apiData[j].link + '">' +apiGroup.order+'.'+ apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
                    html += '<ul class="sectlevel2" style="'+display+'">';
                    let doc = apiData[j].list;
                    for (let m = 0; m < doc.length; m++) {
                       let spanString;
                       if (doc[m].deprecated === 'true') {
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