package com.example.ifood.activity.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.ifood.R;

public class UsuarioEnderecosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_enderecos);

        iniciaComponentes();

        configCliques();
    }
    private void configCliques(){
        findViewById(R.id.ib_voltar).setOnClickListener(view -> finish());
    }

    private void iniciaComponentes(){
        TextView text_toobar = findViewById(R.id.text_toobar);
        text_toobar.setText("Endereços");
    }
}