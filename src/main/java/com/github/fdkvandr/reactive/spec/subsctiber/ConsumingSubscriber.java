package com.github.fdkvandr.reactive.spec.subsctiber;

import com.github.fdkvandr.reactive.spec.Subscriber;
import com.github.fdkvandr.reactive.spec.Subscription;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class ConsumingSubscriber<T> implements Subscriber<T> {

    private Subscription subscription;
    private final Consumer<T> consumer;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(Integer.MAX_VALUE);
    }

    @Override
    public void onNext(T value) {
        consumer.accept(value);
    }

    @Override
    public void onComplete() {
    }
}
