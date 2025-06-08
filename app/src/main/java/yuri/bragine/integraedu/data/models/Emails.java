package yuri.bragine.integraedu.data.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Emails {
    @SerializedName("principal")
    private EmailInfo principal;

    @SerializedName("secundarios")
    private List<EmailInfo> secundarios;

    public EmailInfo getPrincipal() { return principal; }
    public List<EmailInfo> getSecundarios() { return secundarios; }
}