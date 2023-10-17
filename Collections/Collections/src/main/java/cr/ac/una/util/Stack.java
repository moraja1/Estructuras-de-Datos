package cr.ac.una.util;

public class Stack<T> {

    public Stack() {
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public int count() {
        return elements.count();
    }

    public T peek() { // top()
        T r = null;
        if (!isEmpty()) {
            r = elements.get(0);
        }
        return r;
    }

    public Stack<T> push(T data) { // O(1)
        elements.add(data, 0);
        return this;
    }

    public T pop() { // O(1)
        return elements.remove(0);
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    // La implementación de la pila se hace por medio
    // de una lista enlazada y DELEGACION.
    // Si se utiliza una lista enlazada, no existe
    // una limitación en cuanto al número de elementos
    // que la estructura puede contener.
    //
    private final Collection<T> elements = new List<>();
}
