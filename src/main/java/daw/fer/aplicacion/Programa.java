/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daw.fer.aplicacion;

import daw.fer.modelo.Factura;
import daw.fer.modelo.FacturaDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author fer
 */
public class Programa {

    public static void main(String[] args) {

        FacturaDAO daoFactura = new FacturaDAO();
        List<Factura> listaFacturas = generarListaFichero("facturas.csv", ";");
        
        
         try {
             
            //Inserto todas las facturas en la tabla facturas
            System.out.println("Nº facturas insertadas " + daoFactura.insertFactura(listaFacturas));
            
            //Compruebo que todas las facturas se ha insertado en la tabla facturas
            System.out.println("-----------------------------------------");
            System.out.println("Comprobamos en una nueva lista que se recogen los datos desde la tabla.");
            List<Factura> nuevaLista = daoFactura.getAll();
            System.out.println("-------- Lista con datos recogidos desde la B.D -------------");
            nuevaLista.forEach(System.out::println);
            
            //Busco a la factura con clave primaria Factura1
            System.out.println("-----------------------------------------");
            System.out.println("Factura con primary key Factura1: ");
            System.out.println(daoFactura.findByPk("Factura1"));

            //Borro una factura de la tabla
            System.out.println("-----------------------------------------");
            System.out.println("Se va a borrar la factura con pk Factura3");
            System.out.println("Nº personas borradas " + 
                    daoFactura.deleteFactura(new Factura("Factura3",LocalDate.of(2023, 4,27),"HptutKhxee",395.096693654351)));

            //Compruebo si se ha borrado correctamente la factura anterior
            System.out.println("-----------------------------------------");
            nuevaLista = daoFactura.getAll();
            System.out.println("-------- Lista con datos recogidos desde la B.D despues de borrar una persona -------------");
            nuevaLista.forEach(System.out::println);

            //Modifico los datos de la factura con pk Factura5
            System.out.println("-----------------------------------------");
            System.out.println("Modificación de la factura con pk Factura5");
            System.out.println("Nº Personas modificadas " + 
                    daoFactura.updateFactura("Factura5", new Factura("Factura5",LocalDate.of(2024, 4,27),"NuevaFactura",500.0)));
            
            //Compruebo que los datos de la factura con pk Factura5 han sido modificados
            System.out.println("-----------------------------------------");
            nuevaLista = daoFactura.getAll();
            System.out.println("-------- Lista con datos recogidos desde la B.D despues de modificar una factura -------------");
            nuevaLista.forEach(System.out::println);


        } catch (SQLException sqle) {
            System.out.println("No se ha podido realizar la operación:");
            System.out.println(sqle.getMessage());
        }

    }

    public static List<Factura> generarListaFichero(String nomFichero, String separador) {

        List<Factura> listaFactura = new ArrayList<>();

        String idFichero = nomFichero;
        String[] tokens;
        String linea;

        Factura f = null;
        
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //Doy formato a la fecha

        try ( Scanner datosFichero = new Scanner(new File(idFichero), "ISO-8859-1")) {

            while (datosFichero.hasNextLine()) {

                linea = datosFichero.nextLine();// Guarda la línea completa en un String

                tokens = linea.split(separador); //Se guarda cada elemento en funcion de el separador

               
                f = new Factura(tokens[0],LocalDate.parse(tokens[1], formatter), tokens[2], Double.valueOf(tokens[3]));

                listaFactura.add(f);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return listaFactura;

    }

}
