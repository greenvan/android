package com.greenvan.notas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class NotasDB {


    static class NotasDBHelper extends SQLiteOpenHelper{

        private static final String DATABASE_NAME = "notas.db";
        private static final int DATABASE_VERSION = 1;
        private static final String SQL_CREATE_TABLA_NOTAS =
                "CREATE TABLE Notas (" +
                        "id INTEGER PRIMARY KEY,"+
                        "titulo TEXT,"+
                        "texto TEXT"+
                        ");"
                ;



        public NotasDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TABLA_NOTAS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //No hacemos nada
        }
    }

    private static NotasDBHelper helper;

    public static ArrayList<Nota> loadNotas(Context context){

        ArrayList<Nota> resultado = new ArrayList<>();
        if(helper == null) {
            helper = new NotasDBHelper(context);
        }
        SQLiteDatabase db = helper.getReadableDatabase();

        //La proyección o vista
        String[] columnas = {"id","titulo","texto"};

        Cursor c =db.query("Notas",columnas,null,null,null,null,null);

        if(c!=null && c.getCount()>0){
            while (c.moveToNext()){
                //long id = c.getLong(c.getColumnIndexOrThrow("id"));
                String titulo = c.getString(c.getColumnIndexOrThrow("titulo"));
                String texto = c.getString(c.getColumnIndexOrThrow("texto"));

                resultado.add(new Nota(titulo,texto));
            }
        }
        if(c!=null) c.close();
        db.close();

        return resultado;
    }
}
