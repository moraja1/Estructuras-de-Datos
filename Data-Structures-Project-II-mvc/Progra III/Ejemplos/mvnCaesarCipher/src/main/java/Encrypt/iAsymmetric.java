/*

 */
package Encrypt;

/*
 * Interface to define how asymmetric algorithms should be implemented
 * @author Jose Alberto Vindas
 * @version 1.0
 */

public interface iAsymmetric {
    
    /*
    Asymetric algoritms uses public and private key contract
    */
    
    String PrivateKey = "";
    String PublicKey = "";
    
    public void CreateKeys();
    
    public String GetPublicKey();
    
    public String Encrypt(String text, String PublicKey);
    
    public String Decrypt(String text);
    
    
    
            
            
}
