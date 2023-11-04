package cr.ac.una.util.collections;

import java.util.Iterator;

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
class ListIterator<T> implements Iterator<T> {

    public ListIterator(Link<T> cursor) {
        this.cursor = cursor;
    }

    @Override
    public boolean hasNext() {
        return cursor != null;
    }

    @Override
    public T next() {
        T r = null;
        if (hasNext()) {
            r = cursor.getData();
            cursor = cursor.getNext();
        }
        return r;
    }

    private Link<T> cursor;
}
