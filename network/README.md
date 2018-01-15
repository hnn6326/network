#网络请求框架

##概述
###组成结构
<pre>
    Retrofit + RxJava + OkHttp
</pre>
###调用方式
<pre><code>
    RequestBody requestBody = new RequestBody();
            requestBody.testA = "a";
            requestBody.testB = "b";
            
            NetWorkServices netWorkServices = new NetWorkServices();
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
</code></pre>