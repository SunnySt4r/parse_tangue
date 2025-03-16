package com.example.models;

import java.util.List;

public class FunctionCall extends Expression {
    public String functionName;
    public List<Expression> arguments;
    
    public FunctionCall(String functionName, List<Expression> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }
    
    public String toString() {
        return "FunctionCall{\n" +
                "functionName='" + functionName + '\'' +
                ",\n arguments=" + arguments +
                '}';
    }
    
}
