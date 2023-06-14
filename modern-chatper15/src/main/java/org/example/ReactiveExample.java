package org.example;

import reactor.core.publisher.Flux;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ReactiveExample {

    public static void main(String[] args) {
        Mono<String> mono1 = callExternalServiceA();
        Mono<String> mono2 = callExternalServiceB();

        Mono.zip(mono1, mono2)
                .doOnNext(t -> System.out.println("결과: " + t.getT1() + ", " + t.getT2()))
                .block();
    }

    private static Mono<String> callExternalServiceA() {
        return Mono.fromCallable(() -> {
            // 외부 서비스 A 호출
            return "서비스 A 결과";
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private static Mono<String> callExternalServiceB() {
        return Mono.fromCallable(() -> {
            // 외부 서비스 B 호출
            return "서비스 B 결과";
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
