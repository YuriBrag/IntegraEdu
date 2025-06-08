package yuri.bragine.integraedu;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import yuri.bragine.integraedu.data.models.Evento;
import yuri.bragine.integraedu.data.models.Pessoa;
import yuri.bragine.integraedu.data.repository.EventosCallback;
import yuri.bragine.integraedu.data.repository.PessoaCallback;
import yuri.bragine.integraedu.data.repository.RequestRepository;

public class DashboardsActivity extends AppCompatActivity {

    private static final String TAG = "DashboardsActivity";
    private RequestRepository repository;

    private Button botaoRegulares, botaoAlerta, botaoEvadidos, botaoEgressos;
    private TextView textViewRegulares, textViewAlerta, textViewEvadidos, textViewEgressos;

    private static final int ORIGEM_API = 9;
    private static final String TOKEN_API = "0088c5a7834ebc95321fef219dbd722b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        repository = new RequestRepository();

        setupViews();

        setupListeners();
    }

    private void setupViews() {
        botaoRegulares = findViewById(R.id.btnReg);
        textViewRegulares = findViewById(R.id.txtDaApiRegulares);

        botaoAlerta = findViewById(R.id.btnAlerta);
        textViewAlerta = findViewById(R.id.txtDaApiAlerta);

        botaoEvadidos = findViewById(R.id.btnEva);
        textViewEvadidos = findViewById(R.id.txtDaApiEvadidos);

        botaoEgressos = findViewById(R.id.btnEgressos);
        textViewEgressos = findViewById(R.id.txtDaApiEgressos);
    }

    private void setupListeners() {
        botaoRegulares.setOnClickListener(v -> {
            toggleBuscaDados("Regulares - Acompanhamento do Estudante", textViewRegulares);
        });

        botaoAlerta.setOnClickListener(v -> {
            toggleBuscaDados("Alerta - Acompanhamento do Estudante", textViewAlerta);
        });

        botaoEvadidos.setOnClickListener(v -> {
            toggleBuscaDados("Evadidos - Acompanhamento do Estudante", textViewEvadidos);
        });

        botaoEgressos.setOnClickListener(v -> {
            toggleBuscaDados("Egressos - Acompanhamento do Estudante", textViewEgressos);
        });
    }

    private void toggleBuscaDados(String filtro, TextView targetTextView) {
        if (!targetTextView.getText().toString().isEmpty()) {
            targetTextView.setText("");
        } else {
            buscarEventosEProcessar(filtro, targetTextView);
        }
    }

    private void buscarEventosEProcessar(final String filtroTipoNome, final TextView targetTextView) {
        //targetTextView.setText("Buscando eventos...");
        repository.buscarListaDeEventos(ORIGEM_API, TOKEN_API, new EventosCallback() {
            @Override
            public void onSuccess(List<Evento> eventos) {
                Log.i(TAG, "Eventos recebidos: " + eventos.size());
                //targetTextView.setText("Eventos recebidos. Filtrando...\n");

                List<Evento> eventosFiltrados = eventos.stream()
                        .filter(evento -> evento.getTipoNome() != null && evento.getTipoNome().endsWith(filtroTipoNome))
                        .collect(Collectors.toList());

                if (eventosFiltrados.isEmpty()) {
                    targetTextView.setText("Nenhum evento do tipo '" + filtroTipoNome + "' encontrado.");
                    return;
                }

                //targetTextView.append("\nEventos filtrados encontrados: " + eventosFiltrados.size() + "\n\n");

                for (Evento eventoFiltrado : eventosFiltrados) {
                    try {
                        int pessoaId = Integer.parseInt(eventoFiltrado.getPessoa());
                        buscarDadosPessoa(pessoaId, targetTextView);
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "ID da pessoa não é um número válido: " + eventoFiltrado.getPessoa());
                    }
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Erro ao buscar eventos: " + errorMessage);
                targetTextView.setText("Erro ao buscar eventos: " + errorMessage);
            }
        });
    }

    private void buscarDadosPessoa(int pessoaId, final TextView targetTextView) {
        repository.buscarPessoaPorId(pessoaId, ORIGEM_API, TOKEN_API, new PessoaCallback() {
            @Override
            public void onSuccess(Pessoa pessoa) {
                Log.i(TAG, "Pessoa encontrada para o ID " + pessoaId);

                String nome = pessoa.getNome() != null ? pessoa.getNome() : "N/A";
                String email = "N/A";
                if (pessoa.getEmails() != null && pessoa.getEmails().getPrincipal() != null) {
                    email = pessoa.getEmails().getPrincipal().getEmail();
                }

                String resultado = "Aluno: " + nome + " (ID: " + pessoa.getId() + ")\nEmail: " + email + "\n\n";
                targetTextView.append(resultado);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, errorMessage);
                targetTextView.append("Falha ao buscar dados para ID " + pessoaId + "\n\n");
            }
        });
    }
}
