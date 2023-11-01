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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterCliente extends RecyclerView.Adapter<CustomAdapterCliente.MyViewHolder>{

    private Context context;
    Activity activity;
    private ArrayList cliente_nome, cliente_id, cliente_telefone, cliente_endereco, cliente_status;

    Animation translat_anim;


    public CustomAdapterCliente(Activity activity, Context context, ArrayList cliente_id, ArrayList cliente_nome, ArrayList cliente_endereco, ArrayList cliente_telefone, ArrayList cliente_status) {
        this.activity = activity;
        this.context = context;
        this.cliente_id = cliente_id;
        this.cliente_nome = cliente_nome;
        this.cliente_telefone = cliente_telefone;
        this.cliente_endereco = cliente_endereco;
        this.cliente_status = cliente_status;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row_cliente, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterCliente.MyViewHolder holder, int position) {
        holder.cliente_id_txt.setText(String.valueOf(cliente_id.get(position)));
        holder.cliente_nome_txt.setText(String.valueOf(cliente_nome.get(position)));
        holder.cliente_endereco_txt.setText(String.valueOf(cliente_endereco.get(position)));
        holder.cliente_telefone_txt.setText(String.valueOf(cliente_telefone.get(position)));
        holder.cliente_status_txt.setText(String.valueOf(cliente_status.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //Toast.makeText(context, ""+cliente_id.get(position).toString(), Toast.LENGTH_SHORT).show();

                Intent tela = new Intent(context, ClienteUpdate.class);
                tela.putExtra("id", String.valueOf(cliente_id.get(position)));
                activity.startActivityForResult(tela, 1);

                //context.startActivity(tela);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cliente_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView cliente_id_txt, cliente_nome_txt, cliente_status_txt, cliente_telefone_txt, cliente_endereco_txt;
        LinearLayout mainLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            /*user_id_txt = itemView.findViewById(R.id.user_id);
            user_name_txt = itemView.findViewById(R.id.user_name);
            user_status_txt = itemView.findViewById(R.id.user_status);*/

            cliente_id_txt = itemView.findViewById(R.id.cliente_id);
            cliente_nome_txt = itemView.findViewById(R.id.cliente_nome);
            cliente_telefone_txt = itemView.findViewById(R.id.telefone);
            cliente_endereco_txt = itemView.findViewById(R.id.endereco);
            cliente_status_txt = itemView.findViewById(R.id.status);

           mainLayout = itemView.findViewById(R.id.mainLayout);

            //Animate recyclerview
            translat_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translat_anim);
        }
    }
}
