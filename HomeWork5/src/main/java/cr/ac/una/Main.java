package cr.ac.una;

import cr.ac.una.util.EvaluationTree;
import cr.ac.una.util.Stack;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.util.ArrayDeque;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        new Main().init(args);
    }

    private void init(String[] args) {
        String fileName;
        if (args.length != 1) {
            fileName = FileSystems.getDefault().getPath(getWorkingDirectory() + "\\src\\main\\resources\\expresiones.txt").toString();
        } else {
            fileName = args[0];
        }
        try (FileInputStream fis = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.printf("s: '%s'%n%n", line);
                EvaluationTree tree = new EvaluationTree();
                String postfix = infixToPostfix(line);
                String[] postFixElements = postfix.split(" ");
                for(int i = postFixElements.length-1; i >= 0; i--){
                    tree.add(postFixElements[i]);
                }
                System.out.printf("a: %s%n", tree.inorder());
                while(!isNumber(tree.getRoot())){
                    System.out.printf("a: %s%n", tree.evaluate());
                }
                System.out.println("-".repeat(50));
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        }
    }

    public String infixToPostfix(String expression) {
        StringBuilder str = new StringBuilder();

        expression = expression.replace(" ", "");
        expression = expression.trim();
        Queue<String> operation = new ArrayDeque<>();
        Stack<String> operators = new Stack<>();
        boolean priority = false;
        String slice = expression;
        if(expression.contains("(") || expression.contains(")")){
            slice = splitExpression(expression);
            expression = expression.substring(slice.length());
            if(slice.contains("(") || slice.contains(")")) {
                priority = true;
                slice = slice.substring(1, slice.length()-1);
                if(slice.contains("(") || slice.contains(")")) {
                    str.append(infixToPostfix(slice));
                }
            }
        } else {
            expression = expression.substring(slice.length());
            priority = expression.isEmpty();
        }
        if(str.toString().isEmpty()) {
            //Resuelvo la expresion postfix en una cola y una stack
            while (!slice.isEmpty()) {
                String c = obtainNextValue("", slice);
                slice = slice.substring(c.length());
                if (isNumber(c)) {
                    operation.add(c);
                } else {
                    if ((c.equals("+") || c.equals("-")) && !operators.isEmpty()) {
                        if (operators.peek().equals("*") || operators.peek().equals("/") || operators.peek().equals("^")) {
                            while (!operators.isEmpty()) {
                                operation.add(operators.pop());
                            }
                        }
                    } else if (c.equals("^") && !operators.isEmpty()) {
                        if (operators.peek().equals("*") || operators.peek().equals("/")) {
                            while (!operators.isEmpty()) {
                                operation.add(operators.pop());
                            }
                        }
                    }
                    operators.push(c);
                }
            }
        }

        while(!operation.isEmpty()) {
            str.append(operation.poll()).append(" ");
        }
        if (!priority) {
            str.append(infixToPostfix(expression));
        }
        while(!operators.isEmpty()) {
            str.append(operators.pop()).append(" ");
        }
        if(priority && !expression.isEmpty()) {
            str.append(infixToPostfix(expression));
        }
        return str.toString();
    }

    private String obtainNextValue(String value, String slice) {
        if(!slice.isEmpty()) {
            String c = String.valueOf(slice.charAt(0));
            if(isNumber(c) || c.equals(".")) {
                if (value.isEmpty() || isNumber(value)) {
                    value = value.concat(c);
                    value = obtainNextValue(value, slice.substring(1));
                } else {
                    return value;
                }
            }
            else {
                if (value.isEmpty()) {
                    value = value.concat(c);
                    value = obtainNextValue(value, slice.substring(1));
                } else {
                    return value;
                }
            }
        }
        return value;
    }

    private String splitExpression(String expression) {
        int openParenthesisIdx = 0;
        int openParenthesis = 0;
        int closeParenthesis = 0;
        String slice = null;
        for(int i = 0; i < expression.length(); i++){
            if(expression.charAt(i) == '(') {
                if(openParenthesis == 0) openParenthesisIdx = i;
                openParenthesis++;
                if(openParenthesisIdx > 0) {
                    slice = expression.substring(0, openParenthesisIdx);
                    break;
                }
            }else if(expression.charAt(i) == ')') {
                closeParenthesis++;
                if(closeParenthesis == openParenthesis) {
                    slice = expression.substring(openParenthesisIdx, i+1);
                    break;
                }
            }
        }
        return slice;
    }

    public boolean isNumber(String expression) {
        return expression.matches("\\d+[\\.?,?\\d+]{0,}");
    }

    public static String getWorkingDirectory() {
        return System.getProperty("user.dir");
    }
}