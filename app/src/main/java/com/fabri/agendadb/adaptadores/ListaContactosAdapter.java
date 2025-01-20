package com.fabri.agendadb.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fabri.agendadb.R;
import com.fabri.agendadb.entidades.Contactos;
import com.fabri.agendadb.verActivity;

import java.util.ArrayList;

/**
 * Adaptador personalizado para manejar una lista de contactos en un RecyclerView.
 * Este adaptador enlaza los datos de los contactos con las vistas que los muestran en pantalla.
 */
public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder> {
    // Lista de contactos que se mostrarán en el RecyclerView
    ArrayList<Contactos> listacontactos;

    /**
     * Constructor del adaptador.
     * @param listacontactos Lista de contactos que será manejada por el adaptador.
     */
    public ListaContactosAdapter(ArrayList<Contactos> listacontactos) {
        this.listacontactos = listacontactos;
    }

    /**
     * Infla la vista para cada elemento del RecyclerView.
     * @param parent El ViewGroup padre donde se añadirá la vista inflada.
     * @param viewType Tipo de vista (en este caso, no se usa porque todas son iguales).
     * @return Un nuevo ContactoViewHolder que contiene la vista inflada.
     */
    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla el diseño XML de un elemento de la lista (lista_item_contacto)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_contacto, null, false);
        return new ContactoViewHolder(view); // Retorna un ViewHolder que envuelve la vista inflada
    }

    /**
     * Asigna los datos de un contacto a las vistas correspondientes en una posición específica.
     * @param holder El ViewHolder que contiene las vistas del elemento.
     * @param position La posición del elemento en la lista.
     */
    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        // Obtiene el contacto en la posición actual
        Contactos contactoActual = listacontactos.get(position);
        // Asigna el nombre del contacto a la vista correspondiente
        holder.viewNombre.setText(contactoActual.getNombre());
        // Asigna el teléfono del contacto
        holder.viewTelefono.setText(contactoActual.getTelefono());
        // Asigna el correo del contacto
        holder.viewcorreo.setText(contactoActual.getCoreo());
    }

    /**
     * Retorna la cantidad de elementos en la lista de contactos.
     * @return El tamaño de la lista de contactos.
     */
    @Override
    public int getItemCount() {
        return listacontactos.size(); // Retorna la cantidad total de contactos
    }

    /**
     * Clase interna que representa el ViewHolder para un elemento de contacto.
     * Mantiene referencias a las vistas que muestran los datos de un contacto.
     */
    public class ContactoViewHolder extends RecyclerView.ViewHolder {
        // Vistas para mostrar el nombre, teléfono y correo del contacto
        TextView viewNombre, viewTelefono, viewcorreo;

        /**
         * Constructor del ViewHolder.
         * @param itemView La vista inflada para un elemento de la lista.
         */
        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            // Enlaza las vistas con los IDs definidos en el layout lista_item_contacto
            viewNombre = itemView.findViewById(R.id.viewNombre);
            viewcorreo = itemView.findViewById(R.id.viewCorreo);
            viewTelefono = itemView.findViewById(R.id.viewTelefono);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, verActivity.class);
                    intent.putExtra("ID", listacontactos.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
