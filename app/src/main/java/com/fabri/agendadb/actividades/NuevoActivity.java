package com.fabri.agendadb.actividades;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fabri.agendadb.R;
import com.fabri.agendadb.adaptadores.ListaContactosAdapter;
import com.fabri.agendadb.db.DBHelper;
import com.fabri.agendadb.entidades.Contactos;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para la actividad "NuevoActivity", que permite al usuario agregar un nuevo contacto a la base de datos.
 */
public class NuevoActivity extends AppCompatActivity {
    private ListView listView;
    private List<Contactos> listaContactosFiltrados;
    private List<Contactos> listaArrayContactos;
    private DBHelper dbhelper;
    private ListaContactosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        // Inicializa el DBHelper y las listas
        dbhelper = new DBHelper(this);
        listaArrayContactos = dbhelper.buscarContactos();  // Obtiene todos los contactos
        listaContactosFiltrados = new ArrayList<>(listaArrayContactos);  // Copia la lista completa a la lista filtrada

        // Configura el ListView
        listView = findViewById(R.id.vistaContactos);
        if (listView != null) {
            // Configura el adaptador y asigna la variable miembro
            adapter = new ListaContactosAdapter(this, R.layout.lista_item_contacto, listaContactosFiltrados, listaArrayContactos    ,dbhelper);
            listView.setAdapter(adapter);

            Log.d("NuevoActivity", "ListView configurado correctamente.");
        } else {
            Log.e("NuevoActivity", "No se encontró el ListView (vistaContactos). Revisa el XML.");
        }

        // Configura el SearchView
        SearchView searchView = findViewById(R.id.txtBuscar);
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // No es necesario hacer nada al presionar Enter
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Filtra la lista de contactos según el texto ingresado
                    filterList(newText);
                    return false;
                }
            });
        } else {
            Log.e("NuevoActivity", "No se encontró el SearchView (txtBuscar). Revisa el XML.");
        }
    }

    // Método para filtrar la lista
    private void filterList(String query) {
        listaContactosFiltrados.clear();
        if (query.isEmpty()) {
            listaContactosFiltrados.addAll(listaArrayContactos);  // Si no hay texto, muestra todos los contactos
        } else {
            for (Contactos contacto : listaArrayContactos) {
                if (contacto.getNombre().toLowerCase().contains(query.toLowerCase()) ||
                        contacto.getTelefono().toLowerCase().contains(query.toLowerCase())) {
                    listaContactosFiltrados.add(contacto);  // Filtra por nombre o teléfono
                }
            }
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();  // Notifica al adaptador para que actualice la vista
        } else {
            Log.e("NuevoActivity", "El adaptador es null. Asegúrate de que fue inicializado correctamente.");
        }
    }
}
