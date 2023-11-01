package com.example.gestaodefurodeagua;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ExampleDialogSenha extends AppCompatDialogFragment {

    private EditText senha1, senha2, senha3;
    private ExampleDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_senha, null);


        senha1 = view.findViewById(R.id.senha1);
        senha2 = view.findViewById(R.id.senha2);
        senha3 = view.findViewById(R.id.senha3);


        builder.setView(view)
                .setTitle("Alterar Senha")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s1 = senha1.getText().toString().trim();
                        String s2 = senha2.getText().toString().trim();
                        String s3 = senha3.getText().toString().trim();
                        listener.applyTexts(s1, s2, s3);


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
        void applyTexts(String senha1, String senha2, String senha3);
    }
}
