package com.example.aprenda.organizze.helpers;

import android.util.Base64;

public class Base64Custom {

    public static String codificarBase64(String texto) {
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT ).replaceAll("(\\n|\\r)","");
    }

    public static String decodificarBase64(String textoDecodificado) {
        return Base64.decode(
                textoDecodificado, Base64.DEFAULT
        ).toString();
    }
}