package com.github.fdkvandr.reactive;

import com.github.fdkvandr.reactive.spec.Publisher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        test2();
    }

    private static void test1() {
        Publisher<Integer> publisher = Publisher.from(() -> {
                    System.out.println("Very long operation");
                    return new Integer[]{1, 2, 3};
                })
                .map(it -> it + 1)
                .peek(System.out::println);
        System.out.println("Demonstration that the producer is constructed only after subscription");
        System.out.println("I was called earliest");
        System.out.println(publisher.collect());
    }

    private static void test2() {
        Publisher.just(1, 2, 3)
                .parallel(2)
                .map(it -> {
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return it + 1;
                })
                .consume(it -> log.info("{}", it));
    }
}