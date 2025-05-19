package org.example;

import java.util.concurrent.locks.ReentrantLock;

class ReentrantLockTest {
    private final ReentrantLock lock = new ReentrantLock();
    int count = 0;

    void safeIncrement() {
        lock.lock();       // giống synchronized
        try {
            count++;
        } finally {
            lock.unlock(); // phải luôn unlock
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTest t = new ReentrantLockTest();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                t.safeIncrement();
            }
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                t.safeIncrement();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(t.count); // Kết quả luôn là 2000
    }
}
