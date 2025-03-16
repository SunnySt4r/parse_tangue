package com.example;

import com.example.models.Program;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> tokens = Lexer.tokenize("src/main/resources/test.pt");
        Program program = Parser.parse(tokens);
        System.out.println(program);
    }
}