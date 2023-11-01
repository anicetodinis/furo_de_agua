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

public class ClienteUpdate extends AppCompatActivity {

    ImageButton voltar;
    Button actualizar, eliminar;
    EditText nome, endereco, telefone;
    Spinner status;
    Toolbar toolbar;

    String txtnome, txtendereco, txtid, txttelefone, txtstatus;

    MyDataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_update);

        voltar = findViewById(R.id.voltar);
        actualizar = findViewById(R.id.actualizar);
        eliminar = findViewById(R.id.eliminar);

        nome = findViewById(R.id.nome);
        endereco = findViewById(R.id.endereco);
        telefone = findViewById(R.id.telefone);
        status = findViewById(R.id.status);

        toolbar = findViewById(R.id.toolbar);


        getAndSetIntentData();
        toolbar.setTitle(txtnome);
        toolbar.setTitleMarginStart(180);


        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB = new MyDataBaseHelper(ClienteUpdate.this);
                myDB.updateDataCliente(txtid, nome.getText().toString(), endereco.getText().toString(), telefone.getText().toString(), status.getSelectedItem().toString());

                Intent tela = new Intent(getApplicationContext(), Clientes.class);
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


        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tela = new Intent(getApplicationContext(), Clientes.class);
                startActivity(tela);
                finish();
            }
        });
    }

    void getAndSetIntentData(){
        if (getIntent().hasExtra("id")){
            txtid = getIntent().getStringExtra("id");
            myDB = new MyDataBaseHelper(ClienteUpdate.this);
            Cursor cursor = myDB.readOneCliente(txtid);

            while (cursor.moveToNext()){
                txtnome = cursor.getString(1);
                txtendereco = cursor.getString(2);
                txttelefone = cursor.getString(3);
                txtstatus = cursor.getString(4);
            }

            nome.setText(txtnome);
            endereco.setText(txtendereco);
            telefone.setText(txttelefone);

            ArrayList<String> statu = new ArrayList<>();
            statu.add(txtstatus);
            statu.add("Activo");
            statu.add("Desativado");

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, statu);

            status.setAdapter(adapter);


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
                myDB = new MyDataBaseHelper(ClienteUpdate.this);
                myDB.deleteOneCliente(txtid);

                Intent tela = new Intent(getApplicationContext(), Clientes.class);
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