package com.example.ifood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ifood.R;
import com.example.ifood.model.Produto;

import java.util.List;

public class ProdutoAdapterEmpresa extends RecyclerView.Adapter<ProdutoAdapterEmpresa.MyViewHilder> {

    private List<Produto> produtoList;
    @NonNull
    @Override
    public MyViewHilder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produto_item, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHilder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return produtoList.size();
    }

    static class MyViewHilder extends RecyclerView.ViewHolder{
       public MyViewHilder(@NonNull View itemView) {
           super(itemView);
       }
   }
}
