package com.github.fdkvandr.reactive;

import com.github.fdkvandr.reactive.spec.Publisher;

public class Main {

    public static void main(String[] args) {
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
}