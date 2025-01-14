package com.fabri.agendadb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Clase dbHelper que extiende SQLiteOpenHelper para manejar la creación, actualización y conexión
 * con la base de datos SQLite utilizada por la aplicación.
 */
public class dbHelper extends SQLiteOpenHelper {

    // Constantes que definen la configuración de la base de datos.

    /**
     * Versión de la base de datos. Este valor se incrementa cada vez que se realiza un cambio
     * estructural en la base de datos, como añadir o modificar tablas.
     */
    private static final int DATABASE_VERSION = 4;

    /**
     * Nombre del archivo físico de la base de datos que se almacenará en el sistema de archivos
     * del dispositivo.
     */
    private static final String DATABASE_NOMBRE = "agenda.db";

    /**
     * Nombre de la tabla de contactos en la base de datos.
     */
    public static final String TABLE_CONTACTOS = "t_contactos";

    /**
     * Constructor de la clase dbHelper.
     * @param context Contexto de la aplicación o actividad que crea esta instancia.
     */
    public dbHelper(@Nullable Context context) {
        // Llama al constructor de SQLiteOpenHelper con los parámetros necesarios.
        // DATABASE_NOMBRE es el nombre del archivo de la base de datos.
        // DATABASE_VERSION es la versión actual de la base de datos.
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    /**
     * Método que se ejecuta automáticamente la primera vez que se accede a la base de datos
     * y esta aún no ha sido creada.
     * @param sqLiteDatabase Objeto SQLiteDatabase que representa la base de datos.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Sentencia SQL para crear la tabla "t_contactos".
        // Incluye las columnas: id (clave primaria autoincremental), nombre, teléfono y correo.
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CONTACTOS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," + // Columna id: Clave primaria, autoincremental.
                "nombre TEXT NOT NULL," +                 // Columna nombre: Tipo texto, no puede ser nulo.
                "telefono TEXT NOT NULL," +               // Columna teléfono: Tipo texto, no puede ser nulo.
                "correo TEXT" +                           // Columna correo: Tipo texto, puede ser nulo.
                ")");
    }

    /**
     * Método que se ejecuta automáticamente cuando hay un cambio en la versión de la base de datos.
     * Es útil para manejar actualizaciones de la estructura de la base de datos.
     * @param sqLiteDatabase Objeto SQLiteDatabase que representa la base de datos.
     * @param oldVersion Número de la versión anterior de la base de datos.
     * @param newVersion Número de la nueva versión de la base de datos.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Elimina la tabla existente si ya estaba creada previamente.
        // Esto asegura que la estructura de la base de datos sea la más reciente.
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_CONTACTOS);

        // Llama al método onCreate para volver a crear la tabla con la nueva estructura.
        onCreate(sqLiteDatabase);
    }
}
