package com.example.gestaodefurodeagua;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class UserUpdate extends AppCompatActivity {

    ImageButton voltar;
    Button actualizar, eliminar;
    EditText nome, status, senha;
    Toolbar toolbar;

    String txtnome, txtactivo, txtid, txtsenha = "1234";

    MyDataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);

        voltar = findViewById(R.id.voltar);
        actualizar = findViewById(R.id.actualizar);
        eliminar = findViewById(R.id.eliminar);

        nome = findViewById(R.id.nome);
        status = findViewById(R.id.status);
        senha = findViewById(R.id.senha);

        toolbar = findViewById(R.id.toolbar);


        getAndSetIntentData();
        /*ActionBar ab = getSupportActionBar();
        ab.setTitle(txtnome);*/
        toolbar.setTitle(txtnome);
        toolbar.setTitleMarginStart(180);


        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tela = new Intent(getApplicationContext(), Users.class);
                startActivity(tela);
                finish();

            }
        });



        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB = new MyDataBaseHelper(UserUpdate.this);
                myDB.updateDataUser(txtid, nome.getText().toString(), status.getText().toString(), senha.getText().toString());

                Intent tela = new Intent(getApplicationContext(), Users.class);
                startActivity(tela);
                finish();
               // Toast.makeText(getApplicationContext(), "ID: "+txtid+ " \nNome: "+nome.getText().toString()+" \nSenha: "+senha.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }


    void getAndSetIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nome") && getIntent().hasExtra("activo")){

            //Getting data from Intent
            txtid = getIntent().getStringExtra("id");
            txtnome = getIntent().getStringExtra("nome");
            txtactivo = getIntent().getStringExtra("activo");

            myDB = new MyDataBaseHelper(UserUpdate.this);
            Cursor cursor = myDB.readUser(txtid);

            while (cursor.moveToNext()){
                txtnome = cursor.getString(1);
                txtactivo = cursor.getString(2);
                txtsenha = cursor.getString(3);
            }

            //Setting Intent data
            nome.setText(txtnome);
            senha.setText(txtsenha);
            status.setText(txtactivo);

        }else{
            Toast.makeText(getApplicationContext(), "Nao existem dados", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apagar "+txtnome+"?");
        builder.setMessage("Tem certeza que deseja eliminar o utilizador "+txtnome+"?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDB = new MyDataBaseHelper(UserUpdate.this);
                myDB.deleteOneUser(txtid);

                Intent tela = new Intent(getApplicationContext(), Users.class);
                startActivity(tela);
                finish();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }


}