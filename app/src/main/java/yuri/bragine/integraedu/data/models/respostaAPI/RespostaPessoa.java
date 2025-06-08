package yuri.bragine.integraedu.data.models.respostaAPI;

import com.google.gson.annotations.SerializedName;
import yuri.bragine.integraedu.data.models.Pessoa;

public class RespostaPessoa {
    @SerializedName("success")
    private boolean success;
    @SerializedName("dados")
    private Pessoa dados;
    public boolean isSuccess() { return success; }
    public Pessoa getDados() { return dados; }
}