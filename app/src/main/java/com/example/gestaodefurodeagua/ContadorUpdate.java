package com.example.gestaodefurodeagua;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ContadorUpdate extends AppCompatActivity {

    ImageButton voltar;
    Button actualizar, eliminar;
    EditText nrcontador;
    Spinner status, cliente;

    Toolbar toolbar;

    String txtid, txtnecontador, txtcliente, txtstatus;

    MyDataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador_update);

        voltar = findViewById(R.id.voltar);
        actualizar = findViewById(R.id.actualizar);
        eliminar = findViewById(R.id.eliminar);

        nrcontador = findViewById(R.id.nrcontador);
        status = findViewById(R.id.status);
        cliente = findViewById(R.id.cliente);

        toolbar = findViewById(R.id.toolbar);


        getAndSetIntentData();
       toolbar.setTitle(txtnecontador);
        toolbar.setTitleMarginStart(180);

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nrcontador.getText().toString().isEmpty() || cliente.getSelectedItem().toString().equals("Selecione o titular do contador") || status.getSelectedItem().toString().equals("Selecione o status")) {
                    Toast.makeText(getApplicationContext(), "Preencha corretmente todos os campos do formulario", Toast.LENGTH_SHORT).show();
                } else {
                    myDB = new MyDataBaseHelper(ContadorUpdate.this);
                    myDB.updateDataContador(txtid, nrcontador.getText().toString(), myDB.readIdCliente(cliente.getSelectedItem().toString()), status.getSelectedItem().toString());

                    Intent tela = new Intent(getApplicationContext(), Contadores.class);
                    startActivity(tela);
                    finish();
                }
                // Toast.makeText(getApplicationContext(), "ID: "+txtid+ " \nNome: "+nome.getText().toString()+" \nSenha: "+senha.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               confirmDialog();
            }
        });


        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tela = new Intent(getApplicationContext(), Contadores.class);
                startActivity(tela);
                finish();
            }
        });

    }


    void getAndSetIntentData(){
        if (getIntent().hasExtra("id")){
            txtid = getIntent().getStringExtra("id");
            myDB = new MyDataBaseHelper(ContadorUpdate.this);
            Cursor cursor = myDB.readOneContador(txtid);

            while (cursor.moveToNext()){
                txtnecontador = cursor.getString(1);
                txtcliente = myDB.readNameCliente(cursor.getString(2));
                txtstatus = cursor.getString(3);

            }

            nrcontador.setText(txtnecontador);


            ArrayList<String> statu = new ArrayList<>();
            statu.add(txtstatus);
            statu.add("Activo");
            statu.add("Desativado");

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, statu);
            status.setAdapter(adapter);

            ArrayList<String> client = new ArrayList<>();
            client.add(txtcliente);

            cursor = myDB.readNameCliente();
            while (cursor.moveToNext()){
                client.add(cursor.getString(0));
            }
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, client);
            cliente.setAdapter(adapter2);

        }else{
            Toast.makeText(getApplicationContext(), "Nao existem dados", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apagar "+txtnecontador+"?");
        builder.setMessage("Tem certeza que deseja eliminar o contador "+txtnecontador+"?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDB = new MyDataBaseHelper(ContadorUpdate.this);
                myDB.deleteOneContador(txtid);

                Intent tela = new Intent(getApplicationContext(), Contadores.class);
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