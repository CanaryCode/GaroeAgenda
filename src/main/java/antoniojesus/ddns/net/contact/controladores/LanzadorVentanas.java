/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antoniojesus.ddns.net.contact.controladores;

import antoniojesus.ddns.net.contact.controladores.fxmls.Contactos;
import antoniojesus.ddns.net.contact.modelos.Contacto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    
    public void AbrirFXML(String nombre, String titulo, EventHandler<WindowEvent> eventoCierreVentana, boolean resizable, Window padre, Modality modalidad) {
        
        Stage stage = new Stage();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/" + nombre + ".fxml"));
            
            List<Contacto> l;
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");
            if (titulo == null) {
                stage.setTitle("Contactos Garoé by A. Jesús");
            } else {
                stage.setTitle(titulo);
            }
            if (modalidad != null) {
                stage.initModality(modalidad);
            } else {
                stage.initModality(Modality.NONE);
            }
            if (padre != null) {
                stage.initOwner(padre);
            }
            stage.setScene(scene);
            stage.setResizable(resizable);
            stage.getIcons().add(new Image("/imagenes/guia_telefonica.png"));
            if (eventoCierreVentana == null) {
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        Util.terminar(new ArrayList<Contacto>(Contactos.lista));
                        Sistema.getVentanas().remove(nombre);
                    }
                });
            } else {
                stage.setOnCloseRequest(eventoCierreVentana);
                
            }
            stage.show();
            Sistema.getVentanas().add(stage);
        } catch (IOException ex) {
            Logger.getLogger(LanzadorVentanas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
