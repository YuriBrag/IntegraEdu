package yuri.bragine.integraedu;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import yuri.bragine.integraedu.data.models.Evento;
import yuri.bragine.integraedu.data.models.Pessoa;
import yuri.bragine.integraedu.data.repository.EventosCallback;
import yuri.bragine.integraedu.data.repository.PessoaCallback;
import yuri.bragine.integraedu.data.repository.RequestRepository;

public class EventosActivity extends AppCompatActivity {

    private static final String TAG = "EventosActivity";
    private static final int ORIGEM_API = 9;
    private static final String TOKEN_API = "0088c5a7834ebc95321fef219dbd722b";
    private RequestRepository repository;
    private LinearLayout containerAlunos;
    private ProgressBar progressBar;

    private Map<Integer, List<Evento>> eventosPorPessoa = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        repository = new RequestRepository();
        containerAlunos = findViewById(R.id.containerAlunos);
        progressBar = findViewById(R.id.progressBar);

        buscarEventosFiltrados();
    }

    private void buscarEventosFiltrados() {
        progressBar.setVisibility(View.VISIBLE);

        repository.buscarListaDeEventos(ORIGEM_API, TOKEN_API, new EventosCallback() {
            @Override
            public void onSuccess(List<Evento> eventos) {
                Log.d(TAG, "Total de eventos recebidos: " + eventos.size());

                List<Evento> eventosFiltrados = eventos.stream()
                        .filter(evento -> evento.getTipoNome() != null)
                        .collect(Collectors.toList());

                Log.d(TAG, "Eventos filtrados: " + eventosFiltrados.size());

                // Agrupar eventos por pessoa
                for (Evento evento : eventosFiltrados) {
                    try {
                        int pessoaId = Integer.parseInt(evento.getPessoa());
                        eventosPorPessoa.computeIfAbsent(pessoaId, k -> new ArrayList<>()).add(evento);
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "ID inválido: " + evento.getPessoa());
                    }
                }

                // Buscar dados da pessoa para cada grupo de eventos
                for (Map.Entry<Integer, List<Evento>> entry : eventosPorPessoa.entrySet()) {
                    buscarPessoaComEventos(entry.getKey(), entry.getValue());
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Erro ao buscar eventos: " + errorMessage);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void buscarPessoaComEventos(int pessoaId, List<Evento> eventosDaPessoa) {
        repository.buscarPessoaPorId(pessoaId, ORIGEM_API, TOKEN_API, new PessoaCallback() {
            @Override
            public void onSuccess(Pessoa pessoa) {
                runOnUiThread(() -> criarItemPessoa(pessoa, eventosDaPessoa));
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Erro ao buscar pessoa " + pessoaId + ": " + errorMessage);
            }
        });
    }

    private void criarItemPessoa(Pessoa pessoa, List<Evento> eventosDaPessoa) {
        View itemView = getLayoutInflater().inflate(R.layout.item_aluno, containerAlunos, false);
        TextView nomeAluno = itemView.findViewById(R.id.txtNomeAluno);
        LinearLayout eventosLayout = itemView.findViewById(R.id.containerEventos);

        nomeAluno.setText(pessoa.getNome());

        // Adicionar OnClickListener para o nome do aluno
        nomeAluno.setOnClickListener(v -> {
            if (eventosLayout.getVisibility() == View.GONE) {
                eventosLayout.setVisibility(View.VISIBLE);
            } else {
                eventosLayout.setVisibility(View.GONE);
            }
        });

        // Adicionar todos os eventos da pessoa
        for (Evento evento : eventosDaPessoa) {
            TextView eventoView = new TextView(this);
            eventoView.setText("• " + evento.getTipoNome());
            eventoView.setPadding(32, 8, 8, 8);
            eventosLayout.addView(eventoView);
        }

        containerAlunos.addView(itemView);
    }
}


