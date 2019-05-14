package com.greenvan.notas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Nota> notas;
    private ListView lista_notas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notas = new ArrayList<>();
        notas.add(new Nota("hola","que tal"));
        notas.add(new Nota("1234","567899"));

        lista_notas = (ListView)findViewById(R.id.lista_notas);
        lista_notas.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                notas
        ));
    }
}
