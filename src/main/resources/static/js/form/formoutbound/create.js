$(function () {
    layui.use(['form', 'layedit', 'laydate'], function() {
        var form = layui.form
            , laydate = layui.laydate;

        //日期
        laydate.render({
            elem: '#signDate'
        });
        laydate.render({
            elem: '#outboundDate'
        })

        laydate.render({
            elem: '#inboundDate'
        })
    });


    layui.use(['form' ,'layer'], function(){
        var form = layui.form;
        var layer=layui.layer;

        //监听提交
        form.on('submit(saveFormOutbound)', function(data){
            var formElements = $('#formOutboundForm input:not(".textbox-text,.textbox-value")');
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
                "url": "/formoutbound/save",
                "type": "post",
                "data": JSON.stringify(formData),
                "contentType": "application/json",

                "success": function (data) {
                    if(data.indexOf("ok") > -1) {
                        layer.alert("操作成功",{time: 1000 }, function () {
                            //layer.closeAll();
                            //load();
                        });
                        var id =data.split(":")[1];
                        location.href = '/formoutbound/edit?id='+id;
                        //window.setTimeout(window.refresh(),1000);
                    }
                }
            });
            return false;
        });
        form.render();
    });
});


