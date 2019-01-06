/**
 * 售货合同列表
 */
var pageCurr;
$(function() {
    layui.use('table', function(){
        var table = layui.table
            ,form = layui.form;

        tableIns=table.render({
            toolbar:'#toolbarDemo',
            elem: '#saleContractList'
            ,url:'/sellingcontract/list'
            ,method: 'post' //默认：get请求
            ,cellMinWidth: 80
            ,page: true,
            request: {
                pageName: 'page' //页码的参数名称，默认：page
                ,limitName: 'limit' //每页数据量的参数名，默认：limit
            },response:{
                statusName: 'code' //数据状态的字段名称，默认：code
                ,statusCode: 200 //成功的状态码，默认：0
                ,countName: 'totals' //数据总数的字段名称，默认：count
                ,dataName: 'list' //数据列表的字段名称，默认：data
            }
            ,cols: [[
                {type:'checkbox'}
                ,{field:'id', title:'ID', width:80, unresize: true, sort: true}
                ,{field:'contractNo', title:'合同编号'}

                ,{field:'seller', title: '买方',}
                ,{field:'buyer', title: '卖方', }
                ,{field:'signDate', title: '签订日期',align:'center'}
                ,{field:'createTime',title:'创建时间',align:'center'}
                /*,{field:'isJob', title:'是否在职',width:95,align:'center',templet:'#jobTpl'}*/
                ,{fixed:'right', title:'操作', width:140,align:'center', toolbar:'#optBar'}
            ]]
            ,  done: function(res, curr, count){
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                //console.log(curr);
                //得到数据总量
                //console.log(count);
                pageCurr=curr;
            }
        });

        //监听在职操作
        form.on('switch(isJobTpl)', function(obj){
            //console.log(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
            var data = obj.data;
            setJobUser(obj,this.value,this.name,obj.elem.checked);
        });
        table.on('toolbar(saleContractList)',function(obj) {
            //alert(1);
            //console.log("1");
            var data = table.cache["saleContractList"];
            var newRow = data[0];
            data.push(newRow);
            //tableIns.reload({data:data});
            table.reload("saleContractList",{
                data:data
            });
        });
        //监听工具条
        table.on('tool(saleContractList)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                deleteData(data,data.id);
            } else if(obj.event === 'edit'){
                //编辑
                editSellingContract(data,data.id);
            } else if(obj.event === 'recover'){
                //恢复
                recoverUser(data,data.id);
            }
        });
        //监听提交
        form.on('submit(buyingContractSubmit)', function(data){
            // TODO 校验
            formSubmit(data);
            return false;
        });

        //头工具栏事件
        table.on('toolbar(saleContractList)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id); //获取选中行状态
            switch(obj.event){
                case 'getCheckData':
                    var data = checkStatus.data;  //获取选中行数据
                    layer.alert(JSON.stringify(data));
                    break;
            };
        });
    });
    //搜索框
    layui.use(['form','laydate'], function(){
        var form = layui.form ,layer = layui.layer
            ,laydate = layui.laydate;
        //日期
        laydate.render({
            elem: '#startSignDate'
        });
        laydate.render({
            elem: '#endSignDate'
        });
        //TODO 数据校验
        //监听搜索框
        form.on('submit(searchSubmit)', function(data){
            //重新加载table
            load(data);
            return false;
        });
    });
});

function exportFile() {
    var table = layui.table;
    var checkStatus = table.checkStatus('saleContractList');
    if(checkStatus.data.length > 0) {
        window.location.href = '/sellingcontract/download?id='+checkStatus.data[0].id;
    } else if(checkStatus.data.length == 0) {
        layer.alert("请选择一条记录！");
        //return false;
    }
}

function gen(type) {
    var table = layui.table;
    var checkStatus = table.checkStatus('saleContractList');
    if(checkStatus.data.length > 0) {
        switch (type) {
            case 1: // buying contract
                window.location.href = '/buyingcontract/gen?sellingContractId='+checkStatus.data[0].id;
                break;
            case 2: //export goods list
                window.location.href = '/exportgoodslist/gen?sellingContractId='+checkStatus.data[0].id;
                break;
            case 3: //invoice
                window.location.href = '/invoice/gen?sellingContractId='+checkStatus.data[0].id;
                break;
            case 4:// packing list
                window.location.href = '/packinglist/gen?sellingContractId='+checkStatus.data[0].id;
                break;
            case 5: //报关单
                window.location.href = '/customsclearance/gen?sellingContractId='+checkStatus.data[0].id;
                break;
        }

    } else if(checkStatus.data.length == 0) {
        layer.alert("请选择销售合同！");
        //return false;
    }
}





function editSellingContract(obj,id) {
    window.location.href = '/sellingcontract/edit?id='+id;
}

function deleteData(obj,id) {
    $.ajax({
        "url": "/sellingcontract/delete?id="+id,
        "type": "post",
        "data": null,
        "contentType": "application/json",
        "success": function (data) {
            if(data == "ok") {
                layer.alert("操作成功", {time: 1000 }, function () {
                    //layer.closeAll();

                });
                tableIns.reload();

            }
        }
    });

}

function load(obj){
    //重新加载table
    tableIns.reload({
        where: obj.field
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
}

