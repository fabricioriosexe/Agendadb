package com.fabri.agendadb.entidades;

/**
 * Clase que representa la entidad Contactos.
 * Esta clase se utiliza para almacenar y manipular la información de un contacto
 * en la aplicación.
 */
public class Contactos {

    // Atributos de la clase Contactos que corresponden a las columnas de la tabla en la base de datos.

    /**
     * Identificador único del contacto. Corresponde al campo "id" en la tabla.
     */
    private int id;

    /**
     * Nombre del contacto. Corresponde al campo "nombre" en la tabla.
     */
    private String nombre;

    /**
     * Teléfono del contacto. Corresponde al campo "telefono" en la tabla.
     */
    private String telefono;

    /**
     * Correo electrónico del contacto. Corresponde al campo "correo" en la tabla.
     */
    private String coreo;

    // Métodos Getter y Setter para acceder y modificar los atributos.

    /**
     * Obtiene el identificador único del contacto.
     * @return El ID del contacto.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del contacto.
     * @param id El nuevo ID del contacto.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del contacto.
     * @return El nombre del contacto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del contacto.
     * @param nombre El nuevo nombre del contacto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el teléfono del contacto.
     * @return El teléfono del contacto.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del contacto.
     * @param telefono El nuevo teléfono del contacto.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el correo electrónico del contacto.
     * @return El correo electrónico del contacto.
     */
    public String getCoreo() {
        return coreo;
    }

    /**
     * Establece el correo electrónico del contacto.
     * @param coreo El nuevo correo electrónico del contacto.
     */
    public void setCoreo(String coreo) {
        this.coreo = coreo;
    }
}
