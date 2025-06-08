package yuri.bragine.integraedu.data.models;

import com.google.gson.annotations.SerializedName;

public class EmailInfo {
    @SerializedName("id")
    private String id;

    @SerializedName("email")
    private String email;

    public String getId() { return id; }
    public String getEmail() { return email; }
}