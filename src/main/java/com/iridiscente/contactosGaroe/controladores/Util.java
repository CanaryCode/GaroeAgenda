/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iridiscente.contactosGaroe.controladores;

import com.iridiscente.contactosGaroe.modelos.Contacto;
import com.iridiscente.contactosGaroe.modelos.Admitido;
import com.iridiscente.contactosGaroe.modelos.Persona;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;

/**
 *
 * @author A. Jesús <Antonio Jesús Pérez Delgado ajpd1985@gmail.com>
 */
public class Util {

    public static void desHabilitarNodos(Node... nodos) {
        for (Node nodo : nodos) {
            nodo.setDisable(true);
        }
    }

    public static ObservableList<Contacto> empezar(Text eti, Node... nodos) {
        ObservableList<Contacto> lista = Consultas.getContactos();
        if (lista == null) {
            if (Encriptacion.AESObjectDedcoder("db.dat") != null) {
                lista = FXCollections.observableArrayList((ArrayList) Encriptacion.AESObjectDedcoder("db.dat"));
            } else {
                lista = FXCollections.observableArrayList();
            }
            desHabilitarNodos(nodos);
            eti.setText("no se ha podido conectar con la base de datos. Trabajando en fichero local.");
            eti.setVisible(true);
            if (lista == null) {
                Alert a = new Alert(Alert.AlertType.ERROR, "no se ha podido cargar la base de datos ni el archivo en local se cerrará el programa y llame a \"antonio el desarrollador de esta App.\"",
                        ButtonType.OK);
                a.showAndWait();
                System.exit(0);
            }
        }
        return lista;
    }

    public static void terminar(List<Contacto> listaContactos, List<Admitido> listaAdmitidos, List<Persona> listaUsuarios) {
        if (listaContactos != null && !listaContactos.isEmpty()) {
            Encriptacion.AESObjectEncoder((ArrayList) listaContactos, "db.dat");
        }
        if (listaAdmitidos != null && !listaAdmitidos.isEmpty()) {
            Encriptacion.AESObjectEncoder((ArrayList) listaAdmitidos, "admision.dat");
        }
        if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
            Encriptacion.AESObjectEncoder((ArrayList) listaUsuarios, "users.dat");
        }

        Consultas.salir();
        Conexion.closeSession();
        if (Conexion.sessionFactory != null && !Conexion.sessionFactory.isClosed()) {
            Conexion.sessionFactory.close();
        }
    }
}
