package com.mycompany.archivoscsv;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;


public class GestionCSV {
    
    
    public static void copiaCSV(String origen, String destino) throws IOException{
        //este método recoge las palabras y las metes en un hashmap
        var listaPalabras = new HashMap<String, Integer>();
        
        try( var texto = new FileReader(origen)){
            
            int caracter;
            String palabra = "";
            
            while( (caracter = texto.read())>0){
                
                System.out.print( (char) caracter );
                
                if(caracter != 32 && caracter != 10 && caracter != 13){//32 es el espacio. 10 y 13 es enter
                    palabra += (char) caracter;
                } 
                else{
                    if(palabra.length()>1){//este if es por si hay dos espacios seguidos
                       palabra = limpiarPalabra(palabra);
                        if(palabra.length()>2){
                            listaPalabras = actualizarLista(palabra, listaPalabras);
                        } 
                    }                    
                    palabra = "";
                }
            }
            
            if(palabra.length()>1){//una vez mas para la ultima palabra
                palabra = limpiarPalabra(palabra);
                if(palabra.length()>2){
                    listaPalabras = actualizarLista(palabra, listaPalabras);
                } 
            } 
            
        } catch (IOException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //imprimir hashmap
        System.out.println("\n"+listaPalabras); 

        
        //meter el hashmap en el archivo csv
         String[] cabecera = {"palabra","cantidad"};

        FileWriter out = new FileWriter(destino);
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
          .withHeader(cabecera))) {
            listaPalabras.forEach((palabra, cantidad) -> {
                try {
                    printer.printRecord(palabra, cantidad);
                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        
    }
    
    public static String limpiarPalabra(String palabra){
        
        palabra = LimpiarPalabra.ponerMinusculas(palabra);
        palabra = LimpiarPalabra.quitarPuntuacion(palabra);
        palabra = LimpiarPalabra.quitarAcentos(palabra);
        palabra = LimpiarPalabra.quitarAbreviaturasInglesas(palabra);
        
        return palabra;
    }
    
      public static HashMap<String, Integer> actualizarLista(String palabra, HashMap<String, Integer> listaPalabras){
        //este método mira si ya está la palabra en la lista. 
        //Si lo está añade uno a la cantidad, sino la mete en la lista.
        Integer n = listaPalabras.get(palabra);
        
        if(n != null){
            listaPalabras.remove(palabra);
            listaPalabras.put(palabra, n+1);
        } else{
            listaPalabras.put(palabra, 1);
        }
        return listaPalabras;
    }
}
