package org.example;

import java.util.concurrent.locks.ReentrantReadWriteLock;

class ReadWriteReentrantLock {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private int value = 0;

    int read() {
        lock.readLock().lock();
        try {
            return value;
        } finally {
            lock.readLock().unlock();
        }
    }

    void write(int v) {
        lock.writeLock().lock();
        try {
            value = v;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadWriteReentrantLock rwReentrantLock = new ReadWriteReentrantLock();
        rwReentrantLock.write(0);

        Thread t1 = new Thread(() -> {
            rwReentrantLock.write(1);
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            rwReentrantLock.write(2);
        });

        t1.start();                           // t1 bắt đầu chạy: viết value = 1 → sleep 1s
        System.out.println(rwReentrantLock.read()); // Đọc value: có thể là 1 nếu t1 kịp chạy, 0 nếu t1 chưa kịp chạy
        t2.start();                           // t2 bắt đầu chạy: sleep 1s → write value = 2
        System.out.println(rwReentrantLock.read()); // Đọc value: t2 chưa kịp chạy nên → read value = 1
        t1.join();                            // Đợi t1 kết thúc
        t2.join();                            // Đợi t2 kết thúc
        System.out.println(rwReentrantLock.read()); // Đọc value sau khi cả hai luồng xong → read value = 2
        // 0 1 2
    }
}
