package com.example.aprenda.organizze.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aprenda.organizze.R;
import com.example.aprenda.organizze.enums.Tipo;
import com.example.aprenda.organizze.model.Movimentacao;

import java.util.List;

public class AdapterMovimentacao extends RecyclerView.Adapter<AdapterMovimentacao.ViewHolderMovimentacao> {

    List<Movimentacao> movimentacoes;
    Context context;

    public AdapterMovimentacao(List<Movimentacao> movimentacoes, Context context) {
        this.movimentacoes = movimentacoes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderMovimentacao onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_movimentacao, parent, false);
        return new ViewHolderMovimentacao(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMovimentacao holder, int position) {
        Movimentacao movimentacao = movimentacoes.get(position);

        holder.titulo.setText(movimentacao.getDescricao());
        holder.valor.setText(String.valueOf(movimentacao.getValor()));
        holder.categoria.setText(movimentacao.getCategoria());

        if(movimentacao.getTipo().equals(Tipo.DESPESA.name())) {
            holder.valor.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.valor.setText("-" + movimentacao.getValor());
        }
    }

    @Override
    public int getItemCount() {
        return movimentacoes.size();
    }

    public class ViewHolderMovimentacao extends RecyclerView.ViewHolder {

        TextView titulo;
        TextView categoria;
        TextView valor;

        public ViewHolderMovimentacao(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.txt_adapter_titulo);
            valor = itemView.findViewById(R.id.txt_adapter_valor);
            categoria = itemView.findViewById(R.id.txt_adapter_categoria);
        }
    }
}
