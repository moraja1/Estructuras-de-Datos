package ejemplo2;

public class Triangulo extends DosDimensiones{
    
	String estilo;
    
	double area(){
        return getBase()*getAltura()/2;
    }
    
	void mostrarEstilo(){
        System.out.println("Triangulo es: "+estilo);
    }

}
