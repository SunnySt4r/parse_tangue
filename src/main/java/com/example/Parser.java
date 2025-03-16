package com.example;

import com.example.models.*;
import com.example.models.Number;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Parser {

    private static Set<String> functionsName = new HashSet<>();

    public static Program parse(List<String> tokens) {
        Program program = new Program();
        IteratorWithCache<String> iterator = new IteratorWithCache<>(tokens.iterator());
        while (iterator.hasNext()) {
            AST stmt = parseAST(iterator);
            program.addStatement(stmt);
        }
        return program;
    }

    private static AST parseAST(IteratorWithCache<String> iterator) {
        String token = iterator.peek();
        if ("var".equals(token)) {
            iterator.next();
            return parseVariableDeclaration(iterator);
        } else if ("func".equals(token)) {
            iterator.next();
            return parseFunction(iterator);
        } else if ("if".equals(token)) {
            iterator.next();
            return parseIfStatement(iterator);
        } else if ("return".equals(token)) {
            iterator.next();
            return parseReturnStatement(iterator);
        } else {
            return parseVariableAssignmentOrFunctionCall(iterator);
        }
    }

    private static VariableDeclaration parseVariableDeclaration(IteratorWithCache<String> iterator) {
        String name = iterator.next();
        Expression value = null;
        String next = iterator.next(); // ожидаем "=" или ";"
        if ("=".equals(next)) {
            value = parseExpression(iterator);
            // потребляем ";" если оно присутствует
            if (iterator.hasNext() && ";".equals(iterator.peek())) {
                iterator.next();
            }
        }
        return new VariableDeclaration(name, value);
    }

    private static Function parseFunction(IteratorWithCache<String> iterator) {
        String name = iterator.next();
        functionsName.add(name);
        // Ожидаем "("
        String token = iterator.next();
        if (!"(".equals(token)) {
            throw new RuntimeException("Ожидалась '(' после имени функции, получено: " + token);
        }
        List<String> params = new ArrayList<>();
        while (iterator.hasNext()) {
            token = iterator.next();
            if (")".equals(token)) break;
            if (",".equals(token)) continue;
            params.add(token);
        }
        // Ожидаем "{"
        token = iterator.next();
        if (!"{".equals(token)) {
            throw new RuntimeException("Ожидалась '{' в начале тела функции, получено: " + token);
        }
        Program body = parseBlock(iterator);
        ResultType resultType = body.hasReturn() ? ResultType.SOME : ResultType.VOID;
        return new Function(name, params, resultType, body);
    }

    private static IfStatement parseIfStatement(IteratorWithCache<String> iterator) {
        // Ожидаем "("
        String token = iterator.next();
        if (!"(".equals(token)) {
            throw new RuntimeException("Ожидалась '(' после if, получено: " + token);
        }
        Expression condition = parseExpression(iterator);
        // Ожидаем ")"
        token = iterator.next();
        if (!")".equals(token)) {
            throw new RuntimeException("Ожидалась ')' после условия if, получено: " + token);
        }
        // Ожидаем "{"
        token = iterator.next();
        if (!"{".equals(token)) {
            throw new RuntimeException("Ожидалась '{' в начале блока if, получено: " + token);
        }
        Program ifBody = parseBlock(iterator);
        IfStatement ifStmt = new IfStatement(condition, ifBody, null);

        // Обрабатываем блоки elif/else
        IfStatement current = ifStmt;
        while (iterator.hasNext() && ("elif".equals(iterator.peek()) || "else".equals(iterator.peek()))) {
            token = iterator.next();
            if ("elif".equals(token)) {
                // Ожидаем "("
                token = iterator.next();
                if (!"(".equals(token)) {
                    throw new RuntimeException("Ожидалась '(' после elif, получено: " + token);
                }
                Expression elifCondition = parseExpression(iterator);
                // Ожидаем ")"
                token = iterator.next();
                if (!")".equals(token)) {
                    throw new RuntimeException("Ожидалась ')' после условия elif, получено: " + token);
                }
                // Ожидаем "{"
                token = iterator.next();
                if (!"{".equals(token)) {
                    throw new RuntimeException("Ожидалась '{' в начале блока elif, получено: " + token);
                }
                Program elifBody = parseBlock(iterator);
                current.elseStatement = new IfStatement(elifCondition, elifBody, null);
                current = current.elseStatement;
            } else if ("else".equals(token)) {
                // Ожидаем "{"
                token = iterator.next();
                if (!"{".equals(token)) {
                    throw new RuntimeException("Ожидалась '{' в начале блока else, получено: " + token);
                }
                Program elseBody = parseBlock(iterator);
                current.elseStatement = new IfStatement(null, elseBody, null);
                break;
            }
        }
        return ifStmt;
    }

    private static ReturnStatement parseReturnStatement(IteratorWithCache<String> iterator) {
        Expression returnValue = parseExpression(iterator);
        if (iterator.hasNext() && ";".equals(iterator.peek())) {
            iterator.next();
        }
        return new ReturnStatement(returnValue);
    }

    private static Program parseBlock(IteratorWithCache<String> iterator) {
        Program block = new Program();
        while (iterator.hasNext()) {
            String token = iterator.peek();
            if ("}".equals(token)) {
                iterator.next(); // потребляем '}'
                break;
            }
            AST stmt = parseAST(iterator);
            block.addStatement(stmt);
        }
        return block;
    }

    private static AST parseVariableAssignmentOrFunctionCall(IteratorWithCache<String> iterator) {
        String name = iterator.next();
        String next = iterator.peek();
        if ("=".equals(next)) {
            iterator.next(); // потребляем '='
            Expression value = parseExpression(iterator);
            if (iterator.hasNext() && ";".equals(iterator.peek())) {
                iterator.next();
            }
            return new VariableDeclaration(name, value);
        } else if ("(".equals(next)) {
            iterator.push(name);
            Expression funcCall = parseFunctionCall(iterator);
            if (iterator.hasNext() && ";".equals(iterator.peek())) {
                iterator.next();
            }
            return funcCall;
        } else {
            throw new RuntimeException("Неожиданный токен после идентификатора: " + next);
        }
    }

    private static Expression parseExpression(IteratorWithCache<String> iterator) {
        Expression left = parsePrimary(iterator);
        String next = iterator.peek();
        while (iterator.hasNext() && !(";),".contains(next))) {
            String opToken = iterator.next();
            BinaryOpType opType = BinaryOpType.fromString(opToken);
            Expression right = parsePrimary(iterator);
            left = new BinaryOp(left, opType, right);

            if (iterator.hasNext()) {
                next = iterator.peek();
            }
        }
        return left;
    }

    private static Expression parsePrimary(IteratorWithCache<String> iterator) {
        String token = iterator.next();
        if ("(".equals(token)) {
            Expression expr = parseExpression(iterator);
            String closing = iterator.next();
            if (!")".equals(closing)) {
                throw new RuntimeException("Ожидалась ')' но получено: " + closing);
            }
            return expr;
        } else if (functionsName.contains(token) && iterator.hasNext() && "(".equals(iterator.peek())) {
            iterator.push(token);
            return parseFunctionCall(iterator);
        } else {
            try {
                int value = Integer.parseInt(token);
                return new Number(value);
            } catch (NumberFormatException e) {
                return new VariableReference(token);
            }
        }
    }

    private static Expression parseFunctionCall(IteratorWithCache<String> iterator) {
        String name = iterator.next();
        String token = iterator.next();
        if (!"(".equals(token)) {
            throw new RuntimeException("Ожидалась '(' в вызове функции, получено: " + token);
        }
        List<Expression> args = new ArrayList<>();
        while (iterator.hasNext()) {
            token = iterator.peek();
            if (")".equals(token)) {
                iterator.next();
                break;
            }
            if (",".equals(token)) {
                iterator.next();
                continue;
            }
            args.add(parseExpression(iterator));
        }
        return new FunctionCall(name, args);
    }
}
