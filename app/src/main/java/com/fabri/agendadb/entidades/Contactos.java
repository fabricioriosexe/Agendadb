package com.fabri.agendadb.entidades;

public class Contactos {
    private int id;
    private String nombre;
    private String telefono;
    private String coreo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCoreo() {
        return coreo;
    }

    public void setCoreo(String coreo) {
        this.coreo = coreo;
    }
}
