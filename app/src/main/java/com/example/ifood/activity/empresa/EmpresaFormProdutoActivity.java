package com.example.ifood.activity.empresa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.ifood.R;
import com.example.ifood.model.Categoria;
import com.example.ifood.model.Produto;

import java.util.Locale;

public class EmpresaFormProdutoActivity extends AppCompatActivity {

    private ImageView img_produto;
    private EditText edt_nome;
    private CurrencyEditText edt_valor;
    private CurrencyEditText edt_valor_antigo;
    private Button btn_categoria;
    private EditText edt_descricao;

    private Categoria categoriaSelecionada = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_form_produto);

        iniciaComponentes();

        configCliques();

    }

    private void configCliques(){
        findViewById(R.id.ib_voltar).setOnClickListener(view -> finish());
    }

    public void validaDados(View view){
        String nome = edt_nome.getText().toString().trim();
        double valor = (double) edt_valor.getRawValue() / 100;
        double valorAntigo = (double) edt_valor_antigo.getRawValue() / 100;
        String descricao = edt_descricao.getText().toString().trim();

        if (!nome.isEmpty()){
            if (valor > 0){
                if (categoriaSelecionada != null){
                    if (!descricao.isEmpty()){

                        ocultarTeclado();

                        Produto produto = new Produto();
                        produto.setNome(nome);
                        produto.setValor(valor);
                        produto.setValorAntigo(valorAntigo);
                        produto.setIdCategoria(categoriaSelecionada.getId());
                        produto.setDescricao(descricao);

                        produto.salvar();


                    }else{
                        edt_descricao.requestFocus();
                        edt_descricao.setError("Informe uma descrição");
                    }

                }else{
                    ocultarTeclado();
                    erroSalvarProduto();
                }

            }else{
                edt_valor.requestFocus();
                edt_valor.setError("Informe um valor válido.");
            }

        }else{
            edt_nome.requestFocus();
            edt_nome.setError("Informe um nome.");
        }
    }

    private void erroSalvarProduto(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage("Selecione uma categoria para o produto.");
        builder.setPositiveButton("OK", ((dialogInterface, i) -> {
            dialogInterface.dismiss();
        }));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void iniciaComponentes(){
        TextView text_toobar = findViewById(R.id.text_toobar);
        text_toobar.setText("Novo produto");
        img_produto = findViewById(R.id.img_produto);
        edt_nome = findViewById(R.id.edt_nome);

        edt_valor = findViewById(R.id.edt_valor);
        edt_valor.setLocale(new Locale("PT", "br"));

        edt_valor_antigo = findViewById(R.id.edt_valor_antigo);
        edt_valor_antigo.setLocale(new Locale("PT", "br"));


        btn_categoria = findViewById(R.id.btn_categoria);
        edt_descricao = findViewById(R.id.edt_descricao);

    }

    private void ocultarTeclado(){
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                edt_nome.getWindowToken(),0
        );
    }

}