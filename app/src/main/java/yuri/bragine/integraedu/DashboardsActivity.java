package yuri.bragine.integraedu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout; // Import para o Menu Lateral
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

// Import para a BottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
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

    private PieChart pieChart;
    private Button botaoRegulares, botaoAlerta, botaoEvadidos, botaoEgressos;
    private TextView textViewResultadoUnico;

    private String filtroAtual = null;


    // --- Constantes da API ---
    private static final int ORIGEM_API = 9;
    private static final String TOKEN_API = "0088c5a7834ebc95321fef219dbd722b";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // O nome do seu layout no XML anterior era activity_dashboardS (com S)
        // Se renomeou para activity_dashboard (sem S), está correto.
        // Se o nome do arquivo XML ainda for activity_dashboards.xml, mude a linha abaixo:
        setContentView(R.layout.activity_dashboard); // Verifique se este é o nome correto do seu XML

        repository = new RequestRepository();

        // Configura as views e listeners da sua lógica principal
        setupViews();
        setupListeners();

        // Carrega os dados do gráfico
        loadChartData();

        // --- Configuração dos botões de navegação e menus ---
        setupNavigation();
    }

    private void setupViews() {
        pieChart = findViewById(R.id.pieChart);
        botaoRegulares = findViewById(R.id.btnReg);
        botaoAlerta = findViewById(R.id.btnAlerta);
        botaoEvadidos = findViewById(R.id.btnEva);
        botaoEgressos = findViewById(R.id.btnEgressos);
        textViewResultadoUnico = findViewById(R.id.txtDaApi);
    }

    private void setupListeners() {
        botaoRegulares.setOnClickListener(v -> handleButtonClick("Regulares - Acompanhamento do Estudante"));
        botaoAlerta.setOnClickListener(v -> handleButtonClick("Alerta - Acompanhamento do Estudante"));
        botaoEvadidos.setOnClickListener(v -> handleButtonClick("Evadidos - Acompanhamento do Estudante"));
        botaoEgressos.setOnClickListener(v -> handleButtonClick("Egressos - Acompanhamento do Estudante"));
    }

    private void setupNavigation() {
        // Botão de Voltar
        ImageButton btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> finish());

        // Menu Lateral
        ImageButton btnMenu = findViewById(R.id.btnMenu);
        LinearLayout menuLateral = findViewById(R.id.menuLateral);
        btnMenu.setOnClickListener(v -> {
            if (menuLateral.getVisibility() == View.GONE) {
                menuLateral.setVisibility(View.VISIBLE);
            } else {
                menuLateral.setVisibility(View.GONE);
            }
        });

        // Barra de Navegação Inferior (BottomNavigationView)
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_dashboards); // Define este item como ativo

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
            } else if (itemId == R.id.nav_eventos) {
                startActivity(new Intent(getApplicationContext(), EventosActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.nav_dashboards) {
                // Já estamos aqui, não faz nada
                return true;
            }
            return false;
        });
    }


    // =================================================================================
    // TODA A SUA LÓGICA DE GRÁFICO E API CONTINUA INTACTA AQUI
    // =================================================================================

    private void loadChartData() {
        repository.buscarListaDeEventos(ORIGEM_API, TOKEN_API, new EventosCallback() {
            @Override
            public void onSuccess(List<Evento> eventos) {
                populatePieChart(eventos);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Erro ao carregar dados para o gráfico: " + errorMessage);
                if(pieChart != null) pieChart.setVisibility(View.GONE);
            }
        });
    }

    private void populatePieChart(List<Evento> eventos) {
        int countRegulares = 0;
        int countAlerta = 0;
        int countEvadidos = 0;
        int countEgressos = 0;

        for (Evento evento : eventos) {
            String tipoNome = evento.getTipoNome();
            if (tipoNome != null) {
                if (tipoNome.endsWith("Regulares - Acompanhamento do Estudante")) {
                    countRegulares++;
                } else if (tipoNome.endsWith("Alerta - Acompanhamento do Estudante")) {
                    countAlerta++;
                } else if (tipoNome.endsWith("Evadidos - Acompanhamento do Estudante")) {
                    countEvadidos++;
                } else if (tipoNome.endsWith("Egressos - Acompanhamento do Estudante")) {
                    countEgressos++;
                }
            }
        }

        ArrayList<PieEntry> entries = new ArrayList<>();
        if (countRegulares > 0) entries.add(new PieEntry(countRegulares));
        if (countAlerta > 0) entries.add(new PieEntry(countAlerta));
        if (countEvadidos > 0) entries.add(new PieEntry(countEvadidos));
        if (countEgressos > 0) entries.add(new PieEntry(countEgressos));

        if (entries.isEmpty()) {
            if(pieChart != null) pieChart.setVisibility(View.GONE);
            return;
        }

        PieDataSet dataSet = new PieDataSet(entries, "Situação dos Estudantes");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(14f);
        dataSet.setSliceSpace(2f);

        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);

        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setCenterText("Estudantes");
        pieChart.animateY(1000);

        pieChart.invalidate();
    }

    private void handleButtonClick(String novoFiltro) {
        if (novoFiltro.equals(filtroAtual)) {
            textViewResultadoUnico.setText("");
            textViewResultadoUnico.setVisibility(View.GONE);
            textViewResultadoUnico.setBackgroundColor(Color.TRANSPARENT);
            textViewResultadoUnico.setPadding(0, 0, 0, 0);
            filtroAtual = null;
        } else {
            buscarEventosEProcessar(novoFiltro, textViewResultadoUnico);
        }
    }

    private void buscarEventosEProcessar(final String filtroParaBuscar, final TextView targetTextView) {
        targetTextView.setVisibility(View.VISIBLE);
        targetTextView.setText("Buscando eventos...");
        targetTextView.setBackgroundColor(Color.TRANSPARENT);
        targetTextView.setPadding(0, 0, 0, 0);

        repository.buscarListaDeEventos(ORIGEM_API, TOKEN_API, new EventosCallback() {
            @Override
            public void onSuccess(List<Evento> eventos) {
                targetTextView.setText("");

                List<Evento> eventosFiltrados = eventos.stream()
                        .filter(evento -> evento.getTipoNome() != null && evento.getTipoNome().endsWith(filtroParaBuscar))
                        .collect(Collectors.toList());

                if (eventosFiltrados.isEmpty()) {
                    targetTextView.setText("Nenhum evento do tipo '" + filtroParaBuscar + "' encontrado.");
                    filtroAtual = filtroParaBuscar;
                    return;
                }

                int corFundoSucesso = Color.parseColor("#FFE0F2F1");
                targetTextView.setBackgroundColor(corFundoSucesso);
                targetTextView.setPadding(20, 20, 20, 20);

                filtroAtual = filtroParaBuscar;

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
                filtroAtual = null;
                targetTextView.setBackgroundColor(Color.TRANSPARENT);
                targetTextView.setPadding(0, 0, 0, 0);
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