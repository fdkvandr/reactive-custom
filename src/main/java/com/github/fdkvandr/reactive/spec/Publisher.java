package com.github.fdkvandr.reactive.spec;

import com.github.fdkvandr.reactive.spec.publisher.JustPublisher;
import com.github.fdkvandr.reactive.spec.publisher.MapPublisher;
import com.github.fdkvandr.reactive.spec.subsctiber.CollectingSubscriber;

import java.util.List;
import java.util.function.Function;

public interface Publisher<T> {

    void subscribe(Subscriber<? super T> subscriber);

    @SafeVarargs
    static <T> Publisher<T> just(T... values) {
        return new JustPublisher<>(values);
    }

    default <R> Publisher<R> map(Function<T, R> mapper) {
        return new MapPublisher<>(this, mapper);
    }

    default List<T> collect() {
        CollectingSubscriber<T> subscriber = new CollectingSubscriber<>();
        subscribe(subscriber);
        return subscriber.blockingGet();
    }
}
