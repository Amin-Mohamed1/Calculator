package com.backend.calc;

import java.util.ArrayList;
import java.util.List;


//tokenize the expression 
public class Tokenizer {
        private final List<String> tokens;
        private int index = 0;

        public Tokenizer(String expression) {
            this.tokens = tokenizeExpression(expression);
        }

        private List<String> tokenizeExpression(String expression) {
            List<String> tokens = new ArrayList<>();
            StringBuilder currentToken = new StringBuilder();

            for (char c : expression.toCharArray()) {
                if (isOperator(c) || c == '(' || c == ')') {
                    if (currentToken.length() > 0) {
                        tokens.add(currentToken.toString());
                        currentToken = new StringBuilder();
                    }
                    tokens.add(String.valueOf(c));
                } else if (Character.isDigit(c) || c == '.') {
                    currentToken.append(c);
                }
            }

            if (currentToken.length() > 0) {
                tokens.add(currentToken.toString());
            }

            return tokens;
        }

        public boolean hasNext() {
            return index < tokens.size();
        }

        public String peek() {
            return tokens.get(index);
        }

        public String next() {
            return tokens.get(index++);
        }

        public boolean hasNextNumber() {
            return hasNext() && peek().matches("[0-9]+(\\.[0-9]+)?");
        }

        public double nextDouble() {
            if (!hasNextNumber()) {
                throw new IllegalArgumentException("Expected a number");
            }
            return Double.parseDouble(next());
        }

        private boolean isOperator(char c) {
            return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
        }
    }