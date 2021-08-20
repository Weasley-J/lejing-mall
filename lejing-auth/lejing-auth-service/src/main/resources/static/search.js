let api = [];
api.push({
    alias: 'LoginController',
    order: '1',
    link: '登录页controller',
    desc: '登录页Controller',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '发送验证码给用户手机',
});
api[0].list.push({
    order: '2',
    desc: '用户注册',
});
api[0].list.push({
    order: '3',
    desc: '登录页',
});
api[0].list.push({
    order: '4',
    desc: '用户登录',
});
api[0].list.push({
    order: '5',
    desc: '退出登录&lt;p&gt;从Session中删除的登录用户信息&lt;/p&gt;',
});
api.push({
    alias: 'WeChatController',
    order: '2',
    link: '微信登录_-_登录_&_授权回调',
    desc: '微信登录-登录&授权回调',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '获取扫码人的信息&lt;P&gt;添加数据&lt;/P&gt;',
});
api[1].list.push({
    order: '2',
    desc: '生成微信扫描二维码图片',
});
api.push({
    alias: 'WeiboController',
    order: '3',
    link: '&lt;b&gt;oauth2社交登录controller&lt;/b&gt;',
    desc: '&lt;b&gt;Oauth2社交登录Controller&lt;/b&gt;',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '新浪微博登录',
});
api.push({
    alias: 'error',
    order: '4',
    link: 'error_code_list',
    desc: '错误码列表',
    list: []
})
api.push({
    alias: 'dict',
    order: '5',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[4].list.push({
    order: '1',
    desc: '响应http状态码字典',
});
api[4].list.push({
    order: '2',
    desc: '验证码请求来源字典',
});
api[4].list.push({
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