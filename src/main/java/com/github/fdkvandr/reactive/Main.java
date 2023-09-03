package com.github.fdkvandr.reactive;

import com.github.fdkvandr.reactive.spec.Publisher;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> list = Publisher.just(1, 2, 3)
                .collect();
        System.out.println(list);
    }
}