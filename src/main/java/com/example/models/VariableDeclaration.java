package com.example.models;

public class VariableDeclaration implements AST {
    String name;
    Expression value;

    public VariableDeclaration(String name, Expression value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "VariableDeclaration{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}