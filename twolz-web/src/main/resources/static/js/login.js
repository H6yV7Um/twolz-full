var Login = function(){
    var loginsub = function(){
        if(checkInput())
        {
            $(document).off('click','#loginsubmit');
            var mobile = $("#mobile").val().trim();
            var passwd = $("#passwd").val().trim();

            var pat = /^1\d{10}$/; //手机号码1开头
            if (!pat.test($("#mobile").val())) {
                $('.form-message').html("手机号码格式不正确");
                $("#phone").focus();
                return false;
            }

            $.ajax({
                processData: false,
                type: 'POST',
                url: "/loginPost",
                contentType: 'application/json',
                data: JSON.stringify({mobile: mobile, passwd: passwd}),
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $("#loginsubmit").html("登录中...");
                        $('.form-message').html("");
                        window.location.href = "/main";
                    } else {
                        $(document).on('click','#loginsubmit',function(){
                            loginsub();
                        })
                        $('.form-message').html(data.errorMessage);
                    }
                }
            });
        }
    }
    var changesub = function(){
        $(document).off('click','#changesubmit');
        if(!checkInputform2()){
            $(document).on('click','#changesubmit',function(){
                changesub();
            })
            return false;
        }
        var phone = $("#phonenumber").val().trim();
        var password = $("#newpasswd").val().trim();
        var againPassword = $("#newpasswd2").val().trim();
        var authCode = $("#vertextcode").val().trim();

        $("#changesubmit").html("处理中...");
        $('.form-message').html("");
        $.ajax({
            processData: false,
            type: 'POST',
            url: "/backPassword",
            contentType: 'application/json',
            data: JSON.stringify({phone: phone, password: password, againPassword:againPassword, authCode:authCode}),
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    $.scojs_message('密码找回成功', $.scojs_message.TYPE_OK);
                    $(".resetbox").hide();
                } else {
                    $(document).on('click','#changesubmit',function(){
                        changesub();
                    })
                    $("#changesubmit").html("提&nbsp;&nbsp;&nbsp;&nbsp;交");
                    $('.form-message').html(data.errorMessage);
                }
            }
        });
    }
    //验证码获取倒计时
    var updateTime = function (time) {
        var btn = $("#getvertextcode");
        var timeBegin = new Date();
        var timeEnd;
        btn.attr("disabled", true);
        btn.text(time <= 0 ? "获取验证码" : ("" + (time) + "s重新发送"));
        var hander = setInterval(function () {
            timeEnd = new Date();
            if (time - parseInt((timeEnd - timeBegin) / 1000) <= 0) {
                clearInterval(hander);
                hander = 0;
                btn.removeAttr("disabled");
                btn.text("获取验证码");
            }
            else {
                btn.text("" + (time - parseInt((timeEnd - timeBegin) / 1000)) + "s重新发送");
            }
        }, 1000);
    };
    //输入验证
    var checkInput = function(){
        //验证手机号码是否为空
        var mobileObj = $("#mobile");
        var passwdObj = $("#passwd");

        if (mobileObj.val() == "") {
            mobileObj.addClass("error");
            mobileObj.focus();
            return false;
        }else{
            mobileObj.removeClass("error");
        }
        //验证用户密码是否为空
        if (passwdObj.val() == "") {
            passwdObj.addClass("error");
            passwdObj.focus();
            return false;
        }else{
            passwdObj.removeClass("error");
        }
        if (isNaN(mobileObj.val())) {
            $('.form-message').html("请输入正确的手机号码");
            return false;
        }
        return true;
    }
    var checkInputform2 = function(){
        if ($("#phonenumber").val() == "") {
            $('.form-message2').html("请输入手机号码");
            $("#phonenumber").addClass("error");
            $("#phonenumber").focus();
            return false;
        }
        var pat = /^1\d{10}$/; //手机号码1开头
        var pat2 = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$/; //8位以上数字或字母，至少一个数字和字母
        if (!pat.test($("#phonenumber").val())) {
            $('.form-message2').html("手机格式不正确");
            $("#phonenumber").addClass("error");
            $("#phonenumber").focus();
            return false;
        }
        if ($("#vertextcode").val() == "") {
            $('.form-message2').html("请输入验证码");
            $("#vertextcode").addClass("error");
            return false;
        }
        if ($("#vertextcode").val().length != 6) {
            $('.form-message2').html("验证码必须为6位");
            $("#vertextcode").addClass("error");
            return false;
        }
        if ($("#newpasswd").val() == "") {
            $('.form-message2').html("请输入新密码");
            $("#newpasswd").addClass("error");
            $("#newpasswd").focus();
            return false;
        }
        if (!pat2.test($("#newpasswd").val())) {
            $('.form-message2').html("密码至少为8位,且包含至少一个数字和字母");
            $("#newpasswd").addClass("error");
            $("#newpasswd").focus();
            return false;
        }
        if($("#newpasswd").val() == $("#phonenumber").val()){
            $('.form-message2').html("密码不能和用户名相同");
            $("#newpasswd").addClass("error");
            $("#newpasswd").focus();
            return false;
        }
        if ($("#newpasswd").val() != $("#newpasswd2").val()) {
            $('.form-message2').html("两次密码不一致");
            $("#newpasswd2").addClass("error");
            $("#newpasswd2").focus();
            return false;
        }
        $("#phonenumber").removeClass("error");
        $('.form-message2').html("");
        return true;
    }

    var initBackPwdForm = function(){
        $("#phonenumber").val(null);
        $("#newpasswd").val(null);
        $("#newpasswd2").val(null);
        $("#vertextcode").val(null);

        $("#changesubmit").html("提&nbsp;&nbsp;&nbsp;&nbsp;交");
        $(document).on('click','#changesubmit',function(){
            changesub();
        })
    }

    var actions = function(){
        $(document).on('mousedown','.eye',function(){
            $(this).siblings('input').attr('type','text');
            $(this).addClass('eyeopen');
        })
        $(document).on('touchend','.eye',function(){
            $(this).siblings('input').attr('type','password');
            $(this).removeClass('eyeopen');
        })
        $(document).on('touchstart','.eye',function(){
            $(this).siblings('input').attr('type','text');
            $(this).addClass('eyeopen');
        })
        $(document).on('mouseup','.eye',function(){
            $(this).siblings('input').attr('type','password');
            $(this).removeClass('eyeopen');
        })
        //切换登录框
        $(document).on('click','[data-href="resetbox"]',function(){
            initBackPwdForm();
            $("#loginbox").hide();
            $(".resetbox").show();
        })
        $(document).on('click','.close',function(){
            $(".resetbox").hide();
        })
        $(document).on('touchend','[data-href="resetbox"]',function(){
            initBackPwdForm();
            $(".loginbox").hide();
            $(".resetbox").show();
        })
        $(document).on('touchend','.close',function(){
            $(".resetbox").hide();
            $(".loginbox").show();
        })
        $("#phone").focus();
        //提交按钮
        $(document).on('click','#loginsubmit',function(){
            loginsub();
        })
        $(document).on('click','#changesubmit',function(){
            changesub();
        })
        //键盘enter提交
        $(document).keyup(function (event) {
            if(event.keyCode == 13 && $(".resetbox").is(":visible"))
            {
                changesub();
            }else if(event.keyCode == 13 && $(".resetbox").is(":hidden")){
                loginsub();
            }
        });

        //获取验证码
        $(document).on('click','#getvertextcode',function(){
            if($("#phonenumber").val().length==0){
                $('.form-message2').html("请输入手机号码");
                $("#phonenumber").addClass("error");
                $("#phonenumber").focus();
                return false;
            }
            var pat = /^1\d{10}$/;
            if (!pat.test($("#phonenumber").val())) {
                $('.form-message2').html("手机号码格式不正确");
                $("#phonenumber").addClass("error");
                $("#phonenumber").focus();
                return false;
            }
            $("#phonenumber").removeClass("error");
            $('.form-message2').html("");

            $("#getvertextcode").attr("disabled", true);
            var phone = $("#phonenumber").val();

            $.ajax({
                processData: false,
                type: 'POST',
                url: "/sendBackPwdMsg",
                contentType: 'application/json',
                data: JSON.stringify({phone: phone}),
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        //发送成功，开始计时
                        updateTime(59);
                    } else {
                        $('.form-message').html(data.errorMessage);
                        $("#getvertextcode").removeAttr("disabled");
                    }
                }
            });

        })
        $(document).on('blur','input',function(){
            $(this).removeClass("error");
        })
    }

    return {
        init: function(){
            actions();
        }
    };
}()
$(function(){
    Login.init();
})