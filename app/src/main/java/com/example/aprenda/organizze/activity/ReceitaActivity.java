package com.example.aprenda.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aprenda.organizze.R;
import com.example.aprenda.organizze.config.ConfiguracaoFirebase;
import com.example.aprenda.organizze.enums.Tipo;
import com.example.aprenda.organizze.helpers.Base64Custom;
import com.example.aprenda.organizze.helpers.DateCustom;
import com.example.aprenda.organizze.model.Movimentacao;
import com.example.aprenda.organizze.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ReceitaActivity extends AppCompatActivity {

    private TextInputEditText data, categoria, descricao;
    private EditText valor;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Double receitaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);

        data = findViewById(R.id.ed_data);
        categoria = findViewById(R.id.ed_categoria);
        descricao = findViewById(R.id.ed_descricao);
        valor = findViewById(R.id.ed_valor);

        //preencha o campo data atual
        data.setText(DateCustom.dataAtual());

        //recuperar o valor e já setar o atributo getDespesaTotal
        recuperarReceitaTotal();
    }

    private void recuperarReceitaTotal() {
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64( emailUsuario );
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child( idUsuario );

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue( Usuario.class );
                receitaTotal = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void atualizaReceita(Double despesa) {
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.child("receitaTotal").setValue(despesa);
    }

    public void salvar(View view){

        if(validarCampos()) {
            String dataEscolhida = data.getText().toString();

            Double valorRecuperado = Double.parseDouble(valor.getText().toString());

            movimentacao = new Movimentacao();
            movimentacao.setValor(valorRecuperado);
            movimentacao.setCategoria(categoria.getText().toString());
            movimentacao.setDescricao(descricao.getText().toString());
            movimentacao.setData(dataEscolhida);
            movimentacao.setTipo(Tipo.RECEITA.name());

            atualizaReceita(receitaTotal + valorRecuperado);

            movimentacao.salvar(dataEscolhida);
        }
    }

    private Boolean validarCampos() {

        String campoValor = valor.getText().toString();
        String campoData = data.getText().toString();
        String campoCategoria = categoria.getText().toString();
        String campoDescricao = descricao.getText().toString();

        if(!campoValor.isEmpty()) {

            if(!campoData.isEmpty()) {

                if(!campoCategoria.isEmpty()) {

                    if(!campoDescricao.isEmpty()) {

                        return true;

                    } else {
                        Toast.makeText(ReceitaActivity.this,
                                "Descrição não foi preenchido!",
                                Toast.LENGTH_SHORT);
                        return false;
                    }

                } else {
                    Toast.makeText(ReceitaActivity.this,
                            "Categoria não foi preenchido!",
                            Toast.LENGTH_SHORT);
                    return false;
                }

            } else {
                Toast.makeText(ReceitaActivity.this,
                        "Data não foi preenchida!",
                        Toast.LENGTH_SHORT);
                return false;
            }

        } else {
            Toast.makeText(ReceitaActivity.this,
                    "Valor não foi preenchido!",
                    Toast.LENGTH_SHORT);
            return false;
        }
    }

}
