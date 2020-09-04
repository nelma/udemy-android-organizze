package com.example.aprenda.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private Button btnEntrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edt_email);
        edtSenha = findViewById(R.id.edt_senha);
        btnEntrar = findViewById(R.id.btn_entrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();

                if(!email.isEmpty()) {
                    if(!senha.isEmpty()) {

                        usuario = new Usuario();
                        usuario.setEmail(email);
                        usuario.setSenha(senha);

                        logar();

                    } else {
                        Toast.makeText(
                                LoginActivity.this,
                                "Preencha a senha",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                } else {
                    Toast.makeText(
                            LoginActivity.this,
                            "Preencha o email",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    private void logar() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                    abrirTelaPrincipal();
                } else {
                    String excecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        excecao = "E-mail não cadastrado";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "E-mail ou senha não correspondem a um usuário cadastrado";
                    } catch (Exception e) {
                        excecao = "Erro ao logar usuário";
                        e.printStackTrace();
                    }


                    Toast.makeText(
                            LoginActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });


    }

    public void abrirTelaPrincipal() {
        startActivity(new Intent(this, PrincipalActivity.class));

        //para fechar a Activity Login
        finish();
    }
}
