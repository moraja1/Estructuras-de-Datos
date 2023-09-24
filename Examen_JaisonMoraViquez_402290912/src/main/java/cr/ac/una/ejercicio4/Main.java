package cr.ac.una.ejercicio4;

public class Main {
    public static void main(String[] args) {
        Main m = new Main();
        System.out.println(m.raiz_cuadrada(2, 1, 8));
    }
    double raiz_cuadrada(double a, double X0, int n){
        double Xn1 = (X0 + (a / X0)) / 2;
        double err = 0.00001;
        return (n == 0) || (Math.abs((Xn1 - X0)) < err) ? X0 : raiz_cuadrada(a, Xn1, n-1);
    }
}
