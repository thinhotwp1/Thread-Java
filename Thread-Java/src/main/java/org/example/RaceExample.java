package org.example;

import java.util.concurrent.atomic.AtomicInteger;

class Counter {
    int count = 0;
    AtomicInteger countAtomic = new AtomicInteger(0);

    synchronized void incrementWithInt() {
        count++;
    }

        void incrementWithIntAtomic() {
        countAtomic.incrementAndGet();
    }
}

public class RaceExample {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.incrementWithInt(); // Increment Integer
                counter.incrementWithIntAtomic(); // Increment Atomic Integer
            }
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.incrementWithInt();
                counter.incrementWithIntAtomic();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Count synchronized = " + counter.count); // Kết quả có thể KHÔNG phải 2000!
        System.out.println("Atomic count = " + counter.countAtomic); // Kết quả phải là 2000!
    }
}
