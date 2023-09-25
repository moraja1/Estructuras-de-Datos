package cr.ac.una.util;

import java.util.Iterator;

public class SortableList<T extends Comparable<T>> extends List<T> {

    public void sort() {
        insertionSort(false);
    }

    public void insertionSort(boolean trace) {
        List<T> insertionList = new List<>();
        insertionSort(insertionList, this, trace);
    }

    public void insertionSort(List<T> insertionList, List<T> originalList, boolean trace) {
        while(!originalList.isEmpty()) {
            if(trace) System.out.printf("Sorting: %-45s <- {%3s} <- %s%n", insertionList, originalList.get(0), originalList);
            for(int i = 0; i <= insertionList.count(); i++) {
                if(i == insertionList.count()) {
                    insertionList.add(originalList.remove(0));
                    i = insertionList.count()+1;
                } else {
                    if(originalList.get(0).compareTo(insertionList.get(i)) < 0) {
                        insertionList.add(originalList.remove(0), (i));
                        i = insertionList.count()+1;
                    }
                }
                if(trace && i == insertionList.count()+1) System.out.printf("Sorted:  %-45s <- %s%n", insertionList, originalList);
            }
        }
        while(!insertionList.isEmpty()){
            originalList.add(insertionList.remove(0));
        }
    }

    public void mergeSort(boolean trace) {
        if(count() > 1) {
            List<T> sortedList = mergeSort(this, trace);
            insertionSort(sortedList, this, trace);
        }
    }

    private List<T> mergeSort(List<T> list, boolean trace) {
        List<T> l1 = new List<>();
        List<T> l2 = new List<>();
        if(list.count() > 1) {
            while(!list.isEmpty()) {
                l1.add(list.remove());
                if(!list.isEmpty()) l2.add(list.remove());
            }
        } else {
            return list;
        }
        List<T> sortedList1 = mergeSort(l1, trace);
        List<T> sortedList2 = mergeSort(l2, trace);
        insertionSort(sortedList2, sortedList1, trace);

        return sortedList1;
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
