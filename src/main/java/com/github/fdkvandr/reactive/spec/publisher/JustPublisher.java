package com.github.fdkvandr.reactive.spec.publisher;

import com.github.fdkvandr.reactive.spec.Publisher;
import com.github.fdkvandr.reactive.spec.Subscriber;
import com.github.fdkvandr.reactive.spec.Subscription;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public class JustPublisher<T> implements Publisher<T> {

    private final T[] values;

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        JustSubscription subscription = new JustSubscription(subscriber);
        subscriber.onSubscribe(subscription);
    }

    @RequiredArgsConstructor
    private class JustSubscription implements Subscription {

        private final Subscriber<? super T> subscriber;
        private int position = 0; 

        @Override
        public void request(int n) {
            for (int i = 0; i < n; i++) {
                if (position == values.length) {
                    subscriber.onComplete();
                    return;
                }
                subscriber.onNext(values[position++]);
            }
        }
    }
}
