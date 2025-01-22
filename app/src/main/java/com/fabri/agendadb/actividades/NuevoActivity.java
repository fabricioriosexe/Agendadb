package com.fabri.agendadb.actividades;

// Importación de las librerías necesarias para la funcionalidad de la actividad
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
    // Declaración de las variables miembro
    private ListView listView;
    private List<Contactos> listaContactosFiltrados;
    private List<Contactos> listaArrayContactos;
    private DBHelper dbhelper;
    private ListaContactosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);  // Establece el layout de la actividad

        // Inicializa el DBHelper y las listas
        dbhelper = new DBHelper(this);  // Crea una instancia de DBHelper para interactuar con la base de datos
        listaArrayContactos = dbhelper.buscarContactos();  // Obtiene todos los contactos de la base de datos
        listaContactosFiltrados = new ArrayList<>(listaArrayContactos);  // Copia la lista completa a la lista filtrada

        // Configura el ListView
        listView = findViewById(R.id.vistaContactos);  // Encuentra el ListView en el layout por su ID
        if (listView != null) {
            // Configura el adaptador para mostrar los contactos
            adapter = new ListaContactosAdapter(this, R.layout.lista_item_contacto, listaContactosFiltrados, listaArrayContactos, dbhelper);
            listView.setAdapter(adapter);  // Asocia el adaptador al ListView

            Log.d("NuevoActivity", "ListView configurado correctamente.");  // Mensaje de log si todo está bien
        } else {
            Log.e("NuevoActivity", "No se encontró el ListView (vistaContactos). Revisa el XML.");  // Mensaje de error si no se encuentra el ListView
        }

        // Configura el SearchView para permitir la búsqueda de contactos
        SearchView searchView = findViewById(R.id.txtBuscar);  // Encuentra el SearchView en el layout
        if (searchView != null) {
            // Establece un listener para detectar cambios en el texto ingresado en el SearchView
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
            Log.e("NuevoActivity", "No se encontró el SearchView (txtBuscar). Revisa el XML.");  // Mensaje de error si no se encuentra el SearchView
        }
    }

    // Método para filtrar la lista de contactos según el texto ingresado
    private void filterList(String query) {
        listaContactosFiltrados.clear();  // Limpia la lista filtrada
        if (query.isEmpty()) {
            listaContactosFiltrados.addAll(listaArrayContactos);  // Si no hay texto, muestra todos los contactos
        } else {
            // Recorre todos los contactos y agrega aquellos que coincidan con la búsqueda
            for (Contactos contacto : listaArrayContactos) {
                if (contacto.getNombre().toLowerCase().contains(query.toLowerCase()) ||
                        contacto.getTelefono().toLowerCase().contains(query.toLowerCase())) {
                    listaContactosFiltrados.add(contacto);  // Si el nombre o teléfono contiene el texto de búsqueda, se agrega a la lista filtrada
                }
            }
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();  // Notifica al adaptador que la lista ha cambiado, para que actualice la vista
        } else {
            Log.e("NuevoActivity", "El adaptador es null. Asegúrate de que fue inicializado correctamente.");  // Error si el adaptador es nulo
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualiza el ListView con los datos más recientes de la base de datos
        adapter.clear();  // Limpia la lista actual del adaptador
        adapter.addAll(dbhelper.buscarContactos());  // Agrega los nuevos contactos obtenidos de la base de datos
        adapter.notifyDataSetChanged();  // Notifica al adaptador para que actualice la vista
    }
}
