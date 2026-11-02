package com.devtomi.nutri.modules.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.devtomi.nutri.MainActivity;
import com.devtomi.nutri.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        configObservers();

        binding.btnLogin.setOnClickListener(v->{
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            loginViewModel.login(email, password);
        });
    }

    private void configObservers() {
        loginViewModel.getLoginSuccess().observe(this, success -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        loginViewModel.getErrorResult().observe(this, errorMessage -> {
            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
        });
    }
}