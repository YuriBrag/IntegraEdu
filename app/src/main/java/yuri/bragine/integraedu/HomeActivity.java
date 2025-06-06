package yuri.bragine.integraedu;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.View;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        //Intent intent = getIntent();
        //String resposta = intent.getStringExtra("resposta");

        ImageButton btnMenu = findViewById(R.id.btnMenu);
        LinearLayout menuLateral = findViewById(R.id.menuLateral);

        btnMenu.setOnClickListener(v -> {
            if (menuLateral.getVisibility() == View.GONE) {
                menuLateral.setVisibility(View.VISIBLE);
            } else {
                menuLateral.setVisibility(View.GONE);
            }
        });
        Button btnAlunos = findViewById(R.id.btnAlunos);
        btnAlunos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AlunosActivity.class);
                startActivity(intent);
            }
        });
        Button btnDashboards = findViewById(R.id.btnDashboards);
        btnDashboards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DashboardsActivity.class);
                startActivity(intent);
            }
        });

        Button btnEventos = findViewById(R.id.btnEventos);
        btnEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, EventosActivity.class);
                //intent.putExtra("resposta", resposta);
                startActivity(intent);
            }
        });
    }
}