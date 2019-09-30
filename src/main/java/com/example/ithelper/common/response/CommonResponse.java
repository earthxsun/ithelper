package com.example.ithelper.common.response;

import java.util.HashMap;

public class CommonResponse extends HashMap<String,Object> {

    public CommonResponse message(String message){
        this.put("message",message);
        return this;
    }

    public CommonResponse data(Object data){
        this.put("data",data);
        return this;
    }

    public CommonResponse code(int code){
        this.put("code",code);
        return this;
    }

    @Override
    public CommonResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public CommonResponse() {
    }

    public CommonResponse(Object data) {
        this.put("code",CommonErrorMsg.SUCCESS.getErrCode());
        this.put("message",CommonErrorMsg.SUCCESS.getErrMsg());
        this.put("data",data);
    }
}
