package org.example.service;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.example.config.CountDownLatchExecutor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Slf4j
public class InvitationCodeCallback implements Callback {

    private InvitationCodeProcessService processService = new InvitationCodeProcessService();

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        log.error("请求失败:{}", call.request().url());
        CountDownLatchExecutor.countDown();
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        log.info("请求成功:{}", response);
        processService.process(response);
        CountDownLatchExecutor.countDown();
    }
}
