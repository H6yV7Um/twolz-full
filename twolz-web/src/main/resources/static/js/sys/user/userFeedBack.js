var pagination;

$(function() {
    getFeedBackList();

    $("select").select2({
        minimumResultsForSearch: Infinity
    });

    $("#feedBackModal").on("hidden.bs.modal",function () {
        $('#imgList').children().remove();
        $(this).find("form")[0].reset();
    });
});

function getFeedBackList(f){
    var param = {};
    if(f){
        $.extend(param,$(f.form).serializeObject());
    }
    $("#feedBackList").datagrid({
        url:'/user/userFeedBack/list',
        data:param
    });
}

function initEdit(id) {

    $.ajax({
        processData: false,
        type: 'POST',
        url: "/user/userFeedBack/info/"+id,
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){

            $('#userName').val(data.module.userName);
            $('#createTime').val(new Date(data.module.createTime).format("yyyy-MM-dd hh:mm:ss"));
            $('#userPhone').val(data.module.userPhone);
            if(data.module.imgs)
            {
                var imgHtml= [];
                var img = data.module.imgs.split(",");
                for(var key in img)
                {
                    imgHtml.push('<img src="'+img[key]+'" class="col-xs-3" data-url="'+img[key]+'" />');
                }
                $('#imgList').append(imgHtml.join(""));
            }
            $('#imgList').popimage();
            $('#content').val(data.module.content);

            $('#feedBackModal').modal("show");
            $("#myModalLabel").html("反馈详情");
        }
    });
}


Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,
        "d+" : this.getDate(),
        "h+" : this.getHours(),
        "m+" : this.getMinutes(),
        "s+" : this.getSeconds(),
        "q+" : Math.floor((this.getMonth()+3)/3),
        "S"  : this.getMilliseconds()
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}
