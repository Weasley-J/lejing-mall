let api = [];
api.push({
    alias: 'GrowthChangeHistoryController',
    order: '1',
    link: '成长值变化历史记录',
    desc: '成长值变化历史记录',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '列表',
});
api[0].list.push({
    order: '2',
    desc: '信息',
});
api[0].list.push({
    order: '3',
    desc: '保存',
});
api[0].list.push({
    order: '4',
    desc: '修改',
});
api[0].list.push({
    order: '5',
    desc: '删除',
});
api.push({
    alias: 'IntegrationChangeHistoryController',
    order: '2',
    link: '积分变化历史记录',
    desc: '积分变化历史记录',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '列表',
});
api[1].list.push({
    order: '2',
    desc: '信息',
});
api[1].list.push({
    order: '3',
    desc: '保存',
});
api[1].list.push({
    order: '4',
    desc: '修改',
});
api[1].list.push({
    order: '5',
    desc: '删除',
});
api.push({
    alias: 'MemberCollectSpuController',
    order: '3',
    link: '会员收藏的商品',
    desc: '会员收藏的商品',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '列表',
});
api[2].list.push({
    order: '2',
    desc: '信息',
});
api[2].list.push({
    order: '3',
    desc: '保存',
});
api[2].list.push({
    order: '4',
    desc: '修改',
});
api[2].list.push({
    order: '5',
    desc: '删除',
});
api.push({
    alias: 'MemberCollectSubjectController',
    order: '4',
    link: '会员收藏的专题活动',
    desc: '会员收藏的专题活动',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '列表',
});
api[3].list.push({
    order: '2',
    desc: '信息',
});
api[3].list.push({
    order: '3',
    desc: '保存',
});
api[3].list.push({
    order: '4',
    desc: '修改',
});
api[3].list.push({
    order: '5',
    desc: '删除',
});
api.push({
    alias: 'MemberController',
    order: '5',
    link: '会员',
    desc: '会员',
    list: []
})
api[4].list.push({
    order: '1',
    desc: '列表',
});
api[4].list.push({
    order: '2',
    desc: '信息',
});
api[4].list.push({
    order: '3',
    desc: '保存',
});
api[4].list.push({
    order: '4',
    desc: '修改',
});
api[4].list.push({
    order: '5',
    desc: '删除',
});
api.push({
    alias: 'MemberLevelController',
    order: '6',
    link: '会员等级',
    desc: '会员等级',
    list: []
})
api[5].list.push({
    order: '1',
    desc: '列表',
});
api[5].list.push({
    order: '2',
    desc: '信息',
});
api[5].list.push({
    order: '3',
    desc: '保存',
});
api[5].list.push({
    order: '4',
    desc: '修改',
});
api[5].list.push({
    order: '5',
    desc: '删除',
});
api.push({
    alias: 'MemberLoginLogController',
    order: '7',
    link: '会员登录记录',
    desc: '会员登录记录',
    list: []
})
api[6].list.push({
    order: '1',
    desc: '列表',
});
api[6].list.push({
    order: '2',
    desc: '信息',
});
api[6].list.push({
    order: '3',
    desc: '保存',
});
api[6].list.push({
    order: '4',
    desc: '修改',
});
api[6].list.push({
    order: '5',
    desc: '删除',
});
api.push({
    alias: 'MemberReceiveAddressController',
    order: '8',
    link: '会员收货地址',
    desc: '会员收货地址',
    list: []
})
api[7].list.push({
    order: '1',
    desc: '列表',
});
api[7].list.push({
    order: '2',
    desc: '信息',
});
api[7].list.push({
    order: '3',
    desc: '保存',
});
api[7].list.push({
    order: '4',
    desc: '修改',
});
api[7].list.push({
    order: '5',
    desc: '删除',
});
api.push({
    alias: 'MemberStatisticsInfoController',
    order: '9',
    link: '会员统计信息',
    desc: '会员统计信息',
    list: []
})
api[8].list.push({
    order: '1',
    desc: '列表',
});
api[8].list.push({
    order: '2',
    desc: '信息',
});
api[8].list.push({
    order: '3',
    desc: '保存',
});
api[8].list.push({
    order: '4',
    desc: '修改',
});
api[8].list.push({
    order: '5',
    desc: '删除',
});
api.push({
    alias: 'UndoLogController',
    order: '10',
    link: '',
    desc: '',
    list: []
})
api[9].list.push({
    order: '1',
    desc: '列表',
});
api[9].list.push({
    order: '2',
    desc: '信息',
});
api[9].list.push({
    order: '3',
    desc: '保存',
});
api[9].list.push({
    order: '4',
    desc: '修改',
});
api[9].list.push({
    order: '5',
    desc: '删除',
});
api.push({
    alias: 'dict',
    order: '11',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
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