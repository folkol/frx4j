package com.folkol.rx.operators;

import com.folkol.rx.Observable;
import com.folkol.rx.Observer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class MappingOperatorTest
{
    @Test
    public void shouldMapItems() throws Exception
    {
        List<Integer> xs = Arrays.asList(1, 10, 666);
        Function<Integer, String> map = String::valueOf;
        Consumer<Observer<Integer>> onSubscribe = observer -> xs.forEach(observer::onNext);

        Observable<String> observable = new Observable<>(onSubscribe).map(map);

        List<String> items = new ArrayList<>();
        observable.subscribe(items::add);

        assertEquals(3, items.size());
        assertEquals("1", items.get(0));
        assertEquals("10", items.get(1));
        assertEquals("666", items.get(2));
    }
}