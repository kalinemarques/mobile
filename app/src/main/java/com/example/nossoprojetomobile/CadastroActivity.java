package com.example.nossoprojetomobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextLogin;
    private EditText editTextSenha;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro); // Movido para antes de acessar os EditTexts
        dbHelper = new DatabaseHelper(this);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextSenha = findViewById(R.id.editTextSenha);
    }

    public void cadastrarNovoUsuario(View view) {
        String login = editTextLogin.getText().toString();
        String senha = editTextSenha.getText().toString();

        if (!login.isEmpty() && !senha.isEmpty()) {
            boolean insercaoSucesso = dbHelper.addUser(login, senha);
            if (insercaoSucesso) {
                Toast.makeText(this, "Novo usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao cadastrar novo usuário", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }
}
