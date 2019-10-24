package com.example.ithelper.common.response;

public enum  CommonErrorMsg {

    //操作成功00001
    SUCCESS(99999,"操作成功"),

    //通用错误类型10001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法 , 操作失败"),

    //自定义未知错误
    SYSTEM_ERROR(10002,"系统内部错误"),

    //20000开头为账号申请表相关错误定义
    Application_Not_Exist(20001,"申请表不存在"),

    Application_Already_Exist(20002,"申请表已存在"),

    Application_No_Submit(20003,"申请表还没提交"),

    Applicetion_Cannot_Invalid(20004,"申请表不能作废，请先改为暂存"),

    //30000开头为主机软件信息相关错误定义
    AccountName_NOT_EXIST(30001,"账号名不存在"),

    //40000开头为用户信息相关错误定义
    PASSWORD_NOT_MATCH(40001,"输入的密码不一致"),

    USER_ALREADY_EXIST(40002,"用户已存在"),

    USER_NOT_EXIST(40003,"用户不存在"),

    USER_NO_PERMISSION(40004,"没有权限访问"),

    USER_LOGIN_FAIL(40002,"账号或密码错误！"),

    USER_NOT_LOGIN(40005,"用户还未登陆，请先登录认证！"),

    USER_ALREADY_LOGIN(40006,"用户已经在其他电脑登录，请重新登录"),

    USER_IS_DISABLED(40007,"账号已被禁用，请联系管理员")
    ;


    private int errCode;
    private String errMsg;

    CommonErrorMsg(int errCode,String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public int getErrCode(){
        return this.errCode;
    }

    public String getErrMsg(){
        return this.errMsg;
    }
}
