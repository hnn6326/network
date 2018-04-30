package com.hnn.net.parameter;

import com.google.gson.Gson;

/**
 * Created by hnn on 2018/1/10.
 */

public class RequestParameters {

    public String requestPath;

    public String requestBodyStr;

    public MyRequest request = new MyRequest();

    public String requestStr;

    public String requestType;

    public CacheConfig catchConfig;

    public boolean requestIsPost(){
        return "post".equals(requestType);
    }

    public boolean requestIsGet(){
        return "get".equals(requestType);
    }

    public RequestParameters(){

    }

    public RequestParameters(IParameter parameter, Object requestBody){
        request.requestBody = requestBody;
        requestBodyStr = new Gson().toJson(request.requestBody);
        requestStr = new Gson().toJson(request);
        requestPath = parameter.getRequestPath();
        requestType = parameter.getRequestType();
        catchConfig = parameter.getRequestCacheConfig();
    }

    public class MyRequest{
        //0 不缓存 1缓存
        public CacheConfig catchConfig;

        public Object requestBody;
    }

}
