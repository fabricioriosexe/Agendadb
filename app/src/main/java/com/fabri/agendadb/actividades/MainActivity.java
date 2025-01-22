package com.fabri.agendadb.actividades;

// Importa las clases necesarias para la funcionalidad de la actividad.
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fabri.agendadb.R;
import com.fabri.agendadb.db.DBHelper;
import com.fabri.agendadb.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Clase principal de la aplicación que maneja la actividad principal.
 * Extiende AppCompatActivity para compatibilidad con versiones antiguas de Android.
 */


public class MainActivity extends AppCompatActivity {

    // Elementos de la interfaz que el usuario puede interactuar.
    EditText txtNombre, txtTelefono, txtCorreo; // Campos de texto para ingresar datos del contacto.
    Button btnGuardar;                          // Botón para guardar un contacto.
    FloatingActionButton floatingActionButton; // Botón flotante para abrir una nueva actividad.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activa el diseño Edge-to-Edge, para que la UI utilice todo el espacio disponible en pantalla.
        EdgeToEdge.enable(this);

        // Define el diseño asociado a esta actividad.
        setContentView(R.layout.activity_main);

        // Vincula los elementos de la interfaz de usuario con los definidos en el archivo XML.
        txtNombre = findViewById(R.id.txtNombre);       // Referencia al campo de texto para el nombre.
        txtCorreo = findViewById(R.id.txtCorreo);       // Referencia al campo de texto para el correo.
        txtTelefono = findViewById(R.id.txtTelefono);   // Referencia al campo de texto para el teléfono.
        btnGuardar = findViewById(R.id.btnGuardar);     // Referencia al botón "Guardar".
        floatingActionButton = findViewById(R.id.floatingId); // Referencia al botón flotante.

        // Configura el evento clic para el botón flotante.
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abre la actividad NuevoActivity para agregar un nuevo contacto.
                startActivity(new Intent(MainActivity.this, NuevoActivity.class));
            }
        });

        // Configura el evento clic para el botón "Guardar".
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear una instancia de la clase Contactos para almacenar los datos ingresados.
                Contactos contacto = new Contactos();
                contacto.setNombre(txtNombre.getText().toString());   // Obtiene el nombre del campo de texto.
                contacto.setTelefono(txtTelefono.getText().toString()); // Obtiene el teléfono del campo de texto.
                contacto.setCoreo(txtCorreo.getText().toString());     // Obtiene el correo del campo de texto.

                // Validar que los campos requeridos no estén vacíos.
                if (contacto.getNombre().isEmpty() || contacto.getTelefono().isEmpty()) {
                    // Muestra un mensaje si el nombre o el teléfono están vacíos.
                    Toast.makeText(MainActivity.this, "Por favor, complete los campos obligatorios",
                            Toast.LENGTH_LONG).show();
                    return; // Termina la ejecución del método.
                }

                // Instancia de la clase DBHelper para interactuar con la base de datos.
                DBHelper dbHelper = new DBHelper(MainActivity.this);

                // Llama al método insertContacto para guardar el contacto en la base de datos.
                dbHelper.insertContacto(contacto);

                // Mostrar un mensaje de éxito al usuario.
                Toast.makeText(MainActivity.this, "Contacto guardado exitosamente",
                        Toast.LENGTH_LONG).show();

                // Limpia los campos del formulario después de guardar.
                limpiar();
            }
        });

        // Configura el comportamiento del diseño para manejar las barras del sistema (Edge-to-Edge).
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // Obtiene las dimensiones de las barras del sistema.
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Ajusta los márgenes del diseño según las barras del sistema.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets; // Devuelve los insets originales para que el sistema los aplique.
        });
    }

    /**
     * Método para limpiar los campos del formulario después de guardar un contacto.
     */
    private void limpiar() {
        // Establece el texto de los campos en vacío.
        txtNombre.setText("");   // Limpia el campo de texto para el nombre.
        txtTelefono.setText(""); // Limpia el campo de texto para el teléfono.
        txtCorreo.setText("");   // Limpia el campo de texto para el correo.
    }
}
