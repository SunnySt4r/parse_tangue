package com.example.models;

public class ReturnStatement  implements AST {
    public Expression expression;
    
    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }
    
    public Expression getExpression() {
        return expression;
    }
    
    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public String toString() {
        return "ReturnStatement{\n" +
                "expression=" + expression +
                '}';
    }
}
