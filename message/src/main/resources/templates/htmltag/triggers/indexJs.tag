<script type="text/javascript">
    //打开新增窗口
    function openInsert(){
        window.location.href="${contextPath}/triggers/add.html";
    }

    //打开修改窗口
    function openUpdate(){
        var selected = $("#triggerGrid").datagrid("getSelected");
        if (null == selected) {
            swal('警告','请选中一条数据', 'warning');
            return;
        }
        $('#dlg').dialog('open');
        $('#dlg').dialog('center');
        formFocus("_form", "_triggerCode");
        var formData = $.extend({},selected);
        formData = addKeyStartWith(getOriginalData(formData),"_");
        $('#_form').form('load', formData);
    }

    function saveOrUpdate(){
        if(!$('#_form').form("validate")){
            return;
        }
        var _formData = removeKeyStartWith($("#_form").serializeObject(),"_");
        var _url = null;
        //没有id就新增
        if(_formData.id == null || _formData.id==""){
            _url = "${contextPath}/triggers/insert.action";
        }else{//有id就修改
            _url = "${contextPath}/triggers/update.action";
        }
        $.ajax({
            type: "POST",
            url: _url,
            data: _formData,
            processData:true,
            dataType: "json",
            async : true,
            success: function (data) {
                if(data.code=="200"){
                    $("#triggerGrid").datagrid("reload");
                    $('#dlg').dialog('close');
                }else{
                    swal('错误',data.result, 'error');
                }
            },
            error: function(){
                swal('错误', '远程访问失败', 'error');
            }
        });
    }

    //根据主键删除
    function del() {
        var selected = $("#triggerGrid").datagrid("getSelected");
        if (null == selected) {
            swal('警告','请选中一条数据', 'warning');
            return;
        }
        <#swalConfirm swalTitle="您确认想要删除记录吗？">
                $.ajax({
                    type: "POST",
                    url: "${contextPath}/triggers/delete.action",
                    data: {id:selected.id},
                    processData:true,
                    dataType: "json",
                    async : true,
                    success: function (data) {
                        if(data.code=="200"){
                            $("#triggerGrid").datagrid("reload");
                            $('#dlg').dialog('close');
                        }else{
                            swal('错误',data.result, 'error');
                        }
                    },
                    error: function(){
                        swal('错误', '远程访问失败', 'error');
                    }
                });
        </#swalConfirm>
    }
    //表格查询
    function queryTriggerGrid() {
        var opts = $("#triggerGrid").datagrid("options");
        if (null == opts.url || "" == opts.url) {
            opts.url = "${contextPath}/triggers/listPage.action";
        }
        if(!$('#queryForm').form("validate")){
            return;
        }
        $("#triggerGrid").datagrid("load", bindGridMeta2Form("triggerGrid", "queryForm"));
    }


    //清空表单
    function clearQueryForm() {
        $('#queryForm').form('clear');
    }

    //表格表头右键菜单
    function headerContextMenu(e, field){
        e.preventDefault();
        if (!cmenu){
            createColumnMenu("triggerGrid");
        }
        cmenu.menu('show', {
            left:e.pageX,
            top:e.pageY
        });
    }

    //全局按键事件
    function getKey(e){
        e = e || window.event;
        var keycode = e.which ? e.which : e.keyCode;
        if(keycode == 46){ //如果按下删除键
            var selected = $("#triggerGrid").datagrid("getSelected");
            if(selected && selected!= null){
                del();
            }
        }
    }

    /**
     * 绑定页面回车事件，以及初始化页面时的光标定位
     * @formId
     *          表单ID
     * @elementName
     *          光标定位在指点表单元素的name属性的值
     * @submitFun
     *          表单提交需执行的任务
     */
    $(function () {
        if (document.addEventListener) {
            document.addEventListener("keyup",getKey,false);
        } else if (document.attachEvent) {
            document.attachEvent("onkeyup",getKey);
        } else {
            document.onkeyup = getKey;
        }
        initTriggersGrid();
        queryTriggerGrid();

    });

    /**
     * 初始化grid
     */
    function initTriggersGrid(){
        var pager = $("#triggerGrid").datagrid('getPager');
        pager.pagination({
            <#controls_paginationOpts/>,
            buttons:[
                <#resource code="viewTrigger">
                    {
                        iconCls:'icon-detail',
                        text:'详情',
                        handler:function(){
                            openDetail();
                        }
                    },
                </#resource>
                <#resource code="addTrigger">
                    {
                        iconCls:'icon-add',
                        text:'新增',
                        handler:function(){
                            openInsert();
                        }
                    },
                </#resource>
                <#resource code="editTrigger">
                     {
                         iconCls:'icon-edit',
                         text:'编辑',
                         handler:function(){
                             openUpdate();
                         }
                     },
                </#resource>
                <#resource code="enabledTrigger">
                    {
                        iconCls:'icon-play',
                        text:'启用',
                        id:'play_btn',
                        disabled:true,
                        handler:function(){
                            doEnable(true);
                        }
                    },
                </#resource>
                <#resource code="disabledTrigger">
                    {
                        iconCls:'icon-stop',
                        text:'禁用',
                        id:'stop_btn',
                        disabled:true,
                        handler:function(){
                            doEnable(false);
                        }
                    },
                </#resource>
                <#resource code="exportTrigger">
                    {
                        iconCls:'icon-export',
                        text:'导出',
                        handler:function(){
                            doExport('triggerGrid');
                        }
                    }
                </#resource>
            ]
        });
        //表格仅显示下边框
        $("#triggerGrid").datagrid('getPanel').removeClass('lines-both lines-no lines-right lines-bottom').addClass("lines-bottom");
    }
</script>