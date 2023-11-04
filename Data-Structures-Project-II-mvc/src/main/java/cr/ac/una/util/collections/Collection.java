package cr.ac.una.util.collections;

import java.util.Iterator;

/**
 * -------------------------------------------------------------------
 *
 * (c) 2023
 *
 * @author Georges Alfaro S.
 * @version 1.0.6 2023-09-12
 *
 * --------------------------------------------------------------------
 * @param <T>
 */
public abstract class Collection<T> implements ICollection<T> {

    @Override
    public boolean isEmpty() { // O(1)
        return count() == 0;
    }

    @Override
    public ICollection<T> add(T data) { // O(1)
        return add(data, count());
    }

    @Override
    public ICollection<T> addAll(T[] array) {
        for (T data : array) {
            add(data);
        }
        return this;
    }

    @Override
    public ICollection<T> addAll(ICollection<T> collection) {
        for (T data : collection) {
            add(data);
        }
        return this;
    }

    @Override
    public T remove() { // O(1)
        return remove(count() - 1);
    }

    @Override
    public ICollection<T> clear() { // O(n)
        while (!isEmpty()) {
            remove();
        }
        return this;
    }

    /*
    * En Java, hay cuatro modificadores de acceso: public (público),
    * private (privado), protected (protegido) y predeterminado
    * (sin palabra clave).
    * Cuando no se usa explícitamente ninguna palabra clave,
    * Java establece un acceso predeterminado a una clase, método o atributo.
    * El acceso predeterminado también se denomina package-private (paquete
    * privado), lo que significa que todos los elementos son visibles dentro
    * del mismo paquete pero desde otros paquetes.
    *
    * https://www.baeldung.com/java-access-modifiers
    * 
     */
    String toString(String[] delimiters) {
        StringBuilder r = new StringBuilder(delimiters[0]);

        Iterator<T> i = iterator();
        boolean hasMore = false;
        while (i.hasNext()) {
            if (hasMore) {
                r.append(", ");
            }
            r.append(i.next());
            hasMore = true;
        }

        r.append(delimiters[1]);
        return r.toString();
    }

}
