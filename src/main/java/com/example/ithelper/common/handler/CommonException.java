package com.example.ithelper.common.handler;

public class CommonException extends Exception {

    private int code;

    public CommonException (String msg,int code){
        super(msg);
        this.code =code;
    }

    public int getCode(){
        return this.code;
    }
}
