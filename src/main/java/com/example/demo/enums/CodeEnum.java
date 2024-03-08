package com.example.demo.enums;

public enum CodeEnum {
    SUCCESS(200,"操作成功"),
    ERROR(400,"error"),
    TOKEN_NULL(403,"token不存在"),
    USER_INFO_ERROR(405,"用户信息错误"),
    FAIL_APPLY(407,"请求失败"),
    ROLE_ERROR(406,"角色错误"),
    TOKEN_ILLEGAL(408,"token不合法");



    int code;
    String msg;

    CodeEnum(int code, String message){
        this.code=code;
        this.msg=message;
    }
    public int getCode(){return code;}
    public String getMsg(){return msg;}
    public String toString(){
        return "\"{code\":\""+ this.code +"\","+
                "\"msg\":\""+ this.msg+"\"}";
    }
}
