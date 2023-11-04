package cr.ac.una.util.collections;

public class QueueException extends Exception {

    public QueueException(String message) {
        super(message);
    }

    public QueueException() {
        this("Queue Exception");
    }

}
