
$(function(){
    laydateInt();
    $('[data-toggle="tooltip"]').tooltip();
});

/******************************驱动执行区 begin***************************/


/******************************驱动执行区 end****************************/


/************ 表格是否选中一条数据 start **************/
function isSelectRow(_grid) {
    let rows = _grid.bootstrapTable('getSelections');
    let isSelectFlag = true;
    if (null == rows || rows.length == 0) {
        bs4pop.alert('请选中一条数据');
        isSelectFlag = false;
    }
    return isSelectFlag
}
/************ 表格是否选中一条数据 end **************/

/************ 获取table Index  start **************/
function getIndex(str) {
    return str.split('_')[1];
}
/************ 获取table Index  end ***************/

/************ HTML反转义 start **************/
function HTMLDecode(str) {
    var s = "";
    if (str.length == 0) return "";
    s = str.replace(/&amp;/g, "&");
    s = s.replace(/&lt;/g, "<");
    s = s.replace(/&gt;/g, ">");
    s = s.replace(/&nbsp;/g, " ");
    s = s.replace(/&#39;/g, "\'");
    s = s.replace(/&quot;/g, "\"");
    s = s.replace(/<br\/>/g, "\n");
    return s;
}

/************ HTML反转义 end **************/


/************ 初始化日期/时间 start **************/
function laydateInt() {
    lay('.laydate').each(function () {
        laydate.render({
            elem: this,
            trigger: 'click',
            type: 'date',
            theme: '#007bff',
            done: function (value, date) {
                isStartEndDatetime(this.elem, value);
            }
        });
    });
    lay('.laydatetime').each(function () {
        laydate.render({
            elem: this,
            trigger: 'click',
            type: 'datetime',
            theme: '#007bff',
            done: function (value, date) {
                isStartEndDatetime(this.elem, value);
            }
        });
    });
};

//开始结束时间对比
function isStartEndDatetime (el, value){
    let start, end;

    if ($(el).hasClass('laystart')) {
        start = moment(new Date(value), 'MM-DD-YYYY HH:mm:ss');
        if ($(el).parents('.form-group').find('.layend').length) {
            end = moment(new Date($(el).parents('.form-group').find('.layend:visible').val()), 'MM-DD-YYYY HH:mm:ss');
        } else {
            end = moment(new Date($('.layend:visible').val()), 'MM-DD-YYYY HH:mm:ss');
        }
    } else if ($(el).hasClass('layend')) {
        end = moment(new Date(value), 'MM-DD-YYYY HH:mm:ss');
        if ($(el).parents('.form-group').find('.laystart').length) {
            start = moment(new Date($(el).parents('.form-group').find('.laystart:visible').val()), 'MM-DD-YYYY HH:mm:ss');
        } else {
            start = moment(new Date($('.laystart:visible').val()), 'MM-DD-YYYY HH:mm:ss');
        }
    } else {
        return  false;
    }
    if (start.isAfter(end)) {
        bs4pop.alert('结束时间不能小于开始时间',{} ,function () {$(el).val('')});
    }
}
/************ 初始化日期/时间 end **************/


/*展开收起*/
$(document).on('hide.bs.collapse', 'form .collapse', function () {
    let id = $(this).attr('id');
    $('[data-target="#'+ id +'"]').html('展开 <i class="fa fa-angle-double-down" aria-hidden="true"></i>');
});
$(document).on('show.bs.collapse', 'form .collapse', function () {
    let id = $(this).attr('id');
    $('[data-target="#'+ id +'"]').html('收起 <i class="fa fa-angle-double-up" aria-hidden="true"></i>');
});

/**************************************时间格式化处理************************************/
function dateFtt(fmt,date) {
    var o = {
        "M+" : date.getMonth()+1,     //月份
        "d+" : date.getDate(),     //日
        "h+" : date.getHours(),     //小时
        "m+" : date.getMinutes(),     //分
        "s+" : date.getSeconds(),     //秒
        "q+" : Math.floor((date.getMonth()+3)/3), //季度
        "S" : date.getMilliseconds()    //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

/**
 * 通用查询列表页查询条件form清空
 */
function commonClearQueryForm(formId) {
    $('#' + formId + ' .form-control').val('');
}

/**
 * 通用格式化显示tip
 * @param value
 * @param row
 * @param index
 */
function formatForTip(value,row,index) {
    if (value){
        return "<span data-toggle='tooltip' data-placement='left' title='" + value + "'>" + value + "</span>";
    }
    return "";
}

/**
 * 根据provider获取数据，此方法是同步数据请求
 * @param data 请求参数
 * @returns {[]}
 */
function loadByProviderSync(data) {
    let resultData =[];
    $.ajax({
        type: "POST",
        url: "/convert/list.action",
        data: JSON.stringify(data),
        contentType: 'application/json',
        dataType: "json",
        async : false,
        success : function(ret) {
            resultData =  ret;
        },
        error : function() {
        }
    });
    return resultData;
}

/**
 * 根据provider获取数据，此方法是异步数据请求
 * @param data 请求参数
 * @param first 是否选择第一个
 * @param name 绑定的控件名称
 * @returns {[]}
 */
function loadDataByProviderAsync(data, first, name) {
    return new Promise((resolve, reject) => {
        axios.post('/convert/list.action', data)
            .then((res) => {
                if (first) {
                    if (!app.formData[name]) {
                        app.formData[name] = res.data[0].value;
                    }
                }
                resolve(res.data);
            })
            .catch(function (error) {
                reject(error);
            });
    });
}

/**
 * 根据Provider 获取回显值
 * @param data Provider 查询条件
 * @returns {*}
 */
function loadProviderDisplayText(data) {
    let result = "";
    $.ajax({
        type: "POST",
        url: '/valueProvider/getDisplayText.action',
        data: JSON.stringify(data),
        processData:true,
        contentType: 'application/json',
        dataType: "json",
        async : false,
        success: function (ret) {
            result = ret.data;
        },
        error: function(a,status,e){
            console.error("出错了");
        }
    });
    return result;
}