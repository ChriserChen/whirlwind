package org.example;


import lombok.extern.slf4j.Slf4j;
import org.example.service.InvitationCodeApplyTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * createBy: chenjiacheng
 */
@Slf4j
public class WhirlwindApplication {
    public static void main(String[] args) {
        ExecutorService executeService = Executors.newCachedThreadPool();
        InvitationCodeApplyTask codeApplyTask = new InvitationCodeApplyTask(10);
        executeService.submit(codeApplyTask);
    }
}
