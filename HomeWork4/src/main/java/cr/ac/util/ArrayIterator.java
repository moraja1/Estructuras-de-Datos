package cr.ac.util;

import java.util.Iterator;

/**
 * -------------------------------------------------------------------
 *
 * (c) 2023
 *
 * @author Georges Alfaro S.
 * @version 1.1.0 2023-08-27
 *
 * --------------------------------------------------------------------
 * @param <T>
 */
class ArrayIterator<T> implements Iterator<T> {

    public ArrayIterator(Object[] elements, int k0, int k1) {
        this.elements = elements;
        this.k0 = k0;
        this.k1 = k1;
    }

    public ArrayIterator(Object[] elements, int k1) {
        this(elements, 0, k1);
    }

    @Override
    public boolean hasNext() {
        return k0 != k1;
    }

    @Override
    public T next() {
        T r = null;
        if (hasNext()) {
            r = (T) elements[k0];
            k0 = (k0 + 1) % elements.length;
        }
        return r;
    }

    private final Object[] elements;
    private int k0;
    private final int k1;
}
