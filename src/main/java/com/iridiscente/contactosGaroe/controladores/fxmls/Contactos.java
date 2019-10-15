package com.iridiscente.contactosGaroe.controladores.fxmls;

import com.iridiscente.contactosGaroe.controladores.Conexion;
import com.iridiscente.contactosGaroe.modelos.Contacto;
import com.iridiscente.contactosGaroe.controladores.Util;
import com.iridiscente.contactosGaroe.controladores.Consultas;
import com.iridiscente.contactosGaroe.controladores.LanzadorVentanas;
import com.iridiscente.contactosGaroe.controladores.Sistema;
import com.iridiscente.contactosGaroe.controladores.Validador;
import com.iridiscente.contactosGaroe.modelos.Persona;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.util.Duration;
import org.hibernate.Transaction;

public class Contactos implements Initializable {

    Contacto contactoEnVista = null;

    String textoBuscador = "";
    public static final String nombreVentanaPassword = "cambioPassword";
    FilteredList<Contacto> listraFiltrada;

    @FXML
    private ComboBox<String> cbbBAmbito, cbbAmbito;
    @FXML
    private TableColumn<Contacto, String> tbcolNombre, tbcolTelefono;

    @FXML
    private TextField tfBNombre, tfNombre, tfPrefijo, tfTelefono, tfEmail, tfBTelefono;

    @FXML
    private ImageView imgvMovil, imgvFax, imgvVFijo;

    @FXML
    private TextArea taComentario;
    @FXML
    private Text etiError;
    @FXML
    private RadioButton rdFax, rdFijo, rdMovil;

    @FXML
    private Button btnCrear, btnActualizar, btnBorrar, btnBLimpiar, btnRefrescar, btnLimpiar;
    @FXML
    private TableView<Contacto> tabla;
    @FXML
    private MenuItem mIPassword;
    @FXML
    private MenuBar bUsuario;
    @FXML
    private AnchorPane principal;
    @FXML
    private ToggleGroup tipo;

    public static ObservableList<Contacto> lista = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        etiError.setVisible(
                false);
        imgvFax.setVisible(
                false);
        imgvMovil.setVisible(
                false);
        imgvVFijo.setVisible(
                false);
        tbcolNombre.setCellValueFactory(
                new PropertyValueFactory<Contacto, String>("nombre"));
        tbcolTelefono.setCellValueFactory(
                new PropertyValueFactory<Contacto, String>("telefono"));
        ObservableList<String> listaCombo = FXCollections.observableArrayList();
        Persona p = Sistema.getUsuarioSistema();
//
//        IdleMonitor idleMonitor = new IdleMonitor(Duration.seconds(30),
//                () -> principal.getChildren().setAll(startUI), true);
//        idleMonitor.register(principal.getScene(), Event.ANY);

        if (Conexion.sessionFactory != null) {
            Consultas.entrar();
        }
        if (Sistema.getUsuarioSistema().getDepartamento().equals("dirección") || Sistema.getUsuarioSistema().getDepartamento().equals("administrador")) {
            listaCombo.addAll("público", "clientes");

            List<String> listaDepartamentos = new ArrayList<String>();
            Sistema.listaUsuarios.stream().forEach((t) -> {
                if (!listaDepartamentos.contains(t.getDepartamento())) {
                    listaDepartamentos.add(t.getDepartamento());
                }
            });
            listaCombo.addAll(listaDepartamentos);
        } else if (Sistema.getUsuarioSistema().getDepartamento().equals("recepción") || Sistema.getUsuarioSistema().getDepartamento().equals("calidad")) {
            listaCombo.addAll("público", p.getDepartamento(), "clientes");
        } else {
            listaCombo.addAll("público", p.getDepartamento());
        }
//        } else {
//            listaCombo.add("público");
//        }

        cbbAmbito.setItems(listaCombo);
        cbbAmbito.getSelectionModel().select(0);
        cbbBAmbito.setItems(listaCombo);
        cbbBAmbito.getSelectionModel().select(0);

        lista = Util.empezar(etiError, btnActualizar, btnBorrar, btnCrear, btnRefrescar,
                btnLimpiar, rdFax, rdFijo, rdMovil, tfNombre, tfTelefono, tfEmail, tfPrefijo,
                taComentario, cbbAmbito, bUsuario);

        tabla.getItems().setAll(lista);
        filtrarTabla();

        tipo.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (tipo.getSelectedToggle() != null) {
                    if (tipo.getSelectedToggle().equals(rdFijo)) {
                        imgvVFijo.setVisible(true);
                        imgvMovil.setVisible(false);
                        imgvFax.setVisible(false);
                    }
                    if (tipo.getSelectedToggle().equals(rdMovil)) {
                        imgvVFijo.setVisible(false);
                        imgvMovil.setVisible(true);
                        imgvFax.setVisible(false);
                    }
                    if (tipo.getSelectedToggle().equals(rdFax)) {
                        imgvVFijo.setVisible(false);
                        imgvMovil.setVisible(false);
                        imgvFax.setVisible(true);
                    }
                }
            }
        }
        );

        tfNombre.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    if (newValue.length() > 100) {
                        tfNombre.setText(tfNombre.getText().substring(0, 100));
                    } else {
                        tfNombre.setText(tfNombre.getText().toUpperCase());
                    }
                }
                setContactoEnVista();
            }
        }
        );
        tfEmail.textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (newValue != null) {
                            if (newValue.length() > 100) {
                                tfEmail.setText(taComentario.getText().substring(0, 100));
                            } else {
                                tfEmail.setText(tfEmail.getText().toUpperCase());
                            }
                        }
                        setContactoEnVista();
                    }
                }
                );
        taComentario.textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (newValue != null) {
                            if (newValue.length() > 500) {
                                taComentario.setText(taComentario.getText().substring(0, 500));
                            } else {
                                taComentario.setText(taComentario.getText().toUpperCase());
                            }
                        }
                        setContactoEnVista();
                    }
                });
        tfPrefijo.textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (oldValue.equals("") || oldValue == null) {
                            if (newValue.equals("0")) {
                                tfPrefijo.setText("");
                            }
                        }
                        if (newValue != null) {
                            tfPrefijo.setText(newValue.replaceAll("\\D", ""));

                            if (tfPrefijo.getText().length() > 5) {
                                tfPrefijo.setText(tfPrefijo.getText().substring(0, 5));
                            }

                        }
                        setContactoEnVista();
                    }
                }
                );
        tfTelefono.textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue,
                            String newValue) {
                        if (newValue != null) {
                            tfTelefono.setText(newValue.replaceAll("\\D", ""));

                            if (tfTelefono.getText().length() > 15) {
                                tfTelefono.setText(tfTelefono.getText().substring(0, 15));
                            }
                        }
                        setContactoEnVista();
                    }
                }
                );
        tfBNombre.textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue,
                            String newValue) {
                        if (newValue != null) {
                            tfBNombre.setText(newValue.toUpperCase());
                            filtrarTabla();
                        }
                        setContactoEnVista();
                    }
                }
                );

        cbbAmbito.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            setContactoEnVista();
        });

        tfBTelefono.textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                        if (newValue != null) {
                            tfBTelefono.setText(newValue.replaceAll("\\D", ""));
                            if (tfBTelefono.getText().length() > 15) {
                                tfBTelefono.setText(tfBTelefono.getText().substring(0, 15));
                            }

                            filtrarTabla();
                        }
                    }
                }
                );
        cbbBAmbito.getSelectionModel()
                .selectedItemProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        filtrarTabla();
                    }
                }
                );

        tabla.getSelectionModel()
                .selectedItemProperty().addListener(new ChangeListener<Contacto>() {
                    @Override
                    public void changed(ObservableValue<? extends Contacto> observable, Contacto oldValue,
                            Contacto newValue
                    ) {
                        if (newValue != null) {
                            contactoEnVista = newValue;
                            setFormulario(contactoEnVista);
                        }
                    }
                }
                );
        btnActualizar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event
            ) {
//       
                String mensajeError = "";
                if (contactoEnVista.getTelefono() == null || contactoEnVista.getTelefono().equals("")) {
                    mensajeError += "- El campo 'teléfono' es Obligatorio debes rellenarlo" + ("\n");
                }
                if (contactoEnVista.getNombre() == null || contactoEnVista.getNombre().equals("")) {
                    mensajeError += "- El campo 'nombre' es Obligatorio debes rellenarlo" + ("\n");
                }
                if (contactoEnVista.getPrefijo() == null || contactoEnVista.getPrefijo().equals("")) {
                    mensajeError += "- El campo 'prefijo' es Obligatorio debes rellenarlo" + ("\n");
                }
                if (contactoEnVista.getEmail() != null && !contactoEnVista.getEmail().equals("")) {
                    if (!Validador.esEmailValido(contactoEnVista.getEmail())) {
                        mensajeError += "El email está mal formado o no es valido." + ("\n");
                    }
                }
                Contacto c = null;
                try {
                    c = null;
                    List<Contacto> listaContactos = null;
                    listaContactos = tabla.getItems().stream().filter((t) -> {
                        return t.getTelefono().equals(contactoEnVista.getTelefono()) && t.getPrefijo().equals(contactoEnVista.getPrefijo()) && t.getAmbito().equals(contactoEnVista.getAmbito());
                    }).collect(Collectors.toList());

                    if (!listaContactos.isEmpty()) {
                        c = listaContactos.get(0);
                        if (c.getComentario() == null) {
                            c.setComentario("");
                        }
                        if (c.getEmail() == null) {
                            c.setEmail("");
                        }
                    }

                } catch (Exception e) {
                    Logger.getLogger(Contactos.class.getName()).log(Level.SEVERE, null, e);
                }
                if (c == null) {
                    mensajeError += "- Este número de teléfono no esta en la base de datos, por lo tanto, no se puede actualizar";
                } else {
                    if (contactoEnVista.getAmbito().equals(c.getAmbito())
                            && contactoEnVista.getComentario().equals(c.getComentario())
                            && contactoEnVista.getEmail().equals(c.getEmail())
                            && contactoEnVista.getNombre().equals(c.getNombre())
                            && contactoEnVista.getPrefijo().equals(c.getPrefijo())
                            && contactoEnVista.getTelefono().equals(c.getTelefono())
                            && contactoEnVista.getFax().equals(c.getFax())
                            && contactoEnVista.getFijo().equals(c.getFijo())) {
                        mensajeError += "No has modificado nada, por lo tanto, no hace falta actualizar" + "\n";
                    }
                }

                if (mensajeError.equals("")) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Estas a punto de actualizar un contacto "
                            + "de la lista. ¿Estas de acuerdo?", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> bt = a.showAndWait();

                    if (bt.get() == ButtonType.YES) {
                        Transaction transaction = null;
                        try {
                            transaction = Consultas.actualizarContacto(contactoEnVista);
                            lista = Consultas.getContactos();
                            tabla.getItems().setAll(lista);
                            new Alert(Alert.AlertType.INFORMATION, "El contacto se ha actualizado correctamente", ButtonType.OK).show();
                            filtrarTabla();
                        } catch (Exception e) {
                            Consultas.rollBack(transaction);
                            new Alert(Alert.AlertType.ERROR, "Ha ocurrido un error y no se ha podido actualizar el contacto.", ButtonType.OK).show();
                            Logger.getLogger(Contactos.class.getName()).log(Level.SEVERE, null, e);
                        } finally {
                            Conexion.closeSession();
                        }

                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Se han detectado errores:" + "\n\n" + mensajeError, ButtonType.CLOSE).show();
                }

            }
        }
        );

        btnCrear.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event
            ) {

                String mensajeError = "";
                if (contactoEnVista.getTelefono() == null || contactoEnVista.getTelefono().equals("")) {
                    mensajeError += "- El campo 'teléfono' es Obligatorio debes rellenarlo" + ("\n");
                }
                if (contactoEnVista.getNombre() == null || contactoEnVista.getNombre().equals("")) {
                    mensajeError += "- El campo 'nombre' es Obligatorio debes rellenarlo" + ("\n");
                }
                if (contactoEnVista.getPrefijo() == null || contactoEnVista.getPrefijo().equals("")) {
                    mensajeError += "- El campo 'prefijo' es Obligatorio debes rellenarlo" + ("\n");
                }
                if (contactoEnVista.getEmail() != null && !contactoEnVista.getEmail().equals("")) {
                    if (!Validador.esEmailValido(contactoEnVista.getEmail())) {
                        mensajeError += "- El email está mal formado o no es valido." + ("\n");
                    }
                }

                if (tabla.getItems().contains(contactoEnVista)) {
                    mensajeError += "- Este número de teléfono, con este prefijo y este ámbito,\n"+" ya esta en la base de datos";

                }
                if (mensajeError.equals("")) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Estas a punto de crear de añadir un nuevo contacto\n"
                            + "a la lista. ¿Estas de acuerdo?", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> bt = a.showAndWait();

                    if (bt.get() == ButtonType.YES) {
                        Transaction transaction = null;
                        try {
                            transaction = Consultas.crearContacto(contactoEnVista);
                            lista = Consultas.getContactos();
                            tabla.getItems().setAll(lista);
                            tabla.getItems().sort(new Comparator<Contacto>() {
                                @Override
                                public int compare(Contacto o1, Contacto o2) {
                                    return o1.getNombre().compareTo(o2.getNombre());
                                }
                            });
                            filtrarTabla();
                            new Alert(Alert.AlertType.INFORMATION, "Se ha podido crear el contacto correctamente.", ButtonType.OK).show();

                        } catch (Exception e) {
                            Consultas.rollBack(transaction);
                            new Alert(Alert.AlertType.ERROR, "ha ocurrido un error y no se ha podido crear el contacto.", ButtonType.OK).show();
                            Logger.getLogger(Contactos.class.getName()).log(Level.SEVERE, null, e);
                        } finally {
                            Conexion.closeSession();
                        }
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Se han detectado errores:" + "\n\n" + mensajeError, ButtonType.CLOSE).show();
                }

            }
        }
        );

        btnBorrar.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event
            ) {
//    
                if (tabla.getItems().contains(contactoEnVista)) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Estas a punto de eliminar a un contacto\n"
                            + "de la lista. ¿Estas de acuerdo?", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> bt = a.showAndWait();

                    if (bt.get() == ButtonType.YES) {
                        Transaction transaction = null;
                        try {
                            transaction = Consultas.eliminarContacto(contactoEnVista);
                            lista = Consultas.getContactos();
                            tabla.getItems().setAll(lista);

                            filtrarTabla();
                            new Alert(Alert.AlertType.INFORMATION, "Se ha podido borrar el contacto correctamente.", ButtonType.OK).show();
                            limpiarCampos();

                        } catch (Exception e) {
                            Consultas.rollBack(transaction);
                            new Alert(Alert.AlertType.ERROR, "ha ocurrido un error y no se ha podido eliminar el contacto.", ButtonType.OK).show();
                            Logger.getLogger(Contactos.class.getName()).log(Level.SEVERE, null, e);
                        } finally {
                            Conexion.closeSession();
                        }
                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Este contacto no existe,por lo tanto, no se puede eliminar.", ButtonType.CLOSE);
                    a.show();
                }
            }
        }
        );

        btnBLimpiar.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event
            ) {
                tfBNombre.setText("");
                tfBTelefono.setText("");
                cbbBAmbito.getSelectionModel().selectFirst();
            }
        }
        );
        btnLimpiar.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                limpiarCampos();
            }
        });

        btnRefrescar.setOnAction(
                new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event
            ) {
                tabla.getItems().setAll(Consultas.getContactos());
                tabla.getItems().sort(new Comparator<Contacto>() {
                    @Override
                    public int compare(Contacto o1, Contacto o2) {
                        return o1.getNombre().compareTo(o2.getNombre());
                    }
                });
            }
        }
        );

        mIPassword.setOnAction(
                (event) -> {

                    new LanzadorVentanas().AbrirFXML("cambioPassword", "Cambio de contraseña", null, true, null, Modality.APPLICATION_MODAL, false);

                }
        );
    }

    public void filtrarTabla() {
        listraFiltrada = lista.filtered(new Predicate<Contacto>() {
            @Override
            public boolean test(Contacto t) {
                return t.getNombre().toUpperCase().contains(tfBNombre.getText()) && t.getTelefono().contains(tfBTelefono.getText())
                        && cbbBAmbito.getSelectionModel().getSelectedItem().equals(t.getAmbito());
            }
        });
        tabla.getItems().setAll(listraFiltrada);
        tabla.getItems().sort(new Comparator<Contacto>() {
            @Override
            public int compare(Contacto o1, Contacto o2) {
                return o1.getNombre().compareTo(o2.getNombre());
            }
        });
    }

    private void setFormulario(Contacto newValue) {
        String comentario = "";
        String email = "";
        String nombre = "";
        String prefijo = "";
        String telefono = "";
        String ambito = "";
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
            telefono = newValue.getTelefono();
        }
        if (newValue.getAmbito() != null) {
            ambito = newValue.getAmbito();
        }

        if (newValue.getFijo() != null) {
            if (newValue.getFijo()) {
                rdFijo.setSelected(true);
            } else {
                rdMovil.setSelected(true);
            }
        }

        if (newValue.getFax() != null) {
            if (newValue.getFax()) {
                rdFax.setSelected(true);
            }
        }
        taComentario.setText(comentario);
        tfEmail.setText(email);
        tfNombre.setText(nombre);
        tfPrefijo.setText(prefijo);
        tfTelefono.setText(telefono);
        cbbAmbito.getSelectionModel().select(ambito);
    }

    private void setContactoEnVista() {
        Contacto contacto = new Contacto();
        contacto.setAmbito(cbbAmbito.getSelectionModel().getSelectedItem());
        contacto.setComentario(taComentario.getText());
        contacto.setEmail(tfEmail.getText());
        contacto.setNombre(tfNombre.getText());
        if (tfPrefijo != null) {
            try {
                Integer entero = Integer.valueOf(tfPrefijo.getText());
                contacto.setPrefijo(entero);
            } catch (Exception e) {
                contacto.setPrefijo(Integer.valueOf("34"));
            }

        }
        contacto.setTelefono(tfTelefono.getText());
        contacto.setFax(rdFax.isSelected() ? true : false);
        contacto.setFijo(rdFax.isSelected() ? true : false);

        contactoEnVista = contacto;

    }

    private void limpiarCampos() {
        tfNombre.setText("");
        tfTelefono.setText("");
        tfEmail.setText("");
        tfPrefijo.setText("");
        taComentario.setText("");
        rdFijo.setSelected(false);
        rdMovil.setSelected(false);
        rdFax.setSelected(false);
        cbbAmbito.getSelectionModel().selectFirst();
    }
}
