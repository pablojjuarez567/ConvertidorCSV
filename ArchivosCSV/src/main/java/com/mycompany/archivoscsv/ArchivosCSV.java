package com.mycompany.archivoscsv;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;



public class ArchivosCSV {

    public static HashMap<String, Integer> copiaCSV( String origen, HashMap<String, Integer> listaPalabras){
        //este método recoge las palabras y las metes en un hashmap
        try( var texto = new FileReader(origen)){
            
            int caracter;
            String palabra = "";
            
            while( (caracter = texto.read())>0){
                
                System.out.print( (char) caracter );
                
                if(caracter != 32 && caracter != 10 && caracter != 13){//32 es el espacio. 10 y 13 es enter
                    palabra += (char) caracter;
                } 
                else{
                    palabra = limpiarPalabra(palabra);
                    if(palabra.length()>2){
                        listaPalabras = actualizarLista(palabra, listaPalabras);
                    }
                    palabra = "";
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ArchivosCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaPalabras;
    }
    
    public static String limpiarPalabra(String palabra){
        //Este método lo pone todo en minúscula .
        //También quita signos de puntuación si están al principio o al final.
        palabra = palabra.toLowerCase();
        
        if(palabra.charAt(0) == '(' || palabra.charAt(0) == '¿' || 
           palabra.charAt(0) == '¡' || palabra.charAt(0) == '"' ||
           palabra.charAt(0) == '<'){
            
            palabra=palabra.substring(1);
        }
        
        if( palabra.charAt(palabra.length()-1) == '?' || palabra.charAt(palabra.length()-1) == '!'  
           || palabra.charAt(palabra.length()-1) == '.' || palabra.charAt(palabra.length()-1) == ',' 
           || palabra.charAt(palabra.length()-1) == ':' || palabra.charAt(palabra.length()-1) == ';'){
           
            palabra = palabra.substring(0, palabra.length()-1);
        }        
        
        if(palabra.charAt(palabra.length()-1) == ')' || palabra.charAt(palabra.length()-1) == '>'
           || palabra.charAt(palabra.length()-1) == '"'){
           
            palabra = palabra.substring(0, palabra.length()-1);

        }
        
        return palabra;
    }
    
    public static void escribirCSV(String destino, HashMap<String, Integer> listaPalabras) throws IOException {
        //método para meter los datos en el archivo.csv
        String[] cabecera = {"palabra","cantidad"};

        FileWriter out = new FileWriter(destino);
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
          .withHeader(cabecera))) {
            listaPalabras.forEach((palabra, cantidad) -> {
                try {
                    printer.printRecord(palabra, cantidad);
                } catch (IOException ex) {
                    Logger.getLogger(ArchivosCSV.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
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
    
    
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        
        String origen;
        String destino;
        var listaPalabras = new HashMap<String, Integer>();
        
        System.out.println("Introduce el nombre del archivo txt a procesar (sin poner txt)");
        origen = sc.nextLine();
        destino = origen + "_histograma.csv";
        origen += ".txt";
         
        listaPalabras = copiaCSV(origen, listaPalabras);
        
        System.out.println("\n"+listaPalabras); 
        
        escribirCSV(destino, listaPalabras);
    }
}
