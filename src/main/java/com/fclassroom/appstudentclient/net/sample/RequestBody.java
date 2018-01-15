package com.fclassroom.appstudentclient.net.sample;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hnn on 2018/1/15.
 */

public class RequestBody {

    String testA;


    @SerializedName("b")
    String testB;//自定义gson转化为-----> b

}
