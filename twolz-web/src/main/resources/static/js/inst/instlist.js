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

    $("#instInfoForm").validation({icon: true});

    //添加验证
    $(document).on('click',"#addInst", function (event) {
        console.info("add is in");
        if ($("#instInfoForm").valid(this) == false) {
            return false;
        } else {
            doAdd();
        }
    });
    //修改验证
    $(document).on('click',"#updateInst", function (event) {
        if ($("#instInfoForm").valid(this) == false) {
            return false;
        } else {
            /**************数据处理*************/
            doUpdate();
        }
    });
});

function checkForm(){
    var instMobile = $('#instMobile').val();
    var pat = /^1\d{10}$/;
    var pat2 = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$/; //8位以上数字或字母，至少一个数字和字母
    if (!pat.test(instMobile)) {
        $.scojs_message('手机号码格式不正确', $.scojs_message.TYPE_ERROR);
        $("#phone").focus();
        return false;
    }
    return true;
}

function doAdd() {
    $("#addInst").attr("disabled",true);

    //验证表单
    var ckRes = checkForm();
    if(!ckRes){
        $("#addInst").removeAttr("disabled");
        return false;
    }

    var instMobile = $('#instMobile').val();
    var instName = $('#instName').val();
    var cfuscc = $('#cfuscc').val();
    var instAddress = $('#instAddress').val();
    var state = $(":radio[name='state']:checked").val();
    var dataObj = JSON.stringify({instMobile:instMobile,instName:instName,cfuscc:cfuscc,state:state,instAddress:instAddress});

    $.ajax({
        processData: false,
        type: 'POST',
        url: "/inst/add",
        contentType: 'application/json',
        data: dataObj,
        dataType: 'json',
        success: function(data){
            if(data.success){
                getUserList(1,10);
                $.scojs_message('机构添加成功', $.scojs_message.TYPE_OK);
                $('#instModal').modal("hide");
            } else {
                $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
                $("#addInst").removeAttr("disabled");
            }
        },
        error: function (data) {
            $.scojs_message('机构添加失败', $.scojs_message.TYPE_ERROR);
            $("#addInst").removeAttr("disabled");
        }
    });
}

function doUpdate(){
    $("#updateInst").attr("disabled",true);

    //验证表单
    var ckRes = checkForm();
    if(!ckRes){
        $("#addInst").removeAttr("disabled");
        return false;
    }

    var instId = $('#instId').val();
    var instMobile = $('#instMobile').val();
    var instName = $('#instName').val();
    var cfuscc = $('#cfuscc').val();
    var instAddress = $('#instAddress').val();
    var state = $(":radio[name='state']:checked").val();
    var dataObj = JSON.stringify({id:instId,instMobile:instMobile,instName:instName,cfuscc:cfuscc,state:state,instAddress:instAddress});
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/inst/update",
        contentType: 'application/json',
        data: dataObj,
        dataType: 'json',
        success: function(data){
            if(data.success){
                getUserList(1,10);
                $.scojs_message('用户账号修改成功', $.scojs_message.TYPE_OK);
                $('#instModal').modal("hide");
            } else {
                $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
                $("#updateInst").removeAttr("disabled");
            }
        },
        error: function (data) {
            $.scojs_message('用户账号修改失败', $.scojs_message.TYPE_ERROR);
            $("#updateInst").removeAttr("disabled");
        }
    });
}

function deleteInst(id){
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
                    url: "/inst/delete/"+id,
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
    $("#instInfoForm").validation({icon: false});
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
        url: "/inst/list",
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
    $("#addInst").removeAttr("disabled");
    formtemplate(0);
    $("#instInfoForm").validation({icon: false});
    $("#available").prop("checked",true);
    $("#instAddress").val(null);
    $('#instModal').modal("show");
}

function initEditSysUser(id) {
    $("#updateInst").removeAttr("disabled");
    $.ajax({
        processData: false,
        type: 'GET',
        url: "/inst/info/"+id,
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
            if(data.success){
                formtemplate(data.module);
                $('#instModal').modal("show");
            } else {
                $.scojs_message('获取用户账号信息失败', $.scojs_message.TYPE_ERROR);
            }
        },
        error: function (data) {
            $.scojs_message('获取用户账号信息失败', $.scojs_message.TYPE_ERROR);
        }
    });
}




