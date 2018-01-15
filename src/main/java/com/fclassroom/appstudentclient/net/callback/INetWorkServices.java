package com.fclassroom.appstudentclient.net.callback;

import com.fclassroom.appstudentclient.net.parameter.RequestParameters;

/**
 * Created by hnn on 2018/1/10.
 */

public interface INetWorkServices {

    String sendRequest(RequestParameters requestParameters, IRequestListener requestListener);

}
