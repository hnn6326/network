package com.hnn.net.parameter;

/**
 * Created by hnn on 2018/1/10.
 */

public enum LoginParameter implements IParameter{

    LOGIN("student/login.json", "post");

    LoginParameter(String requestPath, String requestType) {
        mRequestPath = requestPath;
        mRequestType = requestType;
    }

    public String mRequestPath;
    public String mRequestType;

    @Override
    public String getRequestPath() {
        return mRequestPath;
    }

    @Override
    public String getRequestType() {
        return mRequestType;
    }
}
