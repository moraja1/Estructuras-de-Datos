package cr.ac.una;

import cr.ac.una.util.Stack;

import java.util.ArrayDeque;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        new Main().init(args);
    }

    private void init(String[] args) {
        String expression = infixToPostfix("3 + (3 * (3 + 3))");
        System.out.println(expression);
    }

    public String infixToPostfix(String expression) {
        expression = expression.replace(" ", "");
        expression = expression.trim();
        Queue<String> numbers = new ArrayDeque<>();
        Stack<String> operators = new Stack<>();
        String slice = expression;

        //Separo la expresion
        if(expression.contains("(") || expression.contains(")")){
            int openParenthesisIdx = 0;
            int openParenthesis = 0;
            int closeParenthesis = 0;
            for(int i = 0; i < expression.length(); i++){
                if(expression.charAt(i) == '(') {
                    if(openParenthesis == 0) openParenthesisIdx = i;
                    openParenthesis++;
                    if(openParenthesisIdx > 0) {
                        slice = expression.substring(0, openParenthesisIdx);
                        expression = expression.substring(openParenthesisIdx);
                        break;
                    }
                }else if(expression.charAt(i) == ')') {
                    closeParenthesis++;
                    if(closeParenthesis == openParenthesis) {
                        slice = expression.substring(openParenthesisIdx, i+1);
                        if(slice.equals(expression)) {
                            slice = slice.substring(1,slice.length()-1);
                        }
                        if(slice.contains("(") || slice.contains(")")){
                            return infixToPostfix(slice);
                        }
                        break;
                    }
                }
            }
        }

        //Armo la expresion postfix
        for(int i = 0; i < slice.length(); i++) {
            String c = String.valueOf(slice.charAt(i));
            if(isNumber(c)){
                numbers.add(c);
            } else {
                operators.push(c);
            }
        }

        StringBuilder str = new StringBuilder();
        while(!numbers.isEmpty()) {
            str.append(numbers.poll());
        }
        if(!slice.contains("(") && !slice.contains(")") && !expression.contains(slice)) {
            str.append(infixToPostfix(expression));
        }
        while(operators.isEmpty()) {
            str.append(operators.pop());
        }
        return str.toString();

    }

    public boolean isNumber(String expression) {
        return expression.matches("\\d+[\\.?,?\\d+]{0,}");
    }
}