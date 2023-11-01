package com.example.gestaodefurodeagua;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterContador extends RecyclerView.Adapter<CustomAdapterContador.MyViewHolder>{

    private Context context;
    Activity activity;
    private ArrayList contador_id, contador_nr, contador_cliente, contador_status;

    Animation translat_anim;

    public CustomAdapterContador(Activity activity, Context context,  ArrayList contador_id, ArrayList contador_nr, ArrayList contador_cliente, ArrayList contador_status) {
        this.context = context;
        this.activity = activity;
        this.contador_id = contador_id;
        this.contador_nr = contador_nr;
        this.contador_cliente = contador_cliente;
        this.contador_status = contador_status;
    }


    @NonNull
    @Override
    public CustomAdapterContador.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_contador, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterContador.MyViewHolder holder, int position) {
        holder.contador_id_txt.setText(String.valueOf(contador_id.get(position)));
        holder.contador_nr_txt.setText(String.valueOf(contador_nr.get(position)));
        holder.contador_cliente_txt.setText(String.valueOf(contador_cliente.get(position)));
        holder.contador_status_txt.setText(String.valueOf(contador_status.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //Toast.makeText(context, ""+cliente_id.get(position).toString(), Toast.LENGTH_SHORT).show();

                Intent tela = new Intent(context, ContadorUpdate.class);
                tela.putExtra("id", String.valueOf(contador_id.get(position)));
                activity.startActivityForResult(tela, 1);

                //context.startActivity(tela);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contador_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView contador_id_txt, contador_nr_txt, contador_cliente_txt, contador_status_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            contador_id_txt = itemView.findViewById(R.id.id);
            contador_nr_txt = itemView.findViewById(R.id.numero);
            contador_cliente_txt = itemView.findViewById(R.id.cliente);
            contador_status_txt = itemView.findViewById(R.id.status);

            mainLayout = itemView.findViewById(R.id.mainLayout);

            //Animate recyclerview
            translat_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translat_anim);
        }
    }
}
