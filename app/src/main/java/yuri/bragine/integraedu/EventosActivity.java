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

import java.util.ArrayList;
import java.util.List;

import yuri.bragine.integraedu.data.models.Evento;
import yuri.bragine.integraedu.data.models.Pessoa;
import yuri.bragine.integraedu.data.repository.ApiResponseCallback;
import yuri.bragine.integraedu.data.repository.EventosCallback;
import yuri.bragine.integraedu.data.repository.PessoaCallback;
import yuri.bragine.integraedu.data.repository.RequestRepository;


public class EventosActivity extends AppCompatActivity  {

    private static final String TAG = "EventosActivity";
    private RequestRepository repository;
    private TextView textViewResultado;
    private Button botaoChamarApi;

    private static final int ORIGEM_API = 9;
    private static final String TOKEN_API = "0088c5a7834ebc95321fef219dbd722b";
    //private static final int ID_CONTATO = 21;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_eventos);
//
//
//        repository = new RequestRepository();
//
//        textViewResultado = findViewById(R.id.txtDaApi);
//        botaoChamarApi = findViewById(R.id.btnApi);
//
//
//        botaoChamarApi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // if (textViewResultado != null) {
//                //     textViewResultado.setText("Carregando...");
//                // }
//                Toast.makeText(EventosActivity.this, "Chamando API...", Toast.LENGTH_SHORT).show();
//                chamarApi();
//            }
//        });
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos); // Use um layout com botão e textview

        repository = new RequestRepository();
        botaoChamarApi = findViewById(R.id.btnApi);
        textViewResultado = findViewById(R.id.txtDaApi);

        botaoChamarApi.setOnClickListener(v -> {
            textViewResultado.setText("Buscando eventos...");
            buscarEventosEProcessar();
        });
    }
    private void buscarEventosEProcessar() {
        // 1. CHAMA A PRIMEIRA API USANDO O NOVO MÉTODO
        repository.buscarListaDeEventos(ORIGEM_API, TOKEN_API, new EventosCallback() {
            @Override
            public void onSuccess(List<Evento> eventos) {
                Log.i(TAG, "Eventos recebidos: " + eventos.size());
                textViewResultado.setText("Eventos recebidos. Filtrando...\n");

                // 2. FILTRA A RESPOSTA
                List<Evento> eventosFiltrados = new ArrayList<>();
                for (Evento evento : eventos) {
                    if (evento.getTipoNome() != null && evento.getTipoNome().endsWith("Acompanhamento do Estudante")) {
                        eventosFiltrados.add(evento);
                    }
                }

                if (eventosFiltrados.isEmpty()) {
                    textViewResultado.append("\nNenhum evento de acompanhamento encontrado.");
                    return;
                }

                textViewResultado.append("\nEventos de acompanhamento encontrados: " + eventosFiltrados.size() + "\n\n");

                // 3. PARA CADA EVENTO FILTRADO, CHAMA A SEGUNDA API
                for (Evento eventoFiltrado : eventosFiltrados) {
                    try {
                        int pessoaId = Integer.parseInt(eventoFiltrado.getPessoa());
                        buscarDadosPessoa(pessoaId);
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "ID da pessoa não é um número válido: " + eventoFiltrado.getPessoa());
                    }
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Erro ao buscar eventos: " + errorMessage);
                textViewResultado.setText("Erro ao buscar eventos: " + errorMessage);
            }
        });
    }
    private void buscarDadosPessoa(int pessoaId) {
        repository.buscarPessoaPorId(pessoaId, ORIGEM_API, TOKEN_API, new PessoaCallback() {
            @Override
            public void onSuccess(Pessoa pessoa) {
                Log.i(TAG, "Pessoa encontrada para o ID " + pessoaId);

                String nome = pessoa.getNome() != null ? pessoa.getNome() : "N/A";
                String email = "N/A";
                if (pessoa.getEmails() != null && pessoa.getEmails().getPrincipal() != null) {
                    email = pessoa.getEmails().getPrincipal().getEmail();
                }

                String resultado = "Pessoa: " + nome + " (ID: " + pessoa.getId() + ")\nEmail: " + email + "\n\n";
                textViewResultado.append(resultado);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, errorMessage);
                textViewResultado.append("Falha ao buscar dados para ID " + pessoaId + "\n\n");
            }
        });
    }



//    private void chamaAPIContato(String id){
//        repository.getAPIContato(Integer.parseInt(id), ORIGEM_API, TOKEN_API, this);
//    }

//    private void chamarApi() {
//        repository.getAPI( ORIGEM_API, TOKEN_API, this);
//    }
//
//    @Override
//    public void onSuccess(String jsonResponse) {
//        Log.i("ACTIVITY_API", "Sucesso recebido: " + jsonResponse);
//
//        Gson gson = new Gson();
//        RespostaWrapper respostaWrapper = gson.fromJson(jsonResponse, RespostaWrapper.class);
//        respostaWrapper.Resposta=gson.fromJson(jsonResponse, Resposta.class);
//
//        if (respostaWrapper != null && respostaWrapper.Resposta != null) {
//            List<Dado> lista = respostaWrapper.Resposta.dados;
//            for (Dado dado : lista) {
//                Log.i("DADO", "Pessoa: " + dado.pessoa + ", tipoNome: " + dado.tipoNome);
//            }
//        }
//
//
//        if (respostaWrapper != null && respostaWrapper.Resposta != null &&
//                respostaWrapper.Resposta.dados != null) {
//
//            String tipoNome = respostaWrapper.Resposta.dados.get(0).tipoNome;
//            String pessoa= respostaWrapper.Resposta.dados.get(0).pessoa;
//            //chamaAPIContato(pessoa);
//
//
//
//            if (textViewResultado != null) {
//                textViewResultado.setText("Pessoa "+  pessoa  +"tipoNome do primeiro dado: " + tipoNome);
//            }
//
//
//        } else {
//            textViewResultado.setText("Nenhum dado encontrado na resposta.");
//        }
//    }



//    @Override
//    public void onError(String errorMessage) {
//        Log.e("ACTIVITY_API", "Erro recebido: " + errorMessage);
//         if (textViewResultado != null) {
//             textViewResultado.setText("Erro na API:\n" + errorMessage);
//         }
//        Toast.makeText(this, "Erro: " + errorMessage, Toast.LENGTH_LONG).show();
//    }
}
