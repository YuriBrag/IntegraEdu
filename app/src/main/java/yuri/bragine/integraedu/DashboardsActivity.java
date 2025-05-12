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

public class DashboardsActivity extends AppCompatActivity {

    List<Aluno> alunosList = Arrays.asList(
            new Aluno("Juca", "Bus", 9.5, 87, "Alta", 2, "Ativo"),
            new Aluno("Maria", "Silva", 8.7, 92, "Média", 0, "Ativo"),
            new Aluno("Pedro", "Oliveira", 6.3, 75, "Baixa", 3, "Trancado")
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        LinearLayout alunosContainer = findViewById(R.id.alunosContainer);
        int numeroAcima=0;
        int numeroAbaixo=0;
        for (Aluno aluno: alunosList){
            if(aluno.getNota() > 7){
                numeroAcima+=1;
            }
            else{
                numeroAbaixo+=1;
            }
        }

        Button btn = new Button(this);
        Button btn2=  new Button(this);

        btn.setText("Alunos Regulares: " +numeroAcima);
        btn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#808080")));
        btn2.setText("Alunos Irregulares " +numeroAbaixo);
        btn2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#808080")));
        final boolean[] expanded = {false};

        final int contAcima= numeroAcima;
        final int contAbaixo= numeroAbaixo;
        btn.setOnClickListener(v -> {
            if (!expanded[0]) {
                String todosAlunos= new String();
                for( Aluno alunos: alunosList) {
                    if (alunos.getNota() > 7) {
                        todosAlunos += alunos.getNome() + " " + alunos.getSobrenome() + "\n";
                    }
                }
                btn.setText(todosAlunos);
                expanded[0] = true;
            } else {
                btn.setText("Alunos Regulares: " + contAcima);
                expanded[0] = false;
            }
        });

        btn2.setOnClickListener(v -> {
            if (!expanded[0]) {
                String todosAlunos= new String();
                for( Aluno alunos: alunosList) {
                    if (alunos.getNota() <= 7) {
                        todosAlunos += alunos.getNome() + " " + alunos.getSobrenome() + "\n";
                    }
                }
                btn2.setText(todosAlunos);
                expanded[0] = true;
            } else {
                btn2.setText("Alunos Irregulares: " + contAbaixo);
                expanded[0] = false;
            }
        });

        alunosContainer.addView(btn);
        alunosContainer.addView(btn2);

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
