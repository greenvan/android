package com.greenvan.notas;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int NUEVA_NOTA = 0;
    public static final int EDITA_NOTA = 1;
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


        lista_notas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onEditaNota(position);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode==RESULT_OK){

            String titulo = data.getStringExtra("titulo");
            String texto = data.getStringExtra("texto");
            int position = data.getIntExtra("position",-1);



            switch (requestCode){
                case NUEVA_NOTA:
                    Nota nueva_nota = new Nota(titulo,texto);
                    notas.add(nueva_nota);
                    adapter.notifyDataSetChanged();
                    break;
                case EDITA_NOTA:
                    Nota nota = notas.get(position);
                    nota.setTitulo(titulo);
                    nota.setTexto(texto);
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }

        }



    }

    private void onEditaNota(int position) {
        Nota nota = notas.get(position);
        Intent intent = new Intent(this,EditaNotaActivity.class);
        intent.putExtra("titulo", nota.getTitulo());
        intent.putExtra("texto",nota.getTexto());
        intent.putExtra("position", position);
        startActivityForResult(intent, EDITA_NOTA);

    }

    public void onNuevaNota(View view) {
        Intent intent = new Intent(this, EditaNotaActivity.class);
        startActivityForResult(intent, NUEVA_NOTA);

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
            resumen.setText(nota.getTexto().replace("\n"," "));


            return result;
        }
    }

}
