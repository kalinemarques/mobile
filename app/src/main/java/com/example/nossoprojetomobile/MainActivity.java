package com.example.nossoprojetomobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLogin;
    private EditText editTextSenha;
    private CheckBox checkBoxLembrar;
    private SharedPreferences sharedPreferences;
    private DatabaseHelper dbHelper; // Adicione esta linha

    private static final String PREF_NAME = "login_preferences";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_REMEMBER = "remember";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        dbHelper = new DatabaseHelper(this); // Adicione esta linha

        checkBoxLembrar = findViewById(R.id.checkBox);
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextSenha = findViewById(R.id.editTextSenha);

        checkBoxLembrar.setChecked(sharedPreferences.getBoolean(KEY_REMEMBER, false));

        if (checkBoxLembrar.isChecked()) {
            editTextLogin.setText(sharedPreferences.getString(KEY_USERNAME, ""));
        }

        checkBoxLembrar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedPreferences.edit().putBoolean(KEY_REMEMBER, true).apply();
                } else {
                    sharedPreferences.edit().remove(KEY_REMEMBER).apply();
                }
            }
        });
    }

    public void entrar(View view) {
        String login = editTextLogin.getText().toString();
        String senha = editTextSenha.getText().toString();

        if (isValidLogin(login, senha)) {
            if (checkBoxLembrar.isChecked()) {
                sharedPreferences.edit().putString(KEY_USERNAME, login).apply();
            } else {
                sharedPreferences.edit().remove(KEY_USERNAME).apply();
            }

            // Login válido, iniciar a Intent para a HomeActivity
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            // Exibir mensagem de erro
            Toast.makeText(this, "Login ou senha inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    public void cadastrar(View view) {
        Intent intent = new Intent(this, CadastroActivity.class);
        startActivity(intent);
    }

    private boolean isValidLogin(String login, String senha) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{login, senha});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }
}