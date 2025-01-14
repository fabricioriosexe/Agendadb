package com.fabri.agendadb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// Clase que extiende SQLiteOpenHelper para gestionar la creación y actualización de la base de datos.
public class dbHelper extends SQLiteOpenHelper {

    // Versión de la base de datos. Se incrementa si hay cambios en la estructura.
    private static final int DATABASE_VERSION = 4;
    // Nombre del archivo de la base de datos.
    private static final String DATABASE_NOMBRE = "agenda.db";
    // Nombre de la tabla que se va a crear.
    public static final String TABLE_CONTACTOS = "t_contactos";

    // Constructor de la clase dbHelper.
    // Recibe el contexto de la aplicación para interactuar con el sistema.
    public dbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    // Método que se ejecuta cuando se crea por primera vez la base de datos.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Se ejecuta la sentencia SQL para crear la tabla "t_contactos".
        // La tabla incluye un campo id (clave primaria), nombre, teléfono y correo.
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CONTACTOS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," + // Campo id, se autoincrementa.
                "nombre TEXT NOT NULL," +                 // Campo nombre, no permite valores nulos.
                "telefono TEXT NOT NULL," +               // Campo teléfono, no permite valores nulos.
                "correo TEXT" +                           // Campo correo, puede ser nulo.
                ")");
    }

    // Método que se ejecuta cuando se detecta un cambio en la versión de la base de datos.
    // Esto suele usarse para actualizar la estructura de la base de datos.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Elimina la tabla existente si ya estaba creada.
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_CONTACTOS);
        // Vuelve a crear la tabla desde cero.
        onCreate(sqLiteDatabase);
    }
}
