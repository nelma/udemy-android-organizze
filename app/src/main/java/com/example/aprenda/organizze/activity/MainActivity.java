package com.example.aprenda.organizze.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.aprenda.organizze.R;
import com.example.aprenda.organizze.activity.CadastroActivity;
import com.example.aprenda.organizze.activity.LoginActivity;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Comentada para exibir os slides add
//        setContentView(R.layout.activity_main);


        //hide visible button slide
        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_1)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_2)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_3)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_4)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_cadastro)
                .build()
        );
    }

    public void btnEntrar(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void btnCadastrar(View view) {
        startActivity(new Intent(this, CadastroActivity.class));
    }
}
