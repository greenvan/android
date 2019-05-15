package com.greenvan.notas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class EditaNotaActivity extends AppCompatActivity {

    private int position = -1;
    private EditText edit_titulo;
    private EditText edit_texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_nota);

        edit_titulo = (EditText)findViewById(R.id.edit_titulo);
        edit_texto = (EditText) findViewById(R.id.edit_texto);

        Intent intent = getIntent();

        if(intent.hasExtra("titulo")){
            edit_titulo.setText(intent.getStringExtra("titulo"));
            edit_texto.setText(intent.getStringExtra("texto"));
            position = intent.getIntExtra("position",-1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edita_nota_options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.guardar:
               Intent data = new Intent();
               data.putExtra("titulo",edit_titulo.getText().toString());
               data.putExtra("texto",edit_texto.getText().toString());
               if (position!=-1) data.putExtra("position",position);
               setResult(RESULT_OK, data);
               finish();
               return true;
       }

       return super.onOptionsItemSelected(item);
    }
}
