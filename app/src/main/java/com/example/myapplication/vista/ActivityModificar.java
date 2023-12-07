package com.example.myapplication.vista;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.myapplication.DAL.UpdateUser;
import com.example.myapplication.DAL.showDataUser;
import com.example.myapplication.R;
import com.example.myapplication.Usuario;

public class ActivityModificar extends AppCompatActivity {
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        toolbar = getSupportActionBar();
        toolbar.setTitle("Editar perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button update = findViewById(R.id.bUpdate);
        EditText newUsername = findViewById(R.id.etNusername);
        EditText newFullname = findViewById(R.id.etNfullname);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username= newUsername.getText().toString();
                String fullname = newFullname.getText().toString();
                if (username.isEmpty() && fullname.isEmpty()){
                    Toast.makeText(ActivityModificar.this, "No se ha introducido ningún dato", Toast.LENGTH_SHORT).show();
                }else{
                    String email = getIntent().getStringExtra("EMAIL");
                    Usuario usuario = new Usuario(email, username, fullname);
                    UpdateUser update = new UpdateUser();
                    update.modificarUser("https://uselessutilities.net/ProyetoDAM/modificarDatos.php", ActivityModificar.this, usuario, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if ((response != null) && (response.equalsIgnoreCase("1"))){
                                Log.d("RESPONSE_TAG", "Response from server: " + response);
                                Toast.makeText(ActivityModificar.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                                newUsername.setText("");
                                newFullname.setText("");
                                showDataUser mostrar = new showDataUser();
//                                Intent intent = new Intent(ActivityModificar.this, ActivityHome.class);
//                                startActivity(intent);
//                                finish();
                            }else{
                                Log.d("RESPONSE_TAG", "Response from server: " + response);
                                Toast.makeText(ActivityModificar.this, "No se han podido guardar los cambios", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            // Lógica para manejar la acción de la flecha de retroceso
            Intent intent = new Intent(this, ActivityHome.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}