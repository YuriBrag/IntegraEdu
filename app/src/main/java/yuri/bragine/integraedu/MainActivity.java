package yuri.bragine.integraedu;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import yuri.bragine.integraedu.data.database.AppDatabase;
import yuri.bragine.integraedu.data.models.Entity.Coordenador;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextSenha;
    private Button buttonLogin;

    private Button buttonCadastro;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDatabase(this);


        if (db.coordenadorDao().getAllCoordenadores().isEmpty()) {
            db.coordenadorDao().insert(new Coordenador("Admin Teste", "admin@email.com", "admin123", "111.222.333-44", "99999-9999"));
            //db.coordenadorDao().insert(new Coordenador("Admin", "adm", "adm123", "111.222.333-44", "99999-9999"));
            Toast.makeText(this, "Coordenador de teste criado: admin@email.com / admin123", Toast.LENGTH_LONG).show();
        }

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonCadastro = findViewById(R.id.buttonSignin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarLogin();
            }
        });

        buttonCadastro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }

    private void validarLogin() {
        String email = editTextEmail.getText().toString().trim();
        String senha = editTextSenha.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
            Toast.makeText(this, "Por favor, preencha email e senha", Toast.LENGTH_SHORT).show();
            return;
        }

        Coordenador coordenador = db.coordenadorDao().findUserByCredentials(email, senha);

        if (coordenador != null) {
            Toast.makeText(this, "Bem-vindo, " + coordenador.getNome(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, "Email ou Senha incorretos.", Toast.LENGTH_SHORT).show();
        }
    }
}
