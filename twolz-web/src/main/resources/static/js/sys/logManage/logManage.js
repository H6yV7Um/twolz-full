var pagination;
$(function() {
    var page=1;
    var row=10;
    //日期
    $("#startTime").val(moment().subtract(30, 'days').format('YYYY-MM-DD'));
    $("#endTime").val(moment().format('YYYY-MM-DD'));
    $("#timePicker").val(moment().subtract(30, 'days').format('YYYY-MM-DD') + ' 至 ' + moment().format('YYYY-MM-DD'));
    $("#timePicker").datetimeInit(
        {
            'singleDatePicker':false,
            'maxDate':moment().format('YYYY-MM-DD')
        },
        $("#startTime"),$("#endTime")
    );
    $('#appId').select2({language: 'zh-CN',minimumResultsForSearch: Infinity});
    getList(page,row);


    pagination = $('#logManageListPagination').bootpag({
        total: 0,          // total pages
        page: 1,            // default page
        maxVisible: 10,     // visible pagination
        firstLastUse: true,
        prev: '上一页',
        next: '下一页',
        first: '首页',
        last: '末页',
        leaps: true         // next/prev leaps through maxVisible
    }).on("page", function(event, num){
        getList(num,row);
    });

});

function search() {
    getList(1,10);
}
function getList(page,row){
    var startTime = $('#startTime').val();
    var endTime = $('#endTime').val();
    var userName = $('#userName').val();
    var userPhone = $('#userPhone').val();
    var ipAddr = $('#ipAddr').val();
    var module = $('#module').val();
    var mean = $('#mean').val();
    var fnction = $('#function').val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/logManage/list",
        contentType: 'application/json',
        data: JSON.stringify({page:page,rows:row,startTime:startTime,endTime:endTime,userName:userName,
            userPhone:userPhone,ipAddr:ipAddr,module:module,mean:mean,function:fnction}),
        dataType: 'json',
        success: function(data){
            var templateoutput = template('tpl-list', {list:data.module.list,startRow:data.module.startRow});
            $('#logManageList').html("").html(templateoutput);
            if (data.module.pages == 0) {
                $("#logManageListPagination").empty();
                $('#logManageList').html("<tr><td colspan='10' style='text-align: center'>暂无数据</td></tr>");
            } else {
                pagination.bootpag({
                    total: data.module.pages,          // total pages
                    page: data.module.pageNum,            // default page
                    maxVisible: 10,     // visible pagination
                    firstLastUse: true,
                    prev: '上一页',
                    next: '下一页',
                    first: '首页',
                    last: '末页',
                    leaps: true         // next/prev leaps through maxVisible
                });
            }
        }
    })
}