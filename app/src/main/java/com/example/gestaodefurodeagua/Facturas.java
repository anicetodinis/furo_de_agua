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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Facturas extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener, ExempleDialogFactura.ExampleDialogListener{

    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout frameLayout;

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    ImageView imagem_vazia;
    Button back;

    String utilizador;

    MyDataBaseHelper myBD;

    TextView nodata;
    ArrayList<String> factura_id, factura_leitura,
            factura_status, factura_contador, factura_cliente, factura_valor,
            factura_iva, factura_multa, factura_total, factura_emissao, factura_prazo;

    Double  facturavalor, facturaiva, facturamulta, facturatotal;
    //Date data_emissao, data_pagamento;

    CustomAdapterFactura customAdapterFactura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturas);


        recyclerView = findViewById(R.id.recycleview);
        add_button = findViewById(R.id.add_button);
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
                Intent tela = new Intent(getApplicationContext(), Facturas.class);
                startActivity(tela);
                finish();
            }
        });

        if (getIntent().hasExtra("utilizador")){
            utilizador = getIntent().getStringExtra("utilizador");
        }

        myBD = new MyDataBaseHelper(Facturas.this);

        factura_id = new ArrayList<>();
        factura_leitura = new ArrayList<>();
        factura_status = new ArrayList<>();
        factura_contador = new ArrayList<>();

        factura_cliente = new ArrayList<>();
        factura_valor = new ArrayList<>();
        factura_iva = new ArrayList<>();
        factura_multa = new ArrayList<>();

        factura_total = new ArrayList<>();
        factura_emissao = new ArrayList<>();
        factura_prazo = new ArrayList<>();


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
        customAdapterFactura = new CustomAdapterFactura(Facturas.this, this, factura_id,  factura_leitura,  factura_status,  factura_contador,  factura_cliente,  factura_valor,  factura_iva,  factura_multa,  factura_total,  factura_emissao,  factura_prazo);
        recyclerView.setAdapter(customAdapterFactura);
        recyclerView.setLayoutManager(new LinearLayoutManager(Facturas.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            recreate();
        }
    }

    public void openDialog(){
        ExempleDialogFactura exampleDialog = new ExempleDialogFactura();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
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
                Intent t3 = new Intent(getApplicationContext(), Contadores.class);
                t3.putExtra("utilizador", utilizador);
                startActivity(t3);
                finish();
                break;
            case R.id.factura:
                drawerLayout.closeDrawer(GravityCompat.START);
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

    void storeDataInArrays(){
        try {
            Cursor cursor = myBD.readFacturas();
            if (cursor.getCount() == 0){
                imagem_vazia.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
            }else{
                while (cursor.moveToNext()){
                    factura_id.add(cursor.getString(0));
                    factura_contador.add("Contador: "+ myBD.readNameContador(cursor.getString(1)));
                    factura_cliente.add("Cliente: "+ myBD.readNameCliente(cursor.getString(2)));
                    factura_leitura.add("Leitura: "+ cursor.getString(3)+" m^3");

                    factura_valor.add("valor: "+ cursor.getString(4));
                    factura_iva.add("IVA: "+ cursor.getString(5));
                    factura_multa.add("Multa: "+ cursor.getString(6));
                    factura_total.add("Total: "+ cursor.getString(7));
                    factura_emissao.add("Data Emissao: "+ cursor.getString(8));

                    factura_prazo.add("Prazo de Pagto.: "+ cursor.getString(9));
                    factura_status.add("Status: "+ cursor.getString(10));

                }
                imagem_vazia.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
            }
        }catch (Exception e) {
            AlertDialog.Builder msg = new AlertDialog.Builder(Facturas.this);
            msg.setTitle("Resultado");
            msg.setMessage("Oops! algo deu errado. ");
            msg.setNeutralButton("OK", null);
            msg.show();
        }
    }

    void storeDataInArrays(String id){
        try {
            Cursor cursor = myBD.searchFactura(id);
            if (cursor.getCount() == 0){
                imagem_vazia.setVisibility(View.VISIBLE);
                nodata.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
            }else{
                while (cursor.moveToNext()){
                    factura_id.add(cursor.getString(0));
                    factura_contador.add("Contador: "+ myBD.readNameContador(cursor.getString(1)));
                    factura_cliente.add("Cliente: "+ myBD.readNameCliente(cursor.getString(2)));
                    factura_leitura.add("Leitura: "+ cursor.getString(3)+" m^3");

                    factura_valor.add("valor: "+ cursor.getString(4));
                    factura_iva.add("IVA: "+ cursor.getString(5));
                    factura_multa.add("Multa: "+ cursor.getString(6));
                    factura_total.add("Total: "+ cursor.getString(7));
                    factura_emissao.add("Data Emissao: "+ cursor.getString(8));

                    factura_prazo.add("Prazo de Pagto.: "+ cursor.getString(9));
                    factura_status.add("Status: "+ cursor.getString(10));

                }
                imagem_vazia.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
            }
        }catch (Exception e) {
            AlertDialog.Builder msg = new AlertDialog.Builder(Facturas.this);
            msg.setTitle("Resultado");
            msg.setMessage("Oops! algo deu errado. ");
            msg.setNeutralButton("OK", null);
            msg.show();
        }
    }

    Double getFactura_valor(Double leitura){
        this.facturavalor =  leitura * 70 + 200;
        return this.facturavalor;
    }

    Double getFactura_iva(){
        this.facturaiva = this.facturavalor * 0.17;
        return  this.facturaiva;
    }

    void getFactura_multa(){

    }

    Double getFactura_total(){
        this.facturatotal = this.facturavalor + this.facturaiva;
        return this.facturatotal;
    }

    @Override
    public void applyTexts(String leitura, String cliente, String contador) {

        if (leitura.isEmpty() || cliente.equals("Selecione o titular do contador") || contador.equals("Cliente selecionado sem contador") ) {
            Toast.makeText(getApplicationContext(), "Preencha corretmente todos os campos do formulario", Toast.LENGTH_SHORT).show();
        } else {
            MyDataBaseHelper myDB = new MyDataBaseHelper(Facturas.this);
            String emissao, prazo;

            Double l = Double.parseDouble(leitura.trim());

            Calendar agora = Calendar.getInstance();

            // formata e exibe a data e hora atual
            Format formato = new SimpleDateFormat("yyyy/MM/dd");
            emissao = formato.format(agora.getTime());

            agora.add(Calendar.DAY_OF_MONTH, 10);

            // formata e exibe o resultado
            formato = new SimpleDateFormat("yyyy/MM/dd");
            prazo = formato.format(agora.getTime());

            myDB.addFactura(leitura, myDB.readClienteContadorID(contador), myDB.readIdCliente(cliente), getFactura_valor(l), getFactura_iva(), getFactura_total(), emissao, prazo);

            Intent tela = new Intent(getApplicationContext(), Facturas.class);
            startActivity(tela);
            finish();

            //Toast.makeText(this, " "+myDB.readClienteContadorID(contador)+ " "+myDB.readIdCliente(cliente)+" valor: "+getFactura_valor(l)+" valoriva: "+getFactura_iva()+" valortotal: "+getFactura_total()+" data1: "+emissao+" data2: "+prazo, Toast.LENGTH_SHORT).show();
            /*myDB.addContador(nrcontador, myDB.readIdCliente(cliente), status);

            Intent tela = new Intent(getApplicationContext(), Contadores.class);
            startActivity(tela);
            finish();*/
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
        searchView.setQueryHint("pesquisar factura..");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent tela = new Intent(getApplicationContext(), Facturas.class);
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
}