package com.greenvan.notas;

import android.content.Context;

import java.util.ArrayList;

public class ListaNotas {
    private static ArrayList<Nota> notas;

    public static ArrayList<Nota> get(Context context){
        if (notas ==null){
            notas = NotasDB.loadNotas(context);
        }
        return notas;
    }

    public static void nueva(String titulo, String texto) {
        Nota nueva_nota = new Nota(titulo,texto);
        notas.add(nueva_nota);
    }

    public static void modifica(int position, String titulo, String texto) {
        Nota nota = notas.get(position);
        nota.setTitulo(titulo);
        nota.setTexto(texto);
    }

    public static Nota getNota(int pos){
        return notas.get(pos);
    }
}
