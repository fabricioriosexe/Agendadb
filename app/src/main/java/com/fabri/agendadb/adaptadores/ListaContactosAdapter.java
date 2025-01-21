package com.fabri.agendadb.adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fabri.agendadb.R;
import com.fabri.agendadb.db.DBHelper;
import com.fabri.agendadb.entidades.Contactos;

import java.util.ArrayList;
import java.util.List;

public class ListaContactosAdapter extends ArrayAdapter<Contactos> {

    private Context context;
    private int resource;
    private List<Contactos> contactosList;

    private DBHelper db;

    public ListaContactosAdapter(Context context, int resource, List<Contactos> contactosList, DBHelper database) {
        super(context, resource, contactosList);
        this.context = context;
        this.resource = resource;
        this.contactosList = contactosList;
        this.db = database;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Reutilizar la vista para optimizar el rendimiento
        View view = convertView;

        // Si no hay una vista reutilizable, inflar el diseño
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, parent, false);
        }

        // Obtener el contacto actual de la lista
        Contactos contacto = getItem(position);

        // Vincular las vistas del diseño
        TextView viewNombre = view.findViewById(R.id.viewNombre);
        TextView viewTelefono = view.findViewById(R.id.viewTelefono);
        TextView viewCorreo = view.findViewById(R.id.viewCorreo);
        ImageView btnEliminar = view.findViewById(R.id.btnEliminar);

        // Asignar valores a las vistas
        if (contacto != null) {
            viewNombre.setText(contacto.getNombre());
            viewTelefono.setText(contacto.getTelefono());
            viewCorreo.setText(contacto.getCoreo());
        }

        // Configurar el evento del botón eliminar
        btnEliminar.setOnClickListener(v -> {
            // Eliminar el contacto de la lista y actualizar el adaptador
            contactosList.remove(position);
            db.deleteContacto(contacto.getId());
            Log.d("Contacto borrrado", "Conctacto eliminado con exito");
            notifyDataSetChanged();
        });

        return view;
    }
}
