package com.hnn.net.sample;

import com.hnn.net.parameter.IParameter;

/**
 * Created by hnn on 2018/1/15.
 */

public enum RequestParameter implements IParameter {


    TESTA("student/login.json", "post");

    RequestParameter(String requestPath, String requestType) {
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
