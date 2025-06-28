package yuri.bragine.integraedu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
// import androidx.appcompat.widget.Toolbar; // Não é mais necessário

import androidx.appcompat.app.AppCompatActivity;

// Import para a BottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        // Inicia a busca de dados da sua tela
        buscarEventosFiltrados();

        // Configura toda a navegação (botão voltar e barra inferior)
        setupNavigation();
    }

    private void setupNavigation() {
        // Botão de Voltar
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> finish()); // Fecha a activity e volta

        // Barra de Navegação Inferior (BottomNavigationView)
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_eventos); // Define este item como ativo

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish(); // Fecha a tela atual para não empilhar
                return true;
            } else if (itemId == R.id.nav_alunos) {
                startActivity(new Intent(getApplicationContext(), AlunosActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_dashboards) {
                startActivity(new Intent(getApplicationContext(), DashboardsActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_eventos) {
                // Já estamos aqui, não faz nada
                return true;
            }
            return false;
        });
    }

    // O método onSupportNavigateUp não é mais necessário com o ImageButton
    // @Override
    // public boolean onSupportNavigateUp() {
    //     finish();
    //     return true;
    // }

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

                eventosPorPessoa.clear(); // Limpa dados antigos antes de popular
                for (Evento evento : eventosFiltrados) {
                    try {
                        int pessoaId = Integer.parseInt(evento.getPessoa());
                        eventosPorPessoa.computeIfAbsent(pessoaId, k -> new ArrayList<>()).add(evento);
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "ID inválido: " + evento.getPessoa());
                    }
                }

                if(containerAlunos != null) containerAlunos.removeAllViews(); // Limpa a lista antes de adicionar novos itens
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
        // Infla o layout do item (verifique se você tem um R.layout.item_aluno)
        View itemView = getLayoutInflater().inflate(R.layout.item_aluno, containerAlunos, false);
        TextView nomeAluno = itemView.findViewById(R.id.txtNomeAluno);
        LinearLayout eventosLayout = itemView.findViewById(R.id.containerEventos);

        nomeAluno.setText(pessoa.getNome());

        nomeAluno.setOnClickListener(v -> {
            if (eventosLayout.getVisibility() == View.GONE) {
                eventosLayout.setVisibility(View.VISIBLE);
            } else {
                eventosLayout.setVisibility(View.GONE);
            }
        });

        // Limpa eventos antigos antes de adicionar novos (caso a view seja reutilizada)
        eventosLayout.removeAllViews();
        for (Evento evento : eventosDaPessoa) {
            String tipoNome = evento.getTipoNome();

            if (!"foi importado".equalsIgnoreCase(tipoNome)) {
                TextView eventoView = new TextView(this);
                eventoView.setText("• " + tipoNome);
                eventoView.setPadding(32, 8, 8, 8);
                eventosLayout.addView(eventoView);
            }
        }


        containerAlunos.addView(itemView);
    }
}