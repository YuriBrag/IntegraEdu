package yuri.bragine.integraedu.data.models;

import com.google.gson.annotations.SerializedName;

public class TelefoneInfo {
    @SerializedName("id")
    private String id;

    @SerializedName("telefone")
    private String telefone;

    public String getId() { return id; }
    public String getTelefone() { return telefone; }
}