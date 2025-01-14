package com.fabri.agendadb.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fabri.agendadb.R;
import com.fabri.agendadb.entidades.Contactos;

import java.util.ArrayList;

public class ListaContactosAdapter extends RecyclerView.Adapter<ListaContactosAdapter.ContactoViewHolder> {
    ArrayList<Contactos> listacontactos;
    public ListaContactosAdapter(ArrayList<Contactos> listacontactos) {
        this.listacontactos = listacontactos;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_contacto, null, false);
        return new ContactoViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        holder.viewNombre.setText(listacontactos.get(position).getNombre());
        holder.viewTelefono.setText(listacontactos.get(position).getTelefono());
        holder.viewcorreo.setText(listacontactos.get(position).getCoreo());
    }

    @Override
    public int getItemCount() {
       return listacontactos.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {
        TextView viewNombre,viewTelefono,viewcorreo;


        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombre=itemView.findViewById(R.id.viewNombre);
            viewcorreo=itemView.findViewById(R.id.viewCorreo);
            viewTelefono = itemView.findViewById(R.id.viewTelefono);
        }
    }
}
