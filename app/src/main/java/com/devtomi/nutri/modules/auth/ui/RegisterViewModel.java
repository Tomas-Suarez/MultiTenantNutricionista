package com.devtomi.nutri.modules.auth.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devtomi.nutri.core.network.ApiClient;
import com.devtomi.nutri.modules.auth.dto.request.NutritionistRegisterDTO;
import com.devtomi.nutri.modules.auth.dto.request.PatientRegisterDTO;
import com.devtomi.nutri.modules.auth.network.AuthApiService;
import com.devtomi.nutri.shared.dto.response.MessageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends AndroidViewModel {

    private final AuthApiService authApiService;
    private final MutableLiveData<String> registerSuccessMessage = new MutableLiveData<>();
    private final MutableLiveData<String> errorResult = new MutableLiveData<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        this.authApiService = ApiClient.getClient(getApplication()).create(AuthApiService.class);
    }

    public LiveData<String> getRegisterSuccessMessage() {
        return registerSuccessMessage;
    }

    public LiveData<String> getErrorResult() {
        return errorResult;
    }

    public void register(NutritionistRegisterDTO dto, String repeatPassword) {
        if (dto == null) {
            errorResult.setValue("Datos de registro no válidos");
            return;
        }

        if (dto.getFirstName() == null || dto.getFirstName().trim().isEmpty()) {
            errorResult.setValue("Ingresa tu nombre");
            return;
        }

        if (dto.getLastName() == null || dto.getLastName().trim().isEmpty()) {
            errorResult.setValue("Ingresa tu apellido");
            return;
        }

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            errorResult.setValue("Ingresa un correo electrónico válido");
            return;
        }

        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty() || dto.getPassword().length() < 6) {
            errorResult.setValue("La contraseña debe tener al menos 6 caracteres");
            return;
        }

        if (!dto.getPassword().equals(repeatPassword)) {
            errorResult.setValue("Las contraseñas no coinciden");
            return;
        }

        if (dto.getCuit() == null || !dto.getCuit().matches("\\d{11}")) {
            errorResult.setValue("El CUIT debe contener exactamente 11 dígitos numéricos sin guiones");
            return;
        }

        authApiService.registerNutritionist(dto).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    registerSuccessMessage.setValue(response.body().getMessage());
                } else {
                    errorResult.setValue("Error al registrar nutricionista");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                errorResult.setValue(t.getMessage());
            }
        });
    }

    public void register(PatientRegisterDTO dto, String repeatPassword) {
        if (dto == null) {
            errorResult.setValue("Datos de registro no válidos");
            return;
        }

        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            errorResult.setValue("Ingresa un correo electrónico válido");
            return;
        }

        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty() || dto.getPassword().length() < 6) {
            errorResult.setValue("La contraseña debe tener al menos 6 caracteres");
            return;
        }

        if (!dto.getPassword().equals(repeatPassword)) {
            errorResult.setValue("Las contraseñas no coinciden");
            return;
        }

        authApiService.registerPatient(dto).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    registerSuccessMessage.setValue(response.body().getMessage());
                } else {
                    errorResult.setValue("Error al registrar paciente");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                errorResult.setValue(t.getMessage());
            }
        });
    }
}