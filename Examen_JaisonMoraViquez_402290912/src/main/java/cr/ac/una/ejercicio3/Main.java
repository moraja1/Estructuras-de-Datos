package cr.ac.una.ejercicio3;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Integer[] numbers = new Integer[]{3, 3, 1, 1, 1, 8, 3, 6, 8, 7, 8, 9, 5, 5, 9, 8};
        StringBuilder s = new StringBuilder("{");
        for (int a : numbers){
            s.append(a).append(",");
        }
        s.replace(s.length()-1, s.length(), "}");
        System.out.println(s);
        new Main().sort(numbers);

        s.delete(1, s.length());
        for (int a : numbers){
            s.append(a).append(",");
        }
        s.replace(s.length()-1, s.length(), "}");
        System.out.println(s);
    }

    private void sort(Integer[] numbers) {
        HashMap<Integer, Integer> numOccurrences = new HashMap<>();
        HashMap<Integer, Integer> numIndex = new HashMap<>();
        for(int i = 0; i < numbers.length; i++){
            int num = numbers[i];
            if(!numIndex.containsKey(num)){
                numIndex.put(num, i);
            }
            int occurrences = 0;
            if(!numOccurrences.containsKey(num)){
                for(int j = i; j < numbers.length; j++){
                    if(num == numbers[j]) occurrences++;
                }
                numOccurrences.put(num, occurrences);
            }
        }
        List<Integer> sortedList = new LinkedList<>();
        Set<Integer> keySet = numOccurrences.keySet();
        for(Integer key : keySet){
            int occ = numOccurrences.get(key);
            int idx = numIndex.get(key);
            if (sortedList.isEmpty()){
                for (int a = 0; a < occ; a++){
                    sortedList.add(key);
                }
            } else {
                for(int i = 0; i < sortedList.size(); i++){
                    int listKey = sortedList.get(i);
                    int listOcc = numOccurrences.get(listKey);
                    int listIdx = numIndex.get(listKey);
                    if(occ < listOcc){
                        i += listOcc;
                        if(i >= sortedList.size()){
                            for(int a = 0; a < occ; a++){
                                sortedList.add(key);
                            }
                            i += occ;
                        } else {
                            i--;
                        }
                    } else {
                        if(idx < listIdx || occ > listOcc){
                            for(int a = 0; a < occ; a++){
                                sortedList.add(i, key);
                            }
                            i = sortedList.size();
                        } else {
                            i += listOcc;
                            if(i >= sortedList.size()){
                                for(int a = 0; a < occ; a++){
                                    sortedList.add(key);
                                }
                            } else {
                                i--;
                            }
                        }
                    }
                }
            }
        }
        sortedList.toArray(numbers);
    }

}
