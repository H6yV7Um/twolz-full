
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
        console.info("add is in");
        if ($("#objInfoForm").valid(this) == false) {
            console.info("校验不通过");
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
    var msgVal=$('#tipMsg').val();
    //alert("msg==>"+msgVal);
    var codeVal=$('#tipCode').val();
    var frm = $("#objInfoForm");
        var url = "/sys/tip/add?v="+Math.random();
        $.post(
                url,
                frm.serialize(),
                function(data){
                   var success=data.success;
                   if(!success){
                	   alert(data.errorMessage);
                	   return ;
                   }

                   $('#objInfoModal').modal("hide");
                }
        )
    
}

function doUpdate(){
	//var id=$('#id').val();
	$("#objInfoForm").validation({icon: false});
    //console.info("id ->"+id);
    
	var frm = $("#objInfoForm");
    var url = "/sys/tip/update?v="+Math.random();
    $.post(
            url,
            frm.serialize(),
            function(data){
               var success=data.success;
               if(!success){
            	   alert(data.errorMessage);
            	   return ;
               }

               $('#objInfoModal').modal("hide");
               
               getPageList(1,10);
            }
    )
    
}

function searchList() {
    getPageList(1,10);
}

function getPageList(page,row){
    var searchVal = $('#searchVal').val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/tip/list",
        contentType: 'application/json',
        data: JSON.stringify({page:page,rows:row,searchVal:searchVal}),
        dataType: 'json',
        success: function(data){
            $('#objInfoList').empty();
            var tdHtml = "";
            $.each(data.module.list, function(i, aInfo) {
            	tdHtml += '<tr>';
                tdHtml += "<td>" + (i+1 || "") + "</td>";
                tdHtml += "<td>" + (aInfo.tipCode||"") + "</td>";
                tdHtml += "<td>" + (aInfo.tipMsg||"") + "</td>";
                //tdHtml += "<td>" + (aInfo.updaterId||"") + "</td>";
                //tdHtml += "<td>" + (${#dates.format(aInfo.createTime,'yyyy-MM-dd HH:mm:ss')} )+"</td>";
                //tdHtml += "<td>" + aInfo.createTime+ "</td>";
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

/**
 * 点击添加弹出添加框
 */
function add() {
    $("#objInfoForm").validation({icon: false});
    //设定值为空
    $('#tipCode').val("");
    $('#tipMsg').val("");
    $('#objInfoModal').modal("show");
    $("#myModalLabel").html("添加");
    $("#add").show();
    $("#update").hide();
}

function edit(id) {
    $("#objInfoForm").validation({icon: false});
    console.info("id ->"+id);
    
    $.ajax({
        processData: false,
        type: 'GET',
        url: "/sys/tip/item/"+id,
        contentType: 'application/json',
        dataType: 'json',
        success: function(rs){
        	var item=rs.module;
            //alert("同步提示信息缓存操作完成:"+item.code);
            //设定值为空
        	$('#id').val(id);
            $('#tipCode').val(item.tipCode);
            $('#tipMsg').val(item.tipMsg);
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
        url: "/sys/tip/syncache",
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
           bootbox.alert("同步提示信息缓存操作完成");
        }
    })
}







