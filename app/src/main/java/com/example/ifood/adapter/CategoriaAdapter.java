package com.example.ifood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ifood.R;
import com.example.ifood.model.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.MyViewHolder> {

    private List<Categoria> categoriaList;
    private OnclickListener onclickListener;

    public CategoriaAdapter(List<Categoria> categoriaList, OnclickListener onclickListener) {
        this.categoriaList = categoriaList;
        this.onclickListener = onclickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categoria_item, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Categoria categoria = categoriaList.get(position);

        holder.text_categoria.setText(categoria.getNome());

        holder.itemView.setOnClickListener(view -> onclickListener.Onclick(categoria,position));
    }

    @Override
    public int getItemCount() {
        return categoriaList.size();
    }

    public interface OnclickListener{
        void Onclick(Categoria categoria, int position);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text_categoria;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_categoria = itemView.findViewById(R.id.text_categoria);
        }
    }


}
