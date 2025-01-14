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

public class NuevoActivity extends AppCompatActivity {
    EditText txtNombre,txtTelefono,txtCorreo;
    Button btnGuarda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nuevo);

        txtNombre = findViewById(R.id.txtNombre);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtTelefono = findViewById(R.id.txtTelefono);
        btnGuarda = findViewById(R.id.btnGuardar);


        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbContactos dbcontactos = new dbContactos(NuevoActivity.this);
                long id = dbcontactos.insertarContactos(txtNombre.getText().toString(),
                        txtTelefono.getText().toString(),
                        txtCorreo.getText().toString());


                if (id > 0){
                    Toast.makeText(NuevoActivity.this,"Registro Guardado",
                            Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(NuevoActivity.this,"Error al Guardar",
                            Toast.LENGTH_LONG).show();
                }
            }

            private void limpiar(){
                txtNombre.setText("");
                txtTelefono.setText("");
                txtCorreo.setText("");
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}