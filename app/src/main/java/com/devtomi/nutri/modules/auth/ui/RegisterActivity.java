package com.devtomi.nutri.modules.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.devtomi.nutri.databinding.ActivityRegisterBinding;
import com.devtomi.nutri.modules.auth.dto.request.NutritionistRegisterDTO;
import com.devtomi.nutri.modules.auth.dto.request.PatientRegisterDTO;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        setupListeners();
        configObservers();

        changeVisibilityFields(binding.btnNutricionista.isChecked());
    }

    private void setupListeners() {
        binding.tvBackToLogin.setOnClickListener(v -> finish());

        binding.toggleGroupRole.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            changeVisibilityFields(binding.btnNutricionista.isChecked());
        });

        binding.bRegister.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            String repeatPassword = binding.etRepeatPassword.getText().toString().trim();

            if (binding.btnNutricionista.isChecked()) {
                String firstName = binding.etName.getText().toString().trim();
                String lastName = binding.etLastName.getText().toString().trim();
                String cuit = binding.etCuit.getText().toString().trim();

                registerViewModel.register(new NutritionistRegisterDTO(firstName, lastName, email, password, cuit), repeatPassword);
            }

            if (binding.btnPaciente.isChecked()) {
                registerViewModel.register(new PatientRegisterDTO(email, password), repeatPassword);
            }
        });
    }

    private void changeVisibilityFields(boolean isNutritionist) {
        int visibility = isNutritionist ? View.VISIBLE : View.GONE;

        binding.tvNameLabel.setVisibility(visibility);
        binding.etName.setVisibility(visibility);
        binding.tvLastNameLabel.setVisibility(visibility);
        binding.etLastName.setVisibility(visibility);
        binding.tvCuitLabel.setVisibility(visibility);
        binding.etCuit.setVisibility(visibility);
    }

    private void configObservers() {
        registerViewModel.getRegisterSuccessMessage().observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                Intent intent = null;

                if (binding.btnNutricionista.isChecked()) {
                    String email = binding.etEmail.getText().toString().trim();
                    intent = new Intent(RegisterActivity.this, VerificationEmailActivity.class);
                    intent.putExtra("EXTRA_EMAIL", email);
                }

                if (binding.btnPaciente.isChecked()) {
                    intent = new Intent(RegisterActivity.this, LoginActivity.class);
                }

                if (intent != null) {
                    startActivity(intent);
                    finish();
                }
            }
        });

        registerViewModel.getErrorResult().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}