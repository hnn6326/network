package com.fclassroom.appstudentclient.net;

import com.fclassroom.appstudentclient.net.callback.INetWorkServices;
import com.fclassroom.appstudentclient.net.callback.IRequestListener;
import com.fclassroom.appstudentclient.net.callback.IServices;
import com.fclassroom.appstudentclient.net.parameter.RequestParameters;
import com.fclassroom.appstudentclient.net.util.ObjectUtils;
import com.fclassroom.appstudentclient.net.util.Response;
import com.fclassroom.appstudentclient.scheduler.SchedulersCompat;
import com.google.gson.Gson;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;

/**
 * 网络请求
 * Created by hnn on 2018/1/10.
 */

public class NetWorkServices implements INetWorkServices {

    public static final int NETWORK_TIMEOUT = 60;

    private static final String MEDIA_TYPE = "application/json";

    private String mBaseUrl;

    private Retrofit mRetrofit;

    public void setBaseUrl(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    @Override
    public String sendRequest(RequestParameters requestParameters, final IRequestListener requestListener) {
        Retrofit retrofit = getRetrofit();
        IServices services = retrofit.create(IServices.class);

        Subscriber subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if(requestListener != null){
                    requestListener.onError(new Response(e));
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                if(requestListener != null){
                    requestListener.onSuccess(new Response(responseBody));
                }
            }
        };

        if(requestParameters.requestIsGet()){
            try {
                services.getRequest(mBaseUrl + requestParameters.requestPath,
                        ObjectUtils.objectToMap(requestParameters.requestBody)).timeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                        .onBackpressureBuffer()
                        .take(1)
                        .compose(SchedulersCompat.<ResponseBody>applyComputationSchedulers())
                        .subscribe(subscriber);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }else if(requestParameters.requestIsPost()){
            services.postRequest(mBaseUrl + requestParameters.requestPath, getRequestBody(requestParameters.requestBodyStr))
                    .timeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                    .onBackpressureBuffer()
                    .take(1)
                    .compose(SchedulersCompat.<ResponseBody>applyExecutorSchedulers())
                    .subscribe(subscriber);
        }
        return UUID.randomUUID().toString();
    }

    public Retrofit getRetrofit() {
        if(mRetrofit == null){
            OkHttpClientHelper.getInstance().init();
            mRetrofit = new Retrofit.Builder()
                    .client(OkHttpClientHelper.getInstance().getOkHttpClient())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(mBaseUrl)
                    .build();
        }else{
            return mRetrofit;
        }
        return mRetrofit;
    }

    protected RequestBody getRequestBody(String request) {
        return RequestBody.create(MediaType.parse(MEDIA_TYPE), request);
    }

}
