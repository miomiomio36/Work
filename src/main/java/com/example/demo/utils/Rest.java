package com.example.demo.utils;

import com.example.demo.enums.CodeEnum;

public record Rest<T>(int code, String message, T data) {
    public static <T> Rest<T> success(T data){return new Rest<>(CodeEnum.SUCCESS.getCode(),CodeEnum.SUCCESS.getMsg(),data);}
    public static <T> Rest<T> success(){return new Rest<>(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg(), null);}
    public static <T> Rest<T> failure(CodeEnum codeEnum){return new Rest<>(codeEnum.getCode(),codeEnum.getMsg(),null);}
    public static <T> Rest<T> failure(String msg){return new Rest<>(400,msg,null);}
}
