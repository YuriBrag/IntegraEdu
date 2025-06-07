package yuri.bragine.integraedu.data.models.Entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "coordenadores",
        indices = {@Index(value = {"email"}, unique = true)})
public class Coordenador {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String nome;

    @NonNull
    private String email;

    @NonNull
    private String senha;

    private String cpf;

    private String telefone;

    public Coordenador(@NonNull String nome, @NonNull String email, @NonNull String senha, String cpf, String telefone) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    @NonNull
    public String getNome() { return nome; }
    public void setNome(@NonNull String nome) { this.nome = nome; }
    @NonNull
    public String getEmail() { return email; }
    public void setEmail(@NonNull String email) { this.email = email; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    @NonNull
    public String getSenha() { return senha; }
    public void setSenha(@NonNull String senha) { this.senha = senha; }
}
