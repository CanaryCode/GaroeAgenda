/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iridiscente.contactosGaroe.controladores;

import com.iridiscente.contactosGaroe.controladores.fxmls.Contactos;
import com.iridiscente.contactosGaroe.modelos.Contacto;
import com.iridiscente.contactosGaroe.modelos.VentanaCustom;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 *
 * @author A. Jesús <Antonio Jesús Pérez Delgado ajpd1985@gmail.com>
 */
public class LanzadorVentanas {

    public void AbrirFXML(String nombre, String titulo, EventHandler<WindowEvent> eventoCierreVentana, boolean resizable, Window padre, Modality modalidad,boolean 
            cerrarAplicacion) {

        VentanaCustom vCustom = new VentanaCustom(nombre);
        if (!Sistema.getListaVentanas().contains(vCustom)) {
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("/fxml/" + nombre + ".fxml"));

                List<Contacto> l;
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/styles/Styles.css");
                if (titulo == null) {
                    vCustom.setTitle("Contactos Garoé by A. Jesús");
                } else {
                    vCustom.setTitle(titulo);
                }
                if (modalidad != null) {
                    vCustom.initModality(modalidad);
                } else {
                    vCustom.initModality(Modality.NONE);
                }
                if (padre != null) {
                    vCustom.initOwner(padre);
                }
                vCustom.setScene(scene);
                vCustom.setResizable(resizable);
                vCustom.getIcons().add(new Image("/imagenes/guia_telefonica.png"));
                if (eventoCierreVentana == null) {
                    vCustom.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            if(cerrarAplicacion){
                            Util.terminar(new ArrayList<Contacto>(Contactos.lista),Sistema.listaMac,Sistema.listaUsuarios);}
                            Sistema.getListaVentanas().remove(vCustom);
                        }
                    });
                } else {
                    vCustom.setOnCloseRequest(eventoCierreVentana);

                }
                vCustom.show();
                Sistema.getListaVentanas().add(vCustom);
            } catch (IOException ex) {
                Logger.getLogger(LanzadorVentanas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
           Sistema.getVentanaCustom(vCustom.getNombre());
            new Alert(Alert.AlertType.ERROR, "Esta ventana ya está abierta", ButtonType.OK).show();
        }
    }
}
