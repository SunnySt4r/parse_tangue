package com.example.models;

public class BinaryOp extends Expression{
    private Expression left;
    private BinaryOpType type;
    private Expression right;

    public BinaryOp() {
    }

    public BinaryOp(Expression left, BinaryOpType type, Expression right) {
        this.left = left;
        this.type = type;
        this.right = right;
    }


    public void setLeft(Expression left) {
        this.left = left;
    }

    public void setType(BinaryOpType type) {
        this.type = type;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public BinaryOpType getType() {
        return type;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "BinaryOp{\n" +
                "left=" + left +
                ",\n type=" + type +
                ",\n right=" + right +
                '}';
    }
}


