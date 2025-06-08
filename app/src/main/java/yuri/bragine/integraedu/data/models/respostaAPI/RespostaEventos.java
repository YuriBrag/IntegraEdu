package yuri.bragine.integraedu.data.models.respostaAPI;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import yuri.bragine.integraedu.data.models.Evento;

public class RespostaEventos {
    @SerializedName("success")
    private boolean success;

    @SerializedName("dados")
    private List<Evento> dados;

    // Getters
    public boolean isSuccess() { return success; }
    public List<Evento> getDados() { return dados; }
}