package com.pantanal.read.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtil {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     *
     * @param runnable
     */
    public static void executeWithThreadPool(Runnable runnable) {
        executorService.execute(runnable);
    }
}
