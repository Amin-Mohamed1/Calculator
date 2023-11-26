package com.backend.calc;

public class Parser {
    // parse addition and subtraction
    public static double parseAddSub(Tokenizer tokenizer) {
        double result = parseMulDiv(tokenizer);
        while (tokenizer.hasNext() && (tokenizer.peek().equals("+") || tokenizer.peek().equals("-"))) {
            String operator = tokenizer.next();
            double nextTerm = parseMulDiv(tokenizer);
            if (operator.equals("+")) {
                result += nextTerm;
            } else {
                result -= nextTerm;
            }
        }
        return result;
    }

    // Parse multiplication and division
    private static double parseMulDiv(Tokenizer tokenizer) {
        double result = parsePower(tokenizer);
        while (tokenizer.hasNext() && (tokenizer.peek().equals("*") || tokenizer.peek().equals("/"))) {
            String operator = tokenizer.next();
            double nextFactor = parsePower(tokenizer);
            if (operator.equals("*")) {
                result *= nextFactor;
            } else {
                result /= nextFactor;
            }
        }
        return result;
    }

    // Parse Power
    private static double parsePower(Tokenizer tokenizer) {
        double result = parsePrimary(tokenizer);
        while (tokenizer.hasNext() && tokenizer.peek().equals("^")) {
            tokenizer.next(); // consume '^'
            double exponent = parsePrimary(tokenizer);
            result = Math.pow(result, exponent);
        }
        return result;
    }

    // Parse primary expressions (numbers, unary minus, or parenthesized expressions)
    private static double parsePrimary(Tokenizer tokenizer) {
        if (tokenizer.hasNext() && tokenizer.peek().equals("(")) {
            tokenizer.next(); // consume '('
            double result = parseAddSub(tokenizer);
            tokenizer.next(); // consume ')'
            return result;
        } else if (tokenizer.hasNextNumber()) {
            return tokenizer.nextDouble();
        } else if (tokenizer.hasNext() && tokenizer.peek().equals("-")) {
            tokenizer.next(); // consume '-'
            return -parsePrimary(tokenizer);
        } else {
            throw new IllegalArgumentException("Invalid expression");
        }
    }
}
