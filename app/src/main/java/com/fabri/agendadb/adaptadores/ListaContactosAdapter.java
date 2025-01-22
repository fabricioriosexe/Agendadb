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
    private List<Contactos> listaOriginal;
    private DBHelper db;

    public ListaContactosAdapter(Context context, int resource, List<Contactos> contactosList,List<Contactos> listaOriginal, DBHelper database) {
        super(context, resource, contactosList);
        this.context = context;
        this.resource = resource;
        this.contactosList = contactosList;
        this.listaOriginal = listaOriginal;
        this.db = database;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Reutilizar la vista para optimizar el rendimiento
        ViewHolder viewHolder;

        // Si no hay una vista reutilizable, inflar el diseño
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.viewNombre = convertView.findViewById(R.id.viewNombre);
            viewHolder.viewTelefono = convertView.findViewById(R.id.viewTelefono);
            viewHolder.viewCorreo = convertView.findViewById(R.id.viewCorreo);
            viewHolder.btnEliminar = convertView.findViewById(R.id.btnEliminar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Obtener el contacto actual de la lista
        Contactos contacto = getItem(position);

        // Asignar valores a las vistas, validando que el contacto no sea nulo
        if (contacto != null) {
            viewHolder.viewNombre.setText(contacto.getNombre());
            viewHolder.viewTelefono.setText(contacto.getTelefono());
            viewHolder.viewCorreo.setText(contacto.getCoreo());
        }

        // Configurar el evento del botón eliminar
        viewHolder.btnEliminar.setOnClickListener(v -> {
            // Eliminar el contacto de la lista y actualizar el adaptador
            contactosList.remove(position);
            listaOriginal.remove(contacto);
            db.deleteContacto(contacto.getId());
            Log.d("Contacto borrrado", "Conctacto eliminado con exito");
            notifyDataSetChanged();
        });

        return convertView;
    }

    // ViewHolder para optimizar la referencia a las vistas
    private static class ViewHolder {
        TextView viewNombre;
        TextView viewTelefono;
        TextView viewCorreo;
        TextView btnEliminar;
    }
}