package com.devtomi.nutri.modules.auth.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.devtomi.nutri.databinding.ActivityVerificationEmailBinding;
import com.devtomi.nutri.modules.auth.dto.request.ResendCodeDTO;
import com.devtomi.nutri.modules.auth.dto.request.VerifyCodeDTO;

import java.util.Locale;

public class VerificationEmailActivity extends AppCompatActivity {

    private ActivityVerificationEmailBinding binding;
    private VerificationEmailViewModel verificationEmailViewModel;
    private CountDownTimer countDownTimer;
    private static final long TIMER_DURATION_MS = 120_000L;
    private static final long TIMER_INTERVAL_MS = 1_000L;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerificationEmailBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        verificationEmailViewModel = new ViewModelProvider(this).get(VerificationEmailViewModel.class);

        if (getIntent() != null && getIntent().hasExtra("EXTRA_EMAIL")) {
            email = getIntent().getStringExtra("EXTRA_EMAIL");
            binding.tvEmail.setText(email);
        }

        setupListeners();
        configObservers();
        startTimer();
    }

    private void setupListeners() {
        binding.btnVerify.setOnClickListener(v -> {
            String code = binding.etCodeVerification.getText().toString().trim();
            verificationEmailViewModel.verificationEmail(new VerifyCodeDTO(email, code));
        });

        binding.btnResendCode.setOnClickListener(v -> {
            verificationEmailViewModel.resendCodeVerification(new ResendCodeDTO(email));
        });
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        binding.btnResendCode.setEnabled(false);

        countDownTimer = new CountDownTimer(TIMER_DURATION_MS, TIMER_INTERVAL_MS) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                String timeLeftFormatted = String.format(Locale.getDefault(), "Reenviar código en: %02d:%02d", minutes, seconds);
                binding.tvTimer.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                binding.tvTimer.setText("Puedes reenviar el código ahora");
                binding.btnResendCode.setEnabled(true);
            }
        }.start();
    }

    private void configObservers() {
        verificationEmailViewModel.getVerificationSuccessMessage().observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VerificationEmailActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        verificationEmailViewModel.getResendSuccessMessage().observe(this, message -> {
            if (message != null) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                startTimer();
            }
        });

        verificationEmailViewModel.getErrorResult().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(VerificationEmailActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    // TODO: Al borrar el activity, tambien debemos cancelar el timer, porque sino sigue corriendo y nos puede dar problema de memory leak
    // pd: Esta colocado en la documentación
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}