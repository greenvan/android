package com.greenvan.notas;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private NotasAdapter adapter;
    private ArrayList<Nota> notas;
    private ListView lista_notas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notas = new ArrayList<>();
        notas.add(new Nota("hola","que tal"));
        notas.add(new Nota("1234","567899fykjhk, hjkj   ihli7 86r ,j b.k -lh-ñ.oiy ñ08 79`8 88 ñ-olhlih jhvjhygjhygjuy  lvc ukyedr 5 oluf, v 9jvhjvnbv,kjfkuyu yyyyyyyyyyyyyyyyyy"));

        adapter = new NotasAdapter();

        lista_notas = (ListView)findViewById(R.id.lista_notas);
        lista_notas.setAdapter(new ArrayAdapter<Nota>(this,android.R.layout.simple_list_item_1,notas));

        lista_notas.setAdapter(adapter);
    }



    private class NotasAdapter extends ArrayAdapter<Nota>{

        NotasAdapter() {
            super(MainActivity.this,R.layout.item_lista_notas,notas);
        }


        @Override
        public View getView(int position,  View convertView, ViewGroup parent) {

            View result = convertView;
            if(result==null){
                LayoutInflater inflater = getLayoutInflater();
                result = inflater.inflate(R.layout.item_lista_notas,parent,false);
            }


            Nota nota = getItem(position);
            TextView titulo = (TextView)result.findViewById(R.id.tv_titulo);
            titulo.setText(nota.getTitulo());
            TextView resumen = (TextView)result.findViewById(R.id.tv_resumen);

            /*TODO Crear un resumen en lugar de el texto completo*/
            resumen.setText(nota.getTexto());


            return result;
        }
    }

}
