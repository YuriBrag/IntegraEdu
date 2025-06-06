package yuri.bragine.integraedu.data.repository;

import android.util.Log;

// Remova "extends AppCompatActivity"
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import yuri.bragine.integraedu.data.APIRubeus.APIClient;
import yuri.bragine.integraedu.data.APIRubeus.APIRubeus;
import yuri.bragine.integraedu.data.models.CorpoAPI.BodyContatos;

import com.google.gson.JsonObject;

public class RequestRepository {

    private final APIRubeus rubeus;

    public RequestRepository() {
        this.rubeus = APIClient.getClient().create(APIRubeus.class);
    }

    public void getAPI(Integer id, Integer origem, String token, final ApiResponseCallback callback) {

        BodyContatos body = new BodyContatos(id, origem, token);
        Call<JsonObject> call = rubeus.buscarContatosPorFiltros(body);

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
}
