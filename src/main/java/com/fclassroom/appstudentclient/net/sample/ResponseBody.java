package com.fclassroom.appstudentclient.net.sample;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hnn on 2018/1/15.
 */

public class ResponseBody {

    String testAA;

    @SerializedName("bb")
    String testBB;//自定义gson转化为-----> b

}
