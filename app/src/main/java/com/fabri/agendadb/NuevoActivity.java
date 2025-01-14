package com.fabri.agendadb;

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

import com.fabri.agendadb.db.dbContactos;

/**
 * Clase para la actividad "NuevoActivity", que permite al usuario agregar un nuevo contacto a la base de datos.
 */
public class NuevoActivity extends AppCompatActivity {

    // Declaración de campos de texto para capturar el nombre, teléfono y correo del contacto.
    EditText txtNombre, txtTelefono, txtCorreo;
    // Botón para guardar el contacto en la base de datos.
    Button btnGuarda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Activa el diseño Edge-to-Edge para optimizar la interfaz en pantallas modernas.
        EdgeToEdge.enable(this);

        // Establece el diseño visual de la actividad utilizando el archivo XML correspondiente.
        setContentView(R.layout.activity_nuevo);

        // Vincula los elementos de la interfaz definidos en el diseño XML.
        txtNombre = findViewById(R.id.txtNombre);   // Campo de texto para el nombre.
        txtCorreo = findViewById(R.id.txtCorreo);   // Campo de texto para el correo.
        txtTelefono = findViewById(R.id.txtTelefono); // Campo de texto para el teléfono.
        btnGuarda = findViewById(R.id.btnGuardar);  // Botón para guardar el contacto.

        // Configura el evento clic para el botón "Guardar".
        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Instancia de la clase dbContactos para interactuar con la base de datos.
                dbContactos dbcontactos = new dbContactos(NuevoActivity.this);

                // Inserta el contacto en la base de datos utilizando los valores ingresados por el usuario.
                long id = dbcontactos.insertarContactos(
                        txtNombre.getText().toString(),    // Obtiene el texto del campo "Nombre".
                        txtTelefono.getText().toString(), // Obtiene el texto del campo "Teléfono".
                        txtCorreo.getText().toString()    // Obtiene el texto del campo "Correo".
                );

                // Verifica si el contacto fue guardado correctamente.
                if (id > 0) {
                    // Muestra un mensaje de éxito al usuario.
                    Toast.makeText(NuevoActivity.this, "Registro Guardado",
                            Toast.LENGTH_LONG).show();
                } else {
                    // Muestra un mensaje de error al usuario.
                    Toast.makeText(NuevoActivity.this, "Error al Guardar",
                            Toast.LENGTH_LONG).show();
                }
            }

            /**
             * Método para limpiar los campos de texto después de guardar un contacto.
             */
            private void limpiar() {
                txtNombre.setText("");   // Limpia el campo de texto para el nombre.
                txtTelefono.setText(""); // Limpia el campo de texto para el teléfono.
                txtCorreo.setText("");   // Limpia el campo de texto para el correo.
            }
        });

        // Ajusta los márgenes para evitar que la interfaz quede cubierta por las barras del sistema.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
