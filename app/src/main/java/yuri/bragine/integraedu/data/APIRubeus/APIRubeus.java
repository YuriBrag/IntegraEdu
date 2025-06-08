package yuri.bragine.integraedu.data.APIRubeus;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import yuri.bragine.integraedu.data.models.CorpoAPI.BodyContatos;
import yuri.bragine.integraedu.data.models.CorpoAPI.BodyEventos;
import yuri.bragine.integraedu.data.models.respostaAPI.RespostaEventos;
import yuri.bragine.integraedu.data.models.respostaAPI.RespostaPessoa;

public interface APIRubeus {
    @POST("/api/Evento/listarEventos")
    Call<JsonObject> buscarEventos(@Body BodyEventos body);

    @POST("/api/Evento/listarEventos")
    Call<RespostaEventos> buscarEventos2(@Body BodyEventos body);

//    @POST("/api/Contato/dadosPessoa")
//    Call<JsonObject> buscarContatosPorFiltros2(@Body BodyContatos body);

    @POST("/api/Contato/dadosPessoa")
    Call<RespostaPessoa> buscarPessoaPorId(@Body BodyContatos body);
}
