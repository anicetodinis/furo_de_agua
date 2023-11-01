package com.example.gestaodefurodeagua;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;





public class Admin1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout frameLayout;

    TextView users, clientes, contadores, facturas, username;
    public static String utilizador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin1);



        if (getIntent().hasExtra("utilizador")){
            utilizador = getIntent().getStringExtra("utilizador");
            //Toast.makeText(getApplicationContext(), "id: "+utilizador, Toast.LENGTH_SHORT).show();
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = findViewById(R.id.main_frameLayout);

        drawerLayout = findViewById(R.id.drawerLayout);

        navigationView = findViewById(R.id.navigation);

        users = findViewById(R.id.users);
        clientes = findViewById(R.id.clientes);
        contadores = findViewById(R.id.contadores);
        facturas = findViewById(R.id.facturas);


        MyDataBaseHelper myDB = new MyDataBaseHelper(Admin1.this);
        users.setText(myDB.countUsers());
        clientes.setText(myDB.countClientes());
        contadores.setText(myDB.countContadores());
        facturas.setText(myDB.countFacturas());

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Menu1 menu = new Menu1("Aniceto Dinis");

    }



    public  void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            Intent tela = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(tela);
            finish();
        }
    }

    /*public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.sidemenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case R.id.home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.users:
                Toast.makeText(this, "users", Toast.LENGTH_SHORT).show();
                Intent tela = new Intent(getApplicationContext(), Users.class);
                startActivity(tela);
                finish();
                break;
            case R.id.contador:
                Toast.makeText(this, "Contador", Toast.LENGTH_SHORT).show();
                break;
            case R.id.perfil:
                Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sair:
                Toast.makeText(this, "Sair", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                //Toast.makeText(this, "users", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.users:
                Intent tela = new Intent(getApplicationContext(), Users.class);
                tela.putExtra("utilizador", utilizador);
                startActivity(tela);
                finish();
                break;
            case R.id.cliente:
                Intent t = new Intent(getApplicationContext(), Clientes.class);
                t.putExtra("utilizador", utilizador);
                startActivity(t);
                finish();
                break;
            case R.id.contador:
                Intent t2 = new Intent(getApplicationContext(), Contadores.class);
                t2.putExtra("utilizador", utilizador);
                startActivity(t2);
                finish();
                break;
            case R.id.factura:
                Intent t3 = new Intent(getApplicationContext(), Facturas.class);
                t3.putExtra("utilizador", utilizador);
                startActivity(t3);
                finish();
                break;
            case R.id.perfil:
                Intent t6 = new Intent(getApplicationContext(), Perfil.class);
                t6.putExtra("utilizador", utilizador);
                startActivity(t6);
                finish();
                break;
            case R.id.sair:
                Intent t4 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(t4);
                finish();
                break;
        }
        return true;
    }
}