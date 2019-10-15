package com.iridiscente.contactosGaroe.controladores.fxmls;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.iridiscente.contactosGaroe.controladores.Conexion;
import com.iridiscente.contactosGaroe.controladores.Consultas;
import com.iridiscente.contactosGaroe.controladores.Encriptacion;
import com.iridiscente.contactosGaroe.controladores.Sistema;
import com.iridiscente.contactosGaroe.modelos.Persona;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author A. Jesús <Antonio Jesús Pérez Delgado ajpd1985@gmail.com>
 */
public class CambioPassword implements Initializable {

    @FXML
    private PasswordField pfAntigua, pfNueva, pfRepetida;

    @FXML
    private Button cambiar;
    @FXML
    private Text textoError;
    Persona p = null;

    @Override

    public void initialize(URL url, ResourceBundle rb) {

        pfNueva.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (newValue.length() > 50) {
                    pfNueva.setText(oldValue);
                    textoError.setVisible(true);
                    textoError.setStyle(" -fx-text-fill: red");
                    textoError.setText("las contraseñas no pueden tener mas de 50 caracteres");
                }
            }
        });
        pfAntigua.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (newValue.length() > 50) {
                    pfAntigua.setText(oldValue);
                    textoError.setVisible(true);
                    textoError.setStyle(" -fx-text-fill: red");
                    textoError.setText("las contraseñas no pueden tener mas de 50 caracteres");
                }
            }
        });

        pfRepetida.textProperty().addListener((observable, oldValue, newValue) -> {
            mostrarMensaje();
        });
        pfNueva.textProperty().addListener((observable, oldValue, newValue) -> {
            mostrarMensaje();
        });

        cambiar.setOnAction((event) -> {
            String mensaje = "ha habido errores:" + "\n" + "\n";
            if (pfAntigua.getText().equals("")) {
                mensaje += "- Debes de rellenar el campo de la antigua contraseña" + "\n";
            }
            if (pfNueva.getText().equals("")) {
                mensaje += "- Debes de rellenar el campo de la nueva contraseña" + "\n";
            }
            if (pfNueva.getText().equals("")) {
                mensaje += "- Debes de rellenar el campo de la repetición de la nueva contraseña" + "\n";
            }
            if (mensaje.equals("ha habido errores:" + "\n" + "\n")) {
                if (pfNueva.getText().equals(pfRepetida.getText())) {
                    try {

                        if (Encriptacion.check(pfAntigua.getText(), Sistema.getUsuarioSistema().getPassword())) {
                            Transaction transaction=null;
                            try {
                                Sistema.getUsuarioSistema().setPassword(Encriptacion.getSaltedHash(pfNueva.getText()));
                                transaction=Consultas.setPasswordUsuario(Sistema.getUsuarioSistema());

                                Sistema.getVentanaCustom(Contactos.nombreVentanaPassword).close();
                                Sistema.getListaVentanas().remove(Sistema.getVentanaCustom(Contactos.nombreVentanaPassword));
                                new Alert(Alert.AlertType.INFORMATION, "Se ha realizado el cambio de contraseña", ButtonType.OK).show();
                            } catch (Exception e) {
                                if(transaction!=null){
                                    transaction.rollback();
                                }
                                new Alert(Alert.AlertType.ERROR, "no se ha podido realizar la actualización de la contraseña", ButtonType.OK);
                                Logger.getLogger(CambioPassword.class.getName()).log(Level.SEVERE, null, e);
                            } finally {
                                Conexion.closeSession();
                            }
                        } else {
                            new Alert(Alert.AlertType.ERROR, "La contraseña antigua no coincide", ButtonType.CLOSE).show();
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(CambioPassword.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Tienen que coincidir el campo de la nueva contraseña y el de la repetición de la misma", ButtonType.OK).show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, mensaje, ButtonType.OK).show();
            }
        });
    }

    private void mostrarMensaje() {
        if (!pfNueva.getText().equals("") && !pfNueva.getText().equals("")) {
            if (!pfNueva.getText().equals(pfRepetida.getText())) {
                textoError.setVisible(true);
                textoError.setStyle(" -fx-fill: red;");
                textoError.setText("Las contraseñas 'NO' coinciden.");
            } else {
                textoError.setVisible(true);
                textoError.setStyle("-fx-fill: green;");
                textoError.setText("Las contraseñas coinciden.");
            }
        } else {
            textoError.setVisible(false);
        }

    }
}
