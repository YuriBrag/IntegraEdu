package yuri.bragine.integraedu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class AlunosActivity extends AppCompatActivity {

    List<Aluno> alunosList = Arrays.asList(
            new Aluno("Fernanda", "Lima", 7.8, 89, "Média", 1, "Ativo"),
            new Aluno("Ana Beatriz", "Rocha", 9.1, 94, "Alta", 0, "Ativo"),
            new Aluno("Pedro Henrique", "Alves", 5.4, 68, "Baixa", 5, "Trancado"),
            new Aluno("Rafael", "Cardoso", 6.9, 81, "Média", 2, "Ativo"),
            new Aluno("Juliana", "Martins", 8.3, 90, "Alta", 1, "Ativo"),
            new Aluno("Gustavo", "Pereira", 4.8, 60, "Crítica", 6, "Evadido"),
            new Aluno("Lucas", "Oliveira", 7.1, 85, "Média", 3, "Ativo"),
            new Aluno("Mariana", "Souza", 8.9, 95, "Alta", 0, "Ativo")
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);

        LinearLayout alunosContainer = findViewById(R.id.alunosContainer);

        for (Aluno aluno : alunosList) {
            // Card do aluno
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(16, 16, 16, 16);
            card.setBackgroundResource(R.drawable.bg_aluno_box);
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, 0, 0, 16);
            card.setLayoutParams(cardParams);

            // Nome do aluno (centralizado)
            TextView nomeText = new TextView(this);
            nomeText.setText(aluno.getNome() + " " + aluno.getSobrenome());
            nomeText.setTextSize(18);
            nomeText.setTextColor(Color.BLACK);
            nomeText.setPadding(0, 0, 0, 8);
            nomeText.setGravity(View.TEXT_ALIGNMENT_CENTER); // centraliza o texto
            nomeText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER); // garante centralização
            nomeText.setTypeface(null, android.graphics.Typeface.BOLD);

            // Layout com detalhes (inicialmente escondido)
            LinearLayout detalhesLayout = new LinearLayout(this);
            detalhesLayout.setOrientation(LinearLayout.VERTICAL);
            detalhesLayout.setVisibility(View.GONE);
            detalhesLayout.setPadding(16, 16, 16, 16);
            detalhesLayout.setBackgroundColor(Color.parseColor("#EEEEEE"));

            detalhesLayout.addView(criarDetalhe("Nota geral: " + aluno.getNota()));
            detalhesLayout.addView(criarDetalhe("Frequência: " + aluno.getFrequencia() + "%"));
            detalhesLayout.addView(criarDetalhe("Satisfação: " + aluno.getSatisfacao()));
            detalhesLayout.addView(criarDetalhe("Lançamentos vencidos: " + aluno.getLancamentosVencidos()));
            detalhesLayout.addView(criarDetalhe("Situação da matrícula: " + aluno.getSituacaoMatricula()));

            // Clique para expandir/recolher
            nomeText.setOnClickListener(v -> {
                if (detalhesLayout.getVisibility() == View.GONE) {
                    detalhesLayout.setVisibility(View.VISIBLE);
                } else {
                    detalhesLayout.setVisibility(View.GONE);
                }
            });

            card.addView(nomeText);
            card.addView(detalhesLayout);
            alunosContainer.addView(card);
        }

        // Botão de menu lateral
        LinearLayout menuLateral = findViewById(R.id.menuLateral);
        ImageButton btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(v -> {
            if (menuLateral.getVisibility() == View.GONE) {
                menuLateral.setVisibility(View.VISIBLE);
            } else {
                menuLateral.setVisibility(View.GONE);
            }
        });
    }

    // Método auxiliar para criar os TextViews de detalhes
    private TextView criarDetalhe(String texto) {
        TextView txt = new TextView(this);
        txt.setText(texto);
        txt.setTextColor(Color.parseColor("#333333"));
        txt.setTextSize(14);
        txt.setPadding(0, 4, 0, 4);
        return txt;
    }
}
