package com.fabri.agendadb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.fabri.agendadb.entidades.Contactos;

import java.util.ArrayList;

public class dbContactos extends dbHelper{

    Context context; //que es context?

    public dbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarContactos(String nombre, String telefono,String correo){
        long id = 0;
        try {
           dbHelper dbhelper = new dbHelper(context);

           SQLiteDatabase db = dbhelper.getWritableDatabase();

           ContentValues values = new ContentValues();
           values.put("nombre", nombre);
           values.put("telefono", telefono);
           values.put("correo", correo);

           id = db.insert(TABLE_CONTACTOS, null, values);
       }catch (Exception ex) {
           ex.toString();
       }
        return id;
    }

    public ArrayList<Contactos> mostrarContactos(){
        dbHelper dbhelper = new dbHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();


        ArrayList<Contactos> listacontactos = new ArrayList<>();
        Contactos contacto = null;
        Cursor cursorcontacto = null;

        cursorcontacto = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS,null);
        if (cursorcontacto.moveToFirst()){
            do {
                contacto = new Contactos();
                contacto.setId(cursorcontacto.getInt(0));
                contacto.setNombre(cursorcontacto.getString(1));
                contacto.setTelefono(cursorcontacto.getString(2));
                contacto.setCoreo(cursorcontacto.getString(3));
                listacontactos.add(contacto);
            }while (cursorcontacto.moveToNext());
        }

        cursorcontacto.close();
        return listacontactos;
    }
}
