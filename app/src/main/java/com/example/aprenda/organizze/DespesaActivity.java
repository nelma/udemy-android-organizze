package com.example.aprenda.organizze;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.aprenda.organizze.helpers.DateCustom;
import com.google.android.material.textfield.TextInputEditText;

public class DespesaActivity extends AppCompatActivity {

    private TextInputEditText data, categoria, descricao;
    private EditText valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        data = findViewById(R.id.ed_data);
        categoria = findViewById(R.id.ed_categoria);
        descricao = findViewById(R.id.ed_descricao);
        valor = findViewById(R.id.ed_valor);

        //preencha o campo data atual
        data.setText(DateCustom.dataAtual());


    }
}
