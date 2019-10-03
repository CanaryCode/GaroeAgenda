/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antoniojesus.ddns.net.contact.controladores;


import com.sun.xml.internal.ws.api.ha.HaInfo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javafx.application.HostServices;
import javafx.stage.Stage;

/**
 *
 * @author A. Jesús <Antonio Jesús Pérez Delgado ajpd1985@gmail.com>
 */
public class Sistema {
    
    private static Set <Stage> mapaVentanas;
    private static HostServices hostServices;
   // public static final String V_LOGIN="V_LOGIN",V_CONTACTOS="V_CONTACTOS";

    public static Set <Stage> getVentanas() {
        if (mapaVentanas == null) {
            mapaVentanas = new HashSet<Stage>();
        }
        return mapaVentanas;
    }

    public static HostServices getHostServices() {
        return hostServices;
    }

    public static void setHostServices(HostServices hostServices) {
        Sistema.hostServices = hostServices;
    }
}
