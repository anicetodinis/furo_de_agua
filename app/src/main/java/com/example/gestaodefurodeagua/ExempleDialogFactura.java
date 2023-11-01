package com.example.gestaodefurodeagua;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class ExempleDialogFactura extends AppCompatDialogFragment {

    EditText leitura;
    Spinner cliente, contador;

    private Context context;

    private ExampleDialogListener listener;

    MyDataBaseHelper myBD;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_factura, null);

        leitura = view.findViewById(R.id.leitura);
        cliente = view.findViewById(R.id.cliente);
        contador = view.findViewById(R.id.contador);

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

        cliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Cursor cursor2 = myBD.readClienteContadorNrcontador(myBD.readIdCliente(cliente.getSelectedItem().toString()));
                ArrayList<String> cont = new ArrayList<>();
                if (cursor2.getCount() == 0){
                    cont.add("Cliente selecionado sem contador");
                }else{
                    while (cursor2.moveToNext()){
                        cont.add(cursor2.getString(0));
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, cont);
                contador.setAdapter(adapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setView(view)
                .setTitle("Lançar Factura Mensal de Agua")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String leit = leitura.getText().toString().trim();
                        String client = cliente.getSelectedItem().toString().trim();
                        String cont = contador.getSelectedItem().toString().trim();

                        listener.applyTexts(leit, client, cont);

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
        void applyTexts(String leitura, String cliente, String contador);
    }

}
