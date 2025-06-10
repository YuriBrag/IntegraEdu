package yuri.bragine.integraedu.data.models;

import com.google.gson.annotations.SerializedName;

public class Evento {
    @SerializedName("id")
    private String id;

    @SerializedName("tipoNome")
    private String tipoNome;

    @SerializedName("pessoa")
    private String pessoa; // O ID da pessoa

    // Getters
    public String getId() { return id; }
    public String getTipoNome() { return tipoNome; }
    public String getPessoa() { return pessoa; }

}