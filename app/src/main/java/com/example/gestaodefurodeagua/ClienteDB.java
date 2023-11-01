package com.example.gestaodefurodeagua;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ClienteDB extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Furo.db";
    private static final int DATABASE_VERSION = 1;

    private  static  final String TABLE_NAME = "cliente";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_ENDERECO = "endereco";
    private static final String COLUMN_TELEFONE = "telefone";
    private static final String COLUMN_STATUS = "status";

    public ClienteDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOME + " TEXT, " +
                COLUMN_ENDERECO + " TEXT, " +
                COLUMN_TELEFONE + " TEXT, " +
                COLUMN_STATUS + " TEXT);";
        db.execSQL(query);



        //addCliente();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS clientes");

    }


    void addCliente(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put (COLUMN_NOME, "Fabiao");
        cv.put (COLUMN_ENDERECO, "Mahotas, Q. 12 Casa 12");
        cv.put (COLUMN_TELEFONE, "853214832");
        cv.put (COLUMN_STATUS, "Activo");

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1){
            Toast.makeText(context, "Ouve algum erro ao inserir os dados!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Dados inseridos com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readClientes(){
        String query = "SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }


}
