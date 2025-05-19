package org.example;

import java.util.concurrent.TimeUnit;

public class ThreadExample extends Thread{

    public static void main(String[] args) {
        ThreadExample threadExample = new ThreadExample();
        threadExample.start();
    }

    @Override
    public void run() {
        try {
            System.out.println("ThreadExample running...");
            Thread.sleep(3000L);
            System.out.println("ThreadExample finished.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}