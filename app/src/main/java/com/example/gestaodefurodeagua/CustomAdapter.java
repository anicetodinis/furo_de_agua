package com.example.gestaodefurodeagua;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {

    private Context context;
    Activity activity;
    private ArrayList user_id, user_name, user_pass, user_active;

    List<String> usersList;

    Animation translat_anim;

    CustomAdapter(Activity activity,Context context, ArrayList user_id, ArrayList user_name, ArrayList user_pass, ArrayList user_active){
        this.context = context;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_pass = user_pass;
        this.user_active = user_active;
        this.activity = activity;

        this.usersList = new ArrayList<>(user_name);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {


        holder.user_id_txt.setText(String.valueOf(user_id.get(position)));
        holder.user_name_txt.setText(String.valueOf(user_name.get(position)));
        holder.user_status_txt.setText(String.valueOf(user_active.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tela = new Intent(context, UserUpdate.class);
                tela.putExtra("id", String.valueOf(user_id.get(position)));
                tela.putExtra("nome", String.valueOf(user_name.get(position)));
                tela.putExtra("activo", String.valueOf(user_active.get(position)));
                activity.startActivityForResult(tela, 1);
                //activity.finish();
                //context.startActivity(tela);
            }
        });
    }

    @Override
    public int getItemCount() {
        return user_id.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        //run on background thead
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<String> filteredList = new ArrayList<>();

            if (constraint.toString().isEmpty()){
                filteredList.addAll(usersList);
            }else{
                for (String user: usersList){
                    if (user.toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(user);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filterResults;

            return filterResults;
        }
        //run on a ui thead
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            usersList.clear();
            usersList.addAll((Collection<? extends String>) results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView user_id_txt, user_name_txt, user_status_txt;
        LinearLayout mainLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            user_id_txt = itemView.findViewById(R.id.user_id);
            user_name_txt = itemView.findViewById(R.id.user_name);
            user_status_txt = itemView.findViewById(R.id.user_status);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            //Animate recyclerview
            translat_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translat_anim);
        }
    }
}
