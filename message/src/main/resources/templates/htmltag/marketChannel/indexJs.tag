<script type="text/javascript">

    function saveOrUpdate(){
        if(!$('#_form').form("validate")){
            return;
        }
        var _formData = removeKeyStartWith($("#_form").serializeObject(),"_");
        var _url = null;
        //没有id就新增
        if(_formData.id == null || _formData.id==""){
            _url = "${contextPath}/marketChannel/insert.action";
        }else{//有id就修改
            _url = "${contextPath}/marketChannel/update.action";
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
                    $("#marketChannelGrid").datagrid("reload");
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
        var selected = $("#marketChannelGrid").datagrid("getSelected");
        if (null == selected) {
            swal('警告','请选中一条数据', 'warning');
            return;
        }
    <#swalConfirm swalTitle="您确认想要删除记录吗？">
            $.ajax({
                type: "POST",
                url: "${contextPath}/marketChannel/delete.action",
                data: {id:selected.id},
                processData:true,
                dataType: "json",
                async : true,
                success: function (data) {
                    if(data.code=="200"){
                        $("#marketChannelGrid").datagrid("reload");
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
    function queryGrid() {
        var opts = $("#marketChannelGrid").datagrid("options");
        if (null == opts.url || "" == opts.url) {
            opts.url = "${contextPath}/marketChannel/list.action";
        }
        if(!$('#form').form("validate")){
            return;
        }
        $("#marketChannelGrid").datagrid("load", bindGridMeta2Form("marketChannelGrid", "form"));
    }


    //清空表单
    function clearForm() {
        $('#form').form('clear');
    }

    //表格表头右键菜单
    function headerContextMenu(e, field){
        e.preventDefault();
        if (!cmenu){
            createColumnMenu("marketChannelGrid");
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
            var selected = $("#marketChannelGrid").datagrid("getSelected");
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
        bindFormEvent("_form", "_marketCode", queryGrid);
        bindFormEvent("_form", "_marketCode", saveOrUpdate, function (){$('#dlg').dialog('close');});
        if (document.addEventListener) {
            document.addEventListener("keyup",getKey,false);
        } else if (document.attachEvent) {
            document.attachEvent("onkeyup",getKey);
        } else {
            document.onkeyup = getKey;
        }
        var pager = $('#marketChannelGrid').datagrid('getPager');    // get the pager of treegrid
        pager.pagination({
            <#controls_paginationOpts/>,
            buttons:[
            {
                iconCls:'icon-export',
                text:'导出',
                handler:function(){
                    doExport('marketChannelGrid');
                }
            }
        ]
    });

        initMarketChannelDetailGrid();
        var pagerDetail = $('#marketChannelDetailGrid').datagrid('getPager');    // get the pager of treegrid
        pagerDetail.pagination({
            <#controls_paginationOpts/>,
            buttons:[
            {
                iconCls:'icon-add',
                text:'新增',
                handler:function(){
                    $("#marketChannelDetailGrid").dataGridEditor().insert();
                }
            },
            {
                iconCls:'icon-edit',
                text:'修改',
                handler:function(){
                    $("#marketChannelDetailGrid").dataGridEditor().update();
                }
            },
            {
                iconCls:'icon-remove',
                text:'删除',
                handler:function(){
                    del();
                }
            },
            {
                iconCls:'icon-ok',
                text:'保存',
                handler:function(){
                    $("#marketChannelDetailGrid").dataGridEditor().save();
                }
            }
        ]
    });
        //表格仅显示下边框
        $('#marketChannelGrid').datagrid('getPanel').removeClass('lines-both lines-no lines-right lines-bottom').addClass("lines-bottom");
        queryGrid();
    })




    function initMarketChannelDetailGrid() {
        //设置grid的可编辑信息
        $("#marketChannelDetailGrid").dataGridEditor({
            insertUrl: "${contextPath!}/marketChannel/insert.action",
            updateUrl: "${contextPath!}/marketChannel/update.action",
            deleteUrl: '${contextPath!}/marketChannel/delete.action',
            // onBeginEdit: function (index,row) {
            //     var editors = $("#marketChannelDetailGrid").datagrid('getEditors', index);
            //     editors[0].target.trigger('focus');
            //     //获取市场字段的编辑框
            //     var ed = $("#marketChannelDetailGrid").datagrid('getEditor', {index:index,field:'marketCode'});
            //     //存在ID，数据编辑情况下,市场信息不可更改
            //     if (row.id != 'temp'){
            //         var obj = {};
            //         obj.code = row.$_firmCode;
            //         obj.name = row.firmCode;
            //         var datas = [];
            //         datas.push(obj);
            //         $(ed.target).combobox('loadData', datas);
            //     }else{
            //         //新增数据时，加载市场信息
            //         $(ed.target).combobox("loadData", firms);
            //     }
            //     // firmLoadSuccess(index);
            //     // setOptBtnDisplay(true);
            // },
            onEndEdit: function () {
                // setOptBtnDisplay(false);
            },
            onSaveSuccess: function (row, data) {
                // roleGrid.datagrid("load");
            }
        });
    }
</script>