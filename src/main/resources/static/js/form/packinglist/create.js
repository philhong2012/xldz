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
        })

        laydate.render({
            elem: '#packingDate'
        })
    });


    layui.use(['form' ,'layer'], function(){
        var form = layui.form;
        var layer=layui.layer;

        //监听提交
        form.on('submit(savePackingList)', function(data){
            var formElements = $('#packingListForm input:not(".textbox-text,.textbox-value")');
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
            var changes = getChanges();
            formData.details = changes.insertOrUpdate;
            formData.toDeletes = changes.deleted;
            $.ajax({
                "url": "/packinglist/save",
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


