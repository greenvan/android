package com.greenvan.notas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class NotasDB {

    private static Context context;
    public static void setContext(Context context){
        NotasDB.context=context;
    }

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



        public NotasDBHelper() {
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

    public static NotasDBHelper getHelper(){
        if(helper == null) {
            helper = new NotasDBHelper();
        }
        return helper;
    }

    public static ArrayList<Nota> loadNotas(){

        ArrayList<Nota> resultado = new ArrayList<>();
        if(helper == null) {
            helper = new NotasDBHelper();
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


    public static Nota nueva(String titulo, String texto) {
        Nota resultado = new Nota(titulo,texto);

        SQLiteDatabase db = getHelper().getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("titulo",titulo);
        values.put("texto",texto);

        long id = db.insert("Notas",null, values);
        resultado.setId(id);

        return resultado;
    }

    public static void actualiza(Nota nota) {

        SQLiteDatabase db = getHelper().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo",nota.getTitulo());
        values.put("texto",nota.getTexto());

        String where = "id = ?";
        String[] args = {Long.toString(nota.getId())};

        db.update("Notas",values, where, args);

    }
}
