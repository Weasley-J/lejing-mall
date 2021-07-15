let api = [];
api.push({
    alias: 'HelloRabbitController',
    order: '1',
    link: '测试rabbit_-_controller',
    desc: '测试Rabbit - Controller',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '',
});
api[0].list.push({
    order: '2',
    desc: '送消息给MQ',
});
api[0].list.push({
    order: '3',
    desc: '测试创建订单发送消息给 ORDER_EVENT_RELEASE_ORDER_QUEUE 队列',
});
api[0].list.push({
    order: '4',
    desc: '',
});
api.push({
    alias: 'OrderAppController',
    order: '2',
    link: '订单业务_controller',
    desc: '订单业务 Controller',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '去订单结算确认页',
});
api[1].list.push({
    order: '2',
    desc: '提交订单结算',
});
api.push({
    alias: 'IndexController',
    order: '3',
    link: '首页controller',
    desc: '首页Controller',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '首页',
});
api.push({
    alias: 'MqMessageController',
    order: '4',
    link: 'mq消息表controller',
    desc: 'MQ消息表Controller',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '精装版查询MQ消息表列表',
});
api[3].list.push({
    order: '2',
    desc: '查询MQ消息表列表',
});
api[3].list.push({
    order: '3',
    desc: '获取MQ消息表详情',
});
api[3].list.push({
    order: '4',
    desc: '新增MQ消息表',
});
api[3].list.push({
    order: '5',
    desc: '修改MQ消息表',
});
api[3].list.push({
    order: '6',
    desc: '批量删除MQ消息表',
});
api.push({
    alias: 'OrderController',
    order: '5',
    link: '订单controller',
    desc: '订单Controller',
    list: []
})
api[4].list.push({
    order: '1',
    desc: '根据订单号查询订单状态',
});
api[4].list.push({
    order: '2',
    desc: '获取当前登录用的订单数据&lt;ul&gt;    &lt;li&gt;用户信息从拦截器里面取&lt;/li&gt;&lt;/ul&gt;',
});
api[4].list.push({
    order: '3',
    desc: '查询订单列表',
});
api[4].list.push({
    order: '4',
    desc: '获取订单详情',
});
api[4].list.push({
    order: '5',
    desc: '新增订单',
});
api[4].list.push({
    order: '6',
    desc: '修改订单',
});
api[4].list.push({
    order: '7',
    desc: '批量删除订单',
});
api.push({
    alias: 'OrderItemController',
    order: '6',
    link: '订单项信息controller',
    desc: '订单项信息Controller',
    list: []
})
api[5].list.push({
    order: '1',
    desc: '查询订单项信息列表',
});
api[5].list.push({
    order: '2',
    desc: '获取订单项信息详情',
});
api[5].list.push({
    order: '3',
    desc: '新增订单项信息',
});
api[5].list.push({
    order: '4',
    desc: '修改订单项信息',
});
api[5].list.push({
    order: '5',
    desc: '批量删除订单项信息',
});
api.push({
    alias: 'OrderOperateHistoryController',
    order: '7',
    link: '订单操作历史记录controller',
    desc: '订单操作历史记录Controller',
    list: []
})
api[6].list.push({
    order: '1',
    desc: '查询订单操作历史记录列表',
});
api[6].list.push({
    order: '2',
    desc: '获取订单操作历史记录详情',
});
api[6].list.push({
    order: '3',
    desc: '新增订单操作历史记录',
});
api[6].list.push({
    order: '4',
    desc: '修改订单操作历史记录',
});
api[6].list.push({
    order: '5',
    desc: '批量删除订单操作历史记录',
});
api.push({
    alias: 'OrderReturnApplyController',
    order: '8',
    link: '订单退货申请controller',
    desc: '订单退货申请Controller',
    list: []
})
api[7].list.push({
    order: '1',
    desc: '查询订单退货申请列表',
});
api[7].list.push({
    order: '2',
    desc: '获取订单退货申请详情',
});
api[7].list.push({
    order: '3',
    desc: '新增订单退货申请',
});
api[7].list.push({
    order: '4',
    desc: '修改订单退货申请',
});
api[7].list.push({
    order: '5',
    desc: '批量删除订单退货申请',
});
api.push({
    alias: 'OrderReturnReasonController',
    order: '9',
    link: '退货原因controller',
    desc: '退货原因Controller',
    list: []
})
api[8].list.push({
    order: '1',
    desc: '查询退货原因列表',
});
api[8].list.push({
    order: '2',
    desc: '获取退货原因详情',
});
api[8].list.push({
    order: '3',
    desc: '新增退货原因',
});
api[8].list.push({
    order: '4',
    desc: '修改退货原因',
});
api[8].list.push({
    order: '5',
    desc: '批量删除退货原因',
});
api.push({
    alias: 'OrderSettingController',
    order: '10',
    link: '订单配置信息controller',
    desc: '订单配置信息Controller',
    list: []
})
api[9].list.push({
    order: '1',
    desc: '查询订单配置信息列表',
});
api[9].list.push({
    order: '2',
    desc: '获取订单配置信息详情',
});
api[9].list.push({
    order: '3',
    desc: '新增订单配置信息',
});
api[9].list.push({
    order: '4',
    desc: '修改订单配置信息',
});
api[9].list.push({
    order: '5',
    desc: '批量删除订单配置信息',
});
api.push({
    alias: 'PayWebController',
    order: '11',
    link: '阿里支付_-_controller',
    desc: '阿里支付 - Controller',
    list: []
})
api[10].list.push({
    order: '1',
    desc: '用户下单: 支付宝支付&lt;ul&gt;    &lt;li&gt;让支付页让浏览器展示&lt;/li&gt;    &lt;li&gt;支付成功以后，跳转到用户的订单列表页&lt;/li&gt;&lt;/ul&gt;',
});
api.push({
    alias: 'PaymentInfoController',
    order: '12',
    link: '支付信息表controller',
    desc: '支付信息表Controller',
    list: []
})
api[11].list.push({
    order: '1',
    desc: '查询支付信息表列表',
});
api[11].list.push({
    order: '2',
    desc: '获取支付信息表详情',
});
api[11].list.push({
    order: '3',
    desc: '新增支付信息表',
});
api[11].list.push({
    order: '4',
    desc: '修改支付信息表',
});
api[11].list.push({
    order: '5',
    desc: '批量删除支付信息表',
});
api.push({
    alias: 'RefundInfoController',
    order: '13',
    link: '退款信息controller',
    desc: '退款信息Controller',
    list: []
})
api[12].list.push({
    order: '1',
    desc: '查询退款信息列表',
});
api[12].list.push({
    order: '2',
    desc: '获取退款信息详情',
});
api[12].list.push({
    order: '3',
    desc: '新增退款信息',
});
api[12].list.push({
    order: '4',
    desc: '修改退款信息',
});
api[12].list.push({
    order: '5',
    desc: '批量删除退款信息',
});
api.push({
    alias: 'UndoLogController',
    order: '14',
    link: '撤销日志表controller',
    desc: '撤销日志表Controller',
    list: []
})
api[13].list.push({
    order: '1',
    desc: '查询撤销日志表列表',
});
api[13].list.push({
    order: '2',
    desc: '获取撤销日志表详情',
});
api[13].list.push({
    order: '3',
    desc: '新增撤销日志表',
});
api[13].list.push({
    order: '4',
    desc: '修改撤销日志表',
});
api[13].list.push({
    order: '5',
    desc: '批量删除撤销日志表',
});
api.push({
    alias: 'error',
    order: '15',
    link: 'error_code_list',
    desc: '错误码列表',
    list: []
})
api.push({
    alias: 'dict',
    order: '16',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[15].list.push({
    order: '1',
    desc: '响应http状态码字典',
});
api[15].list.push({
    order: '2',
    desc: '订单状态字典',
});
api[15].list.push({
    order: '3',
    desc: '支付方式字典',
});
api[15].list.push({
    order: '4',
    desc: '验证码请求来源字典',
});
api[15].list.push({
    order: '5',
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