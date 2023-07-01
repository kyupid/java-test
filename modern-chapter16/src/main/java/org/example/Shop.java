package org.example;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

public class Shop {
    private String name;
    private double price;
    private Random random = new Random();

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    private double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    private static List<String> findPricesV1(List<Shop> shops, String product) {
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    private static List<String> findPricesV2(List<Shop> shops, String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    private static List<String> findPricesV3(List<Shop> shops, String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))))
                .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    private static List<String> findPricesV4(List<Shop> shops, String product) {
        final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true); // 프로그램 종료를 방해하지 않는 데몬 스레드를 사용한다.
                return t;
            }
        });

        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is " + shop.getPrice(product), executor))
                .collect(Collectors.toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static void test(List<Shop> shops) {
        long start = System.nanoTime();
        System.out.println(findPricesV4(shops, "myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }
}
