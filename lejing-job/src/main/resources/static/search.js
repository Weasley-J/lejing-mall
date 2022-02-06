let api = [];
const apiDocListSize = 1
api.push({
    name: 'default',
    order: '1',
    list: []
})
api[0].list.push({
    alias: 'ScheduleJobController',
    order: '1',
    link: 'quartz定时任务调度controller',
    desc: 'Quartz定时任务调度Controller',
    list: []
})
api[0].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/list',
    desc: '获取时任务列表',
});
api[0].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/save',
    desc: '新增cron定时任务',
});
api[0].list[0].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/info/{id}',
    desc: '获取定时任务详情',
});
api[0].list[0].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/edit',
    desc: '更新定时任务',
});
api[0].list[0].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/remove/{ids}',
    desc: '删除定时任务',
});
api[0].list[0].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/update/status',
    desc: '修改定时任务状态',
});
api[0].list[0].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/run/at/now/{jobName}/{jobGroup}',
    desc: '立即执行一次任务',
});
api[0].list[0].list.push({
    order: '8',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/pause/all',
    desc: '暂停全部任务',
});
api[0].list[0].list.push({
    order: '9',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/resume/all',
    desc: '恢复全部任务',
});
api[0].list[0].list.push({
    order: '10',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/pause/one/{jobName}/{jobGroup}',
    desc: '暂停单个定时任务',
});
api[0].list[0].list.push({
    order: '11',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/resume/one/{jobName}/{jobGroup}',
    desc: '恢复单个定时任务',
});
api[0].list[0].list.push({
    order: '12',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/check/{jobName}/{jobGroup}',
    desc: '检查任务是否存在',
});
api[0].list[0].list.push({
    order: '13',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/status/{jobName}/{jobGroup}',
    desc: '获取任务状态信息',
});
api[0].list[0].list.push({
    order: '14',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/delete/{jobName}/{jobGroup}',
    desc: '从调度器中删除任务',
});
api[0].list[0].list.push({
    order: '15',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/create/simple/job',
    desc: '创建简单定时任务 &lt;ul&gt;     &lt;li&gt;不持久化到[业务数据库]&lt;/li&gt;     &lt;li&gt;创建简单的调度任务：从什么时间开始，循环间隔多少分钟，什么时间结束&lt;/li&gt; &lt;/ul&gt;',
});
api[0].list[0].list.push({
    order: '16',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/update/simple/job',
    desc: '更新简单定时任务 &lt;ul&gt;     &lt;li&gt;不持久化到[业务数据库]&lt;/li&gt;     &lt;li&gt;创建简单的调度任务：从什么时间开始，循环间隔多少分钟，什么时间结束&lt;/li&gt; &lt;/ul&gt;',
});
api[0].list.push({
    alias: 'EmailJobController',
    order: '2',
    link: '获取邮件参数controller',
    desc: '获取邮件参数Controller',
    list: []
})
api[0].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:88/api/schedule/job/email/params',
    desc: '此接口用来获取创建定时发送邮件的任务参数,即发送邮件的入参 &lt;p&gt;供quartz执行任发送邮件务时使用从JobDataMap中获取使用',
});
api[0].list.push({
    alias: 'error',
    order: '3',
    link: 'error_code_list',
    desc: '错误码列表',
    list: []
})
api[0].list.push({
    alias: 'dict',
    order: '4',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[0].list[3].list.push({
    order: '1',
    deprecated: 'false',
    url: '',
    desc: '响应http状态码字典',
});
api[0].list[3].list.push({
    order: '2',
    deprecated: 'false',
    url: '',
    desc: '验证码请求来源字典',
});
api[0].list[3].list.push({
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