
var pagination;
$(function() {
    var page=1;
    var row=10;
    getPageList(page,row);
    
    pagination = $('#infoListPagination').bootpag({
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
        getPageList(num,row);
    });

    $("#objInfoForm").validation({icon: true});
    //添加验证
    $("#add").on('click', function (event) {
        //console.info("add is in");
        if ($("#objInfoForm").valid(this) == false) {
            //console.info("校验不通过");
            return false;
        } else {
            doAdd();
        }
    })
    //修改验证
    $("#update").on('click', function (event) {
        if ($("#objInfoForm").valid(this) == false) {
            return false;
        } else {
            /**************数据处理*************/
            doUpdate();
        }
    })
});

function doAdd() {
    console.info("验证通过。");
    var frm = $("#objInfoForm");
        var url = "/sys/param/add?v="+Math.random();
    $.post(
        url,
        frm.serialize(),
        function(data){
            if(data.success){
                getPageList(1,10);
                $.scojs_message('添加成功', $.scojs_message.TYPE_OK);
            } else {
                $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
            }
            $('#objInfoModal').modal("hide");
        }
    )

}

function doUpdate(){
	//var id=$('#id').val();
	$("#objInfoForm").validation({icon: false});
    //console.info("id ->"+id);
    $("#paramName").attr("disabled", false);
    $("#paramKey").attr("disabled", false);
	var frm = $("#objInfoForm");
    var url = "/sys/param/update?v="+Math.random();
    $.post(
            url,
            frm.serialize(),
            function(data){
                if(data.success){
                    getPageList(1,10);
                    $.scojs_message('更新成功', $.scojs_message.TYPE_OK);
                } else {
                    $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
                }
                $("#paramName").attr("disabled", true);
                $("#paramKey").attr("disabled", true);
               $('#objInfoModal').modal("hide");
            }
    )
    
}


function getPageList(page,row){
    var keywords = $('#searchVal').val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/param/list",
        contentType: 'application/json',
        data: JSON.stringify({page:page,rows:row,keywords:keywords}),
        dataType: 'json',
        success: function(data){
            $('#objInfoList').empty();
            var tdHtml = "";
            $.each(data.module.list, function(i, aInfo) {
            	tdHtml += '<tr>';
                tdHtml += "<td>" + (data.module.pageSize*(data.module.pageNum-1) + i + 1 || "") + "</td>";
                tdHtml += "<td>" + (aInfo.paramName || "") + "</td>";
                tdHtml += "<td>" + (aInfo.paramKey||"") + "</td>";
                tdHtml += "<td>" + (aInfo.paramValue||"") + "</td>";
                tdHtml += "<td>" + (aInfo.memo||"") + "</td>";
                // tdHtml += "<td>" + (aInfo.updateBy||"") + "</td>";
                // tdHtml += "<td>" + aInfo.createTime+ "</td>";
                tdHtml += "<td><div class='icon-flex'><a href='javascript:void(0);' title='修改' onclick='edit("+aInfo.id+")'><i class='oicon oicon-edit'></i></a></div></td>";
            });
            $('#objInfoList').html(tdHtml);
            if (data.module.pages == 0) {
                $("#infoListPagination").empty();
                $('#objInfoList').html("<tr><td colspan='6' style='text-align: center'>暂无数据</td></tr>");
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



function edit(id) {
    $("#objInfoForm").validation({icon: false});
    console.info("id ->"+id);
    
    $.ajax({
        processData: false,
        type: 'GET',
        url: "/sys/param/item/"+id,
        contentType: 'application/json',
        dataType: 'json',
        success: function(rs){
        	var item=rs.module;
            //alert("同步提示信息缓存操作完成:"+item.code);
            //设定值为空
        	$('#id').val(id);
            $('#paramName').val(item.paramName);
            $('#paramKey').val(item.paramKey);
            $('#paramValue').val(item.paramValue);
            $('#memo').val(item.memo);
            $("#paramName").attr("disabled", true);
            $("#paramKey").attr("disabled", true);
        }
    })
    
   
    $('#objInfoModal').modal("show");
    $("#myModalLabel").html("修改");
    $("#add").hide();
    $("#update").show();
}

function synCache(){
	$.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/param/syncache",
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
           alert("同步提示信息缓存操作完成");
        }
    })
}

/**
 * 点击添加弹出添加框
 */
function add() {
    $("#objInfoForm").validation({icon: false});
    //设定值为空
    $('#paramName').val("");
    $('#paramKey').val("");
    $('#paramValue').val("");
    $('#memo').val("");
    $("#paramName").attr("disabled", false);
    $("#paramKey").attr("disabled", false);
    $('#objInfoModal').modal("show");
    $("#myModalLabel").html("添加");
    $("#add").show();
    $("#update").hide();
}




