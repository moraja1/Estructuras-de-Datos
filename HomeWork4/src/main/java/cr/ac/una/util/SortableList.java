package cr.ac.una.util;

import java.util.Iterator;

public class SortableList<T extends Comparable<T>> extends List<T> {

    public void sort() {
        insertionSort(false);
    }

    public void insertionSort(boolean trace) {
        List<T> insertionList = new List<>();
        while(!isEmpty()){
            insertionList.add(remove());
        }
        while(insertionList.isEmpty()){
            for(T item : this){
                if(isEmpty()){
                    add(insertionList.remove());
                } else {
                    if(insertionList.get(0).compareTo(item) < 0){

                    }
                }
            }
        }
    }

    public void mergeSort(boolean trace) {
        throw new UnsupportedOperationException();
    }

    public static <T> String toString(List<T> L) {
        return toString(L, false);
    }

    public static <T> String toString(List<T> L, boolean vertical) {
        StringBuilder r = new StringBuilder("{");
        if (vertical) {
            r.append("\n");
        }

        Iterator<T> i = L.iterator();
        while (i.hasNext()) {
            if (vertical) {
                r.append("\t");
            }
            r.append(i.next());
            if (i.hasNext()) {
                r.append(", ");
            }
            if (vertical) {
                r.append("\n");
            }
        }
        r.append("}");
        return r.toString();
    }

}