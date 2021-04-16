package com.gferl.latihanuki;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {
    Context context;
    List<ToDo> todo;

    public ToDoAdapter(Context context, List<ToDo> todo) {
        this.context = context;
        this.todo = todo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_todo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titletodo.setText(todo.get(position).getTitletodo());
        holder.desctodo.setText(todo.get(position).getDesctodo());
        holder.datetodo.setText(todo.get(position).getDatetodo());

        String getTitleTodo = todo.get(position).getTitletodo();
        String getDescTodo = todo.get(position).getDesctodo();
        String getDateTodo = todo.get(position).getDatetodo();
        String getIdtodo = todo.get(position).getIdtodo();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, EditTodo.class);
                i.putExtra("titletodo", getTitleTodo);
                i.putExtra("desctodo", getDescTodo);
                i.putExtra("datetodo", getDateTodo);
                i.putExtra("idtodo", getIdtodo);
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return todo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titletodo, desctodo, datetodo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titletodo = (TextView)itemView.findViewById(R.id.jdl_todo);
            desctodo = (TextView)itemView.findViewById(R.id.desk_todo);
            datetodo = (TextView)itemView.findViewById(R.id.tgl_todo);
        }
    }
}
