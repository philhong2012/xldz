$(function () {
    layui.use(['form', 'layedit', 'laydate'], function() {
        var form = layui.form
            , laydate = layui.laydate;

        //日期
        laydate.render({
            elem: '#endCreateTime'
        });
        laydate.render({
            elem: '#startCreateTime'
        });
        laydate.render({
            elem: '#validityDate'
        });
        laydate.render({
            elem: '#exportDate'
        });

        laydate.render({
            elem: '#declareDate'
        })


    });


    layui.use(['form' ,'layer'], function(){
        var form = layui.form;
        var layer=layui.layer;

        //监听提交
        form.on('submit(saveCustomsClearance)', function(data){
            var formElements = $('#customsClearanceForm input:not(".textbox-text,.textbox-value")');
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
                "url": "/customsclearance/save",
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
            return false;
        });
        form.render();
    });
});


