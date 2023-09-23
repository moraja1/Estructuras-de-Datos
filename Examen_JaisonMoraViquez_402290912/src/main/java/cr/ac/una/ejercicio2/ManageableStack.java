package cr.ac.una.ejercicio2;

import cr.ac.una.util.Stack;

public class ManageableStack<T> extends Stack<T> {
    public ManageableStack() {
        super();
    }

    public T extract(int i){
        if(count() <= i || i < 0){
            throw new IllegalArgumentException("i is not a possible position in the stack.");
        }
        Stack<T> extractions = new Stack<>();
        for(int idx = 0; idx < i; idx++){
            extractions.push(pop());
        }
        T element = pop();
        while(!extractions.isEmpty()){
            push(extractions.pop());
        }
        return element;
    }
}
