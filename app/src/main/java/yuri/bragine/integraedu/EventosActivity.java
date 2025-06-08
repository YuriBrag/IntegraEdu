package yuri.bragine.integraedu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;

import yuri.bragine.integraedu.data.models.Entity.Resposta;
import yuri.bragine.integraedu.data.models.Entity.RespostaWrapper;
import yuri.bragine.integraedu.data.models.Entity.Dado;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import yuri.bragine.integraedu.data.repository.ApiResponseCallback;
import yuri.bragine.integraedu.data.repository.RequestRepository;


public class EventosActivity extends AppCompatActivity implements ApiResponseCallback {

    private RequestRepository repository;
    private TextView textViewResultado;
    private Button botaoChamarApi;

    private static final int ORIGEM_API = 9;
    private static final String TOKEN_API = "0088c5a7834ebc95321fef219dbd722b";
    private static final int ID_CONTATO = 21;
    public int ID_BUSCA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_eventos);


        repository = new RequestRepository();

        textViewResultado = findViewById(R.id.txtDaApi);
        botaoChamarApi = findViewById(R.id.btnApi);


        botaoChamarApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (textViewResultado != null) {
                //     textViewResultado.setText("Carregando...");
                // }
                Toast.makeText(EventosActivity.this, "Chamando API...", Toast.LENGTH_SHORT).show();
                chamarApi();
            }
        });
    }



    private void chamaAPIContato(String id){
        repository.getAPIContato(Integer.parseInt(id), ORIGEM_API, TOKEN_API, this);
    }

    private void chamarApi() {
        repository.getAPI(ID_CONTATO, ORIGEM_API, TOKEN_API, this);
    }

    @Override
    public void onSuccess(String jsonResponse) {
        Log.i("ACTIVITY_API", "Sucesso recebido: " + jsonResponse);

        Gson gson = new Gson();
        RespostaWrapper respostaWrapper = gson.fromJson(jsonResponse, RespostaWrapper.class);
        respostaWrapper.Resposta=gson.fromJson(jsonResponse, Resposta.class);

        if (respostaWrapper != null && respostaWrapper.Resposta != null) {
            List<Dado> lista = respostaWrapper.Resposta.dados;
            for (Dado dado : lista) {
                Log.i("DADO", "Pessoa: " + dado.pessoa + ", tipoNome: " + dado.tipoNome);
            }
        }


        if (respostaWrapper != null && respostaWrapper.Resposta != null &&
                respostaWrapper.Resposta.dados != null) {

            String tipoNome = respostaWrapper.Resposta.dados.get(0).tipoNome;
            String pessoa= respostaWrapper.Resposta.dados.get(0).pessoa;
            //chamaAPIContato(pessoa);



            if (textViewResultado != null) {
                textViewResultado.setText("Pessoa "+  pessoa  +"tipoNome do primeiro dado: " + tipoNome);
            }


        } else {
            textViewResultado.setText("Nenhum dado encontrado na resposta.");
        }
    }



    @Override
    public void onError(String errorMessage) {
        Log.e("ACTIVITY_API", "Erro recebido: " + errorMessage);
         if (textViewResultado != null) {
             textViewResultado.setText("Erro na API:\n" + errorMessage);
         }
        Toast.makeText(this, "Erro: " + errorMessage, Toast.LENGTH_LONG).show();
    }
}
