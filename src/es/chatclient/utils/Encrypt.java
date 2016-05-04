/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.chatclient.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author adrinfer
 */
public class Encrypt {
    
    //Algoritmos
    public static final String MD5 = "MD5";
    public static final String SHA512 = "SHA-512";

    //Convierte un array de bytes a String usando valores hexadecimales
    private static String toHexadecimal(byte[] digest){
         
        String hash = "";
        
        for(byte aux : digest) 
        {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }
        
        return hash;
        
    }
     
    private static String getStringMessageDigest(String message, String algorithm){
       
        byte[] digest = null;
        byte[] buffer = message.getBytes();
        
        try 
        {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        } 
        catch (NoSuchAlgorithmException ex) 
        {
            System.out.println("Error creando Digest");
        }
        
        return toHexadecimal(digest);
    }
    
    //Encriptar
    public static String encrypt(String message)
    {
        return getStringMessageDigest(getStringMessageDigest(message, Encrypt.MD5), Encrypt.SHA512);
    }


    
}//end class
