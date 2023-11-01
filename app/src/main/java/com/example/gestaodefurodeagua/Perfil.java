package com.example.gestaodefurodeagua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class Perfil extends AppCompatActivity implements ExampleDialogSenha.ExampleDialogListener, NavigationView.OnNavigationItemSelectedListener {

    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout frameLayout;

    MyDataBaseHelper myBD;

    Button alterar;

    String utilizador;

    TextView id, nome, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        id = findViewById(R.id.id);
        nome = findViewById(R.id.nome);
        status = findViewById(R.id.status);
        alterar = findViewById(R.id.alterar);

        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("utilizador")){
            utilizador = getIntent().getStringExtra("utilizador");
        }
        myBD = new MyDataBaseHelper(Perfil.this);
        Cursor cursor = myBD.user(utilizador);

        while (cursor.moveToNext()){
            id.setText("ID: "+cursor.getString(0));
            nome.setText("Nome: "+cursor.getString(1));
            status.setText("Status: "+cursor.getString(2));
        }


        frameLayout = findViewById(R.id.main_frameLayout);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void openDialog(){
        ExampleDialogSenha exampleDialog = new ExampleDialogSenha();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                //Toast.makeText(this, "users", Toast.LENGTH_SHORT).show();
                Intent tela = new Intent(getApplicationContext(), Admin1.class);
                tela.putExtra("utilizador", utilizador);
                startActivity(tela);
                finish();
                break;
            case R.id.users:
                Intent t = new Intent(getApplicationContext(), Users.class);
                t.putExtra("utilizador", utilizador);
                startActivity(t);
                finish();
                break;
            case R.id.cliente:
                Intent t2 = new Intent(getApplicationContext(), Clientes.class);
                t2.putExtra("utilizador", utilizador);
                startActivity(t2);
                finish();
                break;
            case R.id.contador:
                Intent t3 = new Intent(getApplicationContext(), Contadores.class);
                t3.putExtra("utilizador", utilizador);
                startActivity(t3);
                finish();
                break;
            case R.id.factura:
                Intent t6 = new Intent(getApplicationContext(), Facturas.class);
                t6.putExtra("utilizador", utilizador);
                startActivity(t6);
                finish();
                break;
            case R.id.perfil:

                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.sair:
                Intent t4 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(t4);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void applyTexts(String senha1, String senha2, String senha3) {
        if (senha1.isEmpty() || senha2.isEmpty() || senha3.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha corretmente todos os campos do formulario", Toast.LENGTH_SHORT).show();
        } else {

            if (senha2.equals(senha3)){
                MyDataBaseHelper myDB = new MyDataBaseHelper(Perfil.this);
                myDB.alterarSenha(utilizador, senha1, senha2);
            }else{
                Toast.makeText(getApplicationContext(), "Confirmação de senha invalida!", Toast.LENGTH_SHORT).show();
            }


            //myDB.addCliente(nome, endereco, telefone,status);

            /*Intent tela = new Intent(getApplicationContext(), Clientes.class);
            startActivity(tela);
            finish();*/
        }
    }
}