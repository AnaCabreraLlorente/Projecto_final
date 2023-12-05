package com.example.myapplication.vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.myapplication.DAL.showDataUser;
import com.example.myapplication.FragmentHome;
import com.example.myapplication.FragmentoPerfil;
import com.example.myapplication.R;
import com.example.myapplication.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityHome extends AppCompatActivity {

    private FragmentoPerfil fragmentoPerfil;
    private FragmentHome fragmentHome;
    private ActionBar toolbar;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
           String email = getIntent().getStringExtra("EMAIL");
           String password = getIntent().getStringExtra("CONTRASEÑA");
            switch (item.getItemId()) {
                case R.id.navigation_perfil:
                    fragmentoPerfil = new FragmentoPerfil();
                    loadProfileFragment(email, password);
                    openFragment(fragmentoPerfil);
                    return true;
                case R.id.navigation_feed:
                    toolbar.setTitle("Home");
                    fragmentHome = new FragmentHome();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragmentHome);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                case R.id.navigation_sesion:
                    Intent intent_home = new Intent(ActivityHome.this, ActivityLogin.class);
                    startActivity(intent_home);
                    finish();
                    // Tu lógica para la pestaña de artistas
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_prueba);
        toolbar = getSupportActionBar();
        toolbar.setTitle("Home");

        BottomNavigationView bottomNavigation = findViewById(R.id.navigationView);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Button b_perfil = findViewById(R.id.button);

//        String email = getIntent().getStringExtra("EMAIL");
//        String password = getIntent().getStringExtra("CONTRASEÑA");


        //b_perfil.setOnClickListener(new View.OnClickListener() {

//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ActivityHome.this, ActivityDatosUser.class);
//                intent.putExtra("EMAIL", email);
//                intent.putExtra("CONTRASEÑA", password);
//                startActivity(intent);
//                finish();
//
//            }
//        });


    }
    private void loadProfileFragment(String email, String password) {
        if (email != null && password != null) {
            Usuario user = new Usuario(email, password);
            showDataUser data = new showDataUser();
            data.datosUser("https://uselessutilities.net/ProyetoDAM/getDataUser.php",
                    ActivityHome.this, user, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d("Response", response);
                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");
                                if (message.equals("1")) {
                                    String userName = jsonObject.getString("username");
                                    String fullName = jsonObject.getString("fullname");
                                    String userEmail = jsonObject.getString("email");
                                    displayProfileFragment(userName, fullName, userEmail);
                                }
                            } catch (JSONException e) {
                                Log.e("JSONException", "Error parsing JSON: " + e.getMessage());
                            }
                        }
                    });
        } else {
            Log.e("ActivityDatosUser", "et_email o et_password es nulo");
        }
    }

    private void displayProfileFragment(String userName, String fullName, String userEmail) {
        toolbar.setTitle("Perfil");

        // Cargar el fragmento de perfil si aún no está cargado
        if (fragmentoPerfil == null) {
            fragmentoPerfil = new FragmentoPerfil();
            openFragment(fragmentoPerfil);
        }

        // Actualizar la información del perfil
        if (fragmentoPerfil != null) {
            fragmentoPerfil.updateProfileInfo(userName, fullName, userEmail);
        }
    }

    private void openFragment(FragmentoPerfil fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}