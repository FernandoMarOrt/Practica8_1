/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daw.fer.modelo;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.DoubleStream;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author fer
 */
public class Factura {

    private static int contadorInstancias = 0;
    private String pk;
    private LocalDate fechaEmision;
    private String descripcion;
    private double totalImporteFactura;

    // Constructor por defecto
    public Factura() {
        contadorInstancias++;
        pk = "Factura" + contadorInstancias;
        fechaEmision = LocalDate.now();
        descripcion = RandomStringUtils.randomAlphabetic(10);
        totalImporteFactura = generarImporteAleatorio();
    }

    // Constructor parametrizado
    public Factura(String pk, LocalDate fechaEmision, String descripcion, double totalImporteFactura) {
        contadorInstancias++;
        this.pk = pk;
        this.fechaEmision = fechaEmision;
        this.descripcion = descripcion;
        this.totalImporteFactura = totalImporteFactura;
    }

    // Genera un importe aleatorio entre 100€ y 1000€
    private double generarImporteAleatorio() {
        Random random = new Random();
        DoubleStream importeStream = random.doubles(1, 100, 1001);
        return importeStream.findFirst().getAsDouble();
    }

    // Getters y setters
    public String getpk() {
        return pk;
    }

    public void setpk(String pk) {
        this.pk = pk;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getTotalImporteFactura() {
        return totalImporteFactura;
    }

    public void setTotalImporteFactura(double totalImporteFactura) {
        this.totalImporteFactura = totalImporteFactura;
    }

    public static int getContadorInstancias() {
        return contadorInstancias;
    }

    public static void setContadorInstancias(int contadorInstancias) {
        Factura.contadorInstancias = contadorInstancias;
    }

    @Override
    public String toString() {
        return pk + ";" + fechaEmision + ";" + descripcion + ";" + totalImporteFactura + ";";
    }

}
