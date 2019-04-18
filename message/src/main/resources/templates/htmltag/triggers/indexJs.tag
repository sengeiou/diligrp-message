<script type="text/javascript">
    //打开新增窗口
    function openInsert(){
        window.location.href="${contextPath}/triggers/toEdit.html";
    }

    //打开修改窗口
    function openUpdate(){
        var selected = $("#triggerGrid").datagrid("getSelected");
        if (null == selected) {
            swal('警告','请选中一条数据', 'warning');
            return;
        }
        window.location.href="${contextPath}/triggers/toEdit.html?id="+selected["id"];
    }

    /**
     * datagrid行点击事件
     * 目前用于来判断 启禁用是否可点
     */
    function onClickRow(index,row) {
        var state = row.$_enabled;
        if (state == 0){
            //当用户状态为 禁用，可操作 启用
            $('#play_btn').linkbutton('enable');
            $('#stop_btn').linkbutton('disable');
        }else if(state == 1){
            //当用户状态为正常时，则只能操作 禁用
            $('#stop_btn').linkbutton('enable');
            $('#play_btn').linkbutton('disable');
        } else{
            //其它情况，按钮不可用
            $('#stop_btn').linkbutton('disable');
            $('#play_btn').linkbutton('disable');
        }
    }

    //根据主键删除
    function del() {
        var selected = $("#triggerGrid").datagrid("getSelected");
        if (null == selected) {
            swal({
                title: '警告',
                text: '请选中一条数据',
                type: 'warning',
                width: 300,
            });
            return;
        }
        <#swalConfirm swalTitle="删除后，将无法推送消息！是否确定删除？">
            $.ajax({
                type: "POST",
                url: "${contextPath}/triggers/delete.action",
                data: {id:selected.id},
                processData:true,
                dataType: "json",
                async : true,
                success: function (data) {
                    if(data.success){
                        $("#triggerGrid").datagrid("reload");
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

    /**
     * 禁启用操作
     * @param enable 是否启用:true-启用
     */
    function doEnable(enable) {
        var selected = $("#triggerGrid").datagrid("getSelected");
        if (null == selected) {
            swal({
                title: '警告',
                text: '请选中一条数据',
                type: 'warning',
                width: 300,
            });
            return;
        }
        var msg = (enable || 'true' == enable) ? '请确认是否启用' : '请确认是否禁用';
        swal({
            title: msg,
            type: 'question',
            showCancelButton: true,
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
        }).then((result) => {
            if(result.value){
                $.ajax({
                    type: "POST",
                    url: "${contextPath}/triggers/doEnable.action",
                    data: {id: selected.id, enable: enable},
                    processData:true,
                    dataType: "json",
                    async : true,
                    success: function (ret) {
                        if(ret.success){
                            $("#triggerGrid").datagrid("reload");
                            $('#stop_btn').linkbutton('disable');
                            $('#play_btn').linkbutton('disable');
                        }else{
                            swal(
                                '错误',
                                ret.result,
                                'error'
                            );
                        }
                    },
                    error: function(){
                        swal(
                            '错误',
                            '远程访问失败',
                            'error'
                        );
                    }
                });
            }
        });
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
                <#resource code="deleteTrigger">
                    {
                        iconCls:'icon-remove',
                        text:'删除',
                        handler:function(){
                            del();
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