package com.example.gestaodefurodeagua;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Users extends AppCompatActivity implements  ExampleDialog.ExampleDialogListener, NavigationView.OnNavigationItemSelectedListener{

    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout frameLayout;

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    TextView nome, senha, activo;
    ImageView imagem_vazia;
    TextView nodata;
    Button back;

    String utilizador;

    MyDataBaseHelper myBD;
    ArrayList <String> user_id,  user_name, user_pass, user_active;

    CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        recyclerView = findViewById(R.id.recycleview);
        add_button = findViewById(R.id.add_button);

        imagem_vazia = findViewById(R.id.image_vazia);
        nodata = findViewById(R.id.nodata);
        back = findViewById(R.id.back);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tela = new Intent(getApplicationContext(), Users.class);
                startActivity(tela);
                finish();
            }
        });

        myBD = new MyDataBaseHelper(Users.this);
        user_id = new ArrayList<>();
        user_name = new ArrayList<>();
        user_pass = new ArrayList<>();
        user_active = new ArrayList<>();

        if (getIntent().hasExtra("utilizador")){
            utilizador = getIntent().getStringExtra("utilizador");
            //Toast.makeText(getApplicationContext(), "id: "+utilizador, Toast.LENGTH_SHORT).show();
        }

        if (getIntent().hasExtra("nome")){
            String nome = getIntent().getStringExtra("nome");
            if (!nome.isEmpty()){
                storeDataInArrays(myBD.readIdUser(nome));
            }else{
                storeDataInArrays();
            }
        }else{
            storeDataInArrays();
        }


        customAdapter = new CustomAdapter(Users.this, this, user_id, user_name, user_pass, user_active);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Users.this));


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = findViewById(R.id.main_frameLayout);

        drawerLayout = findViewById(R.id.drawerLayout);

        navigationView = findViewById(R.id.navigation);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor = myBD.readData();
        if (cursor.getCount() == 0){
            imagem_vazia.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                user_id.add(cursor.getString(0));
                user_name.add("Nome: "+cursor.getString(1));
                user_pass.add(""+cursor.getString(3));
                user_active.add("Status: "+cursor.getString(2));
            }
            imagem_vazia.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }


    void storeDataInArrays(String id){
        Cursor cursor = myBD.readUser(id);
        if (cursor.getCount() == 0){
            imagem_vazia.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                user_id.add(cursor.getString(0));
                user_name.add("Nome: "+cursor.getString(1));
                user_pass.add(""+cursor.getString(3));
                user_active.add("Status: "+cursor.getString(2));
            }
            imagem_vazia.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    public void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String nome, String senha, String activo) {

        if (nome.isEmpty() || senha.isEmpty() || activo.equals("Selecione o status")) {
            Toast.makeText(getApplicationContext(), "Preencha corretmente todos os campos do formulario", Toast.LENGTH_SHORT).show();
        } else {
            MyDataBaseHelper myDB = new MyDataBaseHelper(Users.this);
            myDB.addUser(nome, senha, activo);

            Intent tela = new Intent(getApplicationContext(), Users.class);
            startActivity(tela);
            finish();
        }
    }

    public  void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            Intent tela = new Intent(getApplicationContext(), Admin1.class);
            startActivity(tela);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(getApplicationContext(), "Pesquise atraves do nome do utilizador.", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                return true;
            }
        };

        menu.findItem(R.id.action_search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("pesquisar utilizador");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent tela = new Intent(getApplicationContext(), Users.class);
                tela.putExtra("nome", query);
                startActivity(tela);
                finish();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //customAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
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
                drawerLayout.closeDrawer(GravityCompat.START);
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