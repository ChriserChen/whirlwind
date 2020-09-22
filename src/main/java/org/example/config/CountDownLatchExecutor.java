package org.example.config;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchExecutor {
    private static CountDownLatch countDownLatch;

    public static synchronized void countDown() {
        if(Objects.isNull(countDownLatch)){
            System.exit(0);
        }
        countDownLatch.countDown();
        if (countDownLatch.getCount() == 0) {
            System.exit(0);
        }
    }

    public static synchronized void register(Integer count) {
        countDownLatch = new CountDownLatch(count);
    }

}
