/**
 * 售货合同列表
 */
var pageCurr;
$(function() {
    layui.use('table', function(){
        var table = layui.table
            ,form = layui.form;

        tableIns=table.render({
            elem: '#statisticsList'
            ,url:'/statistics/list'
            ,method: 'post' //默认：get请求
            ,cellMinWidth: 80
            ,page: false,
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
              /*  ,{field:'id', title:'ID', width:80, unresize: true, sort: true}*/
                ,{field:'turnOver', title: '营业额',align:'center'}
                ,{field:'exportAmount', title: '出口额',align:'center'}
                ,{field:'profit', title: '利润',align:'center'}

            ]]
            ,  done: function(res, curr, count){
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                //console.log(curr);
                //得到数据总量
                //console.log(count);
                //pageCurr=curr;
            }
        });


        //监听搜索框
        form.on('submit(searchSubmit)', function(data){
            //重新加载table
            load(data);
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
        $.get("/department/list2",function(data){
            initDeptOptions(data,null);
            form.render();
        });
    });
});

//初始化部门下拉框
function initDeptOptions(depts,selected,callBack) {

    $("#deptId").empty();
    var deptOptions = "<option value=''>请选择部门</option>";
    $.each(depts,function (index,item) {
        if(selected == item.id) {
            deptOptions = deptOptions + "<option value='" + item.id + " selected '>" + item.name + "</option>";
        } else {
            deptOptions = deptOptions + "<option value='" + item.id + "'>" + item.name + "</option>";
        }
    });
    $("#deptId").append(deptOptions);


    if(callBack != null) {
        callBack();
    }
    //layui.form.render('select');

}



function exportFile() {
    var table = layui.table;
    var checkStatus = table.checkStatus('statisticsList');
    if (checkStatus.data.length > 0) {
        window.location.href = '/statistics/download?id=' + checkStatus.data[0].id;
    } else if (checkStatus.data.length == 0) {
        layer.alert("请选择一条记录！");
        //return false;
    }
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

