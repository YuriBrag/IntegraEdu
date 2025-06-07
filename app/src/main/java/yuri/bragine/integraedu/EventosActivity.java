package yuri.bragine.integraedu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import yuri.bragine.integraedu.data.repository.ApiResponseCallback;
import yuri.bragine.integraedu.data.repository.RequestRepository;


public class EventosActivity extends AppCompatActivity implements ApiResponseCallback {

    private RequestRepository repository;
    private TextView textViewResultado;
    private Button botaoChamarApi;

    private static final int ORIGEM_API = 9;
    private static final String TOKEN_API = "0088c5a7834ebc95321fef219dbd722b";
    private static final int ID_CONTATO = 21;

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

    private void chamarApi() {
        repository.getAPI(ID_CONTATO, ORIGEM_API, TOKEN_API, this);
    }

    @Override
    public void onSuccess(String jsonResponse) {
        Log.i("ACTIVITY_API", "Sucesso recebido: " + jsonResponse);
         if (textViewResultado != null) {
             textViewResultado.setText("Resposta da API:\n" + jsonResponse);
         }
        Toast.makeText(this, "Sucesso! Resposta recebida.", Toast.LENGTH_SHORT).show();
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
