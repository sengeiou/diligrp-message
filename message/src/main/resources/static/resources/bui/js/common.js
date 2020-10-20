/**
 *
 * @Date 2019-02-22 21:32:00
 * @author chenliangfang
 *
 ***/

/************  start **************/
/************  end ***************/


$(function(){
    laydateInt();
    $('[data-toggle="tooltip"]').tooltip();
});

/******************************驱动执行区 begin***************************/
/*$(function () {
    $(window).resize(function () {
        _grid.bootstrapTable('resetView')
    });
    queryDataHandler();
});*/

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