package com.example.aprenda.organizze.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.aprenda.organizze.config.ConfiguracaoFirebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.aprenda.organizze.R;
import com.google.firebase.auth.FirebaseAuth;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

public class PrincipalActivity extends AppCompatActivity {

    private boolean isFABOpen = false;
    private FloatingActionButton fab2, fab3;
    private MaterialCalendarView calendarView;

    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Organizze");

        //eh importante para rodar em versoes antigas
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab2 = findViewById(R.id.fab_receita);
        fab3 = findViewById(R.id.fab_despesa);
        calendarView = findViewById(R.id.calendarView);
        configurarCalendarView();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sair :
                autenticacao.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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

    public void configurarCalendarView() {
        CharSequence meses[] = {"Janeiro","Fevereiro", "Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};
        calendarView.setTitleMonths(meses);

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                Log.i("data", "date = " + date);
            }
        });
    }

}
