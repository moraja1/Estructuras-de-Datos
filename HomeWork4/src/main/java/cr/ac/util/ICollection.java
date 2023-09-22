package cr.ac.util;

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
public interface ICollection<T> extends Iterable<T> {

    public boolean isEmpty();

    public int count();

    public T get(int p);

    public ICollection<T> add(T data);

    public ICollection<T> add(T data, int p);

    public ICollection<T> addAll(T[] array);

    public ICollection<T> addAll(ICollection<T> collection);

    public T remove();

    public T remove(int p);

    public ICollection<T> clear();

}
