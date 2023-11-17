package ejemplo3;
//Una subclase de DosDimensiones para Triangulo
//Triangulo.java

public class Triangulo extends DosDimensiones{
    
	private final String estilo;
    //Constructor
    Triangulo(String s, double b, double h){
        setBase(b);
        setAltura(h);
        estilo=s;
    }
    
    double area(){
        return getBase()*getAltura()/2;
    }
    
    void mostrarEstilo(){
        System.out.println("Triangulo es: "+estilo);
    }

}
