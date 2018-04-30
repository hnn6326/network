package hnn.com.network;

import com.hnn.net.parameter.CacheConfig;
import com.hnn.net.parameter.IParameter;

/**
 * Created by hnn on 2018/1/15.
 */
public enum  WeatherParameter implements IParameter{

    LIST("data/sk/101190408.html", "get", CacheConfig.DEFAULT);

    String mRequestPath;

    String mRequestType;

    CacheConfig mCacheConfig;



    WeatherParameter(String requestPath, String requestType, CacheConfig cacheConfig) {
        mRequestPath = requestPath;
        mRequestType = requestType;
        mCacheConfig = cacheConfig;
    }


    @Override
    public String getRequestPath() {
        return mRequestPath;
    }

    @Override
    public String getRequestType() {
        return mRequestType;
    }

    @Override
    public CacheConfig getRequestCacheConfig() {
        return mCacheConfig;
    }
}
