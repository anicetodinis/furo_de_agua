package com.example.gestaodefurodeagua;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class ExempleDialogContador extends AppCompatDialogFragment {

    EditText nrcontador;
    Spinner cliente, status;

    private Context context;

    private ExampleDialogListener listener;

    MyDataBaseHelper myBD;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_contador, null);

        nrcontador = view.findViewById(R.id.nrcontador);
        cliente = view.findViewById(R.id.cliente);
        status = view.findViewById(R.id.status);

        ArrayList<String> statu = new ArrayList<>();
        statu.add("Selecione o status");
        statu.add("Activo");
        statu.add("Desativado");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, statu);
        status.setAdapter(adapter);
        myBD = new MyDataBaseHelper(getContext());
        Cursor cursor = myBD.readNameCliente();
        ArrayList<String> client = new ArrayList<>();
        client.add("Selecione o titular do contador");
        if (cursor.getCount() == 0){
            client.add("Ainda nao existem clientes");
        }else{
            while (cursor.moveToNext()){
                client.add(cursor.getString(0));
            }
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, client);
        cliente.setAdapter(adapter2);


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
                        String nr = nrcontador.getText().toString().trim();
                        String client = cliente.getSelectedItem().toString().trim();
                        String stats = status.getSelectedItem().toString().trim();

                        listener.applyTexts(nr, client, stats);

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
        void applyTexts(String nome, String cliente, String status);
    }

}
