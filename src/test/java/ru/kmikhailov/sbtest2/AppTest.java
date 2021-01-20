package ru.kmikhailov.sbtest2;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AppTest {

    @Test
    public void testMultiThread() throws ExecutionException, InterruptedException {
        final TestClass<Integer, Integer> testClass = new TestClass<Integer, Integer>((n) -> {
            return (Executors.newCachedThreadPool());
        });

        List<Integer> intList = IntStream.rangeClosed(1, 1000000).boxed().collect(Collectors.toList());
        intList.parallelStream().forEach((i) -> {
            try {
                Future<Integer> compute = testClass.compute(i, (j) -> ((int)Math.sqrt(j)));
                Assert.assertEquals(compute.get().intValue(), (int)Math.sqrt(i));
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


    }
}
