package com.github.fdkvandr.reactive.spec.subsctiber;

import com.github.fdkvandr.reactive.spec.Subscriber;
import com.github.fdkvandr.reactive.spec.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CollectingSubscriber<T> implements Subscriber<T> {

    private Subscription subscription;
    private final List<T> result = new ArrayList<>();
    private volatile boolean completed = false;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void onNext(T value) {
        result.add(value);
    }

    @Override
    public void onComplete() {
        this.completed = true;
    }

    public List<T> blockingGet() {
        Objects.requireNonNull(subscription, "No subscription find");
        subscription.request(Integer.MAX_VALUE);
        while (!completed) {
            Thread.onSpinWait();
        }
        return result;
    }
}
