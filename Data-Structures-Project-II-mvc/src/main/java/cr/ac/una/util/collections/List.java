package cr.ac.una.util.collections;

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
public class List<T> extends Collection<T> {

    public List() {
        first = null;
        k = 0;
    }

    @Override
    public int count() { // O(1)
        return k;
    }

    @Override
    public T get(int p) { // O(n)
        if ((0 <= p) && (p < count())) {
            Link<T> cursor = first;
            int i = 0;
            while (i < p) {
                cursor = cursor.getNext();
                i++;
            }
            assert ((cursor != null) && (i == p));
            return cursor.getData();
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public ICollection<T> add(T data, int p) {
        if (data != null) {
            if (0 <= p) {
                if (p == 0) {
                    first = new Link<>(data, first);
                } else {
                    Link<T> cursor = first;
                    int i = 0;
                    while ((i < p - 1) && (cursor.getNext() != null)) {
                        cursor = cursor.getNext();
                        i++;
                    }
                    assert (cursor != null);
                    cursor.setNext(new Link<>(data, cursor.getNext()));
                }
                k++;
            } else {
                throw new IndexOutOfBoundsException();
            }
        } else {
            throw new NullPointerException();
        }
        return this;
    }

    @Override
    public T remove(int p) {
        T r = null;
        if (!isEmpty() && (0 <= p) && (p < count())) {
            if (p == 0) {
                r = first.getData();
                first = first.getNext();
            } else {
                Link<T> cursor = first;
                int i = 0;
                while ((i < p - 1) && (cursor.getNext() != null)) {
                    cursor = cursor.getNext();
                    i++;
                }
                assert (cursor.getNext() != null);
                r = cursor.getNext().getData();
                cursor.setNext(cursor.getNext().getNext());
            }
            k--;
        } else {
            throw new IndexOutOfBoundsException();
        }
        return r;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<>(first);
    }

    @Override
    public String toString() {
        return toString(new String[]{"{", "}"});
    }

    Link<T> first;
    int k;
}
