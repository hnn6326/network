package com.hnn.net.parameter;

import com.google.gson.Gson;

/**
 * Created by hnn on 2018/1/10.
 */

public class RequestParameterFactory {

    private RequestParameters mRequestParameters = new RequestParameters();

    public RequestParameters creatRequestParameter(IParameter parameter, Object request){
        mRequestParameters.requestPath = parameter.getRequestPath();
        mRequestParameters.requestType = parameter.getRequestType();
        mRequestParameters.requestBodyStr = new Gson().toJson(request);
        mRequestParameters.requestBody = request;
        return mRequestParameters;
    }

}
