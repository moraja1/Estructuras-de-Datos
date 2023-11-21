/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.una.progra.iii.mvncaesarcipher;
import Encrypt.Caesar;

/**
 *
 * @author josev
 */
public class MvnCaesarCipher {

    public static void main(String[] args) {
       
        Caesar caesar = new Caesar();
        System.out.println("--------------------------------------------------------------"+'\n');
        System.out.println("Example of Caesar Cypher implementation"+'\n');
        String originalText = "Mi primer texto encriptado con el crifrado César AZ";
        String shiftCount = "1";
        System.out.println("Caesar Cipher Example");
        System.out.println("Encryption");
        System.out.println("Text  : " + originalText);
        System.out.println("Shift : " + shiftCount);
        String cipher = caesar.Encrypt(originalText, shiftCount).toString();
        System.out.println("Encrypted Cipher: " + cipher);
        System.out.println("Decryption");
        System.out.println("Encrypted Cipher: " + cipher);
        System.out.println("Shift : " + shiftCount);
        String decryptedPlainText = caesar.Decrypt(cipher, shiftCount).toString();
        System.out.println("Decrypted Plain Text  : " + decryptedPlainText);
        System.out.println("--------------------------------------------------------------"+'\n');
        
    }
}
