
package com.mycompany.archivoscsv;

import java.text.Normalizer;


public class LimpiarPalabra {

    public static String ponerMinusculas(String palabra){
        return palabra.toLowerCase();
    }
    
    public static String quitarPuntuacion(String palabra){
   
        boolean caracter = true;
        
        while(caracter){
            
            caracter = false;
            
            if(palabra.charAt(0) == '(' || palabra.charAt(0) == '¿' || 
                palabra.charAt(0) == '¡' || palabra.charAt(0) == '"' ||
                palabra.charAt(0) == '<'){

                 palabra = palabra.substring(1);
                 caracter = true;
             }
            
            if( palabra.charAt(palabra.length()-1) == '?' || palabra.charAt(palabra.length()-1) == '!'  
                || palabra.charAt(palabra.length()-1) == '.' || palabra.charAt(palabra.length()-1) == ',' 
                || palabra.charAt(palabra.length()-1) == ':' || palabra.charAt(palabra.length()-1) == ';'
                || palabra.charAt(palabra.length()-1) == ')' || palabra.charAt(palabra.length()-1) == '>'
                || palabra.charAt(palabra.length()-1) == '"'    ){

                 palabra = palabra.substring(0, palabra.length()-1);
                 caracter = true;
             }            
        }
        return palabra;
    }
    

    public static String quitarAcentos(String palabra){
        palabra = Normalizer.normalize(palabra, Normalizer.Form.NFD);
        palabra = palabra.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return palabra;
    }
    
    public static String quitarAbreviaturasInglesas(String palabra){
        //Cuando una palabra inglesa tiene "'", la corta
        //por ejemplo: I´m -> I   You´re -> You  She´ll -> She
        for(int i = 0 ; i < palabra.length() ; i++){
            if(palabra.charAt(i) == 39){
                palabra = palabra.substring(0, i);
            }
        }
        return palabra;
    }
    
  
}
