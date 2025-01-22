package com.fabri.agendadb.actividades;

// Importación de las librerías necesarias
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fabri.agendadb.R;
import com.fabri.agendadb.db.DBHelper;
import com.fabri.agendadb.entidades.Contactos;

public class verActivity extends AppCompatActivity {

    // Declaración de las variables miembro
    private EditText editNombre, editTelefono, editCorreo;
    private Button btnGuardar;
    private DBHelper dbHelper;
    private int contactoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);  // Establece el layout de la actividad

        // Inicializar vistas (enlace de los elementos del layout con las variables)
        editNombre = findViewById(R.id.edit_nombre);  // EditText para el nombre
        editTelefono = findViewById(R.id.edit_telefono);  // EditText para el teléfono
        editCorreo = findViewById(R.id.edit_correo);  // EditText para el correo
        btnGuardar = findViewById(R.id.btn_guardar);  // Botón para guardar los cambios

        // Inicializar DBHelper (para interactuar con la base de datos)
        dbHelper = new DBHelper(this);

        // Obtener el ID del contacto desde el Intent
        Intent intent = getIntent();  // Obtiene el Intent que inició esta actividad
        contactoId = intent.getIntExtra("contacto_id", -1);  // Extrae el ID del contacto

        // Verificar que el ID del contacto sea válido
        if (contactoId != -1) {
            cargarDatosContacto(contactoId);  // Si el ID es válido, carga los datos del contacto
        } else {
            // Si el ID no es válido, muestra un mensaje de error y cierra la actividad
            Toast.makeText(this, "Error al cargar el contacto", Toast.LENGTH_SHORT).show();
            finish();  // Finaliza la actividad y regresa a la anterior
        }

        // Configurar el botón "Guardar cambios"
        btnGuardar.setOnClickListener(v -> actualizarContacto());  // Al hacer clic en el botón, se llama al método para actualizar el contacto
    }

    // Método para cargar los datos del contacto en los campos de texto
    private void cargarDatosContacto(int id) {
        // Buscar el contacto por su ID en la base de datos
        for (Contactos contacto : dbHelper.buscarContactos()) {
            if (contacto.getId() == id) {  // Si el ID coincide, se carga la información en los EditText
                editNombre.setText(contacto.getNombre());  // Establece el nombre
                editTelefono.setText(contacto.getTelefono());  // Establece el teléfono
                editCorreo.setText(contacto.getCoreo());  // Establece el correo
                return;  // Sale del método una vez que se encuentran los datos
            }
        }
    }

    // Método para actualizar los datos del contacto en la base de datos
    private void actualizarContacto() {
        // Crear un objeto Contactos con los datos actualizados
        Contactos contacto = new Contactos();
        contacto.setId(contactoId);  // Establece el ID del contacto
        contacto.setNombre(editNombre.getText().toString());  // Establece el nuevo nombre
        contacto.setTelefono(editTelefono.getText().toString());  // Establece el nuevo teléfono
        contacto.setCoreo(editCorreo.getText().toString());  // Establece el nuevo correo

        // Llamar al método de actualización en DBHelper
        dbHelper.updateContacto(contacto);  // Actualiza el contacto en la base de datos

        // Muestra un mensaje de éxito
        Toast.makeText(this, "Contacto actualizado correctamente", Toast.LENGTH_SHORT).show();
        finish();  // Cierra la actividad y regresa a la lista de contactos
    }
}
