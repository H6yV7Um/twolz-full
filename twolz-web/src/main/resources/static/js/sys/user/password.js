/**
 * Created by duanxuepeng on 2017/5/19.
 */
var stepy = function () {
    var count = function(time){
        var span = $("#count");
        var hander = setInterval(function () {
            time = time - 1;
            if(time == -1)
            {
                window.location.href='/main/'
            }else{
                span.text(time);
            }
        }, 1000);
    };
    $(document).on('keyup', function (event) {
        if(event.keyCode == 13 && ($('.stepy-header ul li')[0].className == 'active')) {
            $("#next").attr("disabled",true);
            if ($("#step1").valid(this) == false) {
                $("#next").removeAttr("disabled");
                return false;
            }
            //验证旧密码是否一致
            var password = $("#password").val().trim();

            var dataObj = JSON.stringify({
                password:password});

            $.ajax({
                processData: false,
                type: 'POST',
                url: "/sys/user/checkPwd",
                contentType: 'application/json',
                data: dataObj,
                dataType: 'json',
                success: function (data) {
                    if(data.success){
                        $('a[href="#step2"]').tab('show');
                        $('li[data-mark="#step2"]').addClass('active');
                    } else {
                        $.scojs_message("旧密码不正确", $.scojs_message.TYPE_ERROR);
                        $("#next").removeAttr("disabled");
                    }
                }
            });
        }
        if (event.keyCode == 13 && ($('.stepy-header ul li')[1].className == 'active')) {
            $("#next").attr("disabled",true);
            if ($("#step2").valid(this) == false) {
                $("#next").removeAttr("disabled");
                return false;
            }
            var pat2 = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$/; //8位以上数字或字母，至少一个数字和字母
            if (!pat2.test($("#newpassword").val())) {
                $.scojs_message("密码至少为8位,且包含至少一个数字和字母", $.scojs_message.TYPE_ERROR);
                $("#newpassword").focus();
                return false;
            }
            if($("#newpassword").val() == $("#phone").val()){
                $.scojs_message("密码不能和用户名相同", $.scojs_message.TYPE_ERROR);
                $("#newpassword").focus();
                return false;
            }
            if ($("#newpassword").val() != $("#newpasswordagain").val()) {
                $.scojs_message("两次密码不一致", $.scojs_message.TYPE_ERROR);
                $("#newpasswordagain").focus();
                return false;
            }
            var password = $("#password").val().trim();
            var newpassword = $("#newpassword").val().trim();
            var newpasswordagain = $("#newpasswordagain").val().trim();

            var dataObj = JSON.stringify({
                password:password,
                newpassword:newpassword,
                newpasswordagain:newpasswordagain});

            $.ajax({
                processData: false,
                type: 'POST',
                url: "/sys/user/changePwd",
                contentType: 'application/json',
                data: dataObj,
                dataType: 'json',
                success: function (data) {
                    if(data.success){
                        //提交新密码
                        $('a[href="#step3"]').tab('show');
                        $('li[data-mark="#step3"]').addClass('active');
                        count(3);
                    } else {
                        $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
                        $("#next").removeAttr("disabled");
                    }
                }
            });
        }
    });
    $(document).on('click', '#next', function () {
        $("#next").attr("disabled",true);
        if ($("#step1").valid(this) == false) {
            $("#next").removeAttr("disabled");
            return false;
        }
        //验证旧密码是否一致
        var password = $("#password").val().trim();

        var dataObj = JSON.stringify({
            password:password});

        $.ajax({
            processData: false,
            type: 'POST',
            url: "/sys/user/checkPwd",
            contentType: 'application/json',
            data: dataObj,
            dataType: 'json',
            success: function (data) {
                if(data.success){
                    $('a[href="#step2"]').tab('show');
                    $('li[data-mark="#step2"]').addClass('active');
                } else {
                    $.scojs_message("旧密码不正确", $.scojs_message.TYPE_ERROR);
                    $("#next").removeAttr("disabled");
                }
            }
        });
    });
    $(document).on('click', '#submit', function () {
        $("#next").attr("disabled",true);
        if ($("#step2").valid(this) == false) {
            $("#next").removeAttr("disabled");
            return false;
        }
        var pat2 = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$/; //8位以上数字或字母，至少一个数字和字母
        if (!pat2.test($("#newpassword").val())) {
            $.scojs_message("密码至少为8位,且包含至少一个数字和字母", $.scojs_message.TYPE_ERROR);
            $("#newpassword").focus();
            return false;
        }
        if($("#newpassword").val() == $("#phone").val()){
            $.scojs_message("密码不能和用户名相同", $.scojs_message.TYPE_ERROR);
            $("#newpassword").focus();
            return false;
        }
        if ($("#newpassword").val() != $("#newpasswordagain").val()) {
            $.scojs_message("两次密码不一致", $.scojs_message.TYPE_ERROR);
            $("#newpasswordagain").focus();
            return false;
        }
        var password = $("#password").val().trim();
        var newpassword = $("#newpassword").val().trim();
        var newpasswordagain = $("#newpasswordagain").val().trim();

        var dataObj = JSON.stringify({
            password:password,
            newpassword:newpassword,
            newpasswordagain:newpasswordagain});

        $.ajax({
            processData: false,
            type: 'POST',
            url: "/sys/user/changePwd",
            contentType: 'application/json',
            data: dataObj,
            dataType: 'json',
            success: function (data) {
                if(data.success){
                    //提交新密码
                    $('a[href="#step3"]').tab('show');
                    $('li[data-mark="#step3"]').addClass('active');
                    count(3);
                } else {
                    $.scojs_message(data.errorMessage, $.scojs_message.TYPE_ERROR);
                    $("#next").removeAttr("disabled");
                }
            }
        });
    });
    return {
        init: function () {
            $("#step1").validation();
            $("#step2").validation();
        }
    };

}();
$(function () {
    stepy.init();
    console.log($('.stepy-header ul li')[0].className);
});