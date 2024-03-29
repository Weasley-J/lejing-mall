let api = [];
const apiDocListSize = 1
api.push({
    name: 'default',
    order: '1',
    list: []
})
api[0].list.push({
    alias: 'SmsController',
    order: '1',
    link: '短信controller',
    desc: '短信Controller',
    list: []
})
api[0].list[0].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:88/api/thirdparty/sms/sendCode',
    desc: '发送验证码给用户手机',
});
api[0].list[0].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:88/api/thirdparty/sms/sendStatus',
    desc: '查询短信发送详情',
});
api[0].list.push({
    alias: 'OssPolicyController',
    order: '2',
    link: 'oss_policy文件上传controller',
    desc: 'OSS Policy文件上传Controller',
    list: []
})
api[0].list[1].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:88/api/thirdparty/oss/policy',
    desc: '获取OSS文件上传服务端签名',
});
api[0].list.push({
    alias: 'OssRpcController',
    order: '3',
    link: 'oss远程rpc文件上传controller',
    desc: 'Oss远程RPC文件上传Controller',
    list: []
})
api[0].list[2].list.push({
    order: '1',
    deprecated: 'false',
    url: 'http://127.0.0.1:88/api/thirdparty/oss/rpc/bucket/create',
    desc: '创建存储空间',
});
api[0].list[2].list.push({
    order: '2',
    deprecated: 'false',
    url: 'http://127.0.0.1:88/api/thirdparty/oss/rpc/bucket/delete',
    desc: '删除存储空间',
});
api[0].list[2].list.push({
    order: '3',
    deprecated: 'false',
    url: 'http://127.0.0.1:88/api/thirdparty/oss/rpc/upload',
    desc: '上传文件至OSS &lt;p&gt; 1. 转存网络文件 2. 上传本地文件到时需要指定文件完整路径 &lt;/p&gt;',
});
api[0].list[2].list.push({
    order: '4',
    deprecated: 'false',
    url: 'http://127.0.0.1:88/api/thirdparty/oss/rpc/upload/multipart/file',
    desc: '上传文件至OSS（普通上传）',
});
api[0].list[2].list.push({
    order: '5',
    deprecated: 'false',
    url: 'http://127.0.0.1:88/api/thirdparty/oss/rpc/delete/single',
    desc: '删除单个文件',
});
api[0].list[2].list.push({
    order: '6',
    deprecated: 'false',
    url: 'http://127.0.0.1:88/api/thirdparty/oss/rpc/delete/many',
    desc: '批量删除文件',
});
api[0].list[2].list.push({
    order: '7',
    deprecated: 'false',
    url: 'http://127.0.0.1:88/api/thirdparty/oss/rpc/file/exist/{objectUrl}',
    desc: '判断文件是否存在',
});
api[0].list[2].list.push({
    order: '8',
    deprecated: 'false',
    url: 'http://127.0.0.1:88/api/thirdparty/oss/rpc/bucket/exist/{bucketName}',
    desc: '判断OSS的全局命名空间(存储空间)是否存在',
});
api[0].list.push({
    alias: 'error',
    order: '4',
    link: 'error_code_list',
    desc: '错误码列表',
    list: []
})
api[0].list.push({
    alias: 'dict',
    order: '5',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
api[0].list[4].list.push({
    order: '1',
    deprecated: 'false',
    url: '',
    desc: '响应http状态码字典',
});
api[0].list[4].list.push({
    order: '2',
    deprecated: 'false',
    url: '',
    desc: '验证码请求来源字典',
});
api[0].list[4].list.push({
    order: '3',
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