package com.example.models;

public class IfStatement implements AST {
    public Expression condition;
    public AST body;
    public IfStatement elseStatement;

    public IfStatement(Expression condition, AST body, IfStatement elseStatement) {
        this.condition = condition;
        this.body = body;
        this.elseStatement = elseStatement;
    }

    public void setElseStatement(IfStatement elseStatement) {
        this.elseStatement = elseStatement;
    }

    @Override
    public String toString() {
        return "IfStatement{\n" +
                "condition=" + condition +
                ",\n body=" + body +
                (elseStatement == null ? "" : ", elseStatement=" + elseStatement) +
                '}';
    }
}
