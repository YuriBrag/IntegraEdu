package yuri.bragine.integraedu.data.models.CorpoAPI;

public class BodyEventos {
    private Integer origem = 9;
    private String token = "0088c5a7834ebc95321fef219dbd722b";
    //private Integer id = 21;

    public BodyEventos(Integer origem, String token) {
        this.token = token;
        this.origem = origem;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}