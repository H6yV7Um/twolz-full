package com.twolz.qiyi.common.core;

/**
 * @author 刘为
 * 系统管理的错误码
 *  0、系统管理(10000~19999)
 *  1、用户中心(20000~29999)
 *	2、资源中心(30000~39999)
 */
public class SysCode extends BaseResultCode{

    //用户名或密码不正确
    public final static String LOGIN_UN_OR_PWD_INCORRECT = "1001";
    //用户手机号码已存在
    public final static String USER_MOBILE_EXISTS = "1002";
    //操作失败，该角色名称已存在
    public final static String ROLE_EXISTS = "1003";
    //用户名不能为空
    public final static String LOGIN_USER_NAME_EMPTY = "1004";
    //密码不能为空
    public final static String LOGIN_PASSWORD_EMPTY = "1020";
    //用户账号已停用
    public final static String LOGIN_USER_NAME_DISABLE = "1005";
    //手机验证码不能为空
    public final  static String BACKPWD_AUTH_CODE_EMPTY = "1006";
    //新密码不能为空
    public final  static String BACKPWD_PASSWORD_EMPTY = "1007";
    //两次密码不一致
    public final  static String BACKPWD_PASSWORD_NEQ = "1008";
    //手机验证码不正确
    public final  static String BACKPWD_AUTH_CODE_INCORRECT = "1009";
    //用户账号已停用
    public final  static String BACKPWD_USER_NAME_DISABLE = "1010";
    //手机号码不能为空
    public final  static String BACKPWD_MOBILE_EMPTY = "1011";
    //姓名重复
    public final  static String NAME_IS_EXIST = "1012";
    //旧密码不正确
    public final  static String CHANGEPWD_PWD_INCORRECT = "1013";
    //新密码不能为空
    public final  static String CHANGEPWD_PASSWORD_EMPTY = "1014";
    //两次密码不一致
    public final  static String CHANGEPWD_PASSWORD_NEQ = "1015";
    //新密码不能与旧密码相同
    public final  static String OLD_PASSWORD_NEQ = "1016";
    //文件上传失败
    public final  static String UPLOAD_FILE_ERROR = "1017";
    //用户不存在
    public final static String COMMON_ERROR_FORMAT = "1018";
    //该编码的提示信息已存在
    public final static String TIP_CODE_EXIST = "1019";
    //反馈信息提交不成功
    public final static String USERFEED_ERROR = "1020";
    //反馈信息列表获取失败
    public final static String USERFEED_GETERROR = "1021";
}
