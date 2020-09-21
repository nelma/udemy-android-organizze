package com.example.aprenda.organizze.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.aprenda.organizze.adapter.AdapterMovimentacao;
import com.example.aprenda.organizze.config.ConfiguracaoFirebase;
import com.example.aprenda.organizze.helpers.Base64Custom;
import com.example.aprenda.organizze.model.Movimentacao;
import com.example.aprenda.organizze.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.aprenda.organizze.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    private boolean isFABOpen = false;
    private FloatingActionButton fab2, fab3;
    private MaterialCalendarView calendarView;
    private TextView txtSaudacao;
    private TextView txtSaldo;
    private Double despesaTotal = 0.0;
    private Double receitaTotal = 0.0;
    private Double resumo = 0.0;
    private RecyclerView recyclerView;
    private AdapterMovimentacao adapterMovimentacao;
    private List<Movimentacao> movimentacoes = new ArrayList<>();

    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private DatabaseReference usuarioRef;
    private ValueEventListener valueEventListenerUsuario;

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
        txtSaudacao = findViewById(R.id.txt_saudacao);
        txtSaldo = findViewById(R.id.txt_saldo);
        calendarView = findViewById(R.id.calendarView);
        recyclerView = findViewById(R.id.recycler_view);

        configurarCalendarView();


        //configurar adapter
        adapterMovimentacao = new AdapterMovimentacao(movimentacoes, this);

        //configurar recylcerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterMovimentacao);

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarResumo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuarioRef.removeEventListener(valueEventListenerUsuario);
        Log.i("Evento", "evento foi removido!");
    }

    private void recuperarResumo() {

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        usuarioRef = firebaseRef.child("usuarios").child(idUsuario);

        Log.i("Evento", "evento foi adicionado!");

        valueEventListenerUsuario = usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                despesaTotal = usuario.getDespesaTotal();
                receitaTotal = usuario.getReceitaTotal();
                resumo = receitaTotal - despesaTotal;

                //https://docs.oracle.com/javase/7/docs/api/java/text/DecimalFormat.html
                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                String resultadoFormatado = decimalFormat.format(resumo);

                txtSaudacao.setText("Olá, " + usuario.getNome());
                txtSaldo.setText("R$ " + resultadoFormatado);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
