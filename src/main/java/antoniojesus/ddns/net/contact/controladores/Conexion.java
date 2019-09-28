/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antoniojesus.ddns.net.contact.controladores;

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
        if (sessionFactory == null) {
            try{
            sessionFactory = HibernateUtil.getSessionFactory();}
            catch(Exception e ){
            }
        }
        if (session == null || !session.isOpen()) {
            session = sessionFactory.openSession();
        }
       
        return session;
    }

    public void closeSession() {
        if (session != null) {
            sessionFactory.close();
        }
    }
}
