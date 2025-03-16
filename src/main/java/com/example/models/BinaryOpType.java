package com.example.models;

public enum BinaryOpType {
    ADD, SUB, MUL, DIV, MOD, EQ, NEQ, LT, GT, LE, GE;

    public static BinaryOpType fromString(String s) {
        switch (s) {
            case "+":
                return ADD;
            case "-":
                return SUB;
            case "*":
                return MUL;
            case "/":
                return DIV;
            case "%":
                return MOD;
            case "==":
                return EQ;
            case "!=":
                return NEQ;
            case "<":
                return LT;
            case ">":
                return GT;
            case "<=":
                return LE;
            case ">=":
                return GE;
            default:
                throw new IllegalArgumentException("Invalid binary operator: " + s);
        }
    }
    
}
