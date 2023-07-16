
package com.crown.passwordmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passwordmanager.R;

import java.util.ArrayList;

class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.viewHolder> {

    public ArrayList<DataModel> list;
    public Context context;

    public RecyclerAdapter(ArrayList<DataModel> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        DataModel model=list.get(position);
        holder.title.setText(model.getTitle());
        if(model.getTitle().length() > 17){
            holder.title.setText(model.getTitle().substring(0,17)+ ".....");
        }
        holder.userName.setText(model.getUserName());
        if(model.getUserName().length() > 30){
            holder.userName.setText(model.getUserName().substring(0,30)+ ".....");
        }
        holder.password.setText("*****");
        holder.date.setText(model.getDate());


        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent =new Intent(context,UpdatePasswordActivity.class);
                intent.putExtra("title",model.getTitle());
                intent.putExtra("date",model.getDate());
                intent.putExtra("userName",model.getUserName());
                intent.putExtra("password",model.getPassword());
                intent.putExtra("NO",model.getNO());
                context.startActivity(intent);
            }
        });
        holder.password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.password.getText().toString().equals("*****")){
                    holder.password.setText(model.getPassword());
                }
                else {
                    holder.password.setText("*****");
                }
            }
        });

        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getUserName().length() > 40) {
                    if (holder.userName.getText().toString().equals(model.getUserName().substring(0, 30) + ".....")) {
                        holder.userName.setText(model.getUserName());
                        holder.password.setVisibility(View.GONE);
                        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) holder.userName.getLayoutParams();
                        params.height = 170;
                        holder.userName.setLayoutParams(params);
                    } else {
                        holder.userName.setText(model.getUserName().substring(0, 30) + ".....");
                        holder.password.setVisibility(View.VISIBLE);
                        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) holder.userName.getLayoutParams();
                        params.height = 100;
                        holder.userName.setLayoutParams(params);
                    }
                }
                else if (model.getUserName().length() > 30) {
                    if (holder.userName.getText().toString().equals(model.getUserName().substring(0, 30) + ".....")) {
                        holder.userName.setText(model.getUserName());
                        holder.password.setVisibility(View.GONE);
                    } else {
                        holder.userName.setText(model.getUserName().substring(0, 30) + ".....");
                        holder.password.setVisibility(View.VISIBLE);
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class viewHolder extends RecyclerView.ViewHolder {

        TextView title,userName,password,date;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.title);
            userName=itemView.findViewById(R.id.userName);
            password=itemView.findViewById(R.id.password);
            date=itemView.findViewById(R.id.date);

        }
    }

}

