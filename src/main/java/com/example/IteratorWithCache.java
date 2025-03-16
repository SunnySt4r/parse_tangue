package com.example;

import java.util.Iterator;
import java.util.Stack;

public class IteratorWithCache<T> implements Iterator<T> {
    private Iterator<T> iterator;
    private Stack<T> cache;

    public IteratorWithCache(Iterator<T> iterator) {
        this.iterator = iterator;
        this.cache = new Stack<>();
    }

    @Override
    public boolean hasNext() {
        return !cache.empty() ||  iterator.hasNext();
    }

    @Override
    public T next() {
        if (cache.empty()) {
            return iterator.next();
        }
        return cache.pop();
    }

    public T peek() {
        if (cache.empty()) {
            cache.push(iterator.next());
        }
        return cache.peek();
    }

    public void push(T item) {
        cache.push(item);
    }
    
}
