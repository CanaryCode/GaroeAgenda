/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antoniojesus.ddns.net.contact.controladores;

import antoniojesus.ddns.net.contact.modelos.Contacto;
import antoniojesus.ddns.net.contact.modelos.Persona;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author A. Jesús <Antonio Jesús Pérez Delgado ajpd1985@gmail.com>
 */
public class Consultas {

    public static ObservableList<Contacto> getContactos() {
        ObservableList<Contacto> lista = FXCollections.observableArrayList();
        Session session = null;
        Transaction transaction = null;
        try {
            session = new Conexion().getSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Contacto");
            lista.addAll(query.list());
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();;
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return lista;
    }

    public static void actualizarContacto(Contacto contacto) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = new Conexion().getSession();
            transaction = session.beginTransaction();
            session.update(contacto);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();;
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static void crearContacto(Contacto contacto) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = new Conexion().getSession();
            transaction = session.beginTransaction();
            session.save(contacto);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();;
            }
        } finally {
            if (session != null || session.isOpen()) {
                session.close();
            }
        }
    }

    public static void eliminarContacto(Contacto contacto) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = new Conexion().getSession();
            transaction = session.beginTransaction();
            session.delete(contacto);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();;
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
   public static List<Persona> getUsuario(Persona persona){
        List<Persona> lista=null;
        Session session = null;
        Transaction transaction = null;
        try {
            session = new Conexion().getSession();
            transaction = session.beginTransaction();
            SQLQuery query = session.createSQLQuery("SELECT * FROM contactos.persona where password like '"+persona.getPassword()+"' and usuario like '"+persona.getUsuario()+"'");
            lista=(List<Persona>) query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();;
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return lista;
   }
}
