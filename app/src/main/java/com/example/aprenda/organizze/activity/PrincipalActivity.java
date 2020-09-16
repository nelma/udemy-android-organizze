package com.example.aprenda.organizze.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.example.aprenda.organizze.R;

public class PrincipalActivity extends AppCompatActivity {

    private boolean isFABOpen = false;
    private FloatingActionButton fab2, fab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab2 = findViewById(R.id.fab_receita);
        fab3 = findViewById(R.id.fab_despesa);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void showFABMenu() {
        isFABOpen = true;

        fab2.animate().translationY(-getResources().getDimension(R.dimen.standart_55));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standart_105));
    }

    private void closeFABMenu() {
        isFABOpen = false;

        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
    }

    public void adicionarReceita(View view) {
        startActivity(new Intent(this, ReceitaActivity.class));

    }

    public void adicionarDespesa(View view) {
        startActivity(new Intent(this, DespesaActivity.class));
    }

}
