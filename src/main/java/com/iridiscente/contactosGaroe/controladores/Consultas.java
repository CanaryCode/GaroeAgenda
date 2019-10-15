/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iridiscente.contactosGaroe.controladores;

import com.iridiscente.contactosGaroe.modelos.Admitido;
import com.iridiscente.contactosGaroe.modelos.AuditoriaContacto;
import com.iridiscente.contactosGaroe.modelos.Contacto;
import com.iridiscente.contactosGaroe.modelos.Persona;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

/**
 *
 * @author A. Jesús <Antonio Jesús Pérez Delgado ajpd1985@gmail.com>
 */
public class Consultas {

    @SuppressWarnings("unchecked")
    public static ObservableList<Contacto> getContactos() {
        ObservableList<Contacto> lista = null;
        Session session = new Conexion().getSession();
        if (session != null) {
            lista = FXCollections.observableArrayList();
            Transaction transaction = null;;
            transaction = session.beginTransaction();
            NativeQuery query = session.createSQLQuery("select * from contacto").addEntity(Contacto.class);
            lista.addAll(query.list());
            session.getTransaction().commit();
            cerrarConexion();
        }
        return lista;
    }

    public static Transaction actualizarContacto(Contacto contactoNuevo) throws Exception {
        Session session = null;
        Transaction transaction = null;
        Contacto contactoViejo = getContactoByTelefono(contactoNuevo.getTelefono(), contactoNuevo.getPrefijo().toString(), contactoNuevo.getAmbito());
        contactoNuevo.setId(contactoViejo.getId());
        session = new Conexion().getSession();
        transaction = session.beginTransaction();
        session.update(contactoNuevo);

        AuditoriaContacto ac = new AuditoriaContacto();
        if (contactoViejo.getComentario() == null) {
            ac.setComentarioViejo("");
        } else {
            ac.setComentarioViejo(contactoViejo.getComentario());
        }

        if (contactoViejo.getEmail() == null) {
            ac.setEmailViejo("");
        } else {
            ac.setEmailViejo(contactoNuevo.getEmail());
        }

        if (contactoNuevo.getComentario() == null) {
            ac.setComentarioNuevo("");
        } else {
            ac.setComentarioNuevo(contactoNuevo.getComentario());
        }

        if (contactoNuevo.getEmail() == null) {
            ac.setEmailNuevo("");
        } else {
            ac.setEmailNuevo(contactoNuevo.getEmail());
        }
        ac.setAmbitoNuevo(contactoNuevo.getAmbito());
        ac.setAmbitoViejo(contactoViejo.getAmbito());
        ac.setNombreNuevo(contactoNuevo.getNombre());
        ac.setNombreViejo(contactoViejo.getNombre());
        ac.setFaxNuevo(contactoNuevo.getFax());
        ac.setFaxViejo(contactoViejo.getFax());
        ac.setFijoNuevo(contactoNuevo.getFijo());
        ac.setFijoViejo(contactoViejo.getFijo());
        ac.setPrefijoNuevo(contactoNuevo.getPrefijo());
        ac.setPrefijoViejo(contactoViejo.getPrefijo());
        ac.setTelefonoNuevo(contactoNuevo.getTelefono());
        ac.setTelefonoViejo(contactoViejo.getTelefono());
        ac.setEquipo(Sistema.getLocalHostName());
        ac.setHora(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        ac.setMac(Sistema.getMacAddress());
        ac.setPersona(Sistema.getUsuarioSistema());
        ac.setQuery("actualizar");
        session.save(ac);

        session.getTransaction().commit();
        return transaction;
    }

    public static Transaction crearContacto(Contacto contacto) throws Exception {
        Session session = null;
        Transaction transaction = null;
        session = new Conexion().getSession();
        transaction = session.beginTransaction();
        session.save(contacto);

        AuditoriaContacto ac = new AuditoriaContacto();
        if (contacto.getComentario() == null) {
            ac.setComentarioNuevo("");
        } else {
            ac.setComentarioNuevo(contacto.getComentario());
        }
        ac.setAmbitoNuevo(contacto.getAmbito());
        if (contacto.getEmail() == null) {
            ac.setEmailNuevo("");
        } else {
            ac.setEmailNuevo(contacto.getEmail());
        }
        ac.setNombreNuevo(contacto.getNombre());
        ac.setFaxNuevo(contacto.getFax());
        ac.setFijoNuevo(contacto.getFijo());
        ac.setPrefijoNuevo(contacto.getPrefijo());
        ac.setTelefonoNuevo(contacto.getTelefono());
        ac.setEquipo(Sistema.getLocalHostName());
        ac.setHora(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        ac.setMac(Sistema.getMacAddress());
        ac.setPersona(Sistema.getUsuarioSistema());
        ac.setQuery("crear");
        session.save(ac);
        session.getTransaction().commit();

        return transaction;
    }

    public static Transaction eliminarContacto(Contacto contacto) throws Exception {
        Session session = null;
        Transaction transaction = null;

        contacto.setId(getContactoByTelefono(contacto.getTelefono(), contacto.getPrefijo().toString(), contacto.getAmbito()).getId());
        session = new Conexion().getSession();
        transaction = session.beginTransaction();
        session.delete(contacto);

        AuditoriaContacto ac = new AuditoriaContacto();
        if (contacto.getComentario() == null) {
            ac.setComentarioViejo("");
        } else {
            ac.setComentarioViejo(contacto.getComentario());
        }
        ac.setAmbitoViejo(contacto.getAmbito());
        if (contacto.getEmail() == null) {
            ac.setEmailViejo("");
        } else {
            ac.setEmailNuevo(contacto.getEmail());
        }
        ac.setNombreViejo(contacto.getNombre());
        ac.setFaxViejo(contacto.getFax());
        ac.setFijoViejo(contacto.getFijo());
        ac.setPrefijoViejo(contacto.getPrefijo());
        ac.setTelefonoViejo(contacto.getTelefono());
        ac.setEquipo(Sistema.getLocalHostName());
        ac.setHora(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        ac.setMac(Sistema.getMacAddress());
        ac.setPersona(Sistema.getUsuarioSistema());
        ac.setQuery("eliminar");

        session.save(ac);

        session.getTransaction().commit();
        return transaction;
    }

    @SuppressWarnings("unchecked")
    public static Persona getUsuario(Persona persona) throws Exception {
        Persona p = null;
        Session session = null;
        Transaction transaction = null;
        session = new Conexion().getSession();
        if (session != null) {
            transaction = session.beginTransaction();
            NativeQuery query = session.createSQLQuery("SELECT * FROM contactos.persona where usuario like '" + persona.getUsuario() + "'").addEntity(Persona.class);
            if (query.list() != null && !query.list().isEmpty()) {
                p = ((List<Persona>) query.list()).get(0);
            }
            session.getTransaction().commit();
            cerrarConexion();
        }
        return p;
    }

    public static List<String> getDepartamentos() {
        List<String> departamentos = new ArrayList<String>();

        Session session = null;
        Transaction transaction = null;

        session = new Conexion().getSession();
        transaction = session.beginTransaction();
        NativeQuery query = session.createSQLQuery("SELECT distinct departamento FROM contactos.persona");
        if (query.list() != null && !query.list().isEmpty()) {
            List<String> list = query.list();
            departamentos.addAll(query.list());
        }
        session.getTransaction().commit();
        cerrarConexion();

        return departamentos;
    }

    public static Transaction setPasswordUsuario(Persona persona) throws Exception {
        Session session = null;
        Transaction transaction = null;
        session = new Conexion().getSession();
        transaction = session.beginTransaction();
        session.update(persona);
        session.getTransaction().commit();
        cerrarConexion();
        return transaction;
    }

    public static Contacto getContactoByTelefono(String numeroTelefono, String prefijo, String ambito) {
        Contacto contacto = null;

        Session session = null;
        Transaction transaction = null;
        session = new Conexion().getSession();
        transaction = session.beginTransaction();
        NativeQuery sQLQuery = session.createSQLQuery("select * from contactos.contacto where contacto.telefono like '"
                + numeroTelefono + "' and contacto.prefijo = '"
                + prefijo + "' and contacto.ambito like '" + ambito + "'").addEntity(Contacto.class);
        if (sQLQuery.list() != null && !sQLQuery.list().isEmpty()) {
            contacto = (Contacto) sQLQuery.list().get(0);
        }
        session.getTransaction().commit();

        cerrarConexion();
        return contacto;
    }

    public static void rollBack(Transaction t) {

        if (t != null) {
            t.rollback();;
        }
    }

    public static void cerrarConexion() {
        if (Conexion.session != null && Conexion.session.isOpen()) {
            Conexion.session.close();
        }
    }

    public static void entrar() {
        Session session = null;
        Transaction transaction = null;
        session = new Conexion().getSession();
        transaction = session.beginTransaction();

        AuditoriaContacto ac = new AuditoriaContacto();
        ac.setEquipo(Sistema.getLocalHostName());
        ac.setHora(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        ac.setMac(Sistema.getMacAddress());
        ac.setPersona(Sistema.getUsuarioSistema());
        ac.setQuery("entrar");

        session.save(ac);

        session.getTransaction().commit();
        cerrarConexion();
    }

    public static void salir() {
        Session session = null;
        Transaction transaction = null;
        session = new Conexion().getSession();
        if (session != null) {
            transaction = session.beginTransaction();

            AuditoriaContacto ac = new AuditoriaContacto();
            ac.setEquipo(Sistema.getLocalHostName());
            ac.setHora(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            ac.setMac(Sistema.getMacAddress());
            ac.setPersona(Sistema.getUsuarioSistema());
            ac.setQuery("salir");

            session.save(ac);

            session.getTransaction().commit();
            cerrarConexion();
        }
    }

    public static ObservableList<Admitido> listaAdmision() throws Exception {
        ObservableList<Admitido> lista = FXCollections.observableArrayList();
        Session session = null;
        Transaction transaction = null;
        session = new Conexion().getSession();

        NativeQuery sQLQuery = null;
        transaction = session.beginTransaction();

        sQLQuery = session.createSQLQuery("select * from admitido").addEntity(Admitido.class);
        if (sQLQuery.list() != null && !sQLQuery.list().isEmpty()) {
            List<Admitido> l = sQLQuery.list();
            lista.addAll(l);
        }
        session.getTransaction().commit();
        cerrarConexion();
        return lista;
    }

    public static List<Persona> getTodosLosUsuarios() {
        ObservableList<Persona> lista = FXCollections.observableArrayList();
        Session session = null;
        Transaction transaction = null;
        session = new Conexion().getSession();

        NativeQuery sQLQuery = null;
        transaction = session.beginTransaction();

        sQLQuery = session.createSQLQuery("select * from persona where usuario is not null").addEntity(Persona.class);
        if (sQLQuery.list() != null && !sQLQuery.list().isEmpty()) {
        List<Persona> l = sQLQuery.list();
        lista.addAll(l);}
        session.getTransaction().commit();
        cerrarConexion();
        return lista;
    }
}
