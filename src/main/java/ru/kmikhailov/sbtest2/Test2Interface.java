package ru.kmikhailov.sbtest2;

import java.util.concurrent.Future;
import java.util.function.Function;

public interface Test2Interface<K,V> {
    Future<V> compute(K k, Function<K, V> f);
}
