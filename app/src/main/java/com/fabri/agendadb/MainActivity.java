package com.fabri.agendadb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fabri.agendadb.db.DBHelper;
import com.fabri.agendadb.entidades.Contactos;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal de la aplicación. Es el punto de entrada donde se inicializan los componentes principales.
 */
public class MainActivity extends AppCompatActivity {


    // Lista de contactos que se utilizará para llenar el RecyclerView.
    ArrayList<Contactos> listaArrayContactos;

    DBHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activa el diseño Edge-to-Edge, optimizando la visualización en dispositivos modernos.
        EdgeToEdge.enable(this);

        // Establece el diseño visual para esta actividad.
        setContentView(R.layout.activity_main);

        // Inicializa la lista de contactos.
        listaArrayContactos = new ArrayList<>();

        // Instancia la clase DBHelper para acceder a la base de datos.
        dbhelper = new DBHelper(MainActivity.this);

        // Aquí llamamos al método buscarContactos para obtener la lista de contactos
        List<Contactos> contactos = dbhelper.buscarContactos();

        // Imprime los contactos en la consola
        for (Contactos contacto : contactos) {
            Log.d("MainActivity", "Contacto: ID = " + contacto.getId() + ", Nombre = " + contacto.getNombre() +
                    ", Teléfono = " + contacto.getTelefono() + ", Correo = " + contacto.getCoreo());
        }

        // Ajusta los márgenes de la interfaz para acomodarse a las barras del sistema (status bar, navigation bar).
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainActivity), (v, insets) -> {
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
