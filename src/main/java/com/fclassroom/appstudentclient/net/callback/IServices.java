package com.fclassroom.appstudentclient.net.callback;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by hnn on 2018/1/10.
 */

public interface IServices {

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @POST
    Observable<ResponseBody> postRequest(@Url String url, @Body RequestBody route);

    @Headers({"Content-Type:application/json;charset=utf-8", "Accept:application/json;"})
    @GET
    Observable<ResponseBody> getRequest(@Url String url,  @QueryMap Map<String, Object> map);
}
