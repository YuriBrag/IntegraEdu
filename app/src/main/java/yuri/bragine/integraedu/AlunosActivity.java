package yuri.bragine.integraedu;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class AlunosActivity extends AppCompatActivity {

    List<Aluno> alunosList = Arrays.asList(
            new Aluno("Juca", "Bus", 9.5, 87, "Alta", 2, "Ativo"),
            new Aluno("Maria", "Silva", 8.7, 92, "Média", 0, "Ativo"),
            new Aluno("Pedro", "Oliveira", 6.3, 75, "Baixa", 3, "Trancado")
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);

        LinearLayout alunosContainer = findViewById(R.id.alunosContainer);

        for (Aluno aluno : alunosList) {
            Button btn = new Button(this);
            btn.setText(aluno.getNome() + " " + aluno.getSobrenome());
            btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#808080")));

            final boolean[] expanded = {false};

            btn.setOnClickListener(v -> {
                if (!expanded[0]) {
                    String detalhes = aluno.getNome() + " " + aluno.getSobrenome() + "\n"
                            + "Nota geral: " + aluno.getNota() + "\n"
                            + "Frequência: " + aluno.getFrequencia() + "%\n"
                            + "Satisfação: " + aluno.getSatisfacao() + "\n"
                            + "Lançamentos vencidos: " + aluno.getLancamentosVencidos() + "\n"
                            + "Situação da matrícula: " + aluno.getSituacaoMatricula();
                    btn.setText(detalhes);
                    expanded[0] = true;
                } else {
                    btn.setText(aluno.getNome() + " " + aluno.getSobrenome());
                    expanded[0] = false;
                }
            });

            alunosContainer.addView(btn);
        }
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
