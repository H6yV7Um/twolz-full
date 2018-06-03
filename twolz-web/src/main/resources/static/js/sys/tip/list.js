$(function () {
    searchList();

    $(this).find("form").validation({icon: true});
    // $("#testSl1").select2({
    //     minimumResultsForSearch: Infinity
    // });
    $("#testSl1").select2();
    $("#testSl2").select2();

    $("#objInfoModal").on('hidden.bs.modal',function(){
        $(this).find("form")[0].reset();
        $(this).find("form").validation();
    });
    $("#view").on('hidden.bs.modal',function(){
        $(this).find("form")[0].reset();
    });



});

function searchList(f) {
    var param = {};
    if(f){
        $.extend(param,$(f.form).serializeObject());
    }
    $("#tbody_01").datagrid({
        url:'/sys/tip/list',
        data:param
    });
}

function toAdd() {
    $("#objInfoModal").modal("show");
}

function save(f) {

    var formData = {};
    if(f){
        formData = $(f.form).serializeObject();
    }
    $(f.form).secarFormSubmit({
        url:"/market/coupon/save",
        data:formData,
        success:function (data) {
            if(data.success){
                $.scojs_message('保存成功', $.scojs_message.TYPE_OK);
                $("#add").modal("hide");
                $("#tbody_01").datagrid();
            }else{
                $.scojs_message(data.module.errorMsg, $.scojs_message.TYPE_ERROR);
            }
        }
    });
} 



