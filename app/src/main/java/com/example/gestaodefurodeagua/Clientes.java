package com.example.gestaodefurodeagua;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.ArrayAdapter;
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

public class Clientes extends AppCompatActivity implements ExampleDialogCliente.ExampleDialogListener, NavigationView.OnNavigationItemSelectedListener {

    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout frameLayout;

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    ImageView imagem_vazia;
    TextView nodata, id, nome, endereco, telefone, status;
    Button back;

    Spinner statu;

    String utilizador;

    ArrayList<String> cliente_id,  cliente_nome, cliente_endereco, cliente_telefone, cliente_status;

    CustomAdapterCliente customAdapterCliente;

    MyDataBaseHelper myBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        id = findViewById(R.id.cliente_id);
        nome = findViewById(R.id.cliente_nome);
        endereco = findViewById(R.id.endereco);
        telefone = findViewById(R.id.telefone);
        status = findViewById(R.id.status);

        back = findViewById(R.id.back);

        if (getIntent().hasExtra("utilizador")){
            utilizador = getIntent().getStringExtra("utilizador");
        }

        myBD = new MyDataBaseHelper(Clientes.this);
        cliente_id = new ArrayList<>();
        cliente_nome = new ArrayList<>();
        cliente_endereco = new ArrayList<>();
        cliente_telefone = new ArrayList<>();
        cliente_status = new ArrayList<>();

        /*ArrayList<String> status = new ArrayList<>();
        status.add("Activo");
        status.add("Desativado");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, status);
*/

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
                Intent tela = new Intent(getApplicationContext(), Clientes.class);
                startActivity(tela);
                finish();
            }
        });

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
                storeDataInArrays(myBD.readIdCliente(nome));
            }else{
                storeDataInArrays();
            }
        }else{
            storeDataInArrays();
        }
        customAdapterCliente = new CustomAdapterCliente(Clientes.this, this, cliente_id, cliente_nome, cliente_endereco, cliente_telefone, cliente_status);
        recyclerView.setAdapter(customAdapterCliente);
        recyclerView.setLayoutManager(new LinearLayoutManager(Clientes.this));
    }

    void storeDataInArrays(){
        Cursor cursor = myBD.readClientes();
        if (cursor.getCount() == 0){
            imagem_vazia.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                cliente_id.add(cursor.getString(0));
                cliente_nome.add("Nome: "+ cursor.getString(1));
                cliente_endereco.add("Endereço: "+ cursor.getString(2));
                cliente_telefone.add("Telefone: "+ cursor.getString(3));
                cliente_status.add("Status: "+cursor.getString(4));
            }
            imagem_vazia.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    void storeDataInArrays(String id){
        Cursor cursor = myBD.readOneCliente(id);
        if (cursor.getCount() == 0){
            imagem_vazia.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
        }else{
            while (cursor.moveToNext()){
                cliente_id.add(cursor.getString(0));
                cliente_nome.add("Nome: "+ cursor.getString(1));
                cliente_endereco.add("Endereço: "+ cursor.getString(2));
                cliente_telefone.add("Telefone: "+ cursor.getString(3));
                cliente_status.add("Status: "+cursor.getString(4));
            }
            imagem_vazia.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            recreate();
        }
    }

    public void openDialog(){
        ExampleDialogCliente exampleDialog = new ExampleDialogCliente();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    public ArrayAdapter<String> status(){
        ArrayList<String> statu = new ArrayList<>();
        statu.add("Activo");
        statu.add("Desativado");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, statu);

        return adapter;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(getApplicationContext(), "Pesquise atraves do nome do cliente.", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                return true;
            }
        };

        menu.findItem(R.id.action_search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("pesquisar cliente..");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent tela = new Intent(getApplicationContext(), Clientes.class);
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
                drawerLayout.closeDrawer(GravityCompat.START);
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

    @Override
    public void applyTexts(String nome, String endereco, String telefone, String status) {
        if (nome.isEmpty() || endereco.isEmpty() || telefone.isEmpty() || status.equals("Selecione o status")) {
            Toast.makeText(getApplicationContext(), "Preencha corretmente todos os campos do formulario", Toast.LENGTH_SHORT).show();
        } else {
            MyDataBaseHelper myDB = new MyDataBaseHelper(Clientes.this);
            myDB.addCliente(nome, endereco, telefone,status);

            Intent tela = new Intent(getApplicationContext(), Clientes.class);
            startActivity(tela);
            finish();
        }
    }
}