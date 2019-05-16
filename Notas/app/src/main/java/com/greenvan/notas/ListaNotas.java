package com.greenvan.notas;

import java.util.ArrayList;

public class ListaNotas {
    private static ArrayList<Nota> notas;

    public static ArrayList<Nota> get(){
        if (notas ==null){
            notas = NotasDB.loadNotas();
        }
        return notas;
    }

    public static void nueva(String titulo, String texto) {
        Nota nueva_nota = NotasDB.nueva(titulo,texto);
        notas.add(nueva_nota);

    }

    public static void modifica(int position, String titulo, String texto) {
        Nota nota = notas.get(position);
        nota.setTitulo(titulo);
        nota.setTexto(texto);
        NotasDB.actualiza(nota);
    }

    public static Nota getNota(int pos){
        return notas.get(pos);
    }
}
