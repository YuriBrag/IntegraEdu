package yuri.bragine.integraedu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class EventosActivity extends AppCompatActivity {

    List<Aluno> alunosList = Arrays.asList(
            new Aluno("Seminário", "Literatura", 9.5, 87, "Alta", 2, "Ativo"),
            new Aluno("Semana", "de Informática", 8.7, 92, "Média", 0, "Ativo"),
            new Aluno("Feira", "de Livro", 6.3, 75, "Baixa", 3, "Trancado")
    );

    List<String> nomesParticipantes = Arrays.asList(
            "Juca Bus",
            "Maria Silva",
            "Pedro Oliveira"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);

        LinearLayout alunosContainer = findViewById(R.id.alunosContainer);

        for (Aluno aluno : alunosList) {
            // Botão principal com nome do evento
            Button btn = new Button(this);
            btn.setText(aluno.getNome() + " " + aluno.getSobrenome());

            // Container para detalhes (checkboxes)
            LinearLayout detalhesLayout = new LinearLayout(this);
            detalhesLayout.setOrientation(LinearLayout.VERTICAL);
            detalhesLayout.setVisibility(View.GONE);

            for (String nome : nomesParticipantes) {
                CheckBox checkBox = new CheckBox(this);
                checkBox.setText(nome);
                detalhesLayout.addView(checkBox);
            }

            final boolean[] expanded = {false};

            btn.setOnClickListener(v -> {
                if (!expanded[0]) {
                    detalhesLayout.setVisibility(View.VISIBLE);
                    expanded[0] = true;
                } else {
                    detalhesLayout.setVisibility(View.GONE);
                    expanded[0] = false;
                }
            });

            alunosContainer.addView(btn);
            alunosContainer.addView(detalhesLayout);
        }

        // Menu lateral
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
}
