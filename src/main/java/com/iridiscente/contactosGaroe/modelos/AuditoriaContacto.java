package com.iridiscente.contactosGaroe.modelos;
// Generated 11-oct-2019 6:53:57 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * AuditoriaContacto generated by hbm2java
 */
public class AuditoriaContacto  implements java.io.Serializable {


     private Integer id;
     private Persona persona;
     private String nombreNuevo;
     private Integer prefijoNuevo;
     private String telefonoNuevo;
     private Boolean fijoNuevo;
     private Boolean faxNuevo;
     private String comentarioNuevo;
     private String emailNuevo;
     private String ambitoNuevo;
     private String query;
     private String nombreViejo;
     private String telefonoViejo;
     private Integer prefijoViejo;
     private String emailViejo;
     private String ambitoViejo;
     private Boolean fijoViejo;
     private Boolean faxViejo;
     private String comentarioViejo;
     private String mac;
     private String equipo;
     private Date hora;

    public AuditoriaContacto() {
    }

	
    public AuditoriaContacto(String query) {
        this.query = query;
    }
    public AuditoriaContacto(Persona persona, String nombreNuevo, Integer prefijoNuevo, String telefonoNuevo, Boolean fijoNuevo, Boolean faxNuevo, String comentarioNuevo, String emailNuevo, String ambitoNuevo, String query, String nombreViejo, String telefonoViejo, Integer prefijoViejo, String emailViejo, String ambitoViejo, Boolean fijoViejo, Boolean faxViejo, String comentarioViejo, String mac, String equipo, Date hora) {
       this.persona = persona;
       this.nombreNuevo = nombreNuevo;
       this.prefijoNuevo = prefijoNuevo;
       this.telefonoNuevo = telefonoNuevo;
       this.fijoNuevo = fijoNuevo;
       this.faxNuevo = faxNuevo;
       this.comentarioNuevo = comentarioNuevo;
       this.emailNuevo = emailNuevo;
       this.ambitoNuevo = ambitoNuevo;
       this.query = query;
       this.nombreViejo = nombreViejo;
       this.telefonoViejo = telefonoViejo;
       this.prefijoViejo = prefijoViejo;
       this.emailViejo = emailViejo;
       this.ambitoViejo = ambitoViejo;
       this.fijoViejo = fijoViejo;
       this.faxViejo = faxViejo;
       this.comentarioViejo = comentarioViejo;
       this.mac = mac;
       this.equipo = equipo;
       this.hora = hora;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Persona getPersona() {
        return this.persona;
    }
    
    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    public String getNombreNuevo() {
        return this.nombreNuevo;
    }
    
    public void setNombreNuevo(String nombreNuevo) {
        this.nombreNuevo = nombreNuevo;
    }
    public Integer getPrefijoNuevo() {
        return this.prefijoNuevo;
    }
    
    public void setPrefijoNuevo(Integer prefijoNuevo) {
        this.prefijoNuevo = prefijoNuevo;
    }
    public String getTelefonoNuevo() {
        return this.telefonoNuevo;
    }
    
    public void setTelefonoNuevo(String telefonoNuevo) {
        this.telefonoNuevo = telefonoNuevo;
    }
    public Boolean getFijoNuevo() {
        return this.fijoNuevo;
    }
    
    public void setFijoNuevo(Boolean fijoNuevo) {
        this.fijoNuevo = fijoNuevo;
    }
    public Boolean getFaxNuevo() {
        return this.faxNuevo;
    }
    
    public void setFaxNuevo(Boolean faxNuevo) {
        this.faxNuevo = faxNuevo;
    }
    public String getComentarioNuevo() {
        return this.comentarioNuevo;
    }
    
    public void setComentarioNuevo(String comentarioNuevo) {
        this.comentarioNuevo = comentarioNuevo;
    }
    public String getEmailNuevo() {
        return this.emailNuevo;
    }
    
    public void setEmailNuevo(String emailNuevo) {
        this.emailNuevo = emailNuevo;
    }
    public String getAmbitoNuevo() {
        return this.ambitoNuevo;
    }
    
    public void setAmbitoNuevo(String ambitoNuevo) {
        this.ambitoNuevo = ambitoNuevo;
    }
    public String getQuery() {
        return this.query;
    }
    
    public void setQuery(String query) {
        this.query = query;
    }
    public String getNombreViejo() {
        return this.nombreViejo;
    }
    
    public void setNombreViejo(String nombreViejo) {
        this.nombreViejo = nombreViejo;
    }
    public String getTelefonoViejo() {
        return this.telefonoViejo;
    }
    
    public void setTelefonoViejo(String telefonoViejo) {
        this.telefonoViejo = telefonoViejo;
    }
    public Integer getPrefijoViejo() {
        return this.prefijoViejo;
    }
    
    public void setPrefijoViejo(Integer prefijoViejo) {
        this.prefijoViejo = prefijoViejo;
    }
    public String getEmailViejo() {
        return this.emailViejo;
    }
    
    public void setEmailViejo(String emailViejo) {
        this.emailViejo = emailViejo;
    }
    public String getAmbitoViejo() {
        return this.ambitoViejo;
    }
    
    public void setAmbitoViejo(String ambitoViejo) {
        this.ambitoViejo = ambitoViejo;
    }
    public Boolean getFijoViejo() {
        return this.fijoViejo;
    }
    
    public void setFijoViejo(Boolean fijoViejo) {
        this.fijoViejo = fijoViejo;
    }
    public Boolean getFaxViejo() {
        return this.faxViejo;
    }
    
    public void setFaxViejo(Boolean faxViejo) {
        this.faxViejo = faxViejo;
    }
    public String getComentarioViejo() {
        return this.comentarioViejo;
    }
    
    public void setComentarioViejo(String comentarioViejo) {
        this.comentarioViejo = comentarioViejo;
    }
    public String getMac() {
        return this.mac;
    }
    
    public void setMac(String mac) {
        this.mac = mac;
    }
    public String getEquipo() {
        return this.equipo;
    }
    
    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }
    public Date getHora() {
        return this.hora;
    }
    
    public void setHora(Date hora) {
        this.hora = hora;
    }




}


