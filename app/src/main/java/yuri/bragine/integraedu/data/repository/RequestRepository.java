package yuri.bragine.integraedu.data.repository;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import yuri.bragine.integraedu.data.APIRubeus.APIClient;
import yuri.bragine.integraedu.data.APIRubeus.APIRubeus;
import yuri.bragine.integraedu.data.models.CorpoAPI.BodyContatos;

import com.google.gson.JsonObject;

public class RequestRepository extends AppCompatActivity {

    private APIRubeus rubeus;

    public String resposta;

    public void getAPI(Integer id, Integer origem, String token) {

        rubeus = APIClient.getClient().create(APIRubeus.class);

        BodyContatos body = new BodyContatos(id, origem, token);

        Call<JsonObject> call = rubeus.buscarContatosPorFiltros(body);

        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                resposta = response.body().toString();
                Log.i("API_RESPONSE", "Resposta: " + resposta);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i("API_RESPONSE", "Resposta: " + t.toString());
            }

        });
    }
}
