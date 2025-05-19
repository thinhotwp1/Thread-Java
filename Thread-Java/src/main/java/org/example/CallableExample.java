package org.example;

import java.util.concurrent.*;

public class CallableExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<?> task = () ->{
            Thread.sleep(1000);
            return "Task completed";
        };

        Future<?> future = executorService.submit(task);
        System.out.println("Doing some task while the thread is running...");
        String result = (String) future.get();
        System.out.println(result);

        executorService.shutdown();
    }
}
