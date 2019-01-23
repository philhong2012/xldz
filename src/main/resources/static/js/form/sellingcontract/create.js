$(function () {
    layui.use(['form', 'layedit', 'laydate'], function() {
        var form = layui.form
            , layer = layui.layer
            , layedit = layui.layedit
            , laydate = layui.laydate;

        //日期
        laydate.render({
            elem: '#packingExpiredDate'
        });
        laydate.render({
            elem: '#payingExpiredDate'
        });
        laydate.render({
            elem: '#signDate'
        });
    });


    layui.use(['form' ,'layer'], function(){
        var form = layui.form;
        var layer=layui.layer;





        //console.log(formData);
        //return;

        //监听提交
        form.on('submit(saveSellingContract)', function(data){

            var formElements = $('#sellingContractForm input:not(".textbox-text,.textbox-value")');
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
            //console.log(formData);
            var changes = getChanges();
            formData.details = changes.insertOrUpdate;
            formData.toDeletes = changes.deleted;

            $.ajax({
                "url": "/sellingcontract/save",
                "type": "post",
                "data": JSON.stringify(formData),
                "contentType": "application/json",
                "success": function (data) {
                    if(data == "ok") {
                        layer.alert("操作成功",{time: 1000 }, function () {
                            //layer.closeAll();
                            //load();
                        });
                    }
                }
            });

            /*return;
            $.ajax({
                type: "POST",
                data: $("#sellingContractForm").serialize(),
                url: "/sellingcontract/save",
                success: function (data) {
                    if (data == "ok") {
                        layer.alert("操作成功",function(){
                            layer.closeAll();
                            load();
                        });
                    } else {
                        layer.alert(data);
                    }
                },
                error: function (data) {
                    layer.alert("操作请求错误，请您稍后再试");
                }
            });*/
            return false;
        });
        form.render();
    });
});


