/**
 * 售货合同列表
 */
var pageCurr;
$(function() {
    layui.use('table', function(){
        var table = layui.table
            ,form = layui.form;

        tableIns=table.render({
            elem: '#providerAccountList'
            ,url:'/provideraccount/list'
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
                ,{field:'code', title:'合同编号'}
                ,{field:'amount', title:'金额'}


                ,{field:'createTime', title: '创建日期',align:'center'}
                ,{field:'createUserName', title: '创建人',align:'center'}
                ,{field:'updateUserName', title: '更新人',align:'center'}
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

        //监听工具条
        table.on('tool(providerAccountList)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                deleteData(data,data.id);
            } else if(obj.event === 'edit'){
                //编辑
                editSellingContract(data,data.id);
            } else if(obj.event === 'recover'){
                //恢复
            }
        });

        //监听搜索框
        form.on('submit(searchSubmit)', function(data){
            //重新加载table
            load(data);
            return false;
        });

        //监听提交
        form.on('submit(providerAccountSubmit)', function(data){
            // TODO 校验
            formSubmit(data);
            return false;
        });
    });
    //搜索框
    layui.use(['form','laydate'], function(){
        var form = layui.form ,layer = layui.layer
            ,laydate = layui.laydate;
        //日期
        laydate.render({
            elem: '#startCreateTime'
        });
        laydate.render({
            elem: '#endCreateTime'
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


function editSellingContract(obj,id) {
    window.location.href = '/provideraccount/edit?id='+id;
}


function deleteData(obj,id) {
    $.ajax({
        "url": "/provideraccount/delete?id="+id,
        "type": "post",
        "data": null,
        "contentType": "application/json",
        "success": function (data) {
            if(data == "ok") {
                layer.alert("操作成功",{time: 1000 }, function () {
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

