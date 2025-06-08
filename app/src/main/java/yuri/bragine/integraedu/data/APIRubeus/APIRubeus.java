package yuri.bragine.integraedu.data.APIRubeus;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import yuri.bragine.integraedu.data.models.CorpoAPI.BodyContatos;

public interface APIRubeus {
    @POST("/api/Evento/listarEventos")
    Call<JsonObject> buscarContatosPorFiltros(@Body BodyContatos body);

    @POST("/api/Contato/dadosPessoa")
    Call<JsonObject> buscarContatosPorFiltros2(@Body BodyContatos body);
}
