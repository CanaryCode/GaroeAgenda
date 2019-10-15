/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iridiscente.contactosGaroe.modelos;

import javafx.stage.Stage;

/**
 *
 * @author A. Jesús <Antonio Jesús Pérez Delgado ajpd1985@gmail.com>
 */
public class VentanaCustom extends Stage {

    private String nombre = null;

    public VentanaCustom(String nombre) {
        super();
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            return ((VentanaCustom) obj).getNombre().equals(this.getNombre());
        }else{
            return false;
        }
    }

}
