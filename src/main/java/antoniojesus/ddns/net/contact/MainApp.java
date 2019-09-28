package antoniojesus.ddns.net.contact;

import antoniojesus.ddns.net.contact.controladores.Util;
import antoniojesus.ddns.net.contact.modelos.Contacto;
import antoniojesus.ddns.net.contact.controladores.fxmls.Contactos;
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

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/escenaContactos.fxml"));
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
                Util.terminar(new ArrayList<Contacto>(Contactos.lista));
            }
        });
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
