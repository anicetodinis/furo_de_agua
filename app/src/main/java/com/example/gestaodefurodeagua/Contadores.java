package com.example.gestaodefurodeagua;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Contadores extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener, ExempleDialogContador.ExampleDialogListener{

    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout frameLayout;

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    Button back;

    String utilizador;

    ImageView imagem_vazia;
    TextView nodata, id, nrcontador, cliente, status;
    //Spinner cliente, status;

    ArrayList<String> contador_nr, contador_id, contador_cliente, contador_status;

    CustomAdapterContador customAdapterContador;

    MyDataBaseHelper myBD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contadores);

        id = findViewById(R.id.id);
        nrcontador = findViewById(R.id.nrcontador);
        cliente = findViewById(R.id.cliente);
        status = findViewById(R.id.status);
        back = findViewById(R.id.back);

        if (getIntent().hasExtra("utilizador")){
            utilizador = getIntent().getStringExtra("utilizador");
        }

        recyclerView = findViewById(R.id.recycleview);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tela = new Intent(getApplicationContext(), Contadores.class);
                startActivity(tela);
                finish();
            }
        });

        myBD = new MyDataBaseHelper(Contadores.this);
        contador_id = new ArrayList<>();
        contador_nr = new ArrayList<>();
        contador_cliente = new ArrayList<>();
        contador_status = new ArrayList<>();

        imagem_vazia = findViewById(R.id.image_vazia);
        nodata = findViewById(R.id.nodata);

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


        if (getIntent().hasExtra("nome")){
            String nome = getIntent().getStringExtra("nome");
            if (!nome.isEmpty()){
                storeDataInArrays(nome);
            }else{
                storeDataInArrays();
            }
        }else{
            storeDataInArrays();
        }
        customAdapterContador = new CustomAdapterContador(Contadores.this, this, contador_id, contador_nr, contador_cliente, contador_status);
        recyclerView.setAdapter(customAdapterContador);
        recyclerView.setLayoutManager(new LinearLayoutManager(Contadores.this));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        try {
            Cursor cursor = myBD.readContadores();
            if (cursor.getCount() == 0){
                imagem_vazia.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.VISIBLE);
            }else{
                while (cursor.moveToNext()){
                    contador_id.add(cursor.getString(0));
                    contador_nr.add("Numero de Contador: "+ cursor.getString(1));
                    contador_cliente.add("Cliente: "+ myBD.readNameCliente(cursor.getString(2)));
                    contador_status.add("Status: "+ cursor.getString(3));

                }
                imagem_vazia.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
            }
        }catch (Exception e) {
            AlertDialog.Builder msg = new AlertDialog.Builder(Contadores.this);
            msg.setTitle("Resultado");
            msg.setMessage("Oops! algo deu errado. ");
            msg.setNeutralButton("OK", null);
            msg.show();
        }
    }

    void storeDataInArrays(String id){
        try {
            Cursor cursor = myBD.searchContador(id);
            if (cursor.getCount() == 0){
                imagem_vazia.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);

            }else{
                while (cursor.moveToNext()){
                    contador_id.add(cursor.getString(0));
                    contador_nr.add("Numero de Contador: "+ cursor.getString(1));
                    contador_cliente.add("Cliente: "+ myBD.readNameCliente(cursor.getString(2)));
                    contador_status.add("Status: "+ cursor.getString(3));
                }
                imagem_vazia.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
                back.setVisibility(View.GONE);
            }
        }catch (Exception e) {
            AlertDialog.Builder msg = new AlertDialog.Builder(Contadores.this);
            msg.setTitle("Resultado");
            msg.setMessage("Oops! algo deu errado. ");
            msg.setNeutralButton("OK", null);
            msg.show();
        }
    }

    public void openDialog(){
        ExempleDialogContador exampleDialog = new ExempleDialogContador();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String nrcontador, String cliente, String status) {
        String id = null;
        if (nrcontador.isEmpty() || cliente.equals("Selecione o titular do contador") || status.equals("Selecione o status")) {
            Toast.makeText(getApplicationContext(), "Preencha corretmente todos os campos do formulario", Toast.LENGTH_SHORT).show();
        } else {
            MyDataBaseHelper myDB = new MyDataBaseHelper(Contadores.this);


            //Toast.makeText(this, " "+myDB.readIdCliente(cliente), Toast.LENGTH_SHORT).show();

            myDB.addContador(nrcontador, myDB.readIdCliente(cliente), status);

            Intent tela = new Intent(getApplicationContext(), Contadores.class);
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
                Toast.makeText(getApplicationContext(), "Pesquise atraves do nome do cliente ou numero de contador.", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                return true;
            }
        };

        menu.findItem(R.id.action_search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("pesquisar contador..");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent tela = new Intent(getApplicationContext(), Contadores.class);
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

        //return super.onCreateOptionsMenu(menu);
    }

    @Override
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

                drawerLayout.closeDrawer(GravityCompat.START);
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