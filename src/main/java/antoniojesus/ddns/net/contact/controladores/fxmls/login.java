/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antoniojesus.ddns.net.contact.controladores.fxmls;

import antoniojesus.ddns.net.contact.MainApp;
import antoniojesus.ddns.net.contact.controladores.Animacion;
import antoniojesus.ddns.net.contact.controladores.LanzadorVentanas;
import antoniojesus.ddns.net.contact.controladores.Sistema;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;

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
    @FXML
    private Hyperlink hyperlinkEmail;

    public void initialize(URL url, ResourceBundle rb) {
        Animacion.animaPanel(principal);
        hyperlinkEmail.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Sistema.getHostServices().showDocument("http://mailto:ajpd1985@gmail.com");
            }
        });
        
        btnEntrar.setOnAction((ActionEvent event) -> {
            boolean valido = false;
            String mensajeError = "no se puede conectar:" + "\n";
            if (pfClave.getText().equals("")) {
                txtError.setVisible(true);
                mensajeError += ("Falta que rellenar el campo de la clave, es obligatorio" + "\n");
            }
            if (tfUsuario.getText().equals("")) {
                mensajeError += "Falta que rellenar el campo del usuario, es obligatorio" + "\n";
            }

            if (!mensajeError.equals("no se puede conectar:" + "\n")) {
                txtError.setText(mensajeError);
                txtError.setVisible(true);
                Animacion.textoParpadeante(txtError);
            } else {
                txtError.setVisible(false);
                valido = true;
                if (valido == true) {
                    new LanzadorVentanas().AbrirFXML("escenaContactos", null, null, true, null, Modality.NONE);
                    MainApp.stage.close();
                }
            }
        });
    }

}
