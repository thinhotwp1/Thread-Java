package org.example;

class SharedResource {
    boolean ready = false;

    synchronized void produce() throws InterruptedException {
        while (ready) wait(100); // đợi nếu đã sẵn sàng
        System.out.println("Producing...");
        Thread.sleep(1000L);
        ready = true;
        notify(); // báo cho consumer
    }

    synchronized void consume() throws InterruptedException {
        while (!ready) wait(100); // đợi nếu chưa có gì
        System.out.println("Consuming...");
        Thread.sleep(1000L);
        ready = false;
        notify(); // báo cho producer
    }

    public static void main(String[] args) throws InterruptedException {
        SharedResource sharedResource = new SharedResource();
        sharedResource.produce();
        sharedResource.consume();
    }
}
