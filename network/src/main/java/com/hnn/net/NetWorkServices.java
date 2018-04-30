package com.hnn.net;

import android.content.Context;

import com.google.gson.Gson;
import com.hnn.net.callback.INetWorkServices;
import com.hnn.net.callback.IRequestListener;
import com.hnn.net.callback.IServices;
import com.hnn.net.factory.CatchConverterFactory;
import com.hnn.net.parameter.CacheConfig;
import com.hnn.net.parameter.CatchConfigRequestBody;
import com.hnn.net.parameter.RequestParameters;
import com.hnn.net.util.ObjectUtils;
import com.hnn.net.util.Response;
import com.hnn.scheduler.SchedulersCompat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

    public static final String TAG = NetWorkServices.class.getName();

    private Context mContext;

    public static final int NETWORK_TIMEOUT = 60;

    public static final String MEDIA_TYPE = "application/json";

    private String mBaseUrl;

    private Retrofit mRetrofit;

    private HashMap<String, Subscriber> mRequestList = new HashMap<>();

    public void setBaseUrl(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    public NetWorkServices(Context context){
        mContext = context;
    }

    public NetWorkServices(){

    }

    @Override
    public String sendRequest(RequestParameters requestParameters, final IRequestListener requestListener) {
        Retrofit retrofit = getRetrofit();
        IServices services = retrofit.create(IServices.class);

        final String requestId = UUID.randomUUID().toString();

        Subscriber subscriber = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                mRequestList.remove(requestId);
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

        mRequestList.put(requestId, subscriber);

        if(requestParameters.requestIsGet()){
            services.getRequest(mBaseUrl + requestParameters.requestPath,
                    getRequestBody(requestParameters))
                    .timeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                    .onBackpressureBuffer()
                    .take(1)
                    .compose(SchedulersCompat.<ResponseBody>applyComputationSchedulers())
                    .subscribe(subscriber);
        }else if(requestParameters.requestIsPost()){
            services.postRequest(mBaseUrl + requestParameters.requestPath,
                    postRequestBody(requestParameters))
                    .timeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                    .onBackpressureBuffer()
                    .take(1)
                    .compose(SchedulersCompat.<ResponseBody>applyExecutorSchedulers())
                    .subscribe(subscriber);
        }
        return requestId;
    }

    private HashMap<String, Object> getRequestBody(RequestParameters requestParameters) {
        HashMap<String, Object> getRequest = new HashMap<>();
        try {
            getRequest = (HashMap<String, Object>) ObjectUtils.objectToMap(requestParameters.request.requestBody);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        getRequest.put(CacheConfig.class.getName(), new Gson().toJson(requestParameters.catchConfig));
        return getRequest;
    }

    @Override
    public boolean cancelRequest(String requestId) {
        Subscriber requestSubscriber = mRequestList.get(requestId);
        if(requestSubscriber != null && !requestSubscriber.isUnsubscribed()){
            requestSubscriber.unsubscribe();
            return true;
        }
        return false;
    }

    public Retrofit getRetrofit() {
        if(mRetrofit == null){
            OkHttpClientHelper.getInstance().init(mContext);
            mRetrofit = new Retrofit.Builder()
                    .client(OkHttpClientHelper.getInstance().getOkHttpClient())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(new CatchConverterFactory())
                    .baseUrl(mBaseUrl)
                    .build();
        }else{
            return mRetrofit;
        }
        return mRetrofit;
    }

    protected RequestBody postRequestBody(RequestParameters request) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(MEDIA_TYPE), request.requestStr);
        return requestBody;
    }

    public void destroy(){
        if(mRequestList != null){
            Iterator iterator = mRequestList.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                Subscriber requestSubscriber = (Subscriber) entry.getValue();
                if(requestSubscriber != null && !requestSubscriber.isUnsubscribed()){
                    requestSubscriber.unsubscribe();
                }
            }
        }
    }

}
