package io.jenkins.blueocean.commons;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public class IterableUtils
{
    private IterableUtils(){
    }

    public static <T> T getFirst(Iterable<? extends T> iterable, @Nullable T defaultValue) {
        return getNext(iterable.iterator(), defaultValue);
    }

    public static <T> T getNext( Iterator<? extends T> iterator, @Nullable T defaultValue) {
        return iterator.hasNext() ? iterator.next() : defaultValue;
    }

    public static <T> T find( Iterable<? extends T> iterable,
                              Predicate<? super T> predicate, @Nullable T defaultValue) {
        Optional<? super T> opt = (Optional<T>) StreamSupport.stream(iterable.spliterator(), false).filter(predicate).findFirst();
        return opt.isPresent()?(T) opt.get() :defaultValue;
    }
}
