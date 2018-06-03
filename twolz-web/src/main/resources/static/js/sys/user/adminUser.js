var pagination;
var $roleSel;
var $roles;

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

    $("#adminUserForm").validation({icon: true});

    //添加验证
    $(document).on('click',"#addSysUser", function (event) {
        console.info("add is in");
        if ($("#adminUserForm").valid(this) == false) {
            return false;
        } else {
            doAdd();
        }
    });
    //修改验证
    $(document).on('click',"#updateSysUser", function (event) {
        if ($("#adminUserForm").valid(this) == false) {
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
    if($("#passwd").val()){
        if (!pat2.test($("#passwd").val())) {
            $.scojs_message('密码至少为8位,且包含至少一个数字和字母', $.scojs_message.TYPE_ERROR);
            $("#passwd").focus();
            return false;
        }
        if($("#passwd").val() == $("#phone").val()){
            $.scojs_message('密码不能和用户名相同', $.scojs_message.TYPE_ERROR);
            $("#passwd").focus();
            return false;
        }
    }
    return true;
}

function doAdd() {
    $("#addSysUser").attr("disabled",true);

    //验证表单
    var ckRes = checkForm();
    if(!ckRes){
        $("#addSysUser").removeAttr("disabled");
        return false;
    }

    var mobile = $('#mobile').val();
    var realName = $('#realName').val();
    var passwd = $('#passwd').val();
    var roles = $roleSel.val();
    var status = $(":radio[name='status']:checked").val();
    var dataObj = JSON.stringify({mobile:mobile,realName:realName,passwd:passwd,status:status,roles:roles});

    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/user/add",
        contentType: 'application/json',
        data: dataObj,
        dataType: 'json',
        success: function(data){
            if(data.success){
                getUserList(1,10);
                $.scojs_message('用户账号添加成功', $.scojs_message.TYPE_OK);
                $('#adminUserModal').modal("hide");
            } else {
                $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
                $("#addSysUser").removeAttr("disabled");
            }
        },
        error: function (data) {
            $.scojs_message('用户账号添加失败', $.scojs_message.TYPE_ERROR);
            $("#addSysUser").removeAttr("disabled");
        }
    });
}

function doUpdate(){
    $("#updateSysUser").attr("disabled",true);

    //验证表单
    var ckRes = checkForm();
    if(!ckRes){
        $("#addSysUser").removeAttr("disabled");
        return false;
    }

    var userId = $('#userId').val();
    var mobile = $('#mobile').val();
    var realName = $('#realName').val();
    var roles = $roleSel.val();
    var state = $(":radio[name='status']:checked").val();
    var dataObj = JSON.stringify({id:userId,mobile:mobile,realName:realName,state:state,roles:roles});
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/user/update",
        contentType: 'application/json',
        data: dataObj,
        dataType: 'json',
        success: function(data){
            if(data.success){
                getUserList(1,10);
                $.scojs_message('用户账号修改成功', $.scojs_message.TYPE_OK);
                $('#adminUserModal').modal("hide");
            } else {
                $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
                $("#updateSysUser").removeAttr("disabled");
            }
        },
        error: function (data) {
            $.scojs_message('用户账号修改失败', $.scojs_message.TYPE_ERROR);
            $("#updateSysUser").removeAttr("disabled");
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
                    url: "/sys/user/delete/"+id,
                    contentType: 'application/json',
                    dataType: 'json',
                    success: function(data){
                        if(data.success){
                            getUserList(1,10);
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
    $("#adminUserForm").validation({icon: false});
    $roleSel = $("#roleSel").select2({language: 'zh-CN'});
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
        url: "/sys/user/list",
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
function initAddSysUser() {
    $("#addSysUser").removeAttr("disabled");
    formtemplate(0);
    $("#adminUserForm").validation({icon: false});
    $("#available").prop("checked",true);
    $roleSel.val(null);
    $roles = "";
    $('#adminUserModal').modal("show");
}

function initEditSysUser(id) {
    $("#updateSysUser").removeAttr("disabled");
    $.ajax({
        processData: false,
        type: 'GET',
        url: "/sys/user/info/"+id,
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
            if(data.success){
                formtemplate(data.module);
                $roles = data.module.roles;
                $roleSel.val(data.module.roles).trigger("change");
                $('#adminUserModal').modal("show");
            } else {
                $.scojs_message('获取用户账号信息失败', $.scojs_message.TYPE_ERROR);
            }
        },
        error: function (data) {
            $.scojs_message('获取用户账号信息失败', $.scojs_message.TYPE_ERROR);
        }
    });
}




