package com.example.myapplication.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.myapplication.R;
import com.example.myapplication.Usuario;
import com.example.myapplication.progressDialog;
import com.example.myapplication.DAL.verificarLogin;


public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        Button botonInicio = findViewById(R.id.button);
        ImageView textViewLogo = findViewById(R.id.textViewLogo);
        EditText etEmail = findViewById(R.id.editTextTextEmailAddress);
        EditText etPassword = findViewById(R.id.editTextTextPassword);
        Button OlvPass = findViewById(R.id.olvidoPass);
        TextView registro = findViewById(R.id.registro);

        OlvPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, ActivityRecuperarPass.class);
                startActivity(intent);
                finish();
            }
        });
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_registrer = new Intent(ActivityLogin.this, ActivityRegistro.class);
                startActivity(intent_registrer);
                finish();
            }

        });

        botonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if (password.isEmpty() ||email.isEmpty()) {
                    Toast.makeText(ActivityLogin.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                }else {
                    verificarLogin login = new verificarLogin();
                    Usuario usuario = new Usuario(email, password);
                    login.login("https://uselessutilities.net/ProyetoDAM/login.php",
                            ActivityLogin.this, usuario, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("RESPONSE_TAG", "Response from server: " + response); // Agregar esta línea para ver la respuesta del servidor en los logs
                                    if (response.equals("1")){
                                        Toast.makeText(ActivityLogin.this, "Logeado", Toast.LENGTH_SHORT).show();
//                                        progressDialog dialog = new progressDialog(ActivityLogin.this);
//                                        dialog.show();
                                        Intent intent_registrer = new Intent(ActivityLogin.this, ActivityHome.class);
                                        intent_registrer.putExtra("EMAIL", email);
                                        intent_registrer.putExtra("CONTRASEÑA", password);
                                        startActivity(intent_registrer);
                                        finish();

                                    } else {
                                        Toast.makeText(ActivityLogin.this,
                                                "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                                    }
                                }



                            });


                }}


});}}