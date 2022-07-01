let api = [];
const apiDocListSize = 1
api.push({
    name: 'default',
    order: '1',
    list: []
})
api[0].list.push({
    alias: 'HelloRabbitController',
    order: '1',
    link: '测试rabbit_-_controller',
    desc: '测试Rabbit - Controller',
    list: []
})
api[0].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/{page}.html',
    desc: '',
});
api[0].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/sendMsgToMq',
    desc: '送消息给MQ',
});
api[0].list[0].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/order/order/create/test',
    desc: '测试创建订单发送消息给 ORDER_EVENT_RELEASE_ORDER_QUEUE 队列',
});
api[0].list[0].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/order/order/release/test',
    desc: '',
});
api[0].list.push({
    alias: 'OrderAppController',
    order: '2',
    link: '订单业务_controller',
    desc: '订单业务 Controller',
    list: []
})
api[0].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/toTrade',
    desc: '去订单结算确认页',
});
api[0].list[1].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/submitOrder',
    desc: '提交订单结算',
});
api[0].list.push({
    alias: 'PaymentInfoController',
    order: '3',
    link: '支付信息表controller',
    desc: '支付信息表Controller',
    list: []
})
api[0].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/order/paymentinfo/list',
    desc: '查询支付信息表列表',
});
api[0].list[2].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/order/paymentinfo/info/{id}',
    desc: '获取支付信息表详情',
});
api[0].list[2].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/order/paymentinfo/save',
    desc: '新增支付信息表',
});
api[0].list[2].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/order/paymentinfo/update',
    desc: '修改支付信息表',
});
api[0].list[2].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/order/paymentinfo/delete/{ids}',
    desc: '批量删除支付信息表',
});
api[0].list.push({
    alias: 'OrderController',
    order: '4',
    link: '订单controller',
    desc: '订单Controller',
    list: []
})
api[0].list[3].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/order/order/status',
    desc: '根据订单号查询订单状态',
});
api[0].list[3].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/order/order/member/order/list',
    desc: '获取当前登录用的订单数据  &lt;ul&gt;      &lt;li&gt;用户信息从拦截器里面取&lt;/li&gt;  &lt;/ul&gt;',
});
api[0].list[3].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/order/order/list',
    desc: '查询订单列表',
});
api[0].list[3].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/order/order/info/{id}',
    desc: '获取订单详情',
});
api[0].list[3].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/order/order/save',
    desc: '新增订单',
});
api[0].list[3].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:9010/order/order/update',
    desc: '修改订单',
});
api[0].list[3].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://localhost:9010/order/order/delete/{ids}',
    desc: '批量删除订单',
});
api[0].list.push({
    alias: 'OrderOperateHistoryController',
    order: '5',
    link: '订单操作历史记录controller',
    desc: '订单操作历史记录Controller',
    list: []
})
api[0].list[4].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderoperatehistory/list',
    desc: '查询订单操作历史记录列表',
});
api[0].list[4].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderoperatehistory/info/{id}',
    desc: '获取订单操作历史记录详情',
});
api[0].list[4].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderoperatehistory/save',
    desc: '新增订单操作历史记录',
});
api[0].list[4].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderoperatehistory/update',
    desc: '修改订单操作历史记录',
});
api[0].list[4].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderoperatehistory/delete/{ids}',
    desc: '批量删除订单操作历史记录',
});
api[0].list.push({
    alias: 'OrderSettingController',
    order: '6',
    link: '订单配置信息controller',
    desc: '订单配置信息Controller',
    list: []
})
api[0].list[5].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/order/ordersetting/list',
    desc: '查询订单配置信息列表',
});
api[0].list[5].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/order/ordersetting/info/{id}',
    desc: '获取订单配置信息详情',
});
api[0].list[5].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/order/ordersetting/save',
    desc: '新增订单配置信息',
});
api[0].list[5].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/order/ordersetting/update',
    desc: '修改订单配置信息',
});
api[0].list[5].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/order/ordersetting/delete/{ids}',
    desc: '批量删除订单配置信息',
});
api[0].list.push({
    alias: 'OrderReturnApplyController',
    order: '7',
    link: '订单退货申请controller',
    desc: '订单退货申请Controller',
    list: []
})
api[0].list[6].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderreturnapply/list',
    desc: '查询订单退货申请列表',
});
api[0].list[6].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderreturnapply/info/{id}',
    desc: '获取订单退货申请详情',
});
api[0].list[6].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderreturnapply/save',
    desc: '新增订单退货申请',
});
api[0].list[6].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderreturnapply/update',
    desc: '修改订单退货申请',
});
api[0].list[6].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderreturnapply/delete/{ids}',
    desc: '批量删除订单退货申请',
});
api[0].list.push({
    alias: 'OrderReturnReasonController',
    order: '8',
    link: '退货原因controller',
    desc: '退货原因Controller',
    list: []
})
api[0].list[7].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderreturnreason/list',
    desc: '查询退货原因列表',
});
api[0].list[7].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderreturnreason/info/{id}',
    desc: '获取退货原因详情',
});
api[0].list[7].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderreturnreason/save',
    desc: '新增退货原因',
});
api[0].list[7].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderreturnreason/update',
    desc: '修改退货原因',
});
api[0].list[7].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderreturnreason/delete/{ids}',
    desc: '批量删除退货原因',
});
api[0].list.push({
    alias: 'UndoLogController',
    order: '9',
    link: '撤销日志表controller',
    desc: '撤销日志表Controller',
    list: []
})
api[0].list[8].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/order/undolog/list',
    desc: '查询撤销日志表列表',
});
api[0].list[8].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/order/undolog/info/{id}',
    desc: '获取撤销日志表详情',
});
api[0].list[8].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/order/undolog/save',
    desc: '新增撤销日志表',
});
api[0].list[8].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/order/undolog/update',
    desc: '修改撤销日志表',
});
api[0].list[8].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/order/undolog/delete/{ids}',
    desc: '批量删除撤销日志表',
});
api[0].list.push({
    alias: 'PayWebController',
    order: '10',
    link: '阿里支付_-_controller',
    desc: '阿里支付 - Controller',
    list: []
})
api[0].list[9].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/aliPayOrder',
    desc: '用户下单: 支付宝支付  &lt;ul&gt;      &lt;li&gt;让支付页让浏览器展示&lt;/li&gt;      &lt;li&gt;支付成功以后，跳转到用户的订单列表页&lt;/li&gt;  &lt;/ul&gt;',
});
api[0].list.push({
    alias: 'IndexController',
    order: '11',
    link: '首页controller',
    desc: '首页Controller',
    list: []
})
api[0].list[10].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/',
    desc: '首页',
});
api[0].list.push({
    alias: 'MqMessageController',
    order: '12',
    link: 'mq消息表controller',
    desc: 'MQ消息表Controller',
    list: []
})
api[0].list[11].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/order/mqmessage/grace/list',
    desc: '精装版查询MQ消息表列表',
});
api[0].list[11].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/order/mqmessage/list',
    desc: '查询MQ消息表列表',
});
api[0].list[11].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/order/mqmessage/info/{messageId}',
    desc: '获取MQ消息表详情',
});
api[0].list[11].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/order/mqmessage/save',
    desc: '新增MQ消息表',
});
api[0].list[11].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/order/mqmessage/update',
    desc: '修改MQ消息表',
});
api[0].list[11].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://localhost:9010/order/mqmessage/delete/{messageIds}',
    desc: '批量删除MQ消息表',
});
api[0].list.push({
    alias: 'RefundInfoController',
    order: '13',
    link: '退款信息controller',
    desc: '退款信息Controller',
    list: []
})
api[0].list[12].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/order/refundinfo/list',
    desc: '查询退款信息列表',
});
api[0].list[12].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/order/refundinfo/info/{id}',
    desc: '获取退款信息详情',
});
api[0].list[12].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/order/refundinfo/save',
    desc: '新增退款信息',
});
api[0].list[12].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/order/refundinfo/update',
    desc: '修改退款信息',
});
api[0].list[12].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/order/refundinfo/delete/{ids}',
    desc: '批量删除退款信息',
});
api[0].list.push({
    alias: 'OrderItemController',
    order: '14',
    link: '订单项信息controller',
    desc: '订单项信息Controller',
    list: []
})
api[0].list[13].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderitem/list',
    desc: '查询订单项信息列表',
});
api[0].list[13].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderitem/info/{id}',
    desc: '获取订单项信息详情',
});
api[0].list[13].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderitem/save',
    desc: '新增订单项信息',
});
api[0].list[13].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderitem/update',
    desc: '修改订单项信息',
});
api[0].list[13].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://localhost:9010/order/orderitem/delete/{ids}',
    desc: '批量删除订单项信息',
});
api[0].list.push({
    alias: 'error',
    order: '15',
    link: 'error_code_list',
    desc: '错误码列表',
    list: []
})
api[0].list.push({
    alias: 'dict',
    order: '16',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[0].list[15].list.push({
    order: '1',
    deprecated: 'false',
    url: '',
    desc: '响应http状态码字典',
});
api[0].list[15].list.push({
    order: '2',
    deprecated: 'false',
    url: '',
    desc: '订单状态字典',
});
api[0].list[15].list.push({
    order: '3',
    deprecated: 'false',
    url: '',
    desc: '支付方式字典',
});
api[0].list[15].list.push({
    order: '4',
    deprecated: 'false',
    url: '',
    desc: '验证码请求来源字典',
});
api[0].list[15].list.push({
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