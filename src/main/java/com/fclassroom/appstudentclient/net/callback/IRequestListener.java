package com.fclassroom.appstudentclient.net.callback;

import com.fclassroom.appstudentclient.net.util.Response;

/**
 * Created by hnn on 2018/1/10.
 */

public interface IRequestListener {

    void onSuccess(Response response);
    void onError(Response response);
}
