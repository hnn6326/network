package com.fclassroom.appstudentclient.net.parameter;

import com.google.gson.Gson;

/**
 * Created by hnn on 2018/1/10.
 */

public class RequestParameters {

    public String requestPath;

    public String requestBodyStr;

    public Object requestBody;

    public String requestType;

    public boolean requestIsPost(){
        return "post".equals(requestType);
    }

    public boolean requestIsGet(){
        return "get".equals(requestType);
    }

    public RequestParameters(){

    }

    public RequestParameters(IParameter parameter, Object requestBody){
        this.requestBody = requestBody;
        requestBodyStr = new Gson().toJson(requestBody);
        requestPath = parameter.getRequestPath();
        requestType = parameter.getRequestType();
    }

}
