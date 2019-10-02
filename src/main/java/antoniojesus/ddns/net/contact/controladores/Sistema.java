/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antoniojesus.ddns.net.contact.controladores;

import com.sun.org.apache.bcel.internal.generic.RET;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 *
 * @author A. Jesús <Antonio Jesús Pérez Delgado ajpd1985@gmail.com>
 */
public class Sistema {

    private static ObservableList<Stage> listaVentanas;

    public static ObservableList<Stage> getVentanas() {
        if (listaVentanas == null) {
            listaVentanas = FXCollections.observableArrayList();
        }
        return listaVentanas;
    }
}
