 
public class Ejemplo1
{
    public static void main(String[] args)
    {
        // Ahora creamos la instancia para poder usar sus m�todos
        Coche c1 = new Coche();
        // Taambi�n podr�amos haberlo hecho as�
         c1.setTraccion( Coche.TRACCION_CUATRO_RUEDAS );
        // Hacer esto ser�a lo mismo que lo anterior (pero tendr�amos que conocer
        // los valores) y estar seguros de que el valor de la constante no cambiar�
        c1.setTraccion( 3 );
         System.out.println("El coche tiene: " + c1.getTraccionTexto() );
        System.out.println( "---------------" );
        // Creamos otras dos intancias de Coche para comprobar c�mo funciona la
        // propiedad est�tica 'num' (se incrementa en 1 cada vez que se crea un
        // objeto)
        Coche c2 = new Coche();
        Coche c3 = new Coche();
        Coche c4 = new Coche();
         System.out.println( "Numero de instancias de Coche: [" + c1.getNumInstancias() + "]" );
        System.out.println( "---------------" );
        // Ahora llamamos al m�todo est�tico de la clase Barco (observa que tampoco
        // la hemos instanciado
         Barco.zarpar();
    }
}