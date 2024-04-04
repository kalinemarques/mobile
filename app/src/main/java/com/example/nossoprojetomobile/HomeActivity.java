package com.example.nossoprojetomobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private Button buttonOpcao1, buttonOpcao2, buttonOpcao3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Referências aos elementos do layout
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonOpcao1 = findViewById(R.id.buttonOpcao1);
        buttonOpcao2 = findViewById(R.id.buttonOpcao2);
        buttonOpcao3 = findViewById(R.id.buttonOpcao3);

        // Configurar o TextWatcher para a barra de busca
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Chamado após o texto na barra de busca ser alterado
                filterProjects(s.toString()); // Filtrar projetos com base no texto digitado
            }
        });

        // Configurar OnClickListener para o botão "Sair"
        TextView textViewSair = findViewById(R.id.textViewSair);
        textViewSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia MainActivity (login)
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                // Finaliza a HomeActivity para que o usuário não possa voltar para ela pressionando o botão "Voltar"
                finish();
            }
        });
    }

    // Método para filtrar os projetos com base no texto de busca
    private void filterProjects(String searchText) {
        // Verificar se o texto de busca está vazio
        if (searchText.isEmpty()) {
            // Se estiver vazio, mostrar todos os projetos
            buttonOpcao1.setVisibility(View.VISIBLE);
            buttonOpcao2.setVisibility(View.VISIBLE);
            buttonOpcao3.setVisibility(View.VISIBLE);
        } else {
            // Se houver texto de busca, verificar se o nome do projeto contém o texto de busca
            String projeto1 = buttonOpcao1.getText().toString();
            String projeto2 = buttonOpcao2.getText().toString();
            String projeto3 = buttonOpcao3.getText().toString();

            // Se o nome do projeto contém o texto de busca, mostrar o botão, caso contrário, escondê-lo
            buttonOpcao1.setVisibility(projeto1.toLowerCase().contains(searchText.toLowerCase()) ? View.VISIBLE : View.GONE);
            buttonOpcao2.setVisibility(projeto2.toLowerCase().contains(searchText.toLowerCase()) ? View.VISIBLE : View.GONE);
            buttonOpcao3.setVisibility(projeto3.toLowerCase().contains(searchText.toLowerCase()) ? View.VISIBLE : View.GONE);
        }
    }
}
