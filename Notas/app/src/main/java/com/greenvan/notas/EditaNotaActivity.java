package com.greenvan.notas;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.List;

public class EditaNotaActivity extends AppCompatActivity {

    private int position = -1;
    private EditText edit_titulo;
    private EditText edit_texto;
    private Nota original;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_nota);

        edit_titulo = (EditText)findViewById(R.id.edit_titulo);
        edit_texto = (EditText) findViewById(R.id.edit_texto);

        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);

        if(position!=-1){
            original = ListaNotas.getNota(position);
            edit_titulo.setText(original.getTitulo());
            edit_texto.setText(original.getTexto());
        } else{
            original=new Nota();
        }
    }

    private boolean hayCambios(){
        String titulo = edit_titulo.getText().toString();
        String texto = edit_texto.getText().toString();
        return !original.getTitulo().equals(titulo)|| !original.getTexto().equals(texto);
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
               String titulo = edit_titulo.getText().toString();
               String texto =edit_texto.getText().toString();
               if (position!=-1) {
                   ListaNotas.modifica(position,titulo,texto);
               } else {
                   ListaNotas.nueva(titulo,texto);
               }
               setResult(RESULT_OK);
               finish();
               return true;
           case R.id.borrar:
               AlertDialog.Builder builder = new AlertDialog.Builder(this);
               builder.setTitle(R.string.confirmation);
               builder.setMessage(R.string.confirm_delete);
               builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       ListaNotas.borra(position);
                       setResult(RESULT_OK);
                       finish();
                   }
               });
               builder.setNegativeButton(android.R.string.cancel, null);
               builder.create().show();
       }

       return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(hayCambios()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.confirmation);
            builder.setMessage(R.string.confirm_message);
            builder.setPositiveButton(R.string.ignore, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditaNotaActivity.super.onBackPressed();
                }
            });
            builder.setNegativeButton(R.string.continue_edit, null);
            builder.create().show();
        } else{
            super.onBackPressed();
        }


    }

}
