package com.example.models;

public class Number extends Expression {
    private int value;

    public Number() {
    }

    public Number(int value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Number{" +
                "value=" + value +
                '}';
    }
    
}
