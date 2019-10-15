/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iridiscente.contactosGaroe.controladores.fxmls;

import com.iridiscente.contactosGaroe.MainApp;
import com.iridiscente.contactosGaroe.controladores.Animacion;
import com.iridiscente.contactosGaroe.controladores.Conexion;
import com.iridiscente.contactosGaroe.controladores.Consultas;
import com.iridiscente.contactosGaroe.controladores.Encriptacion;
import com.iridiscente.contactosGaroe.controladores.LanzadorVentanas;
import com.iridiscente.contactosGaroe.controladores.Sistema;
import com.iridiscente.contactosGaroe.modelos.Admitido;
import com.iridiscente.contactosGaroe.modelos.Persona;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class Login implements Initializable {

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
    private Hyperlink hyperlinkEmail, hyperLinkWeb;

    public void initialize(URL url, ResourceBundle rb) {
        Animacion.animaPanel(principal);
        hyperlinkEmail.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Sistema.getHostServices().showDocument("mailto:consultas@iridiscente.com");
            }
        });
        hyperLinkWeb.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Sistema.getHostServices().showDocument("http://www.iridiscente.com");
            }
        });

        btnEntrar.setOnAction((ActionEvent event) -> {
            //SI FALTA ALGÚN CAMPO POR RELLENAR
            String mensajeError = "no se puede conectar:" + "\n";
            if (pfClave.getText().equals("")) {
                txtError.setVisible(true);
                mensajeError += ("Falta que rellenar el campo de la clave, es obligatorio" + "\n");
            }
            if (tfUsuario.getText().equals("")) {
                mensajeError += "Falta que rellenar el campo del usuario, es obligatorio" + "\n";
            }
            //SI HAN HABIDO ERRORES RELLENANDO LOS CAMPOS
            if (!mensajeError.equals("no se puede conectar:" + "\n")) {
                txtError.setText(mensajeError);
                txtError.setVisible(true);
                Animacion.textoParpadeante(txtError);

            } //SI ESTÁN TODOS LOS CAMPOS RELLENOS--------------------------------------CAMPOS RELLENOS
            else {
                txtError.setVisible(false);
                //CREA UNA PERSONA Y CONFIGURA EL USUARIO
                Persona persona = new Persona();
                persona.setUsuario(tfUsuario.getText());
                //BUSCA LOS USUARIOS CON ESE NOMBRE EN LA BASE DE DATOS.
                Persona p = null;
                try {
                    p = Consultas.getUsuario(persona);
                } //SI SE PRODUCE ALGUN ERROR BUSCADO UN USUARIO CON ESE NOMBRE¡
                catch (Exception e) {
//                    if (Conexion.sessionFactory == null) {
//                        Optional<ButtonType> b = new Alert(Alert.AlertType.ERROR, "Ha sido imposible conectar con la base de datos remota, puede que este caida. " + "\n\n"
//                                + "- Para visualizar el fichero local pulse 'SI'." + "\n"
//                                + "- Para volver a intentar, pulse 'NO." + "\n\n\n" + "Si persiste el problema ponerse en contacto con el desarrolador de la aplicación en el enlace de debajo.", ButtonType.YES, ButtonType.NO).showAndWait();
//                        if (b.get() == ButtonType.YES) {
//                            abrirVentanaPrincipal();
//                        }
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
//                    }

                } finally {
//                    Conexion.closeSession();
                }
                //SI HA HABIDO CONEXIÓN CON LA BASE DE DATOS --------------------- *******************LOGIN EN REMOTO**************************
                if (Conexion.sessionFactory != null) {
                    //SI DUESPUES DE LA CONEXION CON LA BASE DE DATOS ENCONTRÓ UN USUARIO CON ESE NOMBRE--------------HAY NOMBRE DE USUARIO CORRECTO
                    if (p != null) {
                        try {
                            //SI EL PASSWORD ESTA CORRECTO-------------------------------------------------------------HAY USUARIO Y PASSWORD CORRECTO
                            if (Encriptacion.check(pfClave.getText(), p.getPassword())) {
                                 Admitido macValida =null;
                                List<Admitido> listaMac = (List<Admitido>) Consultas.listaAdmision();
                                if (listaMac != null && !listaMac.isEmpty()) {
                                    List<Admitido> listaMacValidaFiltrada = listaMac.stream().filter((t) -> {
                                        return t.getMac().equals(Sistema.getMacAddress());
                                    }).collect(Collectors.toList());
                                    
                                    if(listaMacValidaFiltrada!=null&&!listaMacValidaFiltrada.isEmpty()){
                                       macValida =listaMacValidaFiltrada.get(0);
                                    }
                                    //SI EL USUARIO ESTA EN EL ORDENADOR ADECUADO--------------------------------------HAY USUARIO, PASSWORD Y UBICACION CORRECTA.
                                    if (macValida != null) {
                                        Sistema.setUsuarioSistema(p);
                                        Sistema.listaMac = new ArrayList<Admitido>(listaMac);
                                        Sistema.listaUsuarios = new ArrayList<Persona>(Consultas.getTodosLosUsuarios());
                                        abrirVentanaPrincipal("Contactos Garoé by A. Jesús");
                                    } //SI EL USUARIO NO ESTA EN LA UBICACION CORRECTA
                                    else {

                                        Optional<ButtonType> b = new Alert(Alert.AlertType.ERROR, "No puedes acceder desde este ordenador porque no tienes permiso.\n\n- Se procederá a cerrar la aplicación.", ButtonType.OK).showAndWait();
                                        if (b.get().equals(ButtonType.OK)) {
                                            System.exit(0);
                                        }
                                    }

                                }

                            }//SI EL PASSWORD ESTA MAL
                            else {
                                txtError.setText("Contraseña no valida.");
                                txtError.setVisible(true);
                                Animacion.textoParpadeante(txtError);
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }//SI DESPUES DE LA CONEXIÓN CON LA BASE DE DATOS "EXITOSA" NO ENCONTRÓ UN USUARIO CON ESE NOMBRE
                    else {
                        txtError.setText("Usuario no valido.");
                        txtError.setVisible(true);
                        Animacion.textoParpadeante(txtError);
                    }

                } //SI DESPUÉS DE LA CONEXIÓN CON LA BASE DE DATOS  ES "FRACASADA"------------************LOGIN EN LOCAL************
                else {
                    //LEE DEL ARCHIVO LOS USUARIOS GUARDADOS
                    Serializable serializable = (Serializable) Encriptacion.AESObjectDedcoder("users.dat");
                    List<Persona> listaUsuarios = null;
                    if (serializable != null) {
                        listaUsuarios = (List<Persona>) serializable;
                    }
                    //SI ENCUENTRA ALGO EN EL ARCHIVO DE USUARIOS------------------------------------------------ENCONTRADO LISTAS DE USUARIOS
                    if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                        Sistema.listaUsuarios = listaUsuarios;
                        List<Persona> ListaUsuarioValido = listaUsuarios.stream().filter((t) -> {
                            return t.getUsuario().equals(tfUsuario.getText());
                        }).collect(Collectors.toList());
                        Persona usuarioValido = null;
                        //SI EL USUARIO ES VALIDO----------------------------------------------------------------USUARIO EN LOCAL VALIDO
                        if (ListaUsuarioValido != null && !ListaUsuarioValido.isEmpty()) {
                            usuarioValido = ListaUsuarioValido.get(0);
                        }

                        if (usuarioValido != null) {
                            try {
                                //SI LA CLAVE DEL USUARIO LEIDO DEL FICHERO ES CORRECTA---------------------------PASSWORD EN LOCAL VALIDO
                                if (Encriptacion.check(pfClave.getText(), usuarioValido.getPassword())) {
                                    //SI LA MAC ESTA EN LA LISTA DEL FICHERO LOCAL
                                    List<Admitido> admitidos = new ArrayList<>();
                                    try {
                                        admitidos = (List<Admitido>) Encriptacion.AESObjectDedcoder("admision.dat");
                                        //SI LA MAC ESTA EN EL FICHERO DE ADMISION---------------------------------UBICACION DE ORDENADOR VALIDO EN LOCAL
                                        if (admitidos.stream().filter((t) -> {
                                            return t.getMac().equals(Sistema.getMacAddress());
                                        }).findFirst().isPresent()) {
                                            Optional<ButtonType> b = new Alert(Alert.AlertType.ERROR, "Ha sido imposible conectar con la base de datos remota, puede que este caida. No obstante se ha podido loguear con el fichero local." + "\n\n\n"
                                                    + "- Para visualizar el fichero local pulse 'SI'. Tenga en cuenta que se bloqueará los modificadores de contactos y las acciones sobre la base de datos (crear, actualizar, borrar y refrrescar)." + "\n\n"
                                                    + "- Para volver a intentar, pulse 'NO." + "\n\n" + "Si persiste el problema ponerse en contacto con el desarrolador de la aplicación en el enlace de debajo que aparece en la pantalla de \"Log-in\"." + "\n", ButtonType.YES, ButtonType.NO).showAndWait();
                                            if (b.get() == ButtonType.YES) {
                                                Sistema.listaMac = admitidos;
                                                Sistema.setUsuarioSistema(usuarioValido);
                                                abrirVentanaPrincipal("Contactos Garoé by A. Jesús --> SOLO LECTURA");
                                            }

                                        }//SI LA MAC NO ESTÁ EN EL FICHERO LOCAL-----------------------------------------UBICACION INCORRECTA EN LOCAL
                                        else {
                                            Optional<ButtonType> b = new Alert(Alert.AlertType.ERROR, "No puedes acceder desde este ordenador porque no tienes permiso.\n\n- Se procederá a cerrar la aplicación.", ButtonType.OK).showAndWait();
                                            if (b.get().equals(ButtonType.OK)) {
                                                System.exit(0);
                                            }
                                        }
                                    } catch (Exception e) {
                                        new Alert(Alert.AlertType.ERROR, "no se ha podido conectar con la base datos local mientras obtenia permisos", ButtonType.OK).show();
                                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
                                    } finally {
                                        Conexion.closeSession();
                                    }
                                } //SI LA CONTRASEÑA DEL ARCHIVO LEIDO EN EL LOCAL NO ES VALIDO
                                else {
                                    txtError.setText("contraseña no valida.");
                                    txtError.setVisible(true);
                                    Animacion.textoParpadeante(txtError);
                                }
                            } catch (Exception ex) {
                                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } //SI EL USUARIO DEL ARCHIVO LEIDO EN EL FICHERO LOCAL NO ES VALIDO
                        else {
                            txtError.setText("Usuario no valido.");
                            txtError.setVisible(true);
                            Animacion.textoParpadeante(txtError);
                        }
                    }
                }
            }
        }
        );
    }

    private void abrirVentanaPrincipal(String titulo) {
        new LanzadorVentanas().AbrirFXML("escenaContactos", titulo, null, true, null, Modality.NONE, true);
        MainApp.stage.close();
    }

}
