package cr.ac.una.exceptions;

public class MissingOperatorException extends Exception{
    public MissingOperatorException() {
        super("The expression is inconclusive due to a missed operator.");
    }
}
