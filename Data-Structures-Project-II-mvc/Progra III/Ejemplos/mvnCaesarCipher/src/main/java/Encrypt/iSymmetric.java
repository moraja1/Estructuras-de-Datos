/*

*/
package Encrypt;

/*
* Interface to define how symmetric algorithms should be implemented
* @author Jose Alberto Vindas
* @version 1.0
*/


public interface iSymmetric {
    
    /**
     * This function returns the text encrypted using the shift provided 
     * @param text Text to encrypt
     * @param key key to perform the substitution
     * @return  encrypted text
    **/
    public StringBuffer Encrypt (String text, String key);
     
    /**
     * This function returns the text decrypted using the shift provided 
     * @param text Text to encrypt
     * @param key shift number to perform the substitution
     * @return decrypted text
    **/
    public StringBuffer Decrypt (String text, String key) ;
   
    
}
