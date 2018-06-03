
var pagination;

$(function() {
    var page=1;
    var row=10;

    searchList();
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
        getProductList(num,row);
    });

    $("#productForm").validation({icon: true});

    //添加验证
    $(document).on('click',"#addAccount", function (event) {
        console.info("add is in");
        if ($("#productForm").valid(this) == false) {
            return false;
        } else {
            doAdd();
        }
    });
    //修改验证
    $(document).on('click',"#updateAccount", function (event) {
        if ($("#productForm").valid(this) == false) {
            return false;
        } else {
            /**************数据处理*************/
            doUpdate();
        }
    });
});

function searchList() {
    getProductList(1,10);
}

function formtemplate(data){
    var templateoutput;
    if(data == 0) {
        templateoutput = template('tpl-userform',{add:true});
        $("#form-modal").html("").html(templateoutput);
    } else{
        templateoutput = template('tpl-userform',data);
        $("#form-modal").html("").html(templateoutput);
    }
    $("#productForm").validation({icon: false});
    $roleSel = $("#roleSel").select2({language: 'zh-CN'});
}

/**
 * 点击添加弹出添加框
 */
function initAddProduct() {
    $("#addAccount").removeAttr("disabled");
    formtemplate(0);
    $("#productForm").validation({icon: false});
    $("#available").prop("checked",true);
    $roleSel.val(null);
    $roles = "";
    $('#accountModal').modal("show");
}

function searchList(f) {
    var param = {};
    if(f){
        $.extend(param,$(f.form).serializeObject());
    }
    $("#tbody_01").datagrid({
        url: "/sys/userfeedback/list",
        data:param
    });
}










