package com.greenvan.notas;

import java.util.ArrayList;

public class ListaNotas {
    private static ArrayList<Nota> notas;

    public static ArrayList<Nota> get(){
        if (notas ==null){
            notas = new ArrayList<>();
            notas.add(new Nota("hola","que tal"));
            notas.add(new Nota("1234","567899fykjhk, hjkj   ihli7 86r ,j b.k -lh-ñ.oiy ñ08 79`8 88 ñ-olhlih jhvjhygjhygjuy  lvc ukyedr 5 oluf, v 9jvhjvnbv,kjfkuyu yyyyyyyyyyyyyyyyyy"));
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
