package cr.una;

public class Link <E>{
    private final E data;
    private Link next;

    public Link(E data) {
        this.data = data;
    }

    public E getData() {
        return data;
    }

    public Link<E> getNext() {
        return next;
    }

    public void setNext(Link<E> next) {
        this.next = next;
    }
}
