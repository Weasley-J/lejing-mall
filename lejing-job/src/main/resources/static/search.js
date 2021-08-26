let api = [];
api.push({
    alias: 'SysJobController',
    order: '1',
    link: '定时任务调度表controller',
    desc: '定时任务调度表Controller',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '查询定时任务调度表列表',
});
api[0].list.push({
    order: '2',
    desc: '获取定时任务调度表详情',
});
api[0].list.push({
    order: '3',
    desc: '新增定时任务调度表',
});
api[0].list.push({
    order: '4',
    desc: '修改定时任务调度表',
});
api[0].list.push({
    order: '5',
    desc: '批量删除定时任务调度表',
});
api.push({
    alias: 'SysJobLogController',
    order: '2',
    link: '定时任务调度日志表controller',
    desc: '定时任务调度日志表Controller',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '查询定时任务调度日志表列表',
});
api[1].list.push({
    order: '2',
    desc: '获取定时任务调度日志表详情',
});
api[1].list.push({
    order: '3',
    desc: '新增定时任务调度日志表',
});
api[1].list.push({
    order: '4',
    desc: '修改定时任务调度日志表',
});
api[1].list.push({
    order: '5',
    desc: '批量删除定时任务调度日志表',
});
api.push({
    alias: 'SysDictTypeController',
    order: '3',
    link: '字典类型controller',
    desc: '字典类型Controller',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '查询字典类型列表',
});
api[2].list.push({
    order: '2',
    desc: '获取字典类型详情',
});
api[2].list.push({
    order: '3',
    desc: '新增字典类型',
});
api[2].list.push({
    order: '4',
    desc: '修改字典类型',
});
api[2].list.push({
    order: '5',
    desc: '批量删除字典类型',
});
api.push({
    alias: 'SysParamsController',
    order: '4',
    link: '参数管理controller',
    desc: '参数管理Controller',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '查询参数管理列表',
});
api[3].list.push({
    order: '2',
    desc: '获取参数管理详情',
});
api[3].list.push({
    order: '3',
    desc: '新增参数管理',
});
api[3].list.push({
    order: '4',
    desc: '修改参数管理',
});
api[3].list.push({
    order: '5',
    desc: '批量删除参数管理',
});
api.push({
    alias: 'error',
    order: '5',
    link: 'error_code_list',
    desc: '错误码列表',
    list: []
})
api.push({
    alias: 'dict',
    order: '6',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[5].list.push({
    order: '1',
    desc: '响应http状态码字典',
});
api[5].list.push({
    order: '2',
    desc: '验证码请求来源字典',
});
api[5].list.push({
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