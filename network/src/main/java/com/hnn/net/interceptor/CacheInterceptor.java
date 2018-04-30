package com.hnn.net.interceptor;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.hnn.net.NetWorkServices;
import com.hnn.net.parameter.CacheConfig;
import com.hnn.net.parameter.RequestParameters;
import com.hnn.net.util.NetWorkUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.ByteString;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

/**
 *
 * 缓存逻辑处理
 * Created by hnn on 2018/1/29.
 */

public class CacheInterceptor implements Interceptor {

    private static final String TAG = CacheInterceptor.class.getName();

    private CacheConfig cacheConfig = null;
    private Request request;

    private Context mContext;

    public CacheInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        request = chain.request();

        if(request.method().toLowerCase().equals("post")){
            BufferedSink bufferedSink = Okio.buffer(new BufferedSink() {
                @Override
                public Buffer buffer() {
                    return null;
                }

                @Override
                public BufferedSink write(ByteString byteString) throws IOException {
                    RequestParameters.MyRequest myRequest = new Gson().fromJson(byteString.toString(), RequestParameters.MyRequest.class);
                    cacheConfig = myRequest.catchConfig;

                    String requestBodyStr = new Gson().toJson(myRequest.requestBody);
                    request = request.newBuilder().method("post", RequestBody.create(MediaType.parse(NetWorkServices.MEDIA_TYPE), requestBodyStr)).build();
                    return null;
                }

                @Override
                public BufferedSink write(byte[] source) throws IOException {
                    return null;
                }

                @Override
                public BufferedSink write(byte[] source, int offset, int byteCount) throws IOException {
                    return null;
                }

                @Override
                public long writeAll(Source source) throws IOException {
                    return 0;
                }

                @Override
                public BufferedSink write(Source source, long byteCount) throws IOException {
                    return null;
                }

                @Override
                public BufferedSink writeUtf8(String string) throws IOException {
                    return null;
                }

                @Override
                public BufferedSink writeUtf8(String string, int beginIndex, int endIndex) throws IOException {
                    return null;
                }

                @Override
                public BufferedSink writeUtf8CodePoint(int codePoint) throws IOException {
                    return null;
                }

                @Override
                public BufferedSink writeString(String string, Charset charset) throws IOException {
                    return null;
                }

                @Override
                public BufferedSink writeString(String string, int beginIndex, int endIndex, Charset charset) throws IOException {
                    return null;
                }

                @Override
                public BufferedSink writeByte(int b) throws IOException {
                    return null;
                }

                @Override
                public BufferedSink writeShort(int s) throws IOException {
                    return null;
                }

                @Override
                public BufferedSink writeShortLe(int s) throws IOException {
                    return null;
                }

                @Override
                public BufferedSink writeInt(int i) throws IOException {
                    return null;
                }

                @Override
                public BufferedSink writeIntLe(int i) throws IOException {
                    return null;
                }

                @Override
                public BufferedSink writeLong(long v) throws IOException {
                    return null;
                }

                @Override
                public BufferedSink writeLongLe(long v) throws IOException {
                    return null;
                }

                @Override
                public BufferedSink writeDecimalLong(long v) throws IOException {
                    return null;
                }

                @Override
                public BufferedSink writeHexadecimalUnsignedLong(long v) throws IOException {
                    return null;
                }

                @Override
                public void flush() throws IOException {

                }

                @Override
                public BufferedSink emit() throws IOException {
                    return null;
                }

                @Override
                public BufferedSink emitCompleteSegments() throws IOException {
                    return null;
                }

                @Override
                public OutputStream outputStream() {
                    return null;
                }

                @Override
                public void write(Buffer source, long byteCount) throws IOException {

                }

                @Override
                public Timeout timeout() {
                    return null;
                }

                @Override
                public void close() throws IOException {

                }
            });

            request.body().writeTo(bufferedSink);
        }else if(request.method().toLowerCase().equals("get")){

            String cacheConfigStr = request.url().queryParameter(CacheConfig.class.getName());
            cacheConfig = new Gson().fromJson(cacheConfigStr, CacheConfig.class);
            HttpUrl httpUrl = request.url().newBuilder().removeAllQueryParameters(CacheConfig.class.getName()).build();
            request = request.newBuilder().url(httpUrl).build();
        }


        Response response = chain.proceed(request);

        if (cacheConfig != null && cacheConfig.networkCache) {
            int maxAge = cacheConfig.networkCacheTime;
            Log.i(TAG,"has network maxAge="+ maxAge);
            response = response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-age=" + maxAge)
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        }
        return response;
    }
}
