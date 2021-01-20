package ru.kmikhailov.sbtest2;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;

public class TestClass<K, V> {
    private Map<K, Future<V>> map = Collections.synchronizedMap(new Hashtable<>());
    private Function<Void, ExecutorService> executorServiceFactory;
    private ExecutorService executor;

    TestClass(Function<Void, ExecutorService> executorServiceFactory) {
        this.executorServiceFactory = executorServiceFactory;
    }

    public synchronized Future<V> compute(K k, Function<K, V> f) {
        Future<V> vFuture = map.get(k);
        if (vFuture == null) {
            if (executor == null) executor = executorServiceFactory.apply(null);
            FutureTask<V> vFutureTask = new FutureTask<>(() -> f.apply(k));
            executor.execute(vFutureTask);
            vFuture = vFutureTask;
            map.put(k, vFuture);
        }
        return vFuture;
    }
}
