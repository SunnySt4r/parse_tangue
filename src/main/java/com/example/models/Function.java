package com.example.models;

import java.util.List;

public class Function implements AST{
    String name;
    List<String> params;
    ResultType resultType;
    AST body;

    public Function(String name, List<String> params, ResultType resultType, AST body) {
        this.name = name;
        this.params = params;
        this.resultType = resultType;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Function{" +
                "name='" + name + '\'' +
                ",\n params=" + params +
                ",\n resultType=" + resultType +
                ",\n body=" + body +
                '}';
    }
}
