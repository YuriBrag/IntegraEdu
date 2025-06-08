package yuri.bragine.integraedu.data.models;

import com.google.gson.annotations.SerializedName;

public class Pessoa {
    @SerializedName("id")
    private String id;

    @SerializedName("nome")
    private String nome;

    @SerializedName("cpf")
    private String cpf;

    @SerializedName("telefones")
    private Telefones telefones;

    @SerializedName("emails")
    private Emails emails;

    // Getters
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public Telefones getTelefones() { return telefones; }
    public Emails getEmails() { return emails; }

    @Override
    public String toString() {
        String emailPrincipal = (emails != null && emails.getPrincipal() != null) ? emails.getPrincipal().getEmail() : "N/A";
        String telefonePrincipal = (telefones != null && telefones.getPrincipal() != null) ? telefones.getPrincipal().getTelefone() : "N/A";
        return "Pessoa{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + emailPrincipal + '\'' +
                ", telefone='" + telefonePrincipal + '\'' +
                '}';
    }
}