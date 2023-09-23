package cr.ac.una.util;

import java.util.Iterator;

public class Queue<T> implements Iterable<T> {

    public Queue(int n) {
        elements = new Object[n];
        q0 = q1 = 0;
    }

    public Queue() {
        this(MAX_QUEUE);
    }

    public boolean isEmpty() { // O(1)
        return q0 == q1;
    }

    public boolean isFull() {
        return !(count() < elements.length - 1);
    }

    public int count() {
        return (elements.length + q1 - q0) % elements.length;
    }

    public T head() {
        T r = null;
        if (!isEmpty()) {
            r = (T) elements[q0];
        }
        return r;
    }

    public Queue<T> enqueue(T data) throws QueueException {
        if (data != null) {
            if (isFull()) {
                throw new QueueException("Queue is full.");
            } else {
                elements[q1] = data;
                q1 = (q1 + 1) % elements.length;
            }
        }
        return this;
    }

    public T dequeue() {
        T r = null;
        if (!isEmpty()) {
            r = (T) elements[q0];
            // elements[q0] = null;
            q0 = (q0 + 1) % elements.length;
        }
        return r;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<>(elements, q0, q1);
    }

    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean f) {
        StringBuilder r = new StringBuilder();
        r.append("[");
        if (f) {
            for (int i = 0; i < elements.length; i++) {
                if (i > 0) {
                    r.append(", ");
                }

                boolean b = ((q0 < q1) && (q0 <= i) && (i < q1))
                        || ((q1 < q0) && ((q0 <= i) || (i < q1)));

                if (b && elements[i] != null) {
                    r.append(String.format("%2s", elements[i]));
                } else {
                    r.append(" ?");
                }
            }
        } else {
            for (int k0 = q0; k0 != q1; k0 = (k0 + 1) % elements.length) {
                if (k0 != q0) {
                    r.append(", ");
                }
                r.append(String.format("%2s", elements[k0]));
            }
        }
        r.append("]");

        if (f) {
            r.append(String.format(
                    "; q0= %2d, q1= %2d", q0, q1));
        }

        return r.toString();
    }

    private static final int MAX_QUEUE = 10;
    private final Object[] elements;
    private int q0; // posición inicial (cabeza)
    private int q1; // posición final
}
