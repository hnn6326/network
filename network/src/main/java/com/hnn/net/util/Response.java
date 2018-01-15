package com.hnn.net.util;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;

public class Response {

    public int code;//	返回码
    public String message;//	返回文字说明	String

    public String bodyStr;

    public Response(ResponseBody responseBody) {
        try {
            bodyStr = responseBody.string();
            Response tempResponse = new Gson().fromJson(bodyStr, Response.class);
            if(tempResponse != null){
                code = tempResponse.code;
                message = tempResponse.message;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response(Throwable e) {
        code = 0;
        message = e.getMessage();
    }

    public Object getBody(Class type) {
        return new Gson().fromJson(bodyStr, type);
    }
}