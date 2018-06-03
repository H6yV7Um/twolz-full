var pagination;
var $appId;
var $appIdV;

$(function() {
    var page=1;
    var row=10;

    getAppVersionList(page,row);
    $("select").select2({
        minimumResultsForSearch: Infinity
    });

    pagination = $('#appVersionListPagination').bootpag({
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
        getAppVersionList(num,row);
    });

    $("#appVersionForm").validation({icon: true});

    //添加验证
    $(document).on('click',"#addAppVersion", function (event) {
        if ($("#appVersionForm").valid(this) == false) {
            return false;
        } else {
            doAdd();
        }
    });
    //修改验证
    $(document).on('click',"#updateAppVersion", function (event) {
        if ($("#appVersionForm").valid(this) == false) {
            return false;
        } else {
            /**************数据处理*************/
            doUpdate();
        }
    });
});

function checkForm(){

    return true;
}

function doAdd() {
    $("#addAppVersion").attr("disabled",true);

    //验证表单
    var ckRes = checkForm();
    if(!ckRes){
        $("#addAppVersion").removeAttr("disabled");
        return false;
    }

    var appIdV = $appId.val();
    console.log(appIdV);
    var appName = $('#appName').val();
    var version = $('#version').val();
    var versionCode = $('#versionCode').val();
    var downloadUrl = $('#downloadUrl').val();
    var description = $('#description').val();
    var isUpdate = $(":radio[name='isUpdate']:checked").val();
    var dataObj = JSON.stringify({appId:appIdV,appName:appName,version:version,versionCode:versionCode,downloadUrl:downloadUrl,description:description,isUpdate:isUpdate});

    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/appVersion/add",
        contentType: 'application/json',
        data: dataObj,
        dataType: 'json',
        success: function(data){
            if(data.success){
                getAppVersionList(1,10);
                $.scojs_message('添加成功', $.scojs_message.TYPE_OK);
                $('#appVersionModal').modal("hide");
            } else {
                $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
                $("#addAppVersion").removeAttr("disabled");
            }
        },
        error: function (data) {
            $.scojs_message('添加失败', $.scojs_message.TYPE_ERROR);
            $("#addAppVersion").removeAttr("disabled");
        }
    });
}

function doUpdate(){
    $("#updateAppVersion").attr("disabled",true);

    //验证表单
    var ckRes = checkForm();
    if(!ckRes){
        $("#addAppVersion").removeAttr("disabled");
        return false;
    }

    var id = $('#appVersionId').val();
    var appIdV = $appId.val();
    var appName = $('#appName').val();
    var version = $('#version').val();
    var versionCode = $('#versionCode').val();
    var downloadUrl = $('#downloadUrl').val();
    var description = $('#description').val();
    var isUpdate = $(":radio[name='isUpdate']:checked").val();
    var dataObj = JSON.stringify({id:id,appId:appIdV,appName:appName,version:version,versionCode:versionCode,downloadUrl:downloadUrl,description:description,isUpdate:isUpdate});
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/appVersion/update",
        contentType: 'application/json',
        data: dataObj,
        dataType: 'json',
        success: function(data){
            if(data.success){
                getAppVersionList(1,10);
                $.scojs_message('修改成功', $.scojs_message.TYPE_OK);
                $('#appVersionModal').modal("hide");
            } else {
                $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
                $("#updateAppVersion").removeAttr("disabled");
            }
        },
        error: function (data) {
            $.scojs_message('修改失败', $.scojs_message.TYPE_ERROR);
            $("#updateAppVersion").removeAttr("disabled");
        }
    });
}

function deleteUser(id){
    bootbox.confirm({
        title: "删除",
        message: "是否删除用户账号",
        buttons: {
            confirm: {
                label: '是',
                className: 'btn-info'
            },
            cancel: {
                label: '否',
                className: 'btn-default'
            }
        },
        callback: function (result) {
            if(result){
                $.ajax({
                    processData: false,
                    type: 'GET',
                    url: "/sys/appVersion/delete/"+id,
                    contentType: 'application/json',
                    dataType: 'json',
                    success: function(data){
                        if(data.success){
                            getAppVersionList(1,10);
                            $.scojs_message('删除用户账号成功', $.scojs_message.TYPE_OK);
                        } else {
                            $.scojs_message('删除用户账号失败', $.scojs_message.TYPE_ERROR);
                        }
                    },
                    error: function (data) {
                        $.scojs_message('删除用户账号失败', $.scojs_message.TYPE_ERROR);
                    }
                });
            }
        }
    });
}

function searchList() {
    getAppVersionList(1,10);
}

function formtemplate(data){
    var templateoutput;
    if(data == 0) {
        templateoutput = template('tpl-userform',{add:true});
        $("#form-modal").html("").html(templateoutput);
    } else{
        console.info("启用状态------>>>>"+data.available);
        templateoutput = template('tpl-userform',data);
        $("#form-modal").html("").html(templateoutput);
    }
    $("#appVersionForm").validation({icon: false});
    $appId= $("#appId").select2({language: 'zh-CN'});
}

function getAppVersionList(page,row){
    var searchVal = $('#searchVal').val();

    var dataObj = JSON.stringify({
        page:page,
        rows:row,
        searchVal:searchVal
        });
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/appVersion/list",
        contentType: 'application/json',
        data: dataObj,
        dataType: 'json',
        success: function(data){
            //appVersion列表模板渲染
            var templateoutput = template('tpl-userlist', {list:data.module.list,startRow:data.module.startRow})
            $('#appVersionList').html("").html(templateoutput);

            if (data.module.pages == 0) {
                $("#appVersionListPagination").empty();
                $('#appVersionList').html("<tr><td colspan='5' style='text-align: center'>暂无数据</td></tr>");
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
function initAddAppVersion() {
    $("#addAppVersion").removeAttr("disabled");
    formtemplate(0);
    $("#appVersionForm").validation({icon: false});
    $("#available").prop("checked",true);
    $appId.val(null);
    $appIdV="";
    $('#appVersionModal').modal("show");
}

function initEditAppVersion(id) {
    $("#updateAppVersion").removeAttr("disabled");
    $.ajax({
        processData: false,
        type: 'GET',
        url: "/sys/appVersion/info/"+id,
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
            if(data.success){
                formtemplate(data.module);
                $appIdV = data.module.appId;
                $appId.val(data.module.appId).trigger("change");
                $('#appVersionModal').modal("show");
            } else {
                $.scojs_message('获取用户账号信息失败', $.scojs_message.TYPE_ERROR);
            }
        },
        error: function (data) {
            $.scojs_message('获取用户账号信息失败', $.scojs_message.TYPE_ERROR);
        }
    });
}




