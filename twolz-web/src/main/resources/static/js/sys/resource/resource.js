
var $typeSel;
var $parentSel;
var setting = {
    check: {
        enable: true,
        chkStyle: "radio",
        radioType: "all"
    },
    view: {
        dblClickExpand: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        onClick: onTreeClick,
        onCheck: onTreeCheck
    }
};

function onTreeClick(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("menuTree");
    zTree.checkNode(treeNode, !treeNode.checked, null, true);
    return false;
}

function onTreeCheck(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("menuTree"),
        nodes = zTree.getCheckedNodes(true);
    $("#parentId").val(nodes[0].name);
    $("#parentId").attr("data-id",nodes[0].id);
    $("#parentId").validation();
    hideTreeMenu();
}
function showTreeMenu() {
    $("#menuTree").slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}
function hideTreeMenu() {
    $("#menuTree").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
    if (!(event.target.id == "parentId" || event.target.id == "menuTree" || $(event.target).parents("#menuTree").length>0)) {
        hideTreeMenu();
    }
}


$(function () {
    initTreeGrid();

    $typeSel = $("#type").select2({
        minimumResultsForSearch: Infinity
    });

    $("#resourceForm").validation({icon: true});
    $("#add").on('click', function (event) {
        if ($("#resourceForm").valid(this) == false) {
            return false;
        } else {
            doAdd();
        }
    });
    $("#update").on('click', function (event) {
        if ($("#resourceForm").valid(this) == false) {
            return false;
        } else {
            doUpdate();
        }
    });
})

function initTreeGrid() {
    $.ajax({
        processData: false,
        url: "/sys/resource/findResourceTable",
        type: "POST",
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            var tdHtml = "";
            $.each(data.module, function (i, resource) {
                if (resource.parentId == 0) {
                    tdHtml += '<tr class="treegrid-' + resource.id + '"><td colspan="6" ><a>' + (resource.name || "") + '</a></td>';
                } else {
                    tdHtml += '<tr class="treegrid-' + resource.id + ' treegrid-parent-' + resource.parentId + '"><td></i><a>' + (resource.name || "") + '</a></td>';
                    tdHtml += '<td class="hidden" id="resourceId" name="resourceId" value="' + resource.id + '"></td>'
                    tdHtml += '<td>' + (resource.url || "") + '</td>';
                    tdHtml += "<td>" + (resource.permission || "") + "</td>";
                    tdHtml += "<td>" + (resource.sort || "") + "</td>";
                    switch (resource.type) {
                        case 1:
                            tdHtml += "<td>菜单</td>";
                            break;
                        case 2:
                            tdHtml += "<td>按钮</td>";
                            break;
                        case 3:
                            tdHtml += "<td>超链接</td>";
                            break;
                        case 4:
                            tdHtml += "<td>列表</td>";
                            break;
                        case 5:
                            tdHtml += "<td>其他</td>";
                            break;
                        default:
                            tdHtml += "<td></td>";
                            break;
                    }
                    tdHtml +=
                        "<td><div class='icon-flex'><a href='javascript:void(0);' title='添加子节点' onclick='initAdd(" + resource.id + ")' ><i class='oicon oicon-add'></i></a>" +
                        "<a href='javascript:void(0);' title='修改' onclick='initUpdate(" + resource.id + ")' ><i class='oicon oicon-edit'></i></a>" +
                        "<a id='delRResource' href='javascript:void(0);' title='删除' onclick='delResoure("+ resource.id +")'><i class='oicon oicon-del'></i></a>" +
                        "</td></div></tr>";
                }
                
                $('#treeTableTbody').html(tdHtml);
            })
            $("#resourceTreeTable").treegrid({
                expanderTemplate: '<span class="treegrid-expander"></span>',
                indentTemplate: '<span class="treegrid-indent"></span>',
                treeColumn: 0,
                expanderExpandedClass: 'treegrid-expander-expanded',
                expanderCollapsedClass: 'treegrid-expander-collapsed'
            });
            $('#treeTableTbody').find('tr').each(function(){
                if ($(this).treegrid('getDepth')>0){
                    $(this).treegrid('collapse');
                }
            });

            var ztreeNode = data.module;
            $.each(ztreeNode, function (i, resource) {
                resource.pId = resource.parentId;
                resource.url = "";
                resource.open = true;
            });
            var ztree = $.fn.zTree.init($("#menuTree"), setting, ztreeNode);
        }
    })
}

function initAdd(parentId) {
    $("#add").removeAttr("disabled");
    $("#resourceForm").validation({icon: false});
    $("#sort").val("");
    $("#url").val("");
    $("#resourceName").val("");
    $("#permission").val("");
    var treeObj = $.fn.zTree.getZTreeObj("menuTree");
    var nodes = treeObj.getNodeByParam('id',parentId);
    treeObj.checkNode(nodes,true,false);
    $("#parentId").val(nodes.name);
    $("#parentId").attr("data-id",nodes.id);

    $typeSel.val(1).trigger("change");

    $('#selectGroup').modal("show");
    $("#myModalLabel").html("添加");
    $("#update").hide();
    $("#add").show();
}

function doAdd() {
    $("#add").attr("disabled",true);
    var sort = $("#sort").val();
    var url = $("#url").val();
    var parentId = $("#parentId").attr("data-id");
    var name = $("#resourceName").val();
    var permission = $("#permission").val();
    var available=$(":radio[name='available']:checked").val();
    var type = $typeSel.val();
    $.ajax({
        processDate: false,
        type: "POST",
        url: "/sys/resource/add",
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify({
            url: url,
            parentId: parentId,
            name: name,
            permission: permission,
            sort: sort,
            available: available,
            type: type
        }),
        success: function (data) {
            if (data.success){
                initTreeGrid();
                $('#selectGroup').modal("hide");
                $.scojs_message('管理员用户添加成功', $.scojs_message.TYPE_OK);
            }
        },
        error: function (data) {
            $.scojs_message('管理员用户添加失败', $.scojs_message.TYPE_ERROR);
            $("#add").removeAttr("disabled");
        }
    })
}

function initUpdate(id) {
    $("#update").removeAttr("disabled");
    $("#resourceForm").validation({icon: false});
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/resource/findById/"+id,
        contentType: 'application/json',
        dataType: 'json',
        success: function (data) {
            $("#resourceId").val(data.module.id);//资源id
            $("#resourceName").val(data.module.name);
            $("#url").val(data.module.url);
            $("#permission").val(data.module.permission);
            var treeObj = $.fn.zTree.getZTreeObj("menuTree");
            var nodes = treeObj.getNodeByParam("id",data.module.parentId);
            treeObj.checkNode(nodes,true,false);
            $("#parentId").val(nodes.name);
            $("#parentId").attr("data-id",nodes.id);

            $typeSel.val(data.module.type).trigger("change");

            $("#sort").val(data.module.sort);
            $('#selectGroup').modal("show");
            $("#myModalLabel").html("修改");
            $("#add").hide();
            $("#update").show();
        }
    });
}

function doUpdate() {
    $("#update").attr("disabled",true);
    var resourceId = $("#resourceId").val();//资源id
    var resourceName = $("#resourceName").val();//资源名
    var url = $("#url").val();//url地址
    var parentId = $("#parentId").attr("data-id");
    var permission = $("#permission").val();
    var sort = $("#sort").val();
    var available=$(":radio[name='available']:checked").val();
    var type = $typeSel.val();

    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/resource/update",
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify({
            id: resourceId,
            parentId: parentId,
            name: resourceName,
            url: url,
            permission: permission,
            sort: sort,
            available: available,
            type: type
        }),
        success: function (data) {
            if (data.success){
                initTreeGrid();
                $('#selectGroup').modal("hide");
                $.scojs_message('管理员用户修改成功', $.scojs_message.TYPE_OK);
            }
        },
        error: function (data) {
            $.scojs_message('管理员用户修改失败', $.scojs_message.TYPE_ERROR);
            $("#update").removeAttr("disabled");
        }
    });
}

function delResoure(id) {
    bootbox.confirm({
        title: "删除",
        message: "是否删除该资源",
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
                    type: 'POST',
                    url: "/sys/resource/delete/"+id,
                    contentType: 'application/json',
                    dataType: 'json',
                    success: function(data){
                        if(data.success){
                            initTreeGrid();
                            $.scojs_message('删除资源信息成功', $.scojs_message.TYPE_OK);
                        } else {
                            $.scojs_message('删除资源信息失败', $.scojs_message.TYPE_ERROR);
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
