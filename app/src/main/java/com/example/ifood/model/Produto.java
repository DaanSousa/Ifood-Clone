package com.example.ifood.model;

import com.example.ifood.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Produto {
    private String id;
    private String nome;
    private int idLocal;
    private String IdEmpresa;
    private String idCategoria;
    private Double valor;
    private Double valorAntigo;
    private String descricao;
    private String irlImagem;

    public Produto() {
        DatabaseReference produtoRef = FirebaseHelper.getDatabaseReference();
        setId(produtoRef.push().getKey());

    }

    public void salvar(){
        DatabaseReference produtoRef = FirebaseHelper.getDatabaseReference()
                .child("produtos")
                .child(getIdEmpresa())
                .child(getId());
        produtoRef.setValue(this);

    }
    public void remover(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    @Exclude
    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public String getIdEmpresa() {
        return IdEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        IdEmpresa = idEmpresa;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorAntigo() {
        return valorAntigo;
    }

    public void setValorAntigo(Double valorAntigo) {
        this.valorAntigo = valorAntigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getIrlImagem() {
        return irlImagem;
    }

    public void setIrlImagem(String irlImagem) {
        this.irlImagem = irlImagem;
    }
}
