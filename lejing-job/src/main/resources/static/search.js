let api = [];
api.push({
    alias: 'ScheduleJobController',
    order: '1',
    link: 'quartz定时任务调度controller',
    desc: 'quartz定时任务调度Controller',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '获取时任务列表',
});
api[0].list.push({
    order: '2',
    desc: '新增cron定时任务',
});
api[0].list.push({
    order: '3',
    desc: '获取定时任务详情',
});
api[0].list.push({
    order: '4',
    desc: '修改定时任务',
});
api[0].list.push({
    order: '5',
    desc: '删除定时任务',
});
api[0].list.push({
    order: '6',
    desc: '修改定时任务状态',
});
api[0].list.push({
    order: '7',
    desc: '立即执行一次任务',
});
api[0].list.push({
    order: '8',
    desc: '暂停定时任务',
});
api[0].list.push({
    order: '9',
    desc: '恢复定时任务',
});
api[0].list.push({
    order: '10',
    desc: '定时任务是否存在',
});
api[0].list.push({
    order: '11',
    desc: '获取任务状态',
});
api[0].list.push({
    order: '12',
    desc: '创建简单定时任务（不持久化到db）&lt;ul&gt;&lt;li&gt;创建简单的调度任务：从什么时间开始，循环间隔多少分钟，什么时间结束&lt;/li&gt;&lt;/ul&gt;',
});
api[0].list.push({
    order: '13',
    desc: '更新简单定时任务（不持久化到db）&lt;ul&gt;&lt;li&gt;创建简单的调度任务：从什么时间开始，循环间隔多少分钟，什么时间结束&lt;/li&gt;&lt;/ul&gt;',
});
api.push({
    alias: 'error',
    order: '2',
    link: 'error_code_list',
    desc: '错误码列表',
    list: []
})
api.push({
    alias: 'dict',
    order: '3',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '响应http状态码字典',
});
api[2].list.push({
    order: '2',
    desc: '验证码请求来源字典',
});
api[2].list.push({
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