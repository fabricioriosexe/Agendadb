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

public class MainActivity extends AppCompatActivity {

    RecyclerView listaContactos;
    ArrayList<Contactos> listaArrayContactos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        listaContactos.findViewById(R.id.listaContactos);
        listaContactos.setLayoutManager(new LinearLayoutManager(this));

        dbContactos dbcontactos = new dbContactos(MainActivity.this);
        listaArrayContactos = new ArrayList<>();


        ListaContactosAdapter adapter = new ListaContactosAdapter(dbcontactos.mostrarContactos());
        listaContactos.setAdapter(adapter);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() ==R.id.menuNuevo ) {
            nuevoRegistro();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    private void nuevoRegistro(){
        Intent intent = new Intent(this,NuevoActivity.class);
        startActivity(intent);
    }
}