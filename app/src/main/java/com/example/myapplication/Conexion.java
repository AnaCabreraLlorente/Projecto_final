//package com.example.myapplication;
//
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class Conexion {
//
//    // establecer conexion
//    private static final String URL = "192.168.99.2";
//    private static Retrofit retrofit = null;
//
//    // public method that returns connection
//
//    public static Retrofit getConnection(){
//
//        if(retrofit==null){
//            retrofit=new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
//        }
//        return retrofit;
//    }
//
//}
