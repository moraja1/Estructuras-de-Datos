package cr.una;

public class List <E> {
    private Link<E> first;
    private Link<E> last;
    private long size;

    public List(E ... args){
        for(E arg : args){
            add(arg);
        }
        size = args.length;
    }

    public List<E> add(E data) {
        if(first == null){
            first = new Link<E>(data);
            last = first;
        }else{
             last.setNext(new Link<>(data));
             last = last.getNext();
        }
        size++;
        return this;
    }

    public List<E> insert(E data, int idx){
        Link<E> temp;
        if(idx > size){
            return null;
        }else if(idx == size){
            return add(data);
        }else if(idx == 0){
            temp = new Link<>(data);
            temp.setNext(first);
            first = temp;
            size++;
            return this;
        }
        temp = first;
        for(int i = 1; i < idx; i++){
            temp = temp.getNext();
        }
        Link<E> tempNext = temp.getNext();
        temp.setNext(new Link<>(data));
        temp.getNext().setNext(tempNext);
        size++;
        return this;
    }

    public E get(int idx){
        if(idx >= size || idx < 0){
            return null;
        }else if(idx == 0){
            return first.getData();
        } else if (idx == size-1) {
            return last.getData();
        }
        Link<E> temp = first.getNext();
        for(int i = 1; i < idx; i++){
            temp = temp.getNext();
        }
        return temp.getData();
    }

    public boolean isEmpty(){
        return first == null;
    }

    public long getSize() {
        return size;
    }

    @Override
    public String toString() {
        Link<E> temp = first;
        StringBuilder s = new StringBuilder("{\n");
        while(temp != null) {
            s.append(temp.getData()).append("\n");
            temp = temp.getNext();
        }
        s.append("}");
        return s.toString();
    }
}
