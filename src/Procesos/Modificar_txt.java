/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Procesos;

import Empresa.Zapato;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Agudelo
 */
public class Modificar_txt implements Serializable {

    private ArrayList<Zapato> a = new ArrayList<>();
    
    //constructor
    public Modificar_txt() {
        //constructor vacío
    }

    public ArrayList<Zapato> getA() {
        return a;
    }

    public void setA(ArrayList<Zapato> a) {
        this.a = a;
    }

    public Modificar_txt(ArrayList<Zapato> a) {
        this.a = a;
    }

    public void agregarRegistro(Zapato p) {
        this.a.add(p);//agrega 'p' al arraylist
    }

    public void modificarRegistro(int i, Zapato p) {
        this.a.set(i, p);
    }

    public void eliminarRegistro(int i) {
        this.a.remove(i);//elimina la posicion 'i' del arraylist
    }

    public Zapato obtenerRegistro(int i) {
        return (Zapato) a.get(i); //retorna la posicion 'i' del arraylist, retorna una "fila" con todos los datos
    }

    public int cantidadRegistro() {
        return this.a.size();
    }

    public int buscaId(int codigo) {
        for (int i = 0; i < cantidadRegistro(); i++) {
            if (codigo == obtenerRegistro(i).getIdZapatos()) {
                return i;
            }
        }
        return -1;
    }//fin busca id

    public Zapato buscarCelda(int codigo) {
        for (int i = 0; i < cantidadRegistro(); i++) {
            if (codigo == obtenerRegistro(i).getIdZapatos()) {
                return obtenerRegistro(i);
            }
        }
        return null;
    }//fin buscaCelda
    
}
