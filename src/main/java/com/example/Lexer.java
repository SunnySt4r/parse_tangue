package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public static List<String> tokenize(String path) {
        List<String> tokens = new ArrayList<>();
        File file = new File(path);
        try (FileInputStream fr = new FileInputStream(file)) {
            int c;
            StringBuilder token = new StringBuilder();
            while ((c = fr.read()) != -1) {
                char ch = (char) c;
                if (Character.isWhitespace(ch) || ch == '(' || ch == ')' || ch == '{' || ch == '}' || ch == ';' || ch == ',') {
                    if (token.length() > 0) {
                        tokens.add(token.toString().trim());
                        token.setLength(0);
                    }
                    if (!Character.isWhitespace(ch)) {
                        tokens.add(String.valueOf(ch));
                    }
                } else {
                    token.append(ch);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return tokens;
    }
}