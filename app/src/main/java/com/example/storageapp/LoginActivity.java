package com.example.storageapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText edUsername, edPassword;
    private Button btnLogin, btnSignUp;

    private final String CREDENTIAL_SHARED_PREF = "user_credentials";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.ed_username);
        edPassword = findViewById(R.id.ed_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_sign_up);

        // Кнопка для перехода к регистрации
        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // Кнопка для выполнения логина
        btnLogin.setOnClickListener(v -> {
            String enteredUsername = edUsername.getText().toString();
            String enteredPassword = edPassword.getText().toString();

            // Получаем сохраненные данные из SharedPreferences
            SharedPreferences credentials = getSharedPreferences(CREDENTIAL_SHARED_PREF, Context.MODE_PRIVATE);
            String savedUsername = credentials.getString("Username", null);
            String savedPassword = credentials.getString("Password", null);

            // Проверяем совпадение введенных и сохраненных данных
            if (savedUsername != null && savedUsername.equals(enteredUsername) && savedPassword != null && savedPassword.equals(enteredPassword)) {
                // Если логин успешен
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                // Переход к MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Закрыть текущую активность
            } else {
                // Если логин неудачен
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
