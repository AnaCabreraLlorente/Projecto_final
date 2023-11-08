package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().hide();

        BBDD_conexion conexion = new BBDD_conexion();
        try {
            conexion.conectar(this);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ImageButton botonAtras = findViewById(R.id.botonAtras);
        EditText email = findViewById(R.id.editTextTextEmailAddress);
        EditText fullname = findViewById(R.id.editTextNombreCompleto);
        EditText username = findViewById(R.id.editTextTextNombreUsuario);
        EditText password = findViewById(R.id.editTextTextPassword);
        TextView born_date = findViewById(R.id.born_date);
        Spinner spinner_mes = findViewById(R.id.spinner_mes);
        Spinner spinner_dia = findViewById(R.id.spinner_dia);
        Spinner spinner_year = findViewById(R.id.spinner_year);
        Button boton = findViewById(R.id.button);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                MainActivity3.this, R.array.meses, android.R.layout.simple_spinner_dropdown_item);
        spinner_mes.setAdapter(adapter);

        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(
                MainActivity3.this, R.array.dias, android.R.layout.simple_spinner_dropdown_item);
        spinner_dia.setAdapter(adapter1);

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(
                MainActivity3.this, R.array.years, android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setAdapter(adapter2);

        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String mail = email.getText().toString();
                String fullName = fullname.getText().toString();
                String usName = username.getText().toString();
                String pass = password.getText().toString();
                conexion.insertar(BBDD_conexion.encryptPassword(pass), false, usName, fullName, fullName, mail, false, true, timestamp);
                Toast.makeText(MainActivity3.this, "Usuario Registrado", Toast.LENGTH_LONG).show();
            }
        });

    }



}