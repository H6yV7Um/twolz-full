var pagination;

$(function() {
    var page=1;
    var row=10;

    getUserList(page,row);
    $("select").select2({
        minimumResultsForSearch: Infinity
    });

    pagination = $('#adminUserListPagination').bootpag({
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
        getUserList(num,row);
    });

    $("#siteUserForm").validation({icon: true});

    //添加验证
    $(document).on('click',"#addSiteInfo", function (event) {
        console.info("add is in");
        if ($("#siteUserForm").valid(this) == false) {
            return false;
        } else {
            doAdd();
        }
    });
    //修改验证
    $(document).on('click',"#updateSiteInfo", function (event) {
        if ($("#siteUserForm").valid(this) == false) {
            return false;
        } else {
            /**************数据处理*************/
            doUpdate();
        }
    });
});

function checkForm(){
    var mobile = $('#mobile').val();
    var pat = /^1\d{10}$/;
    var pat2 = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$/; //8位以上数字或字母，至少一个数字和字母
    if (!pat.test(mobile)) {
        $.scojs_message('手机号码格式不正确', $.scojs_message.TYPE_ERROR);
        $("#phone").focus();
        return false;
    }
    return true;
}

function doAdd() {
    $("#addSiteInfo").attr("disabled",true);

    //验证表单
    var ckRes = checkForm();
    if(!ckRes){
        $("#addSiteInfo").removeAttr("disabled");
        return false;
    }

    var mobile = $('#mobile').val();
    var userName = $('#userName').val();
    var cardNo = $('#cardNo').val();
    var state = $(":radio[name='state']:checked").val();
    var dataObj = JSON.stringify({mobile:mobile,userName:userName,state:state,cardNo:cardNo});

    $.ajax({
        processData: false,
        type: 'POST',
        url: "/siteuser/add",
        contentType: 'application/json',
        data: dataObj,
        dataType: 'json',
        success: function(data){
            if(data.success){
                getUserList(1,10);
                $.scojs_message('机构添加成功', $.scojs_message.TYPE_OK);
                $('#siteUserModal').modal("hide");
            } else {
                $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
                $("#addSiteInfo").removeAttr("disabled");
            }
        },
        error: function (data) {
            $.scojs_message('机构添加失败', $.scojs_message.TYPE_ERROR);
            $("#addSiteInfo").removeAttr("disabled");
        }
    });
}

function doUpdate(){
    $("#updateSiteInfo").attr("disabled",true);

    //验证表单
    var ckRes = checkForm();
    if(!ckRes){
        $("#addSiteInfo").removeAttr("disabled");
        return false;
    }

    var userId = $('#userId').val();
    var mobile = $('#mobile').val();
    var userName = $('#userName').val();
    var cardNo = $('#cardNo').val();
    var state = $(":radio[name='state']:checked").val();
    var dataObj = JSON.stringify({id:userId,mobile:mobile,userName:userName,state:state,cardNo:cardNo});
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/siteuser/update",
        contentType: 'application/json',
        data: dataObj,
        dataType: 'json',
        success: function(data){
            if(data.success){
                getUserList(1,10);
                $.scojs_message('用户账号修改成功', $.scojs_message.TYPE_OK);
                $('#siteUserModal').modal("hide");
            } else {
                $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
                $("#updateSiteInfo").removeAttr("disabled");
            }
        },
        error: function (data) {
            $.scojs_message('用户账号修改失败', $.scojs_message.TYPE_ERROR);
            $("#updateSiteInfo").removeAttr("disabled");
        }
    });
}

function deleteSiteInfo(id){
    bootbox.confirm({
        title: "删除",
        message: "是否删除机构",
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
                    url: "/siteuser/delete/"+id,
                    contentType: 'application/json',
                    dataType: 'json',
                    success: function(data){
                        if(data.success){
                            getUserList(1,10);
                            $.scojs_message('删除机构成功', $.scojs_message.TYPE_OK);
                        } else {
                            $.scojs_message('删除机构失败', $.scojs_message.TYPE_ERROR);
                        }
                    },
                    error: function (data) {
                        $.scojs_message('删除机构失败', $.scojs_message.TYPE_ERROR);
                    }
                });
            }
        }
    });
}

function searchList() {
    getUserList(1,10);
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
    $("#siteUserForm").validation({icon: false});
}

function getUserList(page,row){
    var searchVal = $('#searchVal').val();

    var dataObj = JSON.stringify({
        page:page,
        rows:row,
        searchVal:searchVal
        });
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/siteuser/list",
        contentType: 'application/json',
        data: dataObj,
        dataType: 'json',
        success: function(data){
            //user列表模板渲染
            var templateoutput = template('tpl-userlist', {list:data.module.list,startRow:data.module.startRow})
            $('#adminUserList').html("").html(templateoutput);

            if (data.module.pages == 0) {
                $("#adminUserListPagination").empty();
                $('#adminUserList').html("<tr><td colspan='5' style='text-align: center'>暂无数据</td></tr>");
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
function initAddSiteInfo() {
    $("#addSiteInfo").removeAttr("disabled");
    formtemplate(0);
    $("#siteUserForm").validation({icon: false});
    $("#available").prop("checked",true);
    $("#instAddress").val(null);
    $('#siteUserModal').modal("show");
}

function initEditSiteInfo(id) {
    $("#updateSiteInfo").removeAttr("disabled");
    $.ajax({
        processData: false,
        type: 'GET',
        url: "/siteuser/info/"+id,
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
            if(data.success){
                formtemplate(data.module);
                $('#siteUserModal').modal("show");
            } else {
                $.scojs_message('获取用户账号信息失败', $.scojs_message.TYPE_ERROR);
            }
        },
        error: function (data) {
            $.scojs_message('获取用户账号信息失败', $.scojs_message.TYPE_ERROR);
        }
    });
}




