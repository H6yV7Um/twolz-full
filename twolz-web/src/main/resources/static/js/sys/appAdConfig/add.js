$(function() {
    getAndroidConfig();
    $('#appType').val('1');

    //取android广告信息
    $('#androidTab').on('shown.bs.tab', function (e) {
        console.info("is android tab");
        $('#appType').val('1');
        getAndroidConfig();
    });
    //取ios广告信息
    $('#iosTab').on('shown.bs.tab', function (e) {
        console.info("is ios tab");
        $('#appType').val('2');
        getIosConfig();
    });

    // 链接输入框能否输入
    setUrlEnabled();

    $("#add").validation({icon: true});
    
    // 保存
    $('#save').on('click', function (e) {
        if ($("#add").valid(this) == false) {
            return false;
        } else {
            doSave();
        }
    });

});

function doSave(){
    var id = $('#id').val();
    if(id === '') {
        doAdd();
    } else {
        doUpdate();
    }
}

// 添加广告信息
function doAdd() {
    if ($(":radio[name='isUrl']:checked").val() ==1 && $("#url").val()=='') {
        $.scojs_message('输入链接地址', $.scojs_message.TYPE_ERROR);
        return false;
    }
    var appType = $('#appType').val();
    var imgs;
    if ($('#appType').val()==='1') {
        imgs = JSON.stringify({'1080*1920':$("#androidpic").attr('data-val')});
    } else if ($('#appType').val()==='2') {
        imgs = JSON.stringify({'640*960':$("#iospic1").attr('data-val'),'640*1136':$("#iospic2").attr('data-val'),
        '750*1334':$("#iospic3").attr('data-val'),'1242*2208':$("#iospic4").attr('data-val')});
    }
    var url = $('#url').val();
    var isUrl = $(":radio[name='isUrl']:checked").val();
    var showTime = $('#showTime').val();
    var dataObj = JSON.stringify({appType:appType,imgs:imgs,url:url,isUrl:isUrl,showTime:showTime});
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/appAdConfig/add",
        contentType: 'application/json',
        data: dataObj,
        dataType: 'json',
        success: function(data){
            if(data.success){
                if ($('#appType').val()==='1') {
                    getAndroidConfig();
                } else if ($('#appType').val()==='2') {
                    getIosConfig();
                }
                $.scojs_message('广告设置保存成功', $.scojs_message.TYPE_OK);
            } else {
                $.scojs_message('广告设置保存失败', $.scojs_message.TYPE_ERROR);
            }
        },
        error: function (data) {
            $.scojs_message('广告设置保存失败', $.scojs_message.TYPE_ERROR);
        }
    });
}

// 更新广告信息
function doUpdate(){
    if ($(":radio[name='isUrl']:checked").val() ==1 && $("#url").val()=='') {
        $.scojs_message('输入链接地址', $.scojs_message.TYPE_ERROR);
        return false;
    }
    var id = $('#id').val();
    var appType = $('#appType').val();
    var imgs;
    if ($('#appType').val()==='1') {
        imgs = JSON.stringify({'1080*1920':$("#androidpic").attr('data-val')});
    } else if ($('#appType').val()==='2') {
        imgs = JSON.stringify({'640*960':$("#iospic1").attr('data-val'),'640*1136':$("#iospic2").attr('data-val'),
        '750*1334':$("#iospic3").attr('data-val'),'1242*2208':$("#iospic4").attr('data-val')});
    }
    var url = $('#url').val();
    var isUrl = $(":radio[name='isUrl']:checked").val();
    var showTime = $('#showTime').val();
    var dataObj = JSON.stringify({id:id,appType:appType,imgs:imgs,url:url,isUrl:isUrl,showTime:showTime});
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/appAdConfig/update",
        contentType: 'application/json',
        data: dataObj,
        dataType: 'json',
        success: function(data){
            if(data.success){
                if ($('#appType').val()==='1') {
                    getAndroidConfig();
                } else if ($('#appType').val()==='2') {
                    getIosConfig();
                }
                $.scojs_message('广告设置保存成功', $.scojs_message.TYPE_OK);
            } else {
                $.scojs_message('广告设置保存失败', $.scojs_message.TYPE_ERROR);
            }
        },
        error: function (data) {
            $.scojs_message('广告设置保存失败', $.scojs_message.TYPE_ERROR);
        }
    });
}

//取android广告信息
function getAndroidConfig(){
    $('#id').val('');
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/appAdConfig/android",
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
            if(data.success){
                $('#id').val(data.module.id);
                if(data.module.isUrl===1){
                    $("#isUrl").prop("checked",true);
                    $("#url").removeAttr('disabled');
                } else if(data.module.isUrl===0) {
                    $("#noUrl").prop("checked",true);
                    $("#url").attr('disabled', 'disabled');
                } else {
                    $("#isUrl").removeAttr("checked");
                    $("#noUrl").removeAttr("checked");
                    $("#url").attr('disabled', 'disabled');
                }
                $('#url').val(data.module.url);
                if(data.module.imgs && data.module.imgs!==''){
                    var img = JSON.parse(data.module.imgs);
                    if (img['1080*1920']){
                        $('#androidpic').attr('src',data.module.imgsHostDomain+img['1080*1920']);
                        $('#androidpic').attr('data-val',img['1080*1920']);
                    }
                }
                $('#showTime').val(data.module.showTime);
            } else {
                $.scojs_message('获取广告设置信息失败', $.scojs_message.TYPE_ERROR);
            }
        },
        error: function (data) {
            $.scojs_message('获取广告设置信息失败', $.scojs_message.TYPE_ERROR);
        }
    });
}

//取ios广告信息
function getIosConfig(){
    $('#id').val('');
    $.ajax({
        processData: false,
        type: 'POST',
        url: "/sys/appAdConfig/ios",
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
            if(data.success){
                $('#id').val(data.module.id);
                if(data.module.isUrl===1){
                    $("#isUrl").prop("checked",true);
                    $("#url").removeAttr('disabled');
                } else if(data.module.isUrl===0) {
                    $("#noUrl").prop("checked",true);
                    $("#url").attr('disabled', 'disabled');
                } else {
                    $("#isUrl").removeAttr("checked");
                    $("#noUrl").removeAttr("checked");
                    $("#url").attr('disabled', 'disabled');
                }
                $('#url').val(data.module.url);
                if(data.module.imgs && data.module.imgs!==''){
                    var img = JSON.parse(data.module.imgs);
                    if (img['640*960']){
                        $('#iospic1').attr('src',data.module.imgsHostDomain+img['640*960']);
                        $('#iospic1').attr('data-val',img['640*960']);
                    }
                    if (img['640*1136']){
                        $('#iospic2').attr('src',data.module.imgsHostDomain+img['640*1136']);
                        $('#iospic2').attr('data-val',img['640*1136']);
                    }
                    if (img['750*1334']){
                        $('#iospic3').attr('src',data.module.imgsHostDomain+img['750*1334']);
                        $('#iospic3').attr('data-val',img['750*1334']);
                    }
                    if (img['1242*2208']){
                        $('#iospic4').attr('src',data.module.imgsHostDomain+img['1242*2208']);
                        $('#iospic4').attr('data-val',img['1242*2208']);
                    }
                }
                $('#showTime').val(data.module.showTime);
            } else {
                $.scojs_message('获取广告设置信息失败', $.scojs_message.TYPE_ERROR);
            }
        },
        error: function (data) {
            $.scojs_message('获取广告设置信息失败', $.scojs_message.TYPE_ERROR);
        }
    });
}

// 链接输入框能否输入
function setUrlEnabled() {
    $('#isUrl').on('click', function (e) {
        $("#url").removeAttr('disabled');
    });
    $('#noUrl').on('click', function (e) {
        $("#url").attr('disabled', 'disabled');
        $("#help").removeClass("has-error");
    });
}





