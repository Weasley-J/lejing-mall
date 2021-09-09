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
    alias: 'EmailController',
    order: '2',
    link: '邮件controller',
    desc: '邮件Controller',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '发送给定的简单邮件消息',
});
api[1].list.push({
    order: '2',
    desc: '发送带附件的邮件消息',
});
api.push({
    alias: 'error',
    order: '3',
    link: 'error_code_list',
    desc: '错误码列表',
    list: []
})
api.push({
    alias: 'dict',
    order: '4',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '响应http状态码字典',
});
api[3].list.push({
    order: '2',
    desc: '验证码请求来源字典',
});
api[3].list.push({
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