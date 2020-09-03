package com.example.aprenda.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aprenda.organizze.R;
import com.example.aprenda.organizze.config.ConfiguracaoFirebase;
import com.example.aprenda.organizze.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {


    private EditText edtNome, edtEmail, edtSenha;
    private Button btnCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = findViewById(R.id.edt_nome);
        edtEmail = findViewById(R.id.edt_email);
        edtSenha = findViewById(R.id.edt_senha);

        btnCadastrar = findViewById(R.id.btn_cadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = edtNome.getText().toString();
                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();

                /**
                 * TODO
                 * exportar para um metodo separado
                 */
                if (!nome.isEmpty()) {

                    if (!email.isEmpty()) {

                        if (!senha.isEmpty()) {

                            usuario = new Usuario();
                            usuario.setNome(nome);
                            usuario.setEmail(email);
                            usuario.setSenha(senha);
                            cadastrar();

                        } else {
                            Toast.makeText(
                                    CadastroActivity.this,
                                    "Preencha a senha",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }


                    } else {
                        Toast.makeText(
                                CadastroActivity.this,
                                "Preencha o email",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                } else {
                    Toast.makeText(
                            CadastroActivity.this,
                            "Preencha o nome",
                            Toast.LENGTH_SHORT
                    ).show();
                }

            }
        });
    }

    public void cadastrar() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(
                            CadastroActivity.this,
                            "Sucesso ao cadastrar usuário.",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            CadastroActivity.this,
                            "Falha ao cadastrar usuário.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

    }
}