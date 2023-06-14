package org.example;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {

    public static void main(String[] args) {
        // 비동기 작업 생성
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "비동기 작업 결과";
        });

        // 비동기 작업의 결과를 받아서 다른 작업을 수행
        CompletableFuture<Void> future2 = future.thenAccept(System.out::println);

        // 모든 비동기 작업이 끝날 때까지 기다림
        future2.join();
    }
}
