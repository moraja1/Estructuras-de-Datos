package cr.ac.una.util;

/**
 * -------------------------------------------------------------------
 *
 * (c) 2023
 *
 * @author Georges Alfaro S.
 * @version 1.0.0 2023-08-15
 *
 * --------------------------------------------------------------------
 * @param <T>
 */
class Link<T> {

    public Link(T data, Link<T> next) {
        this.data = data;
        this.next = next;
    }

    public Link(T data) {
        this(data, null);
    }

    public T getData() {
        return data;
    }

    public Link<T> getNext() {
        return next;
    }

    public void setNext(Link<T> next) {
        this.next = next;
    }

    final T data;
    Link<T> next;
}
