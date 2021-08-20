let api = [];
api.push({
    alias: 'CartController',
    order: '1',
    link: '&lt;b&gt;购物车controller&lt;/b&gt;',
    desc: '&lt;b&gt;购物车Controller&lt;/b&gt;',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '获取当前用户的购物车商品项',
});
api[0].list.push({
    order: '2',
    desc: '去购物车页面的请求浏览器有一个cookie:user-key标识用户的身份，一个月过期如果第一次使用jd的购物车功能，都会给一个临时的用户身份:浏览器以后保存，每次访问都会带上这个cookie；&lt;p&gt;登录：session有没登录：按照cookie里面带来user-key来做第一次，如果没有临时用户，自动创建一个临时用户',
});
api[0].list.push({
    order: '3',
    desc: '添加商品到购物车attributes.addFlashAttribute():将数据放在session中，可以在页面中取出，但是只能取一次attributes.addAttribute():将数据放在url后面',
});
api[0].list.push({
    order: '4',
    desc: '跳转到添加购物车成功页面',
});
api[0].list.push({
    order: '5',
    desc: '商品是否选中',
});
api[0].list.push({
    order: '6',
    desc: '修改购物车中商品数量',
});
api[0].list.push({
    order: '7',
    desc: '删除商品信息',
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