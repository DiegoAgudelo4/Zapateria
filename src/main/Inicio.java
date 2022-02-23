/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Vista.frmVista;

/**
 *
 * @author Agudelo
 */
public class Inicio {

 
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                new frmVista().setVisible(true);
                
            }
        });
    }
    
}
