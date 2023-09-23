package cr.ac.una.ejercicio2;

public class Main {
    public static void main(String[] args) {
        ManageableStack<Integer> mStack = new ManageableStack<>();
        mStack.push(4)
                .push(6)
                .push(3)
                .push(12)
                .push(7)
                .push(1)
                .push(8)
                .push(13);

        System.out.println(mStack);

        System.out.printf("Extracting -> %d%n", mStack.extract(2));
        System.out.println(mStack);
    }
}
