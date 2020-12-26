<script type="text/javascript">

    var flagCount = 0;

    // 模板
    var templateOrigin = '<div class="template-group">\n' +
        '                    <h4 class="template-group-title">模板</h4>\n' +
        '                    <input type="hidden" name="templateId" id="templateId" >\n' +
        '                    <div class="template-item">\n' +
        '                        <input class="easyui-combobox"  id="channel" name="channel" style="width:100%" labelAlign="right" panelHeight="auto" editable="false" required="true" data-options="labelWidth:110,cls: \'channel\',onLoadSuccess:onComboLoadSuccessSelectOne,onChange: originChange,label:\'&lowast;模板来源:\',\n' +
        '                            url:\'${contextPath}/provider/getLookupList.action\',\n' +
        '                            method:\'POST\',\n' +
        '                            queryParams: {provider: \'marketChannelProvider\',queryParams:\'{required:true}\'}" />\n' +
        '                    </div>\n' +
        '                    <div class="template-item">\n' +
        '                        <input class="easyui-combobox" id="marketChannelIds" name="marketChannelIds" style="width:100%" labelAlign="right" panelHeight="auto" editable="false" required="true" data-options="labelWidth:110,label:\'&lowast;AccessKey:\',multiple:true" />\n' +
        '                    </div>\n' +
        '                    <div class="template-item">\n' +
        '                        <input class="easyui-textbox" name="templateName" style="width:100%" labelAlign="right" data-options="labelWidth:110,label:\'模板名称:\',validType:\'length[0,50]\'" />\n' +
        '                    </div>\n' +
        '                    <div class="template-item">\n' +
        '                        <input class="easyui-textbox" name="templateCode" style="width:100%" labelAlign="right" data-options="labelWidth:110,label:\'模板CODE:\',validType:\'length[0,20]\'" />\n' +
        '                    </div>\n' +
        '                    <div class="template-item">\n' +
        '                        <input class="easyui-textbox" name="templateContent" style="width:100%" labelAlign="right" required="true" data-options="labelWidth:110,label:\'&lowast;模板内容:\', height: \'80px\', multiline: true,validType:\'length[1,255]\'" />\n' +
        '                    </div>\n' +
        '                    <div class="edit-limit">\n' +
        '                        <a href="#" class="easyui-linkbutton template-plus-btn" data-options="iconCls:\'icon-add\'">增加</a>\n' +
        '                        <a href="#" class="easyui-linkbutton template-minus-btn" data-options="iconCls:\'icon-remove\'">删除</a>\n' +
        '                    </div>\n' +
        '            </div>';



    // 添加一个模板
    $('.templatewrap').on('click', '.template-plus-btn', function(){
        if($('.template-group').length < 10) {
            let id = new Date().valueOf();
            let template = $('.template-group-origin .template-group').clone(true, true);
            $(template).attr('id', id);
            $(template).find('.template-item [name]').each(function(index, el){
                let name = $(el).attr('name') + flagCount;
                $(el).attr('name', name);
            });
            $('.templatewrap').append(templateOrigin);
            $.parser.parse('#templatewrap .template-group:last-child');
            flagCount++;
        }
    });

    // 删除一个模板
    $('.templatewrap').on('click', '.template-minus-btn', function(){
        if($('.template-group').length > 1) {
            console.log('minus:-----');
            $(this).parents('.template-group').remove();
            $('.templatewrap .template-group:last-child');
        }
    });


    // 保存数据
    $('#record-save-btn').linkbutton({
        onClick: function(){

            // 保存的数据
            var saveData = {};
            // 所有模板数据集
            var templateData = {'templateList': []};

            if(!$('#_form').form("validate")){
                return;
            }
            let baseData = $('#_form .template-base input').serializeObject();
            $('#_form .template-group').each(function(index,el){
                let group = $(el).find('input').serializeObject();
                if (Object.getOwnPropertyNames(group).length) {
                    templateData.templateList.push(group);
                }
            });
            saveData = Object.assign({},baseData,templateData);
            console.log(saveData);
            $.ajax({
                type: "POST",
                url: "${contextPath}/triggers/save.action",
                data: {jsonParams:JSON.stringify(saveData)},
                processData:true,
                dataType: "json",
                traditional: true,
                async : true,
                success: function (ret) {
                    if(ret.success){
                        window.location.href = "${contextPath}/triggers/list.html";
                    }else{
                        saveData = {};
                        swal('错误',ret.result, 'error');
                    }
                },
                error: function(){
                    saveData = {};
                    swal('错误', '远程访问失败', 'error');
                }
            });

        }
    });

</script>