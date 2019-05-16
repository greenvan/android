package com.greenvan.notas;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public static final int NUEVA_NOTA = 0;
    public static final int EDITA_NOTA = 1;
    private NotasAdapter adapter;
    private ListView lista_notas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotasDB.setContext(this);

        adapter = new NotasAdapter();

        lista_notas = (ListView)findViewById(R.id.lista_notas);
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
            switch (requestCode){
                case NUEVA_NOTA:
                case EDITA_NOTA:
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void onEditaNota(int position) {
        Nota nota = ListaNotas.getNota(position);
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
            super(MainActivity.this,
                    R.layout.item_lista_notas,
                    ListaNotas.get()
            );
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
