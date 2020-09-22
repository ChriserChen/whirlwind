package org.example.config;

import okhttp3.OkHttpClient;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

public class OkHttpClientFactory {
    private static final OkHttpClient httpclient = initClient();

    public static OkHttpClient getInstance() {
        return httpclient;
    }

    private static OkHttpClient initClient() {
        SocketAddress socketAddress = new InetSocketAddress("localhost", 3680);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
        return new OkHttpClient().newBuilder()
                .proxy(proxy)
                .connectTimeout(1L, TimeUnit.MINUTES)
                .readTimeout(1L, TimeUnit.MINUTES)
                .writeTimeout(1L, TimeUnit.MINUTES)
                .callTimeout(1L, TimeUnit.MINUTES)
                .build();
    }
}
