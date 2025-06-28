package yuri.bragine.integraedu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        btnAlunos.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AlunosActivity.class);
            startActivity(intent);
        });

        Button btnDashboards = findViewById(R.id.btnDashboards);
        btnDashboards.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, DashboardsActivity.class);
            startActivity(intent);
        });

        Button btnEventos = findViewById(R.id.btnEventos);
        btnEventos.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, EventosActivity.class);
            startActivity(intent);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemReselectedListener(item -> {
            // Não faz nada, apenas impede o recarregamento
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Já estamos na Home, não faz nada
                return true;
            } else if (itemId == R.id.nav_alunos) {
                // Navega para a tela de Alunos
                startActivity(new Intent(HomeActivity.this, AlunosActivity.class));
                return true;
            } else if (itemId == R.id.nav_eventos) {
                // Navega para a tela de Eventos
                startActivity(new Intent(HomeActivity.this, EventosActivity.class));
                return true;
            } else if (itemId == R.id.nav_dashboards) {
                // Navega para a tela de Dashboards
                startActivity(new Intent(HomeActivity.this, DashboardsActivity.class));
                return true;
            }
            return false;
        });
    }
}