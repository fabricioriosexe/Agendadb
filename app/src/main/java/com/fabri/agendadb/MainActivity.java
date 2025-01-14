package com.fabri.agendadb;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fabri.agendadb.R;
import com.fabri.agendadb.adaptadores.ListaContactosAdapter;
import com.fabri.agendadb.db.dbContactos;
import com.fabri.agendadb.db.dbHelper;
import com.fabri.agendadb.entidades.Contactos;

import java.util.ArrayList;

/**
 * Clase principal de la aplicación. Es el punto de entrada donde se inicializan los componentes principales.
 */
public class MainActivity extends AppCompatActivity {

    // RecyclerView para mostrar la lista de contactos.
    RecyclerView listaContactos;
    // Lista de contactos que se utilizará para llenar el RecyclerView.
    ArrayList<Contactos> listaArrayContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activa el diseño Edge-to-Edge, optimizando la visualización en dispositivos modernos.
        EdgeToEdge.enable(this);

        // Establece el diseño visual para esta actividad.
        setContentView(R.layout.activity_main);

        // Vincula el RecyclerView definido en el diseño XML.
        listaContactos = findViewById(R.id.listaContactos);

        // Establece el administrador de diseño para mostrar los elementos en una lista vertical.
        listaContactos.setLayoutManager(new LinearLayoutManager(this));

        // Instancia la base de datos para obtener los contactos almacenados.
        dbContactos dbcontactos = new dbContactos(MainActivity.this);

        // Inicializa la lista de contactos.
        listaArrayContactos = new ArrayList<>();

        // Crea un adaptador que vincula los datos de la base de datos al RecyclerView.
        ListaContactosAdapter adapter = new ListaContactosAdapter(dbcontactos.mostrarContactos());
        // Establece el adaptador en el RecyclerView.
        listaContactos.setAdapter(adapter);

        // Ajusta los márgenes de la interfaz para acomodarse a las barras del sistema (status bar, navigation bar).
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Crea el menú de opciones para la actividad.
     * @param menu El menú donde se inflará la vista del menú principal.
     * @return true si se creó el menú correctamente.
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú utilizando el archivo XML "menu_principal".
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    /**
     * Maneja las acciones seleccionadas en el menú de opciones.
     * @param item El elemento del menú que fue seleccionado.
     * @return true si se manejó el evento, false si se delega a la superclase.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Verifica si se seleccionó el elemento de "Nuevo Registro".
        if (item.getItemId() == R.id.menuNuevo) {
            // Llama al método para abrir la actividad de nuevo registro.
            nuevoRegistro();
            return true;
        } else {
            // Delega el manejo a la superclase para otros elementos del menú.
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Abre la actividad para crear un nuevo registro.
     */
    private void nuevoRegistro() {
        // Crea un Intent para iniciar la actividad "NuevoActivity".
        Intent intent = new Intent(this, NuevoActivity.class);
        startActivity(intent);
    }
}
