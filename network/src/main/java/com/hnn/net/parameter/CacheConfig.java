package com.hnn.net.parameter;

/**
 * 配置缓存文件
 * Created by hnn on 2018/1/29.
 */

public class CacheConfig {

    //网络缓存
    public boolean networkCache = true;
    //有网络情况下缓存时效180秒， 三分钟
    public int networkCacheTime = 180;

    //无网络情况下缓存时效 五天
    public int noNetworkCacheTime = 60 * 60 * 24 * 5;

    public CacheConfig(){

    }

    public CacheConfig(boolean networkCache, int networkCacheTime, int noNetworkCacheTime){
        this.networkCache = networkCache;
        this.networkCacheTime = networkCacheTime;
        this.noNetworkCacheTime = noNetworkCacheTime;
    }

    public CacheConfig(boolean networkCache){
        this.networkCache = networkCache;
    }

    public static CacheConfig DEFAULT = new CacheConfig();

    public static CacheConfig DEFAULT_NO_CACHE = new CacheConfig(false);
}
