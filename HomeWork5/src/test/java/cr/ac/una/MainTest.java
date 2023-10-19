package cr.ac.una;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    Main m = new Main();
    @Test
    void main() {
    }

    @Test
    void toPostfix() {
        String
        expression = m.infixToPostfix("5 -2");
        System.out.println(expression);
        expression = m.infixToPostfix("24 + 3.8 * 5");
        System.out.println(expression);
        expression = m.infixToPostfix("3 + 5 + 6");
        System.out.println(expression);
        expression = m.infixToPostfix("(3 + 5) + 6");
        System.out.println(expression);
        expression = m.infixToPostfix("3 + (5 + 6)");
        System.out.println(expression);
        expression = m.infixToPostfix("2^3");
        System.out.println(expression);
        expression = m.infixToPostfix("((8 + 9) - 5 * (4 / 5)) + 10");
        System.out.println(expression);
        expression = m.infixToPostfix("10 + ((8 + 9 ) - 5 * (4 / 25))");
        System.out.println(expression);
        expression = m.infixToPostfix("8 + 9");
        System.out.println(expression);
        expression = m.infixToPostfix("(8 + 9)");
        System.out.println(expression);
        expression = m.infixToPostfix("(4 / 3)");
        System.out.println(expression);
        expression = m.infixToPostfix("5 * (4 / 5)");
        System.out.println(expression);
        expression = m.infixToPostfix("(8 + 9 ) - 5 * (4 / 7)");
        System.out.println(expression);
        expression = m.infixToPostfix("((8 + 9 ) - 5 * (4 / 3))");
        System.out.println(expression);
        expression = m.infixToPostfix("(1 + 1)");
        System.out.println(expression);
        expression = m.infixToPostfix("(((5)))");
        System.out.println(expression);
        expression = m.infixToPostfix("(1 + (2 + (3 + 4)))");
        System.out.println(expression);
        expression = m.infixToPostfix("(1) + 2");
        System.out.println(expression);
        expression = m.infixToPostfix("((((1)))) + 2");
        System.out.println(expression);
        expression = m.infixToPostfix("((2 * 3 + 4 * 5)) + 1.4");
        System.out.println(expression);
        expression = m.infixToPostfix("(2 * 3 + 4 * 5) + 1");
        System.out.println(expression);
        expression = m.infixToPostfix("1 * 2 + 3");
        System.out.println(expression);
        expression = m.infixToPostfix("1 + 2 * 3");
        System.out.println(expression);
        expression = m.infixToPostfix("3 + 3 * 3 + 3");
        System.out.println(expression);
    }
}