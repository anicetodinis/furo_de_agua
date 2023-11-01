package com.example.gestaodefurodeagua;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class ExampleDialog extends AppCompatDialogFragment {

    private EditText nome, activo, senha, senha1;
    private ExampleDialogListener listener;
    private Spinner status;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
       // super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        nome = view.findViewById(R.id.nome);
        senha = view.findViewById(R.id.senha);
        senha1 = view.findViewById(R.id.senha1);
        activo = view.findViewById(R.id.activo);

        status = view.findViewById(R.id.status);

        ArrayList<String> statu = new ArrayList<>();
        statu.add("Selecione o status");
        statu.add("Activo");
        statu.add("Desativado");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, statu);
        status.setAdapter(adapter);

        builder.setView(view)
                .setTitle("Adicionar Utilizador")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nome.getText().toString().trim();
                        String pass = senha.getText().toString().trim();
                        String pass1 = senha1.getText().toString().trim();
                        String active = activo.getText().toString().trim();
                        String stat = status.getSelectedItem().toString().trim();

                        if (pass.equals(pass1)){
                            listener.applyTexts(name, pass, stat);
                        }else{
                            Toast.makeText(getContext(), "Confirmação de senha invalida", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement ExampleDialogLIstener");
        }


    }

    public interface ExampleDialogListener{
        void applyTexts(String nome, String senha, String activo);
    }
}
