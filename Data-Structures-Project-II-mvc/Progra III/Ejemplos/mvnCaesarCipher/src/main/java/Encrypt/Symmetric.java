/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Encrypt;

/**
 *
 * @author josev
 */
class Symmetric implements iSymmetric{

    @Override
    public StringBuffer Encrypt(String text, String key) {
        return Algorithm(text,key,'E');
    }

    @Override
    public StringBuffer Decrypt(String text, String key) {
        return Algorithm(text,key,'D');
    }
    /**
    * @Param text: texto al que se le va aplicar el algoritmo
    * @Param key: valor a utilizar como llave o rotación
    * @Param action: E para Encrypt, D para Decrypt 
    **/
    private StringBuffer Algorithm(String text, String key, char action)
    {
        StringBuffer result = new StringBuffer();
        
        return result;
    }
    
    
}
