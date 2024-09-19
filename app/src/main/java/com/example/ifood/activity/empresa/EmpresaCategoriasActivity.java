package com.example.ifood.activity.empresa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ifood.R;
import com.example.ifood.adapter.CategoriaAdapter;
import com.example.ifood.helper.FirebaseHelper;
import com.example.ifood.model.Categoria;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tsuryo.swipeablerv.SwipeLeftRightCallback;
import com.tsuryo.swipeablerv.SwipeableRecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EmpresaCategoriasActivity extends AppCompatActivity implements CategoriaAdapter.OnclickListener {

    private CategoriaAdapter categoriaAdapter;
    private List<Categoria> categoriaList = new ArrayList<>();

    private SwipeableRecyclerView rv_categorias;
    private ProgressBar progressBar;
    private TextView text_info;

    private AlertDialog dialog;
    private Categoria categoriaSelecionada;
    private int categoriaIndex = 0;
    private Boolean novaCategoria = true;
    private int acesso = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_categorias);

        iniciaComponentes();

        configCliques();

        recuperaCategorias();

        configRv();
    }

    private void configRv(){
        rv_categorias.setLayoutManager(new LinearLayoutManager(this));
        rv_categorias.setHasFixedSize(true);
        categoriaAdapter = new CategoriaAdapter(categoriaList,this);
        rv_categorias.setAdapter(categoriaAdapter);

        rv_categorias.setListener(new SwipeLeftRightCallback.Listener() {
            @Override
            public void onSwipedLeft(int position) {
            }

            @Override
            public void onSwipedRight(int position) {
                dialogRemoverCategoria(categoriaList.get(position));
            }
        });
    }

    private void configCliques(){
        findViewById(R.id.ib_voltar).setOnClickListener(view -> finish());
        findViewById(R.id.ib_add).setOnClickListener(view -> {
            novaCategoria = true;
            showDialog();
        });
    }

    private void recuperaCategorias(){
        DatabaseReference categoriasRef = FirebaseHelper.getDatabaseReference()
                .child("categorias")
                .child(FirebaseHelper.getIdFirebase());
        categoriasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Categoria categoria = ds.getValue(Categoria.class);
                        categoriaList.add(categoria);
                    }
                    text_info.setText("");

                }else {
                    text_info.setText("Nenhuma categoria cadastrada.");
                }

                progressBar.setVisibility(View.GONE);
                Collections.reverse(categoriaList);
                categoriaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_categoria, null);
        builder.setView(view);

        EditText edt_categoria = view.findViewById(R.id.edt_categoria);
        Button bnt_fechar = view.findViewById(R.id.bnt_fechar);
        Button bnt_salvar = view.findViewById(R.id.btn_salvar);

        if (!novaCategoria){
            edt_categoria.setText(categoriaSelecionada.getNome());
        }

        bnt_salvar.setOnClickListener(view1 -> {
            String nomeCategoria = edt_categoria.getText().toString().trim();

            if (!nomeCategoria.isEmpty()){

                if (novaCategoria){

                    Categoria categoria = new Categoria();
                    categoria.setNome(nomeCategoria);
                    categoria.salvar();

                    categoriaList.add(categoria);

                }else{
                    categoriaSelecionada.setNome(nomeCategoria);
                    categoriaList.set(categoriaIndex,categoriaSelecionada);
                    categoriaSelecionada.salvar();
                }

                if (!categoriaList.isEmpty()){
                    text_info.setText("");
                }

                dialog.dismiss();
                categoriaAdapter.notifyDataSetChanged();

            }else{
                edt_categoria.requestFocus();
                edt_categoria.setError("Informe um nome.");
            }


        });

        bnt_fechar.setOnClickListener(view1 -> dialog.dismiss());

        dialog = builder.create();
        dialog.show();
    }

    private void dialogRemoverCategoria(Categoria categoria){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover categotiria");
        builder.setMessage("Deseja remover a categoria selecionada ?");
        builder.setNegativeButton("Não",(dialogInterface, i) -> {
            dialogInterface.dismiss();
            categoriaAdapter.notifyDataSetChanged();
        });
        builder.setPositiveButton("Sim",((dialogInterface, i) -> {
            categoria.remover();
            categoriaList.remove(categoria);

            if (categoriaList.isEmpty()){
                text_info.setText("Nenhuma categoria cadastrada.");
            }

            categoriaAdapter.notifyDataSetChanged();
            dialogInterface.dismiss();
        }));

        AlertDialog dialog = builder.create();
        dialog.show();

    }
    private void iniciaComponentes(){
        TextView tex_toobar = findViewById(R.id.text_toobar);
        tex_toobar.setText("Categorias");
        rv_categorias = findViewById(R.id.rv_categorias);
        progressBar = findViewById(R.id.progressBar);
        text_info = findViewById(R.id.text_info);
    }

    @Override
    public void Onclick(Categoria categoria, int position) {
        categoriaSelecionada = categoria;
        categoriaIndex = position;
        novaCategoria = false;

        showDialog();
    }
}