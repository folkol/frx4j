package com.folkol.rx.operators;

import com.folkol.rx.Observable;
import com.folkol.rx.Observer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

public class FilteringOperatorTest
{
    @Test
    public void shouldFilterItems() throws Exception
    {
        List<String> ss = Arrays.asList("Hello", "World", "!");
        Consumer<Observer<String>> onSubscribe = observer -> ss.forEach(observer::onNext);
        Predicate<String> filter = x -> !x.startsWith("Wo");

        Observable<String> observable = new Observable<>(onSubscribe).filter(filter);

        List<String> items = new ArrayList<>();
        observable.subscribe(items::add);

        assertEquals(2, items.size());
        assertEquals("Hello", items.get(0));
        assertEquals("!", items.get(1));
    }
}