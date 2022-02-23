/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Empresa;

import java.io.Serializable;

/**
 *
 * @author Agudelo
 */
public class Zapato implements Serializable{

    int IdZapatos;
    String nombre, tamaño, tipo, marca, color;
    
    double precio;

    //constructor vacio
    public Zapato() {
    }
    
    //constructor con todos los campos
    public Zapato(int IdZapatos, String nombre, String tamaño, String tipo, String marca, double precio, String color) {
        this.IdZapatos = IdZapatos;
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.tipo = tipo;
        this.marca = marca;
        this.precio = precio;
        this.color = color;
    }

    //getters and setters
    
    public int getIdZapatos() {
        return IdZapatos;
    }

    public void setIdZapatos(int IdZapatos) {
        this.IdZapatos = IdZapatos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    //fin getters and setters

}
