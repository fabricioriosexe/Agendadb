package com.fabri.agendadb.adaptadores;

// Importación de las librerías necesarias
import android.content.Context;  // Para obtener el contexto de la actividad
import android.content.Intent;   // Para crear y manejar intents
import android.util.Log;         // Para registrar mensajes de depuración
import android.view.LayoutInflater; // Para inflar las vistas desde el XML
import android.view.View;            // Para trabajar con vistas
import android.view.ViewGroup;       // Para manejar grupos de vistas
import android.widget.ArrayAdapter;  // Para adaptar la lista de datos a una vista
import android.widget.ImageView;     // Para manejar imágenes
import android.widget.TextView;      // Para manejar texto en la interfaz

import com.fabri.agendadb.R;  // Archivo de recursos
import com.fabri.agendadb.actividades.verActivity;  // Para navegar a la actividad de ver/modificar contacto
import com.fabri.agendadb.db.DBHelper;  // Para interactuar con la base de datos
import com.fabri.agendadb.entidades.Contactos;  // Para manejar objetos de la clase Contactos

import java.util.List;  // Para manejar listas de contactos

// Adaptador personalizado para la lista de contactos
public class ListaContactosAdapter extends ArrayAdapter<Contactos> {

    private Context context;  // Contexto de la actividad que está utilizando el adaptador
    private int resource;     // Recurso del layout que se utilizará para cada elemento de la lista
    private List<Contactos> contactosList;  // Lista de contactos que se mostrará
    private List<Contactos> listaOriginal;  // Lista original sin filtrar (usada para restaurar la lista)
    private DBHelper db;  // Instancia de DBHelper para interactuar con la base de datos

    // Constructor del adaptador
    public ListaContactosAdapter(Context context, int resource, List<Contactos> contactosList, List<Contactos> listaOriginal, DBHelper database) {
        super(context, resource, contactosList);  // Llama al constructor de ArrayAdapter con los parámetros necesarios
        this.context = context;  // Inicializa el contexto
        this.resource = resource;  // Inicializa el recurso del layout
        this.contactosList = contactosList;  // Inicializa la lista de contactos
        this.listaOriginal = listaOriginal;  // Inicializa la lista original (sin filtrar)
        this.db = database;  // Inicializa la base de datos
    }

    // Método que se llama para obtener una vista para cada elemento de la lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;  // Referencia al ViewHolder que optimiza la reutilización de vistas

        if (convertView == null) {  // Si no hay una vista reciclada, inflamos una nueva
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);  // Infla el layout
            viewHolder = new ViewHolder();  // Crea un nuevo ViewHolder
            // Asocia las vistas del layout con las variables del ViewHolder
            viewHolder.viewNombre = convertView.findViewById(R.id.viewNombre);
            viewHolder.viewTelefono = convertView.findViewById(R.id.viewTelefono);
            viewHolder.viewCorreo = convertView.findViewById(R.id.viewCorreo);
            viewHolder.btnEliminar = convertView.findViewById(R.id.btnEliminar);
            viewHolder.btnModificar = convertView.findViewById(R.id.btnModificar); // Referencia al botón modificar
            convertView.setTag(viewHolder);  // Establece el ViewHolder como tag para la vista reciclada
        } else {
            viewHolder = (ViewHolder) convertView.getTag();  // Recupera el ViewHolder si ya existe
        }

        // Obtener el contacto correspondiente a la posición actual
        Contactos contacto = getItem(position);

        if (contacto != null) {  // Si el contacto no es nulo
            // Establece los valores del contacto en los TextViews
            viewHolder.viewNombre.setText(contacto.getNombre());
            viewHolder.viewTelefono.setText(contacto.getTelefono());
            viewHolder.viewCorreo.setText(contacto.getCoreo());

            // Configurar el evento de clic en el botón "Eliminar"
            viewHolder.btnEliminar.setOnClickListener(v -> {
                // Elimina el contacto de la lista
                contactosList.remove(position);
                listaOriginal.remove(contacto);  // También lo elimina de la lista original
                db.deleteContacto(contacto.getId());  // Elimina el contacto de la base de datos
                Log.d("Contacto eliminado", "Contacto eliminado con éxito");  // Registra el evento en los logs
                notifyDataSetChanged();  // Notifica al adaptador que los datos han cambiado, para actualizar la vista
            });

            // Configurar el evento de clic en el botón "Modificar"
            viewHolder.btnModificar.setOnClickListener(v -> {
                // Crea un Intent para abrir la actividad de modificación
                Intent intent = new Intent(context, verActivity.class);
                intent.putExtra("contacto_id", contacto.getId());  // Pasa el ID del contacto al Intent
                context.startActivity(intent);  // Inicia la actividad de modificación
            });
        }

        return convertView;  // Devuelve la vista personalizada para este elemento de la lista
    }

    // ViewHolder para almacenar las vistas de un elemento de la lista y optimizar su reutilización
    private static class ViewHolder {
        TextView viewNombre;    // Campo para el nombre del contacto
        TextView viewTelefono;  // Campo para el teléfono del contacto
        TextView viewCorreo;    // Campo para el correo del contacto
        TextView btnEliminar;   // Botón para eliminar el contacto
        TextView btnModificar;  // Botón para modificar el contacto
    }
}
