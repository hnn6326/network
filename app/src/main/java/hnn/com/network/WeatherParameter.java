package hnn.com.network;

import com.hnn.net.parameter.IParameter;

/**
 * Created by hnn on 2018/1/15.
 */
public enum  WeatherParameter implements IParameter{

    LIST("data/sk/101010100.html", "get");

    String mRequestPath;

    String mRequestType;



    WeatherParameter(String requestPath, String requestType) {
        mRequestPath = requestPath;
        mRequestType =requestType;
    }


    @Override
    public String getRequestPath() {
        return mRequestPath;
    }

    @Override
    public String getRequestType() {
        return mRequestType;
    }
}
