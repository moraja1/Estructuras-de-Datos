package cr.ac.util;

import java.util.Iterator;

/**
 * -------------------------------------------------------------------
 *
 * (c) 2023
 *
 * @author Georges Alfaro S.
 * @version 1.0.1 2023-09-12
 *
 * --------------------------------------------------------------------
 * @param <T>
 */
public class Array<T> extends Collection<T> {

    public Array(int n) {
        elements = new Object[n];
        k = 0;
    }

    public Array() {
        this(MAX_ELEMENTS);
    }

    public int maxCapacity() {
        return elements.length;
    }

    @Override
    public int count() { // O(1)
        return k;
    }

    @Override
    public T get(int p) { // O(1)
        if (!(p < count())) {
            throw new IndexOutOfBoundsException();
        }
        return (T) elements[p];
    }

    @Override
    public ICollection<T> add(T data, int p) { // O(n)
        if (data != null) {
            if ((k < maxCapacity()) && (0 <= p)) {
                for (int i = count() - 1; i >= p; i--) {
                    elements[i + 1] = elements[i];
                }
                elements[(p > count()) ? count() : p] = data;
                k++;
            }
        } else {
            throw new NullPointerException();
        }
        return this;
    }

    @Override
    public T remove(int p) { // O(n)
        T r = null;
        if (!isEmpty() && (0 <= p) && (p < count())) {
            r = (T) elements[p];
            for (int i = p; i < count() - 1; i++) {
                elements[i] = elements[i + 1];
            }
            k--;
        } else {
            throw new IndexOutOfBoundsException();
        }
        return r;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<>(elements, k);
    }

    @Override
    public String toString() {
        return toString(new String[]{"[", "]"});
    }

    public static final int MAX_ELEMENTS = 128;
    Object[] elements;
    int k;
}
