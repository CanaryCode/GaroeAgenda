/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antoniojesus.ddns.net.contact.controladores.fxmls;

import antoniojesus.ddns.net.contact.MainApp;
import antoniojesus.ddns.net.contact.controladores.Animacion;
import antoniojesus.ddns.net.contact.controladores.LanzadorVentanas;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author A. Jesús <Antonio Jesús Pérez Delgado ajpd1985@gmail.com>
 */
public class login implements Initializable {
  @FXML
    private AnchorPane principal;

    @FXML
    private Label etiTitulo;

    @FXML
    private TextField tfUsuario;

    @FXML
    private PasswordField pfClave;

    @FXML
    private Button btnEntrar;

    @FXML
    private Text txtError;
    public void initialize(URL url, ResourceBundle rb) {
        Animacion.animaPanel(principal);
        btnEntrar.setOnAction((ActionEvent event) -> {
            boolean valido=false;
            
            
            
            
            valido=true;
            if(valido==true){
                new LanzadorVentanas().AbrirFXML("escenaContactos", null, null, true, null, Modality.NONE);
                MainApp.stage.close();
            }
        });
    }   

    
    
}
