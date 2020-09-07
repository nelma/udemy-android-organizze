package com.example.aprenda.organizze.helpers;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String dataAtual() {
        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(data);
    }

    public static String mesAnoDataEscolhida(String data) {
       String retorno[] =  data.split("/");
       String mesAno = retorno[1] + retorno[2];
       return mesAno;
    }
}
