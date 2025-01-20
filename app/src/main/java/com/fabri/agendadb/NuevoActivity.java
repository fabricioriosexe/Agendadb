package com.fabri.agendadb;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fabri.agendadb.db.DBHelper;
import com.fabri.agendadb.entidades.Contactos;

/**
 * Clase para la actividad "NuevoActivity", que permite al usuario agregar un nuevo contacto a la base de datos.
 */
public class NuevoActivity extends AppCompatActivity {

    // Declaración de campos de texto para capturar el nombre, teléfono y correo del contacto.
    EditText txtNombre, txtTelefono, txtCorreo;

    // Botón para guardar el contacto en la base de datos.
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Establece el diseño visual de la actividad utilizando el archivo XML correspondiente.
        setContentView(R.layout.activity_nuevo);

        // Vincula los elementos de la interfaz definidos en el diseño XML.
        txtNombre = findViewById(R.id.txtNombre);     // Campo de texto para el nombre.
        txtCorreo = findViewById(R.id.txtCorreo);     // Campo de texto para el correo.
        txtTelefono = findViewById(R.id.txtTelefono); // Campo de texto para el teléfono.
        btnGuardar = findViewById(R.id.btnGuardar);   // Botón para guardar el contacto.

        // Configura el evento clic para el botón "Guardar".
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear una instancia de la clase Contactos para almacenar los datos ingresados.
                Contactos contacto = new Contactos();
                contacto.setNombre(txtNombre.getText().toString());   // Obtiene el nombre.
                contacto.setTelefono(txtTelefono.getText().toString()); // Obtiene el teléfono.
                contacto.setCoreo(txtCorreo.getText().toString());     // Obtiene el correo.

                // Validar que los campos requeridos no estén vacíos.
                if (contacto.getNombre().isEmpty() || contacto.getTelefono().isEmpty()) {
                    Toast.makeText(NuevoActivity.this, "Por favor, complete los campos obligatorios",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // Instancia de la clase DBHelper para interactuar con la base de datos.
                DBHelper dbHelper = new DBHelper(NuevoActivity.this);

                // Llama al método insertContacto para guardar el contacto en la base de datos.
                dbHelper.insertContacto(contacto);

                // Mostrar un mensaje de éxito al usuario.
                Toast.makeText(NuevoActivity.this, "Contacto guardado exitosamente",
                        Toast.LENGTH_LONG).show();

                // Limpia los campos del formulario.
                limpiar();
            }
        });

        // Ajusta los márgenes para evitar que la interfaz quede cubierta por las barras del sistema.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Método para limpiar los campos del formulario después de guardar un contacto.
     */
    private void limpiar() {
        txtNombre.setText("");   // Limpia el campo de texto para el nombre.
        txtTelefono.setText(""); // Limpia el campo de texto para el teléfono.
        txtCorreo.setText("");   // Limpia el campo de texto para el correo.
    }
}
