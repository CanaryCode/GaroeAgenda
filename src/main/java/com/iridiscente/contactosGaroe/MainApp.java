package com.iridiscente.contactosGaroe;

import  com.iridiscente.contactosGaroe.controladores.Conexion;
import  com.iridiscente.contactosGaroe.controladores.Sistema;
import  com.iridiscente.contactosGaroe.controladores.Util;
import com.iridiscente.contactosGaroe.modelos.Contacto;
import  com.iridiscente.contactosGaroe.controladores.fxmls.Contactos;
import  com.iridiscente.contactosGaroe.modelos.Admitido;
import  com.iridiscente.contactosGaroe.modelos.Persona;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        Sistema.setHostServices(getHostServices());
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        List<Contacto> l;
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("Contactos Garoé by A. Jesús");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image("/imagenes/guia_telefonica.png"));
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (Conexion.sessionFactory != null) {
                    if(Contactos.lista!=null&&!Contactos.lista.isEmpty()&&
                            Sistema.listaMac!=null&&!Sistema.listaMac.isEmpty()&&
                            Sistema.listaUsuarios!=null&&!Sistema.listaUsuarios.isEmpty())
                    Util.terminar(new ArrayList<Contacto>(Contactos.lista), new ArrayList<Admitido>(Sistema.listaMac),
                            new ArrayList<Persona>(Sistema.listaUsuarios));
                }

            }
        }
        );
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
