package com.example.gestaodefurodeagua;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Date;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Furo.db";
    private static final int DATABASE_VERSION = 1;

    private  static  final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_SENHA = "senha";
    private static final String COLUMN_ACTIVO = "activo";

    private  static  final String TABLE_NAME2 = "clientes";
    private static final String COLUMN_IDCLIENTE = "_id_cliente";
    private static final String COLUMN_NOMECLIENTE = "nome";
    private static final String COLUMN_ENDERECO = "endereco";
    private static final String COLUMN_TELEFONE = "telefone";
    private static final String COLUMN_STATUS = "status";

    private  static  final String TABLE_NAME3 = "contador";
    private static final String COLUMN_IDCONTADOR = "_id_contador";
    private static final String COLUMN_NRCONTADOR = "numero";
    private static final String COLUMN_IDCONTADORCLIENTE = "id_cliente";
    private static final String COLUMN_STATUSCONTADOR = "status";

    private  static  final String TABLE_NAME4 = "factura";
    private static final String COLUMN_IDFACTURA = "_id_factura";
    private static final String COLUMN_IDFACTURACONTADOR = "_id_contador";
    private static final String COLUMN_IDFACTURACLIENTE = "_id_cliente";
    private static final String COLUMN_LEITURA = "leitura";
    private static final String COLUMN_VALOR = "valor";
    private static final String COLUMN_IVA = "iva";
    private static final String COLUMN_MULTA = "multa";
    private static final String COLUMN_TOTAL = "total";
    private static final String COLUMN_DATAEMISSAO = "data_emissao";
    private static final String COLUMN_DATAPAGAMENTO = "data_pagamento";
    private static final String COLUMN_STATUSFACTURA = "status";



    MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOME + " TEXT, " +
                COLUMN_ACTIVO + " TEXT, " +
                COLUMN_SENHA + " TEXT);";
        db.execSQL(query);

        String query1 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 +
                " (" + COLUMN_IDCLIENTE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMECLIENTE + " TEXT, " +
                COLUMN_ENDERECO + " TEXT, " +
                COLUMN_TELEFONE + " TEXT, " +
                COLUMN_STATUS + " TEXT);";
        db.execSQL(query1);

        String query2 = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME3+
                "("+ COLUMN_IDCONTADOR+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_NRCONTADOR+" TEXT, "+
                COLUMN_IDCONTADORCLIENTE+ "INTEGER, "+
                COLUMN_STATUSCONTADOR+" TEXT);";
        db.execSQL(query2);

        String query3 =  "CREATE TABLE IF NOT EXISTS "+TABLE_NAME4+
                "("+ COLUMN_IDFACTURA+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_IDFACTURACONTADOR+" INTEGER, "+
                COLUMN_IDFACTURACLIENTE+" INTEGER, "+
                COLUMN_LEITURA +" TEXT, "+
                COLUMN_VALOR + " DECIMAL(18,2), "+
                COLUMN_IVA+ " DECIMAL(18,2), "+
                COLUMN_MULTA+ " DECIMAL(18,2), "+
                COLUMN_TOTAL+ " DECIMAL(18,2), "+
                COLUMN_DATAEMISSAO+ " DATE, "+
                COLUMN_DATAPAGAMENTO+ " DATE, "+
                COLUMN_STATUSFACTURA +" TEXT);";
        db.execSQL(query3);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

    }

    void addUser(String nome, String senha, String activo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put (COLUMN_NOME, nome);
        cv.put (COLUMN_ACTIVO, activo);
        cv.put (COLUMN_SENHA, senha);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1){
            Toast.makeText(context, "Ouve algum erro ao inserir os dados!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Dados inseridos com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    void addCliente(String nome, String endereco, String telefone, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put (COLUMN_NOMECLIENTE, nome);
        cv.put (COLUMN_ENDERECO, endereco);
        cv.put (COLUMN_TELEFONE, telefone);
        cv.put (COLUMN_STATUS, status);

        long result = db.insert("clientes", null, cv);

        if (result == -1){
            Toast.makeText(context, "Ouve algum erro ao inserir os dados!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Dados inseridos com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    void addFactura(String leitura, String contador, String cliente, Double valor, Double iva, Double total, String emissao, String pagamento){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put (COLUMN_IDFACTURACONTADOR, contador);
        cv.put (COLUMN_IDFACTURACLIENTE, cliente);
        cv.put (COLUMN_LEITURA, leitura);
        cv.put (COLUMN_VALOR, valor);
        cv.put (COLUMN_IVA, iva);
        cv.put (COLUMN_MULTA, "");
        cv.put (COLUMN_TOTAL, total);
        cv.put (COLUMN_DATAEMISSAO, emissao);
        cv.put (COLUMN_DATAPAGAMENTO, pagamento);
        cv.put (COLUMN_STATUS, "Pendente");

        long result = db.insert(TABLE_NAME4, null, cv);

        if (result == -1){
            Toast.makeText(context, "Ouve algum erro ao inserir os dados!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Dados inseridos com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    void addContador(String nr, String cliente, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put (COLUMN_NRCONTADOR, nr);
        cv.put (COLUMN_IDCONTADORCLIENTE, cliente);
        cv.put (COLUMN_STATUSCONTADOR, status);

        long result = db.insert(TABLE_NAME3, null, cv);

        if (result == -1){
            Toast.makeText(context, "Ouve algum erro ao inserir os dados!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Dados inseridos com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readClienteContadorNrcontador(String i){
        String id= null;
        String query = "SELECT "+ COLUMN_NRCONTADOR+" FROM " +TABLE_NAME3+ " WHERE "+COLUMN_IDCONTADORCLIENTE+" = '"+i+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }


    //possiveis alteracoes a serem fitas futuramente
    String readClienteContadorID(String i){
        String id= null;
        String query = "SELECT "+ COLUMN_IDCONTADOR+" FROM " +TABLE_NAME3+ " WHERE "+COLUMN_NRCONTADOR+" = '"+i+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        if (cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                id = cursor.getString(0);
            }
        }

        return id;
    }

    String readIdCliente(String i){
        String id= null;
        String query = "SELECT "+ COLUMN_IDCLIENTE+" FROM " +TABLE_NAME2+ " WHERE "+COLUMN_NOMECLIENTE+" = '"+i+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        if (cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                id = cursor.getString(0);
            }
        }

        return id;
    }

    String readIdUser(String i){
        String id= null;
        String query = "SELECT "+ COLUMN_ID+" FROM " +TABLE_NAME+ " WHERE "+COLUMN_NOME+" = '"+i+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        if (cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                id = cursor.getString(0);
            }
        }

        return id;
    }

    String readNameCliente(String i){
        String id= null;
        String query = "SELECT "+ COLUMN_NOMECLIENTE+" FROM " +TABLE_NAME2+ " WHERE "+COLUMN_IDCLIENTE+" = '"+i+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        if (cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                id = cursor.getString(0);
            }
        }

        return id;
    }

    String readNameContador(String i){
        String id= null;
        String query = "SELECT "+ COLUMN_NRCONTADOR+" FROM " +TABLE_NAME3+ " WHERE "+COLUMN_IDCONTADOR+" = '"+i+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        if (cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                id = cursor.getString(0);
            }
        }

        return id;
    }

    String countUsers(){
        String id= "0";
        String query = "SELECT COUNT("+ COLUMN_ID+") FROM " +TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        if (cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                id = cursor.getString(0);
            }
        }

        return id;
    }

    String countClientes(){
        String id= "0";
        String query = "SELECT COUNT("+ COLUMN_IDCLIENTE+") FROM " +TABLE_NAME2;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        if (cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                id = cursor.getString(0);
            }
        }

        return id;
    }

    String countContadores(){
        String id= "0";
        String query = "SELECT COUNT("+ COLUMN_IDCONTADOR+") FROM " +TABLE_NAME3;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        if (cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                id = cursor.getString(0);
            }
        }

        return id;
    }

    String countFacturas(){
        String id= "0";
        String query = "SELECT COUNT("+ COLUMN_IDFACTURA+") FROM " +TABLE_NAME4;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        if (cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                id = cursor.getString(0);
            }
        }

        return id;
    }

    Cursor readData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    Cursor readFacturas(){
        String query = "SELECT * FROM " + TABLE_NAME4;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    Cursor readClientes(){
        String query = "SELECT * FROM "+TABLE_NAME2;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    Cursor readUser(String id){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "+ COLUMN_ID +" = "+ id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    Cursor readContadores(){
        String query = "SELECT * FROM " + TABLE_NAME3;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    Cursor readOneCliente(String id){
        String query = "SELECT * FROM " + TABLE_NAME2 + " WHERE "+ COLUMN_IDCLIENTE +" = '"+ id+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    Cursor readOneContador(String id){
        String query = "SELECT * FROM " + TABLE_NAME3 + " WHERE "+ COLUMN_IDCONTADOR +" = '"+ id+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    Cursor searchContador(String id){
        String query = "SELECT co."+COLUMN_IDCONTADOR+", co."+COLUMN_NRCONTADOR+", co."+COLUMN_IDCONTADORCLIENTE+", co."+COLUMN_STATUSCONTADOR+
                " FROM " + TABLE_NAME3 + " AS co " +
                "INNER JOIN "+TABLE_NAME2+" AS cl ON cl."+COLUMN_IDCLIENTE+" = co."+COLUMN_IDCONTADORCLIENTE+
                " WHERE co."+ COLUMN_NRCONTADOR +" = '"+ id+"' OR cl."+COLUMN_NOMECLIENTE+" = '"+ id+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    Cursor searchFactura(String id){
        String query = "SELECT ft."+COLUMN_IDFACTURA+", ft."+COLUMN_IDFACTURACONTADOR+", ft."+COLUMN_IDFACTURACLIENTE+", ft."+COLUMN_LEITURA+
                ", ft."+COLUMN_VALOR+", ft."+COLUMN_IVA+", ft."+COLUMN_MULTA+", ft."+COLUMN_TOTAL+", ft."+COLUMN_DATAEMISSAO+", ft."+COLUMN_DATAPAGAMENTO+", ft."+COLUMN_STATUSFACTURA+
                " FROM " + TABLE_NAME4 + " AS ft " +
                "INNER JOIN "+TABLE_NAME2+" AS cl ON cl."+COLUMN_IDCLIENTE+" = ft."+COLUMN_IDFACTURACLIENTE+
                " INNER JOIN "+TABLE_NAME3+" AS co ON co."+COLUMN_IDCONTADOR+" = ft."+COLUMN_IDFACTURACONTADOR+
                " WHERE co."+ COLUMN_NRCONTADOR +" = '"+ id+"' OR cl."+COLUMN_NOMECLIENTE+" = '"+ id+"' OR ft."+COLUMN_IDFACTURA +" = '"+ id+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }



    Cursor readNameCliente(){
        String query = "SELECT "+COLUMN_NOMECLIENTE +" FROM " + TABLE_NAME2;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }



    void updateDataUser(String row_id, String nome, String activo, String senha){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NOME, nome);
        cv.put(COLUMN_ACTIVO, activo);
        cv.put(COLUMN_SENHA, senha);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});

        if (result == -1){
            Toast.makeText(context, "Erro, O update falhou, verifique os dados!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Dados actualizados com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmarpagamento(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_STATUS, "Paga");

        long result = db.update(TABLE_NAME4, cv, COLUMN_IDFACTURA+"=?", new String[]{row_id});

        if (result == -1){
            Toast.makeText(context, "Algum erro com a BD ao confirmar pagamento!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Pagamento efectuado com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    void updateDataContador(String row_id, String nr, String cliente, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NRCONTADOR, nr);
        cv.put(COLUMN_IDCONTADORCLIENTE, cliente);
        cv.put(COLUMN_STATUSCONTADOR, status);

        long result = db.update(TABLE_NAME3, cv, COLUMN_IDCONTADOR+"=?", new String[]{row_id});

        if (result == -1){
            Toast.makeText(context, "Erro, O update falhou, verifique os dados!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Dados actualizados com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    void alterarSenha(String id, String senha1, String senha2){
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+" = '"+id+"' AND "+COLUMN_SENHA+" = '"+senha1+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        if (cursor.getCount() == 0){
            Toast.makeText(context, "Senha actual invalida", Toast.LENGTH_SHORT).show();
        }else{
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(COLUMN_SENHA, senha2);

            long result = db.update(TABLE_NAME, cv, COLUMN_ID+"=?", new String[]{id});

            if (result == -1){
                Toast.makeText(context, "Erro, O update falhou, verifique os dados!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Senha actualizada com sucesso!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    void updateDataCliente(String row_id, String nome, String endereco, String telefone, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put (COLUMN_NOMECLIENTE, nome);
        cv.put (COLUMN_ENDERECO, endereco);
        cv.put (COLUMN_TELEFONE, telefone);
        cv.put (COLUMN_STATUS, status);

        long result = db.update(TABLE_NAME2, cv, COLUMN_IDCLIENTE+"=?", new String[]{row_id});

        if (result == -1){
            Toast.makeText(context, "Erro, O update falhou, verifique os dados!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Dados actualizados com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneUser(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});

        if (result == -1){
            Toast.makeText(context, "Erro ao apagar os dados!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Dados apagados com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneFactura(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME4, COLUMN_IDFACTURA+"=?", new String[]{row_id});

        if (result == -1){
            Toast.makeText(context, "Erro ao apagar os dados!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Dados apagados com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneCliente(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME2, COLUMN_IDCLIENTE+"=?", new String[]{row_id});

        if (result == -1){
            Toast.makeText(context, "Erro ao apagar os dados!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Dados apagados com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneContador(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME3, COLUMN_IDCONTADOR+"=?", new String[]{row_id});

        if (result == -1){
            Toast.makeText(context, "Erro ao apagar os dados!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Dados apagados com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    Boolean Login(String user, String senha){
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_NOME+" = '"+user+"' AND "+COLUMN_SENHA+" = '"+senha+"'";
        //String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_NOME+" = '"+user+"'";

        Cursor cursor = null;
        SQLiteDatabase db = null;
        Boolean res = false;
        try {

            db = this.getReadableDatabase();

            cursor = db.rawQuery(query, null);

            if (cursor.getCount() != 0){
                res = true;
            }

        }catch (Exception e){
            Log.d("Erro!", "Algo deu errado");
            return  false;
        }finally {
            if (db != null){
                db.close();
            }
        }

        db.close();
        cursor.close();

        return res;
    }

    String idUser(String user, String senha){
        String id= "0";
        String query = "SELECT "+COLUMN_ID+" FROM "+TABLE_NAME+" WHERE "+COLUMN_NOME+" = '"+user+"' AND "+COLUMN_SENHA+" = '"+senha+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        if (cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){
                id = cursor.getString(0);
            }
        }

        return id;
    }

    Cursor user(String id){
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+" = '"+id+"'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }



}
