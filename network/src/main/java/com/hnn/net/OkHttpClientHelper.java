package com.hnn.net;

import android.content.Context;
import android.os.Environment;

import com.hnn.net.interceptor.CacheInterceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpClientHelper {

    private OkHttpClient mOkHttpClient;

    private Context mContext;

    public static OkHttpClientHelper getInstance() {
        return OkHttpClientHelperHolder.instance;
    }

    private OkHttpClientHelper() {
    }

    private static class OkHttpClientHelperHolder {
        private static final OkHttpClientHelper instance = new OkHttpClientHelper();
    }

    public void init(Context context) {
        mContext = context;
        innerInit(null);
    }

    public void innerInit(List<Interceptor> interceptors) {
        //网络请求Log
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存设置
        CacheInterceptor cacheInterceptor = new CacheInterceptor(mContext);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (null != interceptors && !interceptors.isEmpty()) {
            for (int i = 0; i < interceptors.size(); i++) {
                builder.addInterceptor(interceptors.get(i));
            }
        }
        builder.addInterceptor(cacheInterceptor);
        builder.addInterceptor(loggingInterceptor);

        //添加缓存设置
        String catchFilePath;
        if(mContext == null){
            catchFilePath = Environment.getExternalStorageDirectory().getPath() + "networkCatch";
        }else{
            catchFilePath = mContext.getExternalCacheDir().getPath() + "networkCatch";
        }
        //设置缓存路径
        File httpCacheDirectory = new File(catchFilePath);
        //设置缓存 20M
        Cache cache = new Cache(httpCacheDirectory, 20 * 1024 * 1024);

        builder.connectTimeout(NetWorkServices.NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(NetWorkServices.NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(NetWorkServices.NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .cache(cache).addInterceptor(new CacheInterceptor(mContext))
        .cookieJar(new CookieJar() {
            private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(HttpUrl.parse(url.host()), cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(HttpUrl.parse(url.host()));
                return cookies != null ? cookies : new ArrayList();
            }
        });

        mOkHttpClient = builder.build();
    }


    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

}