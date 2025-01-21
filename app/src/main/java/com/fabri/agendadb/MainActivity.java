package com.fabri.agendadb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fabri.agendadb.adaptadores.ListaContactosAdapter;
import com.fabri.agendadb.db.DBHelper;
import com.fabri.agendadb.entidades.Contactos;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Contactos> listaContactosFiltrados;
    List<Contactos> listaArrayContactos;
    DBHelper dbhelper;
    ListaContactosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa las listas y DBHelper
        listaContactosFiltrados = new ArrayList<>();  // Inicializa lista filtrada
        dbhelper = new DBHelper(MainActivity.this);

        // Obtén los contactos de la base de datos
        listaArrayContactos = dbhelper.buscarContactos();
        listaContactosFiltrados = new ArrayList<>(listaArrayContactos);  // Copia los datos a la lista filtrada

        // Configura el ListView con un adaptador personalizado
        ListView listView = findViewById(R.id.vistaContactos);
        adapter = new ListaContactosAdapter(this, R.layout.lista_item_contacto, listaContactosFiltrados, dbhelper);  // Usa lista filtrada
        listView.setAdapter(adapter);

        // Configura el clic en los elementos para ver detalles
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            Contactos contactoSeleccionado = listaContactosFiltrados.get(position);  // Usa lista filtrada
            Intent intent = new Intent(this, verActivity.class);  // Asegúrate de que VerActivity existe
            intent.putExtra("ID", contactoSeleccionado.getId());
            startActivity(intent);
        });

        // Configura el SearchView para buscar contactos
        SearchView searchView = findViewById(R.id.txtBuscar);
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
    }

    // Método para filtrar la lista
    private void filterList(String query) {
        listaContactosFiltrados.clear();
        if (query.isEmpty()) {
            listaContactosFiltrados.addAll(listaArrayContactos);  // Si no hay texto, muestra todos los contactos
        } else {
            for (Contactos contacto : dbhelper.buscarContactos()) {
                if (contacto.getNombre().toLowerCase().contains(query.toLowerCase()) ||
                        contacto.getTelefono().toLowerCase().contains(query.toLowerCase())) {
                    listaContactosFiltrados.add(contacto);  // Filtra por nombre o teléfono
                }
            }
        }
        adapter.notifyDataSetChanged();  // Notifica al adaptador para que actualice la vista
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);  // Asegúrate de que el archivo XML exista
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuNuevo) {
            nuevoRegistro();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void nuevoRegistro() {
        Intent intent = new Intent(this, NuevoActivity.class);  // Asegúrate de que NuevoActivity existe
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualiza la lista de contactos con los últimos datos de la base de datos
        listaArrayContactos.clear();
        listaArrayContactos.addAll(dbhelper.buscarContactos());  // Asegúrate de que este método está bien implementado
        listaContactosFiltrados.clear();
        listaContactosFiltrados.addAll(listaArrayContactos);  // Restablece la lista filtrada
        adapter.clear();
        adapter.addAll(dbhelper.buscarContactos());
        adapter.notifyDataSetChanged();
    }
}
