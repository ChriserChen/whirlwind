package org.example.service;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.example.config.CountDownLatchExecutor;
import org.example.config.OkHttpClientFactory;

import java.time.LocalDateTime;

@Slf4j
public class InvitationCodeApplyTask implements Runnable {
    private static final OkHttpClient httpClient = OkHttpClientFactory.getInstance();
    private static final String REQUEST_URL = "http://www.china-duebro.com/newtf";
    private static final InvitationCodeCallback callback = new InvitationCodeCallback();
    private static Request request = new Request.Builder().url(REQUEST_URL).get().build();
    private Integer requestNum;

    public InvitationCodeApplyTask(Integer requestNum) {
        this.requestNum = requestNum;
        CountDownLatchExecutor.register(requestNum);
    }


    @Override
    public void run() {
        for (int i = 0; i < requestNum; i++) {
            log.info("{} :线程:{} 请求数:{} 开始", LocalDateTime.now(), Thread.currentThread().getName(), i + 1);
            httpClient.newCall(request).enqueue(callback);
            log.info("{} :线程:{} 请求数:{} 结束", LocalDateTime.now(), Thread.currentThread().getName(), i + 1);
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
