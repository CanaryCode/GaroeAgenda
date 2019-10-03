package antoniojesus.ddns.net.contact.controladores.fxmls;

import antoniojesus.ddns.net.contact.modelos.Contacto;
import antoniojesus.ddns.net.contact.controladores.Util;
import antoniojesus.ddns.net.contact.controladores.Consultas;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


public class Contactos implements Initializable {

    String textoBuscador = "";
    FilteredList<Contacto> listraFiltrada;
    
    @FXML
    private ComboBox<?> cbbBAmbito,cbbAmbito;
    @FXML
    private TableColumn<Contacto, String> tbcolNombre, tbcolTelefono;

    @FXML
    private TextField tfBNombre, tfNombre, tfPrefijo, tfTelefono, tfEmail,tfBTelefono;

    @FXML
    private ToggleGroup fijo;

    @FXML
    private ImageView imgvMovil, imgvFax;

    @FXML
    private TextArea taComentario;
    @FXML
    private Text etiError;
    @FXML
    private RadioButton rdFax, rdFijo, rdMovil;

    @FXML
    private Button btnCrear, btnActualizar, btnBorrar,btnBLimpiar,btnRefrescar;
    @FXML
    private TableView<Contacto> tabla;
 

   public static ObservableList<Contacto> lista = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        etiError.setVisible(
                false);
        imgvFax.setVisible(
                false);
        tbcolNombre.setCellValueFactory(
                new PropertyValueFactory<Contacto, String>("nombre"));
        tbcolTelefono.setCellValueFactory(
                new PropertyValueFactory<Contacto, String>("telefono"));

       lista=Util.empezar(etiError,btnActualizar,btnBorrar,btnCrear);

        tabla.getItems().setAll(lista);
        tabla.getSortOrder().add(tbcolNombre);
        tabla.getSortOrder().add(tbcolTelefono);
        tfNombre.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    if (newValue.length() > 100) {
                        tfNombre.setText(oldValue);
                    } else {
                        tfNombre.setText(newValue.toUpperCase());
                    }
                }
            }
        }
        );
        tfEmail.textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (newValue != null) {
                            if (newValue.length() > 100) {
                                tfEmail.setText(oldValue);
                            } else {
                                tfEmail.setText(newValue.toUpperCase());
                            }
                        }
                    }
                }
                );
        taComentario.textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue
                    ) {
                        if (newValue != null) {
                            if (newValue.length() > 500) {
                                taComentario.setText(oldValue);
                            } else {
                                taComentario.setText(newValue.toUpperCase());
                            }
                        }
                    }
                }
                );
        tfPrefijo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue
            ) {
                if (newValue != null && newValue.length() > 0) {
                    if (newValue.length() > 5 || !Character.isDigit(newValue.charAt(newValue.length() - 1))) {
                        if (oldValue != null) {
                            tfPrefijo.setText(oldValue);
                        } else {
                            tfPrefijo.setText("");
                        }
                    }
                }
            }
        }
        );
        tfTelefono.textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (newValue != null && newValue.length() > 0) {
                            if (newValue.length() > 15 || !Character.isDigit(newValue.charAt(newValue.length() - 1))) {
                                if (oldValue != null) {
                                    tfTelefono.setText(oldValue);
                                } else {
                                    tfTelefono.setText("");
                                }
                            }
                        }

                    }
                }
                );
        rdFijo.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event
            ) {
                imgvMovil.setImage(new Image("/imagenes/fijo.png"));
                rdFax.setSelected(false);
                rdFax.setDisable(false);
            }
        }
        );
        rdMovil.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event
            ) {
                imgvMovil.setImage(new Image("/imagenes/movil.png"));
                imgvFax.setVisible(false);
                rdFax.setSelected(false);
                rdFax.setDisable(true);
            }
        }
        );
        rdFax.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event
            ) {

                if (rdFax.isSelected()) {
                    imgvFax.setVisible(true);
                } else {
                    imgvFax.setVisible(false);
                }
            }
        }
        );

        tfBNombre.textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue,
                            String newValue
                    ) {
                        textoBuscador = newValue;
                        listraFiltrada = lista.filtered(new Predicate<Contacto>() {
                            @Override
                            public boolean test(Contacto t) {
                                return t.getNombre().toUpperCase().contains(textoBuscador.toUpperCase());
                            }
                        });
                        tabla.getItems().setAll(listraFiltrada);
                    }
                });
        tabla.getSelectionModel()
                .selectedItemProperty().addListener(new ChangeListener<Contacto>() {
                    @Override
                    public void changed(ObservableValue<? extends Contacto> observable, Contacto oldValue,
                            Contacto newValue
                    ) {
                        if (newValue != null) {
                            String comentario = "";
                            String email = "";
                            String nombre = "";
                            String prefijo = "";
                            String telefono = "";
                            if (newValue.getComentario() != null) {
                                comentario = newValue.getComentario();
                            }
                            if (newValue.getEmail() != null) {
                                email = newValue.getEmail();
                            }
                            if (newValue.getNombre() != null) {
                                nombre = newValue.getNombre();
                            }
                            if (newValue.getPrefijo() != null) {
                                prefijo = newValue.getPrefijo().toString();
                            }
                            if (newValue.getTelefono() != null) {
                                telefono = newValue.getTelefono().toString();
                            }
                            taComentario.setText(comentario);
                            tfEmail.setText(email);
                            tfNombre.setText(nombre);
                            tfPrefijo.setText(prefijo);
                            tfTelefono.setText(telefono);
                            if (newValue.getFax() != null) {
                                if (newValue.getFax()) {
                                    rdFax.setSelected(true);
                                    imgvFax.setVisible(true);
                                    rdFax.setDisable(false);
                                    rdFijo.setSelected(true);
                                    imgvMovil.setImage(new Image("/imagenes/fijo.png"));
                                } else {
                                    rdFax.setSelected(false);
                                    imgvFax.setVisible(false);
                                }
                            }
                            if (newValue.getFijo() != null) {
                                if (newValue.getFijo()) {
                                    rdFijo.setSelected(true);
                                    imgvMovil.setImage(new Image("/imagenes/fijo.png"));
                                    rdMovil.setSelected(false);
                                } else {
                                    rdFijo.setSelected(false);
                                    imgvMovil.setImage(new Image("/imagenes/movil.png"));
                                    rdMovil.setSelected(true);
                                    rdFax.setDisable(true);
                                    imgvFax.setVisible(false);
                                }
                            }
                        }
                    }
                });
        btnActualizar.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Contacto contacto = new Contacto();
                String nombre = "";
                String email = "";
                String telefono = "";
                String prefijo = "";
                String comentario = "";
                Boolean fax = Boolean.FALSE;
                Boolean fijo = Boolean.FALSE;
                if (tfEmail.getText() != null) {
                    email = tfEmail.getText();
                }
                if (tfNombre.getText() != null) {
                    nombre = tfNombre.getText();
                }
                if (taComentario.getText() != null) {
                    comentario = taComentario.getText();
                }
                if (tfTelefono.getText() != null) {
                    telefono = tfTelefono.getText();
                }
                if (tfPrefijo.getText() != null) {
                    prefijo = tfPrefijo.getText();
                }
                if (rdFax.isSelected()) {
                    fax = Boolean.TRUE;
                }
                if (rdFijo.isSelected()) {
                    fijo = Boolean.TRUE;
                }
                try {
                    contacto.setPrefijo(new Integer(prefijo));
                } catch (Exception e) {

                }
                contacto.setComentario(comentario);
                contacto.setNombre(nombre);
                contacto.setEmail(email);
                contacto.setTelefono(telefono);
                contacto.setFax(fax);
                contacto.setFijo(fijo);
                if (tabla.getItems().contains(contacto)) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Estas a punto de actualizar un contacto\n"
                            + "de la lista. ¿Estas de acuerdo?", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> bt = a.showAndWait();

                    if (bt.get() == ButtonType.YES) {
                        Consultas.actualizarContacto(contacto);
                        lista = Consultas.getContactos();
                        tabla.getItems().setAll(lista);
                        String s = tfBNombre.getText();
                        tfBNombre.setText("");
                        tfBNombre.setText(s);
                    }

                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "El contacto no existe, por lo tanto, no se puede modificar", ButtonType.CLOSE);
                    a.show();
                }
            }
        });

        btnCrear.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Contacto contacto = new Contacto();
                String nombre = "";
                String email = "";
                String telefono = "";
                String prefijo = "";
                String comentario = "";
                Boolean fax = Boolean.FALSE;
                Boolean fijo = Boolean.FALSE;
                if (tfEmail.getText() != null) {
                    email = tfEmail.getText();
                }
                if (tfNombre.getText() != null) {
                    nombre = tfNombre.getText();
                }
                if (taComentario.getText() != null) {
                    comentario = taComentario.getText();
                }
                if (tfTelefono.getText() != null) {
                    telefono = tfTelefono.getText();
                }
                if (tfPrefijo.getText() != null) {
                    prefijo = tfPrefijo.getText();
                }
                if (rdFax.isSelected()) {
                    fax = Boolean.TRUE;
                }
                if (rdFijo.isSelected()) {
                    fijo = Boolean.TRUE;
                }
                try {
                    contacto.setPrefijo(new Integer(prefijo));
                } catch (Exception e) {

                }
                contacto.setComentario(comentario);
                contacto.setNombre(nombre);
                contacto.setEmail(email);
                contacto.setTelefono(telefono);
                contacto.setFax(fax);
                contacto.setFijo(fijo);

                if (!lista.contains(contacto)) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Estas a punto de crear de añadir un nuevo contacto\n"
                            + "a la lista. ¿Estas de acuerdo?", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> bt = a.showAndWait();

                    if (bt.get() == ButtonType.YES) {
                        Consultas.crearContacto(contacto);
                    }

                    lista.setAll(Consultas.getContactos());
                    tabla.getItems().setAll(lista);
                    String s = tfBNombre.getText();
                    tfBNombre.setText("");
                    tfBNombre.setText(s);
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Este contacto ya existe,por lo tanto, no se puede crear nuevo.", ButtonType.CLOSE);
                    a.show();
                }
            }
        });
        btnBorrar.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Contacto contacto = new Contacto();
                String telefono = "";
                String prefijo = "";
                if (tfTelefono.getText() != null) {
                    telefono = tfTelefono.getText();
                }
                if (tfPrefijo.getText() != null) {
                    prefijo = tfPrefijo.getText();
                }
                try {
                    contacto.setPrefijo(new Integer(prefijo));
                } catch (Exception e) {

                }
                contacto.setTelefono(telefono);
                if (lista.contains(contacto)) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Estas a punto de eliminar a un contacto\n"
                            + "de la lista. ¿Estas de acuerdo?", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> bt = a.showAndWait();

                    if (bt.get() == ButtonType.YES) {
                        Consultas.eliminarContacto(contacto);
                        lista = Consultas.getContactos();
                        tabla.getItems().setAll(lista);
                        String s = tfBNombre.getText();
                        tfBNombre.setText("");
                        tfBNombre.setText(s);

                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Este contacto no existe,por lo tanto, no se puede eliminar.", ButtonType.CLOSE);
                    a.show();
                }
            }
        });

    }

}