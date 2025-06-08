package yuri.bragine.integraedu.data.repository;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import yuri.bragine.integraedu.data.APIRubeus.APIClient;
import yuri.bragine.integraedu.data.APIRubeus.APIRubeus;
import yuri.bragine.integraedu.data.models.CorpoAPI.BodyContatos;
import yuri.bragine.integraedu.data.models.CorpoAPI.BodyEventos;
import yuri.bragine.integraedu.data.models.respostaAPI.RespostaEventos;
import yuri.bragine.integraedu.data.models.respostaAPI.RespostaPessoa;

import com.google.gson.JsonObject;

public class RequestRepository {

    private final APIRubeus rubeus;

    public RequestRepository() {
        this.rubeus = APIClient.getClient().create(APIRubeus.class);
    }

    public void getAPI( Integer origem, String token, final ApiResponseCallback callback) {

        BodyEventos body = new BodyEventos( origem, token);
        Call<JsonObject> call = rubeus.buscarEventos(body);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String resposta = response.body().toString();
                    Log.i("API_RESPONSE", "Resposta: " + resposta);
                    callback.onSuccess(resposta);
                } else {
                    String erroMsg = "Erro na resposta da API. Código: " + response.code();
                    if (response.errorBody() != null) {
                        try {
                            erroMsg += " Detalhes: " + response.errorBody().string();
                        } catch (Exception e) {
                            Log.e("API_RESPONSE", "Erro ao ler corpo do erro", e);
                        }
                    }
                    Log.e("API_RESPONSE", erroMsg);
                    callback.onError(erroMsg);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("API_RESPONSE", "Falha na requisição: " + t.toString(), t);
                callback.onError(t.getMessage());
            }
        });
    }

    public void buscarListaDeEventos(int origem, String token, final EventosCallback callback) {
        // Seu BodyEventos parece precisar de um ID, mas a listagem geral talvez não.
        // Se precisar de um ID fixo para a listagem, passe aqui. Ex: new BodyEventos(21, origem, token)
        // Se não precisar de ID, você talvez precise de um novo construtor em BodyEventos.
        // Usando o construtor que você tem:
        BodyEventos body = new BodyEventos(origem, token); // Passando ID nulo por exemplo

        // Agora a chamada espera um RespostaEventos
        Call<RespostaEventos> call = rubeus.buscarEventos2(body);

        call.enqueue(new Callback<RespostaEventos>() {
            @Override
            public void onResponse(Call<RespostaEventos> call, Response<RespostaEventos> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    callback.onSuccess(response.body().getDados());
                } else {
                    callback.onError("Erro ao buscar eventos. Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<RespostaEventos> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void buscarPessoaPorId(int pessoaId, int origem, String token, final PessoaCallback callback) {
        // Seu endpoint buscarPessoaPorId espera um BodyContatos, que segundo seu
        // código anterior, pode receber (id, origem, token).
        BodyContatos body = new BodyContatos(pessoaId, origem, token);
        Call<RespostaPessoa> call = rubeus.buscarPessoaPorId(body);

        call.enqueue(new Callback<RespostaPessoa>() {
            @Override
            public void onResponse(Call<RespostaPessoa> call, Response<RespostaPessoa> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess() && response.body().getDados() != null) {
                    callback.onSuccess(response.body().getDados());
                } else {
                    callback.onError("Erro ao buscar pessoa com ID " + pessoaId + ". Código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<RespostaPessoa> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
