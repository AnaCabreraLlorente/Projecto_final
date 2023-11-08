package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;

public class bbdd_conect {
    private Connection conexio;

    private String url = "jdbc:postgresql://%s:%d/%s";
    private final String host = "instanciaproyecto.cqj6xncvyxbs.eu-north-1.rds.amazonaws.com";
    private final String database = "postgres";
    private final int port = 5432;
    private final String user = "administrador";
    private final String pass = "12345678";


    public void conectarAsync(Context context, OnConnectionListener listener) {

        new AsyncTask<Void, Void, Connection>() {
                @Override
                protected Connection doInBackground(Void... voids) {
                    try {
                        url = String.format(url, host, port, database);
                        Class.forName("org.postgresql.Driver");
                        Connection conexion = DriverManager.getConnection(url, user, pass);
                        return conexion;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(Connection conexion) {
                    if (conexion != null) {
                        listener.onConnectionSuccessful(conexion);
                    } else {
                        listener.onConnectionFailed();
                    }
                }
            }.execute();
        }

        public interface OnConnectionListener {
            void onConnectionSuccessful(Connection conexion);
            void onConnectionFailed();
        }
    }


