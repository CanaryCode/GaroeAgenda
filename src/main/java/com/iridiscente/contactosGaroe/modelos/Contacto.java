package com.iridiscente.contactosGaroe.modelos;
// Generated 11-oct-2019 6:53:57 by Hibernate Tools 4.3.1

/**
 * Contacto generated by hbm2java
 */
public class Contacto implements java.io.Serializable {
    
    private Integer id;
    private String nombre;
    private Integer prefijo;
    private String telefono;
    private Boolean fijo;
    private Boolean fax;
    private String comentario;
    private String email;
    private String ambito;
    
    public Contacto() {
    }
    
    public Contacto(String telefono) {
        this.telefono = telefono;
    }

    public Contacto(String nombre, Integer prefijo, String telefono, Boolean fijo, Boolean fax, String comentario, String email, String ambito) {
        this.nombre = nombre;
        this.prefijo = prefijo;
        this.telefono = telefono;
        this.fijo = fijo;
        this.fax = fax;
        this.comentario = comentario;
        this.email = email;
        this.ambito = ambito;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getPrefijo() {
        return this.prefijo;
    }
    
    public void setPrefijo(Integer prefijo) {
        this.prefijo = prefijo;
    }

    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getFijo() {
        return this.fijo;
    }
    
    public void setFijo(Boolean fijo) {
        this.fijo = fijo;
    }

    public Boolean getFax() {
        return this.fax;
    }
    
    public void setFax(Boolean fax) {
        this.fax = fax;
    }

    public String getComentario() {
        return this.comentario;
    }
    
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getAmbito() {
        return this.ambito;
    }
    
    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean resultado = false;
        if (obj != null) {
            return ((Contacto) obj).getPrefijo().equals(this.getPrefijo())
                    && ((Contacto) obj).getTelefono().equals(this.getTelefono())
                    && ((Contacto) obj).getAmbito().equals(this.getAmbito());
        }
        
        return resultado;
    }
    
}