package com.fclassroom.appstudentclient.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpClientHelper {

    private OkHttpClient mOkHttpClient;

    public static OkHttpClientHelper getInstance() {
        return OkHttpClientHelperHolder.instance;
    }

    private OkHttpClientHelper() {
    }

    private static class OkHttpClientHelperHolder {
        private static final OkHttpClientHelper instance = new OkHttpClientHelper();
    }

    public void init() {
        init(null);
    }

    public void init(List<Interceptor> interceptors) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (null != interceptors && !interceptors.isEmpty()) {
            for (int i = 0; i < interceptors.size(); i++) {
                builder.addInterceptor(interceptors.get(i));
            }
        }
        builder.addInterceptor(loggingInterceptor);
        builder.connectTimeout(NetWorkServices.NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(NetWorkServices.NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(NetWorkServices.NETWORK_TIMEOUT, TimeUnit.SECONDS)
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