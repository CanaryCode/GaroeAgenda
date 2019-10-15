/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iridiscente.contactosGaroe.controladores;

import com.iridiscente.contactosGaroe.modelos.Admitido;
import com.iridiscente.contactosGaroe.modelos.Persona;
import com.iridiscente.contactosGaroe.modelos.VentanaCustom;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.HostServices;
import javafx.stage.Stage;

/**
 *
 * @author A. Jesús <Antonio Jesús Pérez Delgado ajpd1985@gmail.com>
 */
public class Sistema {

    private static Set<Stage> mapaVentanas;
    private static HostServices hostServices;
    private static Persona usuarioSistema;
    
    public static List<Persona> listaUsuarios;
    public static List<Admitido> listaMac;
    // public static final String V_LOGIN="V_LOGIN",V_CONTACTOS="V_CONTACTOS";
    public static List<VentanaCustom> listaVentanasAbiertas;

    public static Persona getUsuarioSistema() {
        return usuarioSistema;
    }

    public static void setUsuarioSistema(Persona usuarioSistema) {
        Sistema.usuarioSistema = usuarioSistema;
    }

    public static HostServices getHostServices() {
        return hostServices;
    }

    public static void setHostServices(HostServices hostServices) {
        Sistema.hostServices = hostServices;
    }

    public static List<VentanaCustom> getListaVentanas() {
        if (listaVentanasAbiertas == null) {
            listaVentanasAbiertas = new ArrayList<VentanaCustom>();
        }
        return listaVentanasAbiertas;
    }

    public static VentanaCustom getVentanaCustom(String nombre) {
        VentanaCustom vCustom = null;

        vCustom = ((VentanaCustom) Sistema.listaVentanasAbiertas.stream().filter((t) -> {
            return t.getNombre().equals(nombre);
        }).collect(Collectors.toList()).get(0));

        return vCustom;
    }

    public static String getMacAddress() {
        String resultado = "";
        try {
            byte[] b = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < b.length; i++) {
                sb.append(String.format("%02X%s", b[i], (i < b.length - 1) ? "-" : ""));
            }
            resultado = sb.toString();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public static String getLocalHostName() {
        String resultado = "";
        try {
            resultado = Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Sistema.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
}
