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
import com.example.aprenda.organizze.helpers.Base64Custom;
import com.example.aprenda.organizze.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

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

                    String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setIdUsuario(idUsuario);
                    usuario.salvar();

                    //fecha esta activity e exibe a que está na stack(pilha) - intro_cadastro
                    finish();
                } else {

                    String excecao = "";
                    try {
                        //lancar a exception, nao trava a execucao do app
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        excecao = "Digite uma senha forte!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Digite um e-mail valido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        excecao = "Essa conta já foi cadastrada";
                    } catch (Exception e) {
                        excecao = "Erro ao cadastar usuário. Aguarde uns minutos e tente novamente ";
                        e.printStackTrace();
                    }

                    Toast.makeText(
                            CadastroActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

    }
}
