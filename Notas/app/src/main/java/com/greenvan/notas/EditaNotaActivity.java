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
        String action = intent.getAction();
        position = intent.getIntExtra("position",-1);

        if(action!=null && action.equals(Intent.ACTION_SEND)){
            //Caso 0: Una app externa comparte un texto con nosotros
            original= new Nota();
            String titulo = intent.getStringExtra(Intent.EXTRA_SUBJECT);
            String texto = intent.getStringExtra(Intent.EXTRA_TEXT);
            edit_titulo.setText(titulo);
            edit_texto.setText(texto);
        } else if(position!=-1){
            //Caso 1:  Editar una nota desde la app de notas
            original = ListaNotas.getNota(position);
            edit_titulo.setText(original.getTitulo());
            edit_texto.setText(original.getTexto());
        } else{
            //Caso 2: Nueva nota desde nuestra app
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

        String titulo = edit_titulo.getText().toString();
        String texto =edit_texto.getText().toString();

       switch (item.getItemId()){
           case R.id.guardar:
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
               return true;
           case R.id.compartir:
               Intent sendIntent = new Intent();
               sendIntent.setAction(Intent.ACTION_SEND);
               sendIntent.putExtra(Intent.EXTRA_SUBJECT,titulo);
               sendIntent.putExtra(Intent.EXTRA_TEXT, texto);
               sendIntent.setType("text/plain");
               /*Utilizamos un chooser en lugar de startActivity(sendIntent).
               Por si no hubiera app con quien compartir o para mostrar siempre la acción*/
               Intent chooser =
                       Intent.createChooser(sendIntent,getResources().getText(R.string.send_to));
               startActivity(chooser);
               return true;

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
