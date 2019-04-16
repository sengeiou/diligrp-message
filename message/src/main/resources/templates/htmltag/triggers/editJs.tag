<script type="text/javascript">

    var flagCount = 0;
    $('.template-group-origin').css({height: 0, overflow: 'hidden'});


    // 模板
    var templateOrigin = '<div class="template-group-origin">\n' +
        '                <div class="template-group">\n' +
        '                    <h4 class="template-group-title">模板</h4>\n' +
        '                    <input type="hidden" name="templateId" >\n' +
        '                    <div class="template-item">\n' +
        '                        <input class="easyui-combobox" name="channel" style="width:100%" labelAlign="right" data-options="label:\'模板来源:\',\n' +
        '                            url:\'${contextPath}/provider/getLookupList.action\',\n' +
        '                            method:\'POST\',\n' +
        '                            queryParams: {provider: \'firmProvider\'}" />\n' +
        '                    </div>\n' +
        '                    <div class="template-item">\n' +
        '                        <input class="easyui-combobox" name="marketChannelIds" style="width:100%" labelAlign="right" data-options="label:\'AccessKey:\',multiple:true" />\n' +
        '                    </div>\n' +
        '                    <div class="template-item">\n' +
        '                        <input class="easyui-textbox" name="templateName" style="width:100%" labelAlign="right" data-options="label:\'模板名称:\'" />\n' +
        '                    </div>\n' +
        '                    <div class="template-item">\n' +
        '                        <input class="easyui-textbox" name="templateCode" style="width:100%" labelAlign="right" data-options="label:\'模板CODE:\'" />\n' +
        '                    </div>\n' +
        '                    <div class="template-item">\n' +
        '                        <input class="easyui-textbox" name="templateContent" style="width:100%" labelAlign="right" data-options="label:\'模板内容:\', height: \'80px\', multiline: true" />\n' +
        '                    </div>\n' +
        '                    <div class="edit-limit">\n' +
        '                        <a href="#" class="easyui-linkbutton template-plus-btn" data-options="iconCls:\'icon-add\'">add</a>\n' +
        '                        <a href="#" class="easyui-linkbutton template-minus-btn" data-options="iconCls:\'icon-add\'"></a>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>';



    // 添加一个模板
    $('.templatewrap').on('click', '.template-plus-btn', function(){
        if($('.template-group').length < 10) {
            /*let id = new Date().valueOf();
            let template = $('.template-group-origin .template-group').clone(true, true);
            $(template).attr('id', id);
            $(template).find('.template-item [name]').each(function(index, el){
                let name = $(el).attr('name') + flagCount;
                $(el).attr('name', name);
            });*/
            $('.templatewrap').append(templateOrigin);
            $.parser.parse('.template-group:last-child');
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

    // 保存的数据
    var saveData = {};
    // 所有模板数据集
    var templateData = {'templateList': []};

    // 保存数据
    $('#record-save-btn').linkbutton({
        onClick: function(){
            let baseData = $('#_form .template-base input').serializeObject();
            $('#_form .template-group').each(function(index,el){
                let group = $(el).find('input').serializeObject();
                if (Object.getOwnPropertyNames(group).length) {
                    templateData.templateList.push(group);
                }
            });
            saveData = Object.assign({},baseData,templateData);
            console.log(saveData);
        }
    })


</script>