package com.hnn.net.callback;

import com.hnn.net.parameter.RequestParameters;

/**
 * Created by hnn on 2018/1/10.
 */

public interface INetWorkServices {

    String sendRequest(RequestParameters requestParameters, IRequestListener requestListener);

    boolean cancelRequest(String requestId);

}
