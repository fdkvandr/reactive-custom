package com.github.fdkvandr.reactive.spec.publisher;

import com.github.fdkvandr.reactive.spec.Publisher;
import com.github.fdkvandr.reactive.spec.Subscriber;
import com.github.fdkvandr.reactive.spec.Subscription;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class ParallelPublisher<T> implements Publisher<T> {

    private final Publisher<T> upstream;
    private final ThreadPoolExecutor executor;

    public ParallelPublisher(Publisher<T> upstream, int parallelism) {
        this.upstream = upstream;
        this.executor = new ThreadPoolExecutor(parallelism, parallelism, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        upstream.subscribe(new ParallelSubscriber(subscriber));
    }

    @RequiredArgsConstructor
    private class ParallelSubscriber implements Subscriber<T> {

        private final Subscriber<? super T> subscriber;

        @Override
        public void onSubscribe(Subscription subscription) {
            subscriber.onSubscribe(subscription);
        }

        @Override
        public void onNext(T value) {
            executor.submit(() -> subscriber.onNext(value));
        }

        @Override
        public void onComplete() {
            subscriber.onComplete();
            executor.shutdown();
        }
    }
}
