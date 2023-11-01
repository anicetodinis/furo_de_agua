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

public class ExampleDialogCliente extends AppCompatDialogFragment {

    private EditText nome, endereco, telefone;
    private Spinner status;

    private Context context;

    private ExampleDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_cliente, null);

        nome = view.findViewById(R.id.nome);
        endereco = view.findViewById(R.id.endereco);
        telefone = view.findViewById(R.id.telefone);

        status = view.findViewById(R.id.status);

        ArrayList<String> statu = new ArrayList<>();
        statu.add("Selecione o status");
        statu.add("Activo");
        statu.add("Desativado");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, statu);

        status.setAdapter(adapter);

        builder.setView(view)
                .setTitle("Adicionar Cliente")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nome_cliente = nome.getText().toString().trim();
                        String endereco_cliente = endereco.getText().toString().trim();
                        String telefone_cliente = telefone.getText().toString().trim();
                        String status_cliente = status.getSelectedItem().toString().trim();

                        listener.applyTexts(nome_cliente, endereco_cliente, telefone_cliente, status_cliente);


                        //Toast.makeText(getContext(), ""+status_cliente+ " ", Toast.LENGTH_SHORT).show();


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
        void applyTexts(String nome, String endereco, String telefone, String status);
    }
}
