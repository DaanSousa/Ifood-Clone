package com.example.ifood.fragment.empresa;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ifood.R;
import com.example.ifood.activity.empresa.EmpresaFormProdutoActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EmpresaProdutoFragment extends Fragment {

    private FloatingActionButton fab_add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_empresa_produto, container, false);

        iniciaComponentes(view);

        configCliques();

        return view;
    }

    private void configCliques(){
        fab_add.setOnClickListener(view ->
                startActivity(new Intent(requireActivity(), EmpresaFormProdutoActivity.class)));
    }

    private void iniciaComponentes(View view){
        fab_add = view.findViewById(R.id.fab_add);

    }


}