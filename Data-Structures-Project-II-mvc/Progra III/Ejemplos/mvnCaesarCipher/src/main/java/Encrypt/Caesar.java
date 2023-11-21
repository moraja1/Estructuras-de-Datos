/*
 */
package Encrypt;

/*
 * This class provides simple implementation of Caesar algorithm using using US alphabet and ASCII code to allow uppercase and lowercase chars
 * The methods will exclude any other char that is out of US alphabet
 * This implementation tries to explain how the Development Principle "Don;t repeat your self" can be applied
 * Functions Encrypt do the same process but Encrypt moves to right and Decrypt moves to left
 * @author Jose Alberto Vindas
 * @version 1.0
 */
public class Caesar implements iSymmetric{

    @Override
    /*
     * This function returns the text encrypted using the shift provided 
     * @param text: Text to encrypt
     * @param shift: shift number to perform the substitution
     * @return Text Encrypted using the shift provided
    */
    public StringBuffer Encrypt(String text, String shift) {
        return Algorithm(text,checkShift(shift),'E');
    }

    @Override
     /*
     * This function returns the text decrypted using the shift provided 
     * @param text: Text to encrypt
     * @param shift: shift number to perform the substitution
     * @return Text Decrypted using the shift provided
    */
    public StringBuffer Decrypt(String text, String shift) {
        return Algorithm(text,checkShift(shift),'D');
    }
    /*
    * @param text: text to which the algorithm will be applied
    * @param shift: shift number to perform the substitution
    * @param action: E to Encrypt, D to Decrypt 
    * @return The result of applying the algorithm
    **/
    private StringBuffer Algorithm(String text, int shift, char action)
    {
        StringBuffer result = new StringBuffer();
        int ASCIIValue; 
        char ch;
        
        if (action == 'D')
            shift = 26 - shift;
        
        //will check all the string
        for (int i = 0; i < text.length(); i++) {
            //Get ASCII Value
            ASCIIValue = (int) text.charAt(i);
            
            //Check if char is part of the alphabet
            if((ASCIIValue >= 65 && ASCIIValue <=90) || (ASCIIValue >= 97 && ASCIIValue <=122))
            {
                if (Character.isUpperCase(text.charAt(i))) {
                    ch = (char) (((int) text.charAt(i) + shift - 65) % 26 + 65);
                    result.append(ch);
                } else {
                    ch = (char) (((int) text.charAt(i) + shift - 97) % 26 + 97);
                    result.append(ch);
                }
            }
            else//keep as it, due we don't recognize it
            {
                result.append(text.charAt(i));
            }    
        }
        return result;
    }

    /*
    * Check shift boundary and adjust it if necessary
    * @param shift: shift number to perform the substitution
    * @param action: E to Encrypt, D to Decrypt 
    * @return returns a valid int number between 1 and 26 if the string is valid, otherwise returns 0
    */
    private int checkShift(String shift)
    {
        int intValue = 0;
        
        if(shift == null || shift.equals("")) {
            System.out.println("String cannot be parsed, it is null or empty.");
        }
        
        try {
           intValue = Integer.parseInt(shift);
           
           if (intValue> 26)
            {
                intValue = intValue % 26;
            }
           
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        
        return intValue;
    }
        
     
    
}
