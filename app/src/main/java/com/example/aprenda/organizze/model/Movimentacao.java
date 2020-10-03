package com.example.aprenda.organizze.model;

import android.util.Log;

import com.example.aprenda.organizze.config.ConfiguracaoFirebase;
import com.example.aprenda.organizze.helpers.Base64Custom;
import com.example.aprenda.organizze.helpers.DateCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Movimentacao {

    private String data;
    private String categoria;
    private String descricao;
    private String tipo;
    private Double valor;
    private String key;

    public Movimentacao() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void salvar(String data) {

        //recuperando o email da autenticacao
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String idUsuario = Base64Custom.codificarBase64(
                autenticacao.getCurrentUser().getEmail()
        );

        String mesAno = DateCustom.mesAnoDataEscolhida(data);

        DatabaseReference reference = ConfiguracaoFirebase.getFirebaseDatabase();

        //push eh para adicionar - gerar um id do firebase
        reference.child("movimentacao")
                .child(idUsuario)
                .child(mesAno)
                .push()
                .setValue(this);

    }
}
