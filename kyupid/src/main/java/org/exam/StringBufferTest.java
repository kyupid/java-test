package org.exam;

import java.util.Date;

public class StringBufferTest {
    private static StringBuffer buffer = new StringBuffer();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(new Date().getTime());
        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    buffer.append(j);
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("StringBuffer final length: " + buffer.length());
        System.out.println(new Date().getTime());
    }
}
