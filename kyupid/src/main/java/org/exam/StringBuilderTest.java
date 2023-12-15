package org.exam;

import java.util.Date;

public class StringBuilderTest {
    private static StringBuilder builder;

    public static void main(String[] args) throws InterruptedException {
        System.out.println(new Date().getTime());
        for (int i = 0; i < 10; i++) {
            builder = new StringBuilder();
            Thread[] threads = new Thread[10];

            for (int j = 0; j < threads.length; j++) {
                threads[j] = new Thread(() -> {
                    for (int k = 0; k < 100; k++) {
                        builder.append(k);
                    }
                });
                threads[j].start();
            }

            for (Thread thread : threads) {
                thread.join();
            }

            System.out.println("StringBuilder final length: " + builder.length());
        }
    }
}
