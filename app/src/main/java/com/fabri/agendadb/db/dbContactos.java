package com.fabri.agendadb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.fabri.agendadb.entidades.Contactos;

import java.util.ArrayList;

/**
 * Clase dbContactos que extiende de dbHelper para manejar las operaciones de la base de datos relacionadas con contactos.
 * Permite insertar y recuperar contactos de una base de datos SQLite.
 */
public class dbContactos extends dbHelper {

    // Context representa el entorno global de información sobre la aplicación.
    // Es necesario para operaciones relacionadas con la base de datos y recursos.
    Context context;

    /**
     * Constructor de la clase dbContactos.
     * @param context El contexto de la aplicación o actividad que crea esta instancia.
     */
    public dbContactos(@Nullable Context context) {
        super(context); // Llama al constructor de la clase dbHelper.
        this.context = context; // Almacena el contexto localmente para uso posterior.
    }

    /**
     * Método para insertar un nuevo contacto en la base de datos.
     * @param nombre Nombre del contacto.
     * @param telefono Teléfono del contacto.
     * @param correo Correo del contacto.
     * @return El ID del contacto recién insertado, o 0 si hubo un error.
     */
    public long insertarContactos(String nombre, String telefono, String correo) {
        long id = 0; // Variable para almacenar el ID del contacto insertado.
        try {
            // Crea una instancia de dbHelper para acceder a la base de datos.
            dbHelper dbhelper = new dbHelper(context);

            // Obtiene una instancia de la base de datos en modo escritura.
            SQLiteDatabase db = dbhelper.getWritableDatabase();

            // ContentValues almacena los datos que se insertarán.
            ContentValues values = new ContentValues();
            values.put("nombre", nombre); // Inserta el nombre en la columna "nombre".
            values.put("telefono", telefono); // Inserta el teléfono en la columna "telefono".
            values.put("correo", correo); // Inserta el correo en la columna "correo".

            // Inserta los valores en la tabla TABLE_CONTACTOS y obtiene el ID generado.
            id = db.insert(TABLE_CONTACTOS, null, values);
        } catch (Exception ex) {
            // Captura cualquier excepción y la convierte a String (aunque no se maneja aquí).
            ex.toString();
        }
        return id; // Devuelve el ID del contacto insertado, o 0 si falló.
    }

    /**
     * Método para recuperar todos los contactos almacenados en la base de datos.
     * @return Una lista de objetos Contactos con los datos obtenidos.
     */
    public ArrayList<Contactos> mostrarContactos() {
        // Crea una instancia de dbHelper para acceder a la base de datos.
        dbHelper dbhelper = new dbHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        // Lista para almacenar los contactos recuperados.
        ArrayList<Contactos> listacontactos = new ArrayList<>();
        Contactos contacto = null; // Variable temporal para cada contacto.
        Cursor cursorcontacto = null; // Cursor para recorrer los resultados de la consulta.

        // Ejecuta una consulta SQL para obtener todos los contactos.
        cursorcontacto = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS, null);

        // Verifica si el cursor tiene al menos un registro.
        if (cursorcontacto.moveToFirst()) {
            do {
                // Crea un nuevo objeto Contactos y lo llena con los datos del cursor.
                contacto = new Contactos();
                contacto.setId(cursorcontacto.getInt(0)); // Obtiene el ID (columna 0).
                contacto.setNombre(cursorcontacto.getString(1)); // Obtiene el nombre (columna 1).
                contacto.setTelefono(cursorcontacto.getString(2)); // Obtiene el teléfono (columna 2).
                contacto.setCoreo(cursorcontacto.getString(3)); // Obtiene el correo (columna 3).
                // Añade el contacto a la lista.
                listacontactos.add(contacto);
            } while (cursorcontacto.moveToNext()); // Avanza al siguiente registro del cursor.
        }

        // Cierra el cursor para liberar recursos.
        cursorcontacto.close();

        // Retorna la lista completa de contactos.
        return listacontactos;
    }
}
