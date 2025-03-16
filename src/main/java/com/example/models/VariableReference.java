package com.example.models;

public class VariableReference extends Expression {
    private final String name;

    public VariableReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "VariableReference{name='" + name + "'}";
    }
}
