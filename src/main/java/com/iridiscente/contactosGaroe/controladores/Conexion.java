/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iridiscente.contactosGaroe.controladores;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author A. Jesús <Antonio Jesús Pérez Delgado ajpd1985@gmail.com>
 */
public class Conexion {

    public static SessionFactory sessionFactory;
    public static Session session;

    public Session getSession() {
       //SI NO HAY UNA FACTORIA ABIERTA
        if (sessionFactory == null) {
            try {
                //CREA UNA FACTORIA
                sessionFactory = HibernateUtil.getSessionFactory();
            } catch (Exception e) {
                if(session!=null && session.isOpen()){
                    session.close();
                    session=null;
                }
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        try {
            //SI SE CREO BIEN LA FACTORIA...............
            if (sessionFactory != null) {
                //SI YA HAY UNA SESSION Y ESTA ABIERTA
                if (session != null && session.isOpen()) {
                    session.close();
                    session = sessionFactory.openSession();
                }//SI HAY UNA SESSION PERO  NO ESTA ABIERTA
                else if(session != null ){
                    session=null;
                    session = sessionFactory.openSession();
                } //SI NO HAY UNA SESSION CREA UNA
                else{
                    session=sessionFactory.openSession();
                }
            }
        } catch (Exception e) {
            sessionFactory.close();
            session.close();
            sessionFactory = null;
            session = null;
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);

        }

        return session;
    }

    public static void closeSession() {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }
}
