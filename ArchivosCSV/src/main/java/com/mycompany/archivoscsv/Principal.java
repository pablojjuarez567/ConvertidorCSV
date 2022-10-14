package com.mycompany.archivoscsv;

import java.io.IOException;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        
        String origen;
        String destino;
                
        System.out.println("Introduce el nombre del archivo txt a procesar (sin poner txt)");
        origen = sc.nextLine();
        destino = origen + "_histograma.csv";
        origen += ".txt";
         
        GestionCSV.copiaCSV(origen, destino);  
    }
}
