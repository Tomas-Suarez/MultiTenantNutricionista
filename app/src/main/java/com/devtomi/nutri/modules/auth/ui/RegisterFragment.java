package com.devtomi.nutri.modules.auth.ui;

import android.os.Bundle;
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
import com.devtomi.nutri.databinding.FragmentRegisterBinding;
import com.devtomi.nutri.modules.auth.dto.request.NutritionistRegisterDTO;
import com.devtomi.nutri.modules.auth.dto.request.PatientRegisterDTO;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private RegisterViewModel registerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        setupListeners();
        configObservers();

        changeVisibilityFields(binding.btnNutricionista.isChecked());
    }

    private void setupListeners() {
        binding.tvBackToLogin.setOnClickListener(v ->
                Navigation.findNavController(v).popBackStack(R.id.loginFragment, false));

        binding.toggleGroupRole.addOnButtonCheckedListener((group, checkedId, isChecked) ->
                changeVisibilityFields(binding.btnNutricionista.isChecked()));

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
        registerViewModel.getRegisterSuccessMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

                NavController navController = Navigation.findNavController(requireView());

                if (binding.btnNutricionista.isChecked()) {
                    String email = binding.etEmail.getText().toString().trim();

                    Bundle args = new Bundle();
                    args.putString("email", email);

                    navController.navigate(R.id.action_registerFragment_to_verificationEmailFragment, args);
                }

                if (binding.btnPaciente.isChecked()) {
                    navController.popBackStack(R.id.loginFragment, false);
                }
            }
        });

        registerViewModel.getErrorResult().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
