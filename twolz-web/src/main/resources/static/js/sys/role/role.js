/**
 * Created by liuwei
 * date: 2017-04-06
 */
var pagination;
$(function () {

    searchList();

    $("#roleForm").validation({icon: true});
    //添加角色信息
    $("#add").on('click', function (event) {
        if ($("#roleForm").valid(this) == false) {
            return false;
        } else {
            /**************数据处理*************/
            doAdd();
        }
    })
    //修改角色信息
    $("#update").on('click', function (event) {
        if ($("#versionForm").valid(this) == false) {
            return false;
        } else {
            /**************数据处理*************/
            doUpdate();
        }
    })
})


function searchList(f) {
    var param = {};
    if(f){
        $.extend(param,$(f.form).serializeObject());
    }
    $("#tbody_01").datagrid({
        url:'/sys/role/list',
        data:param
    });
}

function initAdd() {
    $("#add").removeAttr("disabled");
    $("#roleForm").validation({icon: false});
    $("#available").prop("checked",true);

    //设定值为空
    $("#roleId").val("");
    $("#roleName").val("");
    $("#add").show();
    $("#update").hide();
    $('#roleModal').modal("show");
    $("#myModalLabel").html("添加");

}

function doAdd() {
    $("#add").attr("disabled",true);
    var roleName = $("#roleName").val().trim();//角色名
    var roleType = $("#roleType").val();
    var available = $(":radio[name='available']:checked").val();

    var dataObj = JSON.stringify({
        roleName:roleName,
        roleType:roleType,
        available:available});

    //执行添加ajax
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/role/add",
        contentType: 'application/json',
        data: dataObj,
        dataType: 'json',
        success: function (data) {
            if(data.success){
                searchList();
                $.scojs_message('添加系统角色成功', $.scojs_message.TYPE_OK);
                $('#roleModal').modal("hide");
            } else {
                $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
                $("#add").removeAttr("disabled");
            }
        },
        error: function (data) {
            $.scojs_message('添加系统角色失败', $.scojs_message.TYPE_ERROR);
            $("#add").removeAttr("disabled");
        }
    });
}

function initEdit(id) {
    $("#update").removeAttr("disabled");
    $.ajax({
        processData: false,
        type: 'GET',
        url: "/sys/role/info/" + id,
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            $("#roleId").val(data.module.id);//角色id
            $("#roleName").val(data.module.roleName);
            $("#roleType").val(data.module.roleType);
            $("#roleForm").validation({icon: false});

            if(data.module.available==1){
                $("#available").prop("checked",true);
            } else {
                $("#unAvailable").prop("checked",true);
            }
            $('#roleModal').modal("show");
            $("#myModalLabel").html("修改");
            $("#add").hide();
            $("#update").show();
        }
    });
}

function doUpdate() {
    $("#update").attr("disabled",true);
    var roleId = $("#roleId").val();//角色id
    var roleName = $("#roleName").val().trim();//角色名
    var roleType = $("#roleType").val();
    var available = $(":radio[name='available']:checked").val();

    var dataObj = JSON.stringify({
        id: roleId,
        roleName:roleName,
        roleType:roleType,
        available:available}
        );

    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/role/update",
        contentType: 'application/json',
        dataType: 'json',
        data:dataObj,
        success: function (data) {
            if(data.success){
                searchList();
                $.scojs_message('修改系统角色成功', $.scojs_message.TYPE_OK);
                $('#roleModal').modal("hide");
            } else {
                $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
                $("#update").removeAttr("disabled");
            }
        },
        error: function (data) {
            $.scojs_message('修改系统角色失败', $.scojs_message.TYPE_ERROR);
            $("#update").removeAttr("disabled");
        }
    });
}

function deleteRole(id) {
    bootbox.confirm({
        title: "删除",
        message: "是否删除系统角色",
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
                    url: "/sys/role/delete/"+id,
                    contentType: 'application/json',
                    dataType: 'json',
                    success: function(data){
                        if(data.success){
                            searchList();
                            $.scojs_message('删除角色信息成功', $.scojs_message.TYPE_OK);
                        } else {
                            $.scojs_message('删除角色信息失败', $.scojs_message.TYPE_ERROR);
                        }
                    },
                    error: function (data) {
                        $.scojs_message('删除角色信息失败', $.scojs_message.TYPE_ERROR);
                    }
                });
            }
        }
    });
}

var $resourcesTree;

function initGrantFun(roleId) {
    $("#updateResource").removeAttr("disabled");
    //初始化之前需要先清空资源树 否则不会生成新的树
    //$('#resourcesTree').data('jstree', false).empty();
    $.jstree.destroy('#resourcesTree');
    $('#resourcesTreeModel').modal("show");
    $('#treeRoleId').val(roleId);
    $.ajax({
        cache: false,
        processData: false,
        type: 'GET',
        url: "/sys/resource/resourceTree/"+roleId,
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            $resourcesTree = $('#resourcesTree').jstree({
                'core': {
                    'data': data.module
                },
                "themes": {
                    "dots": true,
                    "responsive": false
                },
                "plugins": ["checkbox", "types"],
                'types': {
                    "default": {
                        "icon": "fa fa-folder tree-item-icon-color icon-lg"
                    },
                    "file": {
                        "icon": "fa fa-file tree-item-icon-color icon-lg"
                    }
                },
                version : 1,
                "checkbox": {
                    // "cascade": "",
                    // "three_state": false,
                    // "tie_selection": false,
                    // "whole_node": true,
                    // "keep_selected_style": false
                }
            })/*.on("check_node.jstree uncheck_node.jstree", function(e, data) {
                console.log(data.node.id + ' ' + data.node.text +
                    (data.node.state.checked ? ' CHECKED': ' NOT CHECKED'))
            })*/
        }
    });

}

function grantFun() {
    $("#updateResource").attr("disabled",true);
    var resourceList = $('#resourcesTree').jstree(true).get_selected();
    var treeRoleId = $("#treeRoleId").val();
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/role/grantFun",
        contentType: 'application/json',
        data: JSON.stringify({
            roleId: treeRoleId,
            resourceList:resourceList
        }),
        dataType: 'json',
        success: function(data){
            if(data.success){
                $('#resourcesTreeModel').modal("hide");
                $.scojs_message('角色授权成功', $.scojs_message.TYPE_OK);
            } else {
                $.scojs_message('角色授权失败', $.scojs_message.TYPE_ERROR);
                $("#updateResource").removeAttr("disabled");
            }
        },
        error: function (data) {
            $.scojs_message('角色授权失败', $.scojs_message.TYPE_ERROR);
            $("#updateResource").removeAttr("disabled");
        }
    });

}

function checkAllTree() {
    $resourcesTree.jstree(true).check_all();
}

function uncheckAllTree() {
    $resourcesTree.jstree(true).uncheck_all();
}

//禁用按钮
var $btnIds = ["add","update","updateResource"];
/**
 * 禁用按钮
 * @param flag；0：禁用，1：启用
 */
function $SetBtn(flag) {
    if(flag == 1){
        for(var i = 0;i<$btnIds.length;i++){
            var btnId = $btnIds[i];
            $("#"+btnId).removeAttr("disabled");
        }
    }else if(flag == 0){
        for(var i = 0;i<$btnIds.length;i++){
            var btnId = $btnIds[i];
            $("#"+btnId).attr("disabled",true);
        }
    }
}
