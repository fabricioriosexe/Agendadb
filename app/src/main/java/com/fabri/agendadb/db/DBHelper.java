package com.fabri.agendadb.db;

// Importación de las librerías necesarias
import android.content.ContentValues;  // Para manejar valores de la base de datos como pares clave-valor
import android.content.Context;       // Para acceder al contexto de la aplicación
import android.database.Cursor;       // Para acceder a los resultados de una consulta SQL
import android.database.sqlite.SQLiteDatabase;  // Para interactuar con la base de datos SQLite

import com.fabri.agendadb.entidades.Contactos;  // Para manejar la clase Contactos
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;  // Ayuda para trabajar con bases de datos pre-pobladas

import java.io.File;        // Para trabajar con archivos
import java.io.FileOutputStream;  // Para escribir archivos
import java.io.IOException;  // Para manejar errores de entrada/salida
import java.io.InputStream;  // Para leer archivos
import java.io.OutputStream;  // Para escribir en archivos
import java.util.ArrayList;  // Para usar listas dinámicas
import java.util.List;  // Para trabajar con listas

/**
 * Clase DBHelper que extiende SQLiteAssetHelper.
 * Esta clase permite trabajar con una base de datos SQLite pre-poblada,
 * almacenada en la carpeta `assets/databases` de tu proyecto.
 */
public class DBHelper extends SQLiteAssetHelper {

    // Nombre del archivo de la base de datos en la carpeta assets/databases
    private static final String DATABASE_NAME = "agenda.db";

    // Versión actual de la base de datos
    private static final int DATABASE_VERSION = 1;

    // Nombres de tabla y columnas
    private static final String TABLE_CONTACTOS = "Contacto";  // Tabla que contiene los contactos
    private static final String COLUMN_ID = "id";             // Columna para el ID del contacto
    private static final String COLUMN_NOMBRE = "nombre";     // Columna para el nombre del contacto
    private static final String COLUMN_TELEFONO = "telefono"; // Columna para el teléfono del contacto
    private static final String COLUMN_CORREO = "correo";     // Columna para el correo del contacto


    /**
     * Constructor de la clase DBHelper.
     *
     * @param context Contexto de la aplicación que invoca esta clase.
     */
    public DBHelper(Context context) {
        // Llama al constructor de la clase padre SQLiteAssetHelper.
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // Obtén una instancia de la base de datos en modo escritura.
        SQLiteDatabase db = getWritableDatabase();

        // Verifica si la base de datos existe en el dispositivo.
        if (!checkDatabaseExistence(db)) {
            // Si no existe, copia la base de datos desde la carpeta assets.
            try {
                copyDatabase(context);
            } catch (IOException e) {
                e.printStackTrace(); // Muestra un error en el log si falla la copia.
            }
        }
    }

    /**
     * Verifica si la base de datos existe en el almacenamiento del dispositivo.
     *
     * @param db Instancia de SQLiteDatabase para obtener la ruta de la base de datos.
     * @return true si la base de datos existe, false en caso contrario.
     */
    private boolean checkDatabaseExistence(SQLiteDatabase db) {
        // Obtiene la ruta absoluta de la base de datos.
        String path = db.getPath();

        // Verifica si el archivo en esa ruta existe.
        File file = new File(path);
        return file.exists();
    }

    /**
     * Copia la base de datos pre-poblada desde la carpeta `assets/databases`
     * al almacenamiento interno del dispositivo.
     *
     * @param context Contexto de la aplicación que invoca esta clase.
     * @throws IOException Si ocurre un error durante la copia.
     */
    private void copyDatabase(Context context) throws IOException {
        // Cierra cualquier conexión existente con la base de datos.
        close();

        // Ruta de salida donde se copiará la base de datos en el dispositivo.
        OutputStream databaseOutputStream = new FileOutputStream(getDatabasePath(context));

        // Ruta de entrada, es decir, la base de datos pre-poblada en assets/databases.
        InputStream databaseInputStream = context.getAssets().open("databases/" + DATABASE_NAME);

        // Buffer para transferir datos entre el archivo de entrada y el de salida.
        byte[] buffer = new byte[1024]; // 1024 bytes por lectura
        int length;

        // Lee y copia los datos del archivo de entrada al de salida.
        while ((length = databaseInputStream.read(buffer)) > 0) {
            databaseOutputStream.write(buffer, 0, length);
        }

        // Cierra los flujos de entrada y salida.
        databaseInputStream.close();
        databaseOutputStream.flush();
        databaseOutputStream.close();
    }

    /**
     * Obtiene la ruta completa donde se almacena la base de datos en el dispositivo.
     *
     * @param context Contexto de la aplicación que invoca esta clase.
     * @return Ruta absoluta de la base de datos en el almacenamiento interno.
     */
    private String getDatabasePath(Context context) {
        return context.getDatabasePath(DATABASE_NAME).getPath();
    }

    // Insertar un nuevo contacto en la base de datos
    public void insertContacto(Contactos contacto) {
        // Obtener la base de datos en modo escritura
        SQLiteDatabase db = getWritableDatabase();

        // Crear un contenedor para los datos del contacto
        ContentValues cv = new ContentValues();

        // Asociar los atributos de la entidad Contactos con las columnas de la tabla
        cv.put("nombre", contacto.getNombre());         // Columna "nombre"
        cv.put("telefono", contacto.getTelefono());     // Columna "telefono"
        cv.put("correo", contacto.getCoreo());          // Columna "correo"

        // Insertar los datos en la tabla "Contacto"
        db.insert("Contacto", null, cv);

        // Cerrar la conexión con la base de datos
        db.close();
    }

    // Buscar todos los contactos en la base de datos
    public List<Contactos> buscarContactos() {
        List<Contactos> returnList = new ArrayList<>();  // Lista donde se almacenarán los contactos obtenidos.

        SQLiteDatabase db = getReadableDatabase();  // Obtiene la base de datos en modo lectura.

        // Consulta SQL para seleccionar todos los contactos de la tabla "Contacto"
        String query = "SELECT DISTINCT * FROM Contacto";

        // Ejecuta la consulta y obtiene un cursor con los resultados.
        Cursor cursor = db.rawQuery(query, null);

        // Si hay resultados, el cursor apunta al primer registro
        if (cursor.moveToFirst()) {
            do {
                // Obtiene los valores de cada columna
                int id = cursor.getInt(0);               // Obtiene el ID del contacto (columna 0).
                String nombre = cursor.getString(1);     // Obtiene el nombre del contacto (columna 1).
                String telefono = cursor.getString(2);   // Obtiene el teléfono (columna 2).
                String correo = cursor.getString(3);     // Obtiene el correo (columna 3).

                // Crea un objeto Contacto con los datos extraídos.
                Contactos contacto = new Contactos();
                contacto.setId(id);
                contacto.setNombre(nombre);
                contacto.setTelefono(telefono);
                contacto.setCoreo(correo);

                // Agrega el objeto Contacto a la lista.
                returnList.add(contacto);

            } while (cursor.moveToNext());  // Repite hasta que no haya más registros en el cursor.
        }

        // Cierra el cursor y la base de datos para liberar recursos.
        cursor.close();
        db.close();

        // Devuelve la lista con los contactos encontrados.
        return returnList;
    }

    // Eliminar un contacto por su ID
    public void deleteContacto(int idContacto) {
        SQLiteDatabase db = getWritableDatabase();
        // Ejecuta la eliminación en la tabla "Contacto", buscando por el ID del contacto
        db.delete("Contacto ", "id = ?", new String[]{String.valueOf(idContacto)});
        db.close();
    }

    // Actualizar un contacto en la base de datos
    public void updateContacto(Contactos contacto) {
        if (contacto == null || contacto.getId() <= 0) {
            throw new IllegalArgumentException("El contacto es nulo o tiene un ID inválido");
        }

        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();

            // Agregamos los valores que se actualizarán
            cv.put(COLUMN_NOMBRE, contacto.getNombre());
            cv.put(COLUMN_TELEFONO, contacto.getTelefono());
            cv.put(COLUMN_CORREO, contacto.getCoreo());

            // Actualizamos la tabla usando el ID del contacto como criterio
            db.update(TABLE_CONTACTOS, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(contacto.getId())});
        } finally {
            if (db != null) db.close();
        }
    }
}
