/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antoniojesus.ddns.net.contact.controladores;

import antoniojesus.ddns.net.contact.modelos.Contacto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author A. Jesús <Antonio Jesús Pérez Delgado ajpd1985@gmail.com>
 */
public class Mapeador {

    public static ObjectMapper mapper;

    private static ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }

    public static void escribirObjeto(List<Contacto> lista) {
        try {
            getMapper();
            mapper.writeValue(new File("listaObjectos.txt"), lista);
        } catch (IOException ex) {
            Logger.getLogger(Mapeador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ObservableList<Contacto> leerObjeto() {
        getMapper();
        ObservableList<Contacto> lista = FXCollections.observableArrayList();
        try {
            lista.setAll((ArrayList<Contacto>)mapper.readValue(new File("listaObjectos.txt"),new TypeReference<ArrayList<Contacto>>(){}));
        } catch (IOException ex) {
            Logger.getLogger(Mapeador.class.getName()).log(Level.SEVERE, null, ex);
        }
       return lista;
    }
    
}
