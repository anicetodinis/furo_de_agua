package com.example.gestaodefurodeagua;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    Button entrar;
    TextInputEditText senha, user;
    MyDataBaseHelper myBD;

    public static String nome;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entrar = (Button) findViewById(R.id.entrar);
        senha = (TextInputEditText) findViewById(R.id.senha);
        user = (TextInputEditText) findViewById(R.id.user);



        //Toast.makeText(getApplicationContext(), "senha: "+senha.getText().toString()+" \nuser: "+user.getText().toString(), Toast.LENGTH_SHORT).show();

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* */
                if (user.getText().toString().isEmpty() || senha.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Preencha corretamente todos os campos!", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                       myBD = new MyDataBaseHelper(MainActivity.this);
                       if (myBD.Login(user.getText().toString(), senha.getText().toString())) {
                            MainActivity.nome = user.getText().toString();
                            Intent tela = new Intent(getApplicationContext(), Admin1.class);
                            tela.putExtra("utilizador", myBD.idUser(user.getText().toString(), senha.getText().toString()));
                            startActivity(tela);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Dados Invalidos, tente novamente!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Algo deu errado!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}