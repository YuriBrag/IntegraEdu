package yuri.bragine.integraedu.data.models.CorpoAPI;

public class BodyContatos {
    private Integer origem = 9;
    private String token = "0088c5a7834ebc95321fef219dbd722b";
    private Integer id = 21;

    public BodyContatos(Integer id, Integer origem, String token) {
        this.id = id;
        this.token = token;
        this.origem = origem;
    }

    // Getters e setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
