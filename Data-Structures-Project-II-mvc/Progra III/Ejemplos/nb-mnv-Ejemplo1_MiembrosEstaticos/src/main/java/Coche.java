
public class Coche
{
    // Esta propiedad estática la usaremos para contar el número de objetos
    // que se vayan creando
     static int num = 0;
    // Las siguientes propiedades estáticas las usaremos como 'constantes', cuyo
    // valor no podrá ser modificado
     public static final int TRACCION_DELANTERA = 1;
     public static final int TRACCION_TRASERA = 2;
     public static final int TRACCION_CUATRO_RUEDAS = 3;
    private int traccion;
    // ----------------------------
    public Coche()
    {
        // Cada vez que se cree un objeto nuevo 'num' se incrementa en 1
        // A las propiedades y métodos estáticos no se puede acceder con 'this'
         num = num + 1;
    }
    // ----------------------------
    public int getTraccion() {
        return this.traccion;
    }
    // ----------------------------
    public void setTraccion(int traccion) {
        this.traccion = traccion;
    }
    // ----------------------------
    public String getTraccionTexto()
    {
        String texto;
        switch( this.traccion )
        {
            case 1:
                texto = "Tracción delantera";
                break;
            case 2:
                texto = "Tracción trasera";
                break;
            case 3:
                texto = "Tracción a las cuatro ruedas";
                break;
            default:
                texto = "Tracción no especificada";
        }
        return texto;
    }
    // ----------------------------
     public int getNumInstancias() {
        return num;
    }
}