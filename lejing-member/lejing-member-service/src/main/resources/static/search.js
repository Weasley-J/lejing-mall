let api = [];
const apiDocListSize = 1
api.push({
    name: 'default',
    order: '1',
    list: []
})
api[0].list.push({
    alias: 'GrowthChangeHistoryController',
    order: '1',
    link: '成长值变化历史记录controller',
    desc: '成长值变化历史记录Controller',
    list: []
})
api[0].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8000/member/growthchangehistory/list',
    desc: '查询成长值变化历史记录列表',
});
api[0].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8000/member/growthchangehistory/info/{id}',
    desc: '获取成长值变化历史记录详情',
});
api[0].list[0].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8000/member/growthchangehistory/save',
    desc: '新增成长值变化历史记录',
});
api[0].list[0].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:8000/member/growthchangehistory/update',
    desc: '修改成长值变化历史记录',
});
api[0].list[0].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:8000/member/growthchangehistory/delete/{ids}',
    desc: '批量删除成长值变化历史记录',
});
api[0].list.push({
    alias: 'IntegrationChangeHistoryController',
    order: '2',
    link: '积分变化历史记录controller',
    desc: '积分变化历史记录Controller',
    list: []
})
api[0].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8000/member/integrationchangehistory/list',
    desc: '查询积分变化历史记录列表',
});
api[0].list[1].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8000/member/integrationchangehistory/info/{id}',
    desc: '获取积分变化历史记录详情',
});
api[0].list[1].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8000/member/integrationchangehistory/save',
    desc: '新增积分变化历史记录',
});
api[0].list[1].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:8000/member/integrationchangehistory/update',
    desc: '修改积分变化历史记录',
});
api[0].list[1].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:8000/member/integrationchangehistory/delete/{ids}',
    desc: '批量删除积分变化历史记录',
});
api[0].list.push({
    alias: 'MemberCollectSpuController',
    order: '3',
    link: '会员收藏的商品controller',
    desc: '会员收藏的商品Controller',
    list: []
})
api[0].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8000/member/membercollectspu/list',
    desc: '查询会员收藏的商品列表',
});
api[0].list[2].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8000/member/membercollectspu/info/{id}',
    desc: '获取会员收藏的商品详情',
});
api[0].list[2].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8000/member/membercollectspu/save',
    desc: '新增会员收藏的商品',
});
api[0].list[2].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:8000/member/membercollectspu/update',
    desc: '修改会员收藏的商品',
});
api[0].list[2].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:8000/member/membercollectspu/delete/{ids}',
    desc: '批量删除会员收藏的商品',
});
api[0].list.push({
    alias: 'MemberCollectSubjectController',
    order: '4',
    link: '会员收藏的专题活动controller',
    desc: '会员收藏的专题活动Controller',
    list: []
})
api[0].list[3].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8000/member/membercollectsubject/list',
    desc: '查询会员收藏的专题活动列表',
});
api[0].list[3].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8000/member/membercollectsubject/info/{id}',
    desc: '获取会员收藏的专题活动详情',
});
api[0].list[3].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8000/member/membercollectsubject/save',
    desc: '新增会员收藏的专题活动',
});
api[0].list[3].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:8000/member/membercollectsubject/update',
    desc: '修改会员收藏的专题活动',
});
api[0].list[3].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:8000/member/membercollectsubject/delete/{ids}',
    desc: '批量删除会员收藏的专题活动',
});
api[0].list.push({
    alias: 'MemberController',
    order: '5',
    link: '会员controller',
    desc: '会员Controller',
    list: []
})
api[0].list[4].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8000/member/member/list',
    desc: '查询会员列表',
});
api[0].list[4].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8000/member/member/info/{id}',
    desc: '获取会员详情',
});
api[0].list[4].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8000/member/member/save',
    desc: '新增会员',
});
api[0].list[4].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:8000/member/member/update',
    desc: '修改会员',
});
api[0].list[4].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:8000/member/member/delete/{ids}',
    desc: '批量删除会员',
});
api[0].list[4].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:8000/member/member/coupon/{couponId}',
    desc: '获取优惠券信息-测试feign远程调用',
});
api[0].list[4].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:8000/member/member/coupon/list',
    desc: '查询优惠券信息列表-测试feign远程调用',
});
api[0].list[4].list.push({
    order: '8',
    deprecated: 'false',
    url: 'http://localhost:8000/member/member/login',
    desc: '用户登录',
});
api[0].list[4].list.push({
    order: '9',
    deprecated: 'false',
    url: 'http://localhost:8000/member/member/oauth2/login',
    desc: '处理微博社交登录',
});
api[0].list[4].list.push({
    order: '10',
    deprecated: 'false',
    url: 'http://localhost:8000/member/member/weixin/login',
    desc: '使用微信的accessToken登录注册用户',
});
api[0].list.push({
    alias: 'MemberLevelController',
    order: '6',
    link: '会员等级controller',
    desc: '会员等级Controller',
    list: []
})
api[0].list[5].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberlevel/list',
    desc: '查询会员等级列表',
});
api[0].list[5].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberlevel/info/{id}',
    desc: '获取会员等级详情',
});
api[0].list[5].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberlevel/save',
    desc: '新增会员等级',
});
api[0].list[5].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberlevel/update',
    desc: '修改会员等级',
});
api[0].list[5].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberlevel/delete/{ids}',
    desc: '批量删除会员等级',
});
api[0].list.push({
    alias: 'MemberLoginLogController',
    order: '7',
    link: '会员登录记录controller',
    desc: '会员登录记录Controller',
    list: []
})
api[0].list[6].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberloginlog/list',
    desc: '查询会员登录记录列表',
});
api[0].list[6].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberloginlog/info/{id}',
    desc: '获取会员登录记录详情',
});
api[0].list[6].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberloginlog/save',
    desc: '新增会员登录记录',
});
api[0].list[6].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberloginlog/update',
    desc: '修改会员登录记录',
});
api[0].list[6].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberloginlog/delete/{ids}',
    desc: '批量删除会员登录记录',
});
api[0].list.push({
    alias: 'MemberReceiveAddressController',
    order: '8',
    link: '会员收货地址controller',
    desc: '会员收货地址Controller',
    list: []
})
api[0].list[7].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberreceiveaddress/list',
    desc: '查询会员收货地址列表',
});
api[0].list[7].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberreceiveaddress/info/{id}',
    desc: '获取会员收货地址详情',
});
api[0].list[7].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberreceiveaddress/save',
    desc: '新增会员收货地址',
});
api[0].list[7].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberreceiveaddress/update',
    desc: '修改会员收货地址',
});
api[0].list[7].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberreceiveaddress/delete/{ids}',
    desc: '批量删除会员收货地址',
});
api[0].list[7].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberreceiveaddress/addresses/{memberId}',
    desc: '查询用户的收货地址列表',
});
api[0].list.push({
    alias: 'MemberStatisticsInfoController',
    order: '9',
    link: '会员统计信息controller',
    desc: '会员统计信息Controller',
    list: []
})
api[0].list[8].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberstatisticsinfo/list',
    desc: '查询会员统计信息列表',
});
api[0].list[8].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberstatisticsinfo/info/{id}',
    desc: '获取会员统计信息详情',
});
api[0].list[8].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberstatisticsinfo/save',
    desc: '新增会员统计信息',
});
api[0].list[8].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberstatisticsinfo/update',
    desc: '修改会员统计信息',
});
api[0].list[8].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:8000/member/memberstatisticsinfo/delete/{ids}',
    desc: '批量删除会员统计信息',
});
api[0].list.push({
    alias: 'UndoLogController',
    order: '10',
    link: '撤销日志表controller',
    desc: '撤销日志表Controller',
    list: []
})
api[0].list[9].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8000/member/undolog/list',
    desc: '查询撤销日志表列表',
});
api[0].list[9].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:8000/member/undolog/info/{id}',
    desc: '获取撤销日志表详情',
});
api[0].list[9].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:8000/member/undolog/save',
    desc: '新增撤销日志表',
});
api[0].list[9].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:8000/member/undolog/update',
    desc: '修改撤销日志表',
});
api[0].list[9].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:8000/member/undolog/delete/{ids}',
    desc: '批量删除撤销日志表',
});
api[0].list.push({
    alias: 'MemberWebController',
    order: '11',
    link: '会员订单页controller',
    desc: '会员订单页Controller',
    list: []
})
api[0].list[10].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:8000/memberOrder.html',
    desc: '获取当前会员的所有订单列表数据',
});
api[0].list.push({
    alias: 'error',
    order: '12',
    link: 'error_code_list',
    desc: '错误码列表',
    list: []
})
api[0].list.push({
    alias: 'dict',
    order: '13',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[0].list[12].list.push({
    order: '1',
    deprecated: 'false',
    url: '',
    desc: '响应http状态码字典',
});
api[0].list[12].list.push({
    order: '2',
    deprecated: 'false',
    url: '',
    desc: '验证码请求来源字典',
});
api[0].list[12].list.push({
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
        const searchValue = search.value;
        let searchGroup = [];
        for (let i = 0; i < api.length; i++) {

            let apiGroup = api[i];

            let searchArr = [];
            for (let i = 0; i < apiGroup.list.length; i++) {
                let apiData = apiGroup.list[i];
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
            if (apiGroup.name.indexOf(searchValue) > -1) {
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
                    html += '<li><a href="#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].href + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + spanString + doc[m].desc + '<span></a> </li>';
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
                    html += '<a class="dd" href="#_' + apiData[j].link + '">' +apiGroup.order+'.'+ apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
                    html += '<ul class="sectlevel2" style="'+display+'">';
                    doc = apiData[j].list;
                    for (let m = 0; m < doc.length; m++) {
                       let spanString;
                       if (doc[m].deprecated == 'true') {
                           spanString='<span class="line-through">';
                       } else {
                           spanString='<span>';
                       }
                       html += '<li><a href="#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].href + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + spanString + doc[m].desc + '<span></a> </li>';
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