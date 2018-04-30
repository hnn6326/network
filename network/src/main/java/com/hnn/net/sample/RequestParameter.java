package com.hnn.net.sample;

import com.hnn.net.parameter.CacheConfig;
import com.hnn.net.parameter.IParameter;

/**
 * Created by hnn on 2018/1/15.
 */

public enum RequestParameter implements IParameter {


    TESTA("student/login.json", "post", CacheConfig.DEFAULT);

    RequestParameter(String requestPath, String requestType, CacheConfig cacheConfig) {
        mRequestPath = requestPath;
        mRequestType = requestType;
        mCacheConfig = cacheConfig;
    }

    public String mRequestPath;
    public String mRequestType;
    public CacheConfig mCacheConfig;

    @Override
    public String getRequestPath() {
        return mRequestPath;
    }

    @Override
    public String getRequestType() {
        return mRequestType;
    }

    @Override
    public CacheConfig getRequestCacheConfig() {
        return mCacheConfig;
    }
}
