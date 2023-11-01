package com.example.gestaodefurodeagua;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


public class Updateusers extends AppCompatDialogFragment{

    private EditText nome, activo, senha;
    private UpdateusersListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_updateuser , null);

        nome = view.findViewById(R.id.nome);
        senha = view.findViewById(R.id.senha);
        activo = view.findViewById(R.id.activo);

        builder.setView(view)
                .setTitle("Actualizar/Eliminar Utilizador")
                .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nome.getText().toString().trim();
                        String pass = senha.getText().toString().trim();
                        String active = activo.getText().toString().trim();
                        listener.applyTexts(name, pass, active);

                        /*MyDataBaseHelper myDB = new MyDataBaseHelper(this);
                        myDB.addUser(name, pass, active);*/

                        //Toast.makeText(context, "Nome "+name+" pass "+pass+" activo "+activo, Toast.LENGTH_SHORT).show();

                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (Updateusers.UpdateusersListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement UpdateuserLIstener");
        }
    }

    public interface UpdateusersListener{
        void applyTexts(String nome, String senha, String activo);
    }

}
