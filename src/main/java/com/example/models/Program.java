package com.example.models;

import java.util.ArrayList;
import java.util.List;

public class Program implements AST {
    public List<AST> statements = new ArrayList<AST>();

    public Program(List<AST> statements) {
        this.statements = statements;
    }

    public Program() {
    }

    public void addStatement(AST statement) {
        statements.add(statement);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AST statement : statements) {
            sb.append(statement.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean hasReturn() {
        for (AST statement : statements) {
            if (statement instanceof ReturnStatement) {
                return true;
            }
        }
        return false;
    }
}
