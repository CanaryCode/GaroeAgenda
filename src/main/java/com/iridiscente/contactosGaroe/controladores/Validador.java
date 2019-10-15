/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iridiscente.contactosGaroe.controladores;

/**
 *
 * @author A. Jesús <Antonio Jesús Pérez Delgado ajpd1985@gmail.com>
 */
public class Validador {
   public static boolean esEmailValido (String email) {
   String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
   return email.matches(regex);
}
}
