package com.example.ifood.activity.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ifood.R;
import com.example.ifood.activity.empresa.EmpresaFinalizaCadastroActivity;
import com.example.ifood.activity.empresa.EmpresaHomeActivity;
import com.example.ifood.activity.usuario.UsuarioFinalizaCadastroActivity;
import com.example.ifood.activity.usuario.UsuarioHomeActivity;
import com.example.ifood.helper.FirebaseHelper;
import com.example.ifood.model.Empresa;
import com.example.ifood.model.Login;
import com.example.ifood.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {
    private Login login;
    private Usuario usuario;
    private Empresa empresa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        verificaAutenticacao();
    }

    private void verificaAutenticacao(){
        if (FirebaseHelper.getAutenticado()){
            verificaCadastro();
        }else{
            finish();
            startActivity(new Intent(this, UsuarioHomeActivity.class));
        }
    }

    private void verificaCadastro(){
        DatabaseReference loginRef = FirebaseHelper.getDatabaseReference()
                .child("login")
                .child(FirebaseHelper.getIdFirebase());
        loginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                login = snapshot.getValue(Login.class);
                verificaAcesso(login);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void verificaAcesso(Login login){
        if(login != null){
            if (login.getTipo().equals("U")){
                if (login.getAcesso()){
                    finish();
                    startActivity(new Intent(getBaseContext(), UsuarioHomeActivity.class));
                }else{
                    recuperaUsuario();
                }
            }else{
                if (login.getAcesso()){
                    finish();
                    startActivity(new Intent(getBaseContext(), EmpresaHomeActivity.class));
                }else{
                    recuperaEmpresa();
                }
            }
        }
    }

    private void recuperaUsuario(){
        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(login.getId());
        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                if(usuario != null){
                    finish();
                    Intent intent = new Intent(getBaseContext(), UsuarioFinalizaCadastroActivity.class);
                    intent.putExtra("login", login);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        startActivity(new Intent(getBaseContext(), UsuarioFinalizaCadastroActivity.class));


    }

    private void recuperaEmpresa(){
        DatabaseReference empresaRef = FirebaseHelper.getDatabaseReference()
                .child("empresas")
                .child(login.getId());
        empresaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Empresa empresa = snapshot.getValue(Empresa.class);
                if(empresa != null){
                    finish();
                    Intent intent = new Intent(getBaseContext(), EmpresaFinalizaCadastroActivity.class);
                    intent.putExtra("login", login);
                    intent.putExtra("empresa", empresa);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        startActivity(new Intent(getBaseContext(), UsuarioFinalizaCadastroActivity.class));


    }
}