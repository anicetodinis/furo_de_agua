package com.example.gestaodefurodeagua;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterFactura extends RecyclerView.Adapter<CustomAdapterFactura.MyViewHolder>{

    Activity activity;
    private Context context;

    Animation translat_anim;

    private ArrayList<String> factura_id, factura_leitura,
            factura_status, factura_contador, factura_cliente, factura_valor,
            factura_iva, factura_multa, factura_total, factura_emissao, factura_prazo;

    public CustomAdapterFactura(Activity activity, Context context, ArrayList<String> factura_id, ArrayList<String> factura_leitura, ArrayList<String> factura_status, ArrayList<String> factura_contador, ArrayList<String> factura_cliente, ArrayList<String> factura_valor, ArrayList<String> factura_iva, ArrayList<String> factura_multa, ArrayList<String> fatura_total, ArrayList<String> factura_emissao, ArrayList<String> factura_prazo) {
        this.activity = activity;
        this.context = context;
        this.factura_id = factura_id;
        this.factura_leitura = factura_leitura;
        this.factura_status = factura_status;
        this.factura_contador = factura_contador;
        this.factura_cliente = factura_cliente;
        this.factura_valor = factura_valor;
        this.factura_iva = factura_iva;
        this.factura_multa = factura_multa;
        this.factura_total = fatura_total;
        this.factura_emissao = factura_emissao;
        this.factura_prazo = factura_prazo;
    }

    @NonNull
    @Override
    public CustomAdapterFactura.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_factura, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.factura_id_txt.setText(String.valueOf(factura_id.get(position)));
        holder.factura_leitura_txt.setText(String.valueOf(factura_leitura.get(position)));

        holder.factura_status_txt.setText(String.valueOf(factura_status.get(position)));
        holder.factura_contador_txt.setText(String.valueOf(factura_contador.get(position)));
        holder.factura_cliente_txt.setText(String.valueOf(factura_cliente.get(position)));
        holder.factura_valor_txt.setText(String.valueOf(factura_valor.get(position)));

        holder.factura_iva_txt.setText(String.valueOf(factura_iva.get(position)));
        holder.factura_multa_txt.setText(String.valueOf(factura_multa.get(position)));
        holder.factura_total_txt.setText(String.valueOf(factura_total.get(position)));
        holder.factura_emissao_txt.setText(String.valueOf(factura_emissao.get(position)));
        holder.factura_prazo_txt.setText(String.valueOf(factura_prazo.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                confirmDialog(String.valueOf(factura_id.get(position)));
                //Toast.makeText(context, ""+cliente_id.get(position).toString(), Toast.LENGTH_SHORT).show();

                /*Intent tela = new Intent(context, ContadorUpdate.class);
                tela.putExtra("id", String.valueOf(contador_id.get(position)));
                activity.startActivityForResult(tela, 1);*/

                //context.startActivity(tela);
            }
        });

    }

    void confirmDialog(String f){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Factura: "+ f);
        builder.setMessage("Qual é a acção que deseja realizar com a factura selecionada?");
        builder.setPositiveButton("Confirmar Pagamento", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDataBaseHelper myDB = new MyDataBaseHelper(context);
                myDB.confirmarpagamento(f);

                Intent tela = new Intent(context, Facturas.class);
                activity.startActivityForResult(tela, 1);
            }
        });
        builder.setNegativeButton("Eliminar Factura", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDataBaseHelper myDB = new MyDataBaseHelper(context);
                myDB.deleteOneFactura(f);

                Intent tela = new Intent(context, Facturas.class);
                activity.startActivityForResult(tela, 1);
            }
        });
        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return factura_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView factura_id_txt, factura_leitura_txt,
                factura_status_txt, factura_contador_txt, factura_cliente_txt, factura_valor_txt,
                factura_iva_txt, factura_multa_txt, factura_total_txt, factura_emissao_txt, factura_prazo_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            factura_id_txt = itemView.findViewById(R.id.id);
            factura_leitura_txt = itemView.findViewById(R.id.leitura);

            factura_status_txt = itemView.findViewById(R.id.status);
            factura_contador_txt = itemView.findViewById(R.id.contador);
            factura_cliente_txt = itemView.findViewById(R.id.cliente);
            factura_valor_txt = itemView.findViewById(R.id.valor);

            factura_iva_txt = itemView.findViewById(R.id.iva);
            factura_multa_txt = itemView.findViewById(R.id.multa);
            factura_total_txt = itemView.findViewById(R.id.total);
            factura_emissao_txt = itemView.findViewById(R.id.emisssao);
            factura_prazo_txt = itemView.findViewById(R.id.prazo);



            mainLayout = itemView.findViewById(R.id.mainLayout);

            //Animate recyclerview
            translat_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translat_anim);
        }
    }
}
