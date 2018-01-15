package com.hnn.net.sample;

import com.hnn.net.NetWorkServices;
import com.hnn.net.callback.IRequestListener;
import com.hnn.net.parameter.RequestParameters;
import com.hnn.net.util.Response;

/**
 * 网络请求Demo
 * Created by hnn on 2018/1/15.
 */

public class Test {

    public static void main(String args[]){
        RequestBody requestBody = new RequestBody();
        requestBody.testA = "a";
        requestBody.testB = "b";

        NetWorkServices netWorkServices = new NetWorkServices();
        netWorkServices.setBaseUrl("http://baidu.com");
        netWorkServices.sendRequest(new RequestParameters(RequestParameter.TESTA, requestBody), new IRequestListener() {
            @Override
            public void onSuccess(Response response) {
                ResponseBody responseBody = (ResponseBody) response.getBody(ResponseBody.class);
                System.out.println(responseBody.testAA);
                //TODO 业务逻辑操作
            }

            @Override
            public void onError(Response response) {
                //TODO 请求错误操作
            }
        });
    }
}
