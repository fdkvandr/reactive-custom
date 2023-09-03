package com.github.fdkvandr.reactive.spec.publisher;

import com.github.fdkvandr.reactive.spec.Publisher;
import com.github.fdkvandr.reactive.spec.Subscriber;
import com.github.fdkvandr.reactive.spec.Subscription;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;

@AllArgsConstructor
public class PeekPublisher<T> implements Publisher<T> {

    private final Publisher<T> source;
    private final Consumer<T> consumer;

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        source.subscribe(new PeekSubscriber(subscriber));
    }

    @AllArgsConstructor
    private final class PeekSubscriber implements Subscriber<T> {

        private final Subscriber<? super T> subscriber;

        @Override
        public void onSubscribe(Subscription subscription) {
            subscriber.onSubscribe(subscription);
        }

        @Override
        public void onNext(T value) {
            consumer.accept(value);
            subscriber.onNext(value);
        }

        @Override
        public void onComplete() {
            subscriber.onComplete();
        }
    }
}
