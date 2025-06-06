package yuri.bragine.integraedu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import yuri.bragine.integraedu.data.models.CorpoAPI.BodyContatos;
import yuri.bragine.integraedu.data.repository.RequestRepository;

public class MainActivity extends AppCompatActivity {

    //private static final String TAG = "MainActivity";
    //private RequestRepository requestRepository;
    //private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //requestRepository = new RequestRepository();

        Button botaoCadastro = findViewById(R.id.buttonSignin);
        Button botaoLogin = findViewById(R.id.buttonLogin);

        botaoCadastro.setOnClickListener(v -> {
            // Vai pra tela de cadastro
        });

        botaoLogin.setOnClickListener(v -> {
            //int origem = 9;
            //String token = "0088c5a7834ebc95321fef219dbd722b";
            //Integer id = 21;

            //requestRepository.getAPI(id, origem, token);

            //String resposta = requestRepository.resposta;

            Intent intent = new Intent(MainActivity.this, HomeActivity.class);

            //intent.putExtra("resposta", resposta);
            startActivity(intent);
        });
    }
}
