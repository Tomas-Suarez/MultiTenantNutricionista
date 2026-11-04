package com.devtomi.nutri.modules.auth.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.devtomi.nutri.R;
import com.devtomi.nutri.databinding.FragmentVerificationEmailBinding;
import com.devtomi.nutri.modules.auth.dto.request.ResendCodeDTO;
import com.devtomi.nutri.modules.auth.dto.request.VerifyCodeDTO;

import java.util.Locale;

public class VerificationEmailFragment extends Fragment {

    private static final long TIMER_DURATION_MS = 120_000L;
    private static final long TIMER_INTERVAL_MS = 1_000L;

    private FragmentVerificationEmailBinding binding;
    private VerificationEmailViewModel verificationEmailViewModel;
    private CountDownTimer countDownTimer;
    private String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentVerificationEmailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        verificationEmailViewModel = new ViewModelProvider(this).get(VerificationEmailViewModel.class);

        if (getArguments() != null && getArguments().containsKey("email")) {
            email = getArguments().getString("email");
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

        binding.btnResendCode.setOnClickListener(v ->
                verificationEmailViewModel.resendCodeVerification(new ResendCodeDTO(email)));
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
        verificationEmailViewModel.getVerificationSuccessMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

                NavController navController = Navigation.findNavController(requireView());
                if (!navController.popBackStack(R.id.loginFragment, false)) {
                    navController.navigate(R.id.loginFragment);
                }
            }
        });

        verificationEmailViewModel.getResendSuccessMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                startTimer();
            }
        });

        verificationEmailViewModel.getErrorResult().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

        binding = null;
    }
}
