package com.github.fdkvandr.reactive.spec.publisher;

import com.github.fdkvandr.reactive.spec.Publisher;
import com.github.fdkvandr.reactive.spec.Subscriber;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class DeferredPublisher<T> implements Publisher<T> {

    private final Supplier<Publisher<T>> supplier;

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        supplier.get().subscribe(subscriber);
    }
}
