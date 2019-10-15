/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antoniojesus.ddns.net.contact.controladores;

import antoniojesus.ddns.net.contact.controladores.Mapeador;
import antoniojesus.ddns.net.contact.controladores.Conexion;
import antoniojesus.ddns.net.contact.modelos.Contacto;
import antoniojesus.ddns.net.contact.controladores.Consultas;
import java.util.List;
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
        if (lista.size()<1) {
            lista = Mapeador.leerObjeto();
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

    public static void terminar(List<Contacto> lista) {
        if (lista.size() > 0) {
            Mapeador.escribirObjeto(lista);
        }
        if (Conexion.sessionFactory != null) {
            if (!Conexion.sessionFactory.isClosed()) {
                Conexion.sessionFactory.close();
            }
            if (Conexion.session.isOpen()) {
                Conexion.session.close();
            }
        }
    }
}
