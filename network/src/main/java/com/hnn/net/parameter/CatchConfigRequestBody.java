package com.hnn.net.parameter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * 带有缓存配置的请求体
 * Created by hnn on 2018/1/29.
 */

public class CatchConfigRequestBody extends RequestBody{

    public CacheConfig mCacheConfig;

    @Override
    public MediaType contentType() {
        return contentType();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
    }
}
