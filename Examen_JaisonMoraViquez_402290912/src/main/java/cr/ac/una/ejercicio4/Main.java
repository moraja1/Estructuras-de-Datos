package cr.ac.una.ejercicio4;

public class Main {
    double err = 0.00001;
    public static void main(String[] args) {
        Main m = new Main();
        System.out.println(m.raiz_cuadrada(2.5, 6));
    }

    /*

    Este método calcula la raíz cuadrada de un número dadas las formulas
    del examen. Saliendo del método si se cumple una de las 2 condiciones
    o itera n veces, o tiene un margen de error menor al marge propuesto
    de 0.00001. Todo lo anterior de manera recursiva. Esto se puede reducir
    a ún unico método utilizando variables globales, sin embargo, con el fin
    de seguir las indicaciones, se hace un método adicional que reciba Xn+1 en
    cada iteración recursiva.

     */
    double raiz_cuadrada(double a, int n){
        double Xn = 1;
        double Xn1 = (Xn + (a / Xn)) / 2;
        return ((n == 0) || (Math.abs((Xn1 - Xn)) / Xn < err)) ? Xn1 : raiz_cuadrada(a, Xn1,n-1);
    }

    double raiz_cuadrada(double a, double Xn, int n){
        double Xn1 = (Xn + (a / Xn)) / 2;
        return ((n == 0) || (Math.abs((Xn1 - Xn)) / Xn < err)) ? Xn1 : raiz_cuadrada(a, Xn1,n-1);
    }
}
