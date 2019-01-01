$(function () {
    layui.use(['form', 'layedit', 'laydate'], function() {
        var form = layui.form
            , laydate = layui.laydate;

        //日期
        laydate.render({
            elem: '#signDate'
        });
        laydate.render({
            elem: '#deliveryDate'
        });
        laydate.render({
            elem: '#validityDate'
        });
    });


    layui.use(['form' ,'layer'], function(){
        var form = layui.form;
        var layer=layui.layer;

        //监听提交
        form.on('submit(saveBuyingContract)', function(data){
            var formElements = $('#buyingContractForm input:not(".textbox-text,.textbox-value")');
            var formData = {contract:{},details:[]};
            formElements.each(function (i,ele) {
                if(ele.type === 'text') {
                    formData.contract[ele.name] = $(ele).val();
                } else if(ele.type === 'checkbox') {
                    if(ele.checked == true) {
                        if(formData.contract[ele.name] == null) {
                            formData.contract[ele.name] = ","+$(ele).val()+",";
                        } else {
                            formData.contract[ele.name] += $(ele).val() + ",";
                        }
                    }
                } else {
                    formData.contract[ele.name] = $(ele).val();
                }
            });
            formData.details = getChanges();
            $.ajax({
                "url": "/buyingcontract/save",
                "type": "post",
                "data": JSON.stringify(formData),
                "contentType": "application/json",
                "success": function (data) {
                    if(data == "ok") {
                        layer.alert("操作成功", function () {
                            layer.closeAll();
                            load();
                        });
                    }
                }
            });
            return false;
        });
        form.render();
    });
});


