package com.example.ifood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ifood.R;
import com.example.ifood.helper.GetMask;
import com.example.ifood.model.Produto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProdutoAdapterEmpresa extends RecyclerView.Adapter<ProdutoAdapterEmpresa.MyViewHilder> {

    private List<Produto> produtoList;
    private Context context;
    private OnclickListener OnclickListener;

    public ProdutoAdapterEmpresa(List<Produto> produtoList, Context context, ProdutoAdapterEmpresa.OnclickListener onclickListener) {
        this.produtoList = produtoList;
        this.context = context;
        OnclickListener = onclickListener;
    }

    @NonNull
    @Override
    public MyViewHilder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produto_item, parent, false);
        return  new MyViewHilder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHilder holder, int position) {
        Produto produto = produtoList.get(position);

        Picasso.get().load(produto.getUrlImagem()).into(holder.img_produto);
        holder.text_nome.setText(produto.getNome());
        holder.text_descricao.setText(produto.getDescricao());
        holder.text_valor.setText(context.getString(R.string.text_valor, GetMask.getValor(produto.getValor())));

        if (produto.getValorAntigo() > 0){
            holder.text_valor_antigo.setText(context.getString(R.string.text_valor, GetMask.getValor(produto.getValorAntigo())));
        }else{
            holder.text_valor_antigo.setText("");
        }

        holder.itemView.setOnClickListener(v -> OnclickListener.OnClick(produto));
    }

    @Override
    public int getItemCount() {
        return produtoList.size();
    }

    public interface OnclickListener{
        void OnClick(Produto produto);
    }

    static class MyViewHilder extends RecyclerView.ViewHolder{

        ImageView img_produto;
        TextView text_nome, text_descricao, text_valor, text_valor_antigo;
       public MyViewHilder(@NonNull View itemView) {
           super(itemView);

           img_produto = itemView.findViewById(R.id.img_produto);
           text_nome = itemView.findViewById(R.id.text_nome);
           text_descricao = itemView.findViewById(R.id.text_descricao);
           text_valor = itemView.findViewById(R.id.text_valor);
           text_valor_antigo = itemView.findViewById(R.id.text_valor_antigo);

       }
   }
}
