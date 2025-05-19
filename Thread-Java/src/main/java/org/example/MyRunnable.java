package org.example;

public class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.print("MyRunnable.run()\n");
    }

    public static void main(String[] args) {
        Thread test1 = new Thread(new MyRunnable());
        test1.start();
        Thread test2 = new Thread(new MyRunnable());
        test2.start();
    }
}
