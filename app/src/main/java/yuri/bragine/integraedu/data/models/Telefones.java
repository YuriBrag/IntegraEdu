package yuri.bragine.integraedu.data.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Telefones {
    @SerializedName("principal")
    private TelefoneInfo principal;

    @SerializedName("secundarios")
    private List<TelefoneInfo> secundarios;

    public TelefoneInfo getPrincipal() { return principal; }
    public List<TelefoneInfo> getSecundarios() { return secundarios; }
}