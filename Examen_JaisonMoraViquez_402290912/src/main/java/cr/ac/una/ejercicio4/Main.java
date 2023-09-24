package cr.ac.una.ejercicio4;

public class Main {
    double Xn = 1;
    double Xn1;
    public static void main(String[] args) {
        Main m = new Main();
        System.out.println(m.raiz_cuadrada(2, 6));
    }

    /*

    Este método calcula la raíz cuadrada de un número dadas las formulas
    del examen. Saliendo del método si se cumple una de las 2 condiciones
    o itera n veces, o tiene un margen de error menor al marge propuesto
    de 0.00001. Todo lo anterior de manera recursiva.

     */
    double raiz_cuadrada(double a, int n){
        double Xn1 = (Xn + (a / Xn)) / 2;
        double err = 0.00001;
        if ((n == 0) || (Math.abs((Xn1 - Xn)) / Xn < err)) return Xn1;
        else Xn = Xn1;
        return raiz_cuadrada(a, n-1);
    }
}
