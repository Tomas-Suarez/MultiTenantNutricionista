package com.devtomi.nutri.modules.auth.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devtomi.nutri.core.network.ApiClient;
import com.devtomi.nutri.modules.auth.dto.request.ResendCodeDTO;
import com.devtomi.nutri.modules.auth.dto.request.VerifyCodeDTO;
import com.devtomi.nutri.modules.auth.network.AuthApiService;
import com.devtomi.nutri.shared.dto.response.MessageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationEmailViewModel extends AndroidViewModel {

    private final AuthApiService authApiService;
    private final MutableLiveData<String> verificationSuccessMessage = new MutableLiveData<>();
    private final MutableLiveData<String> resendSuccessMessage = new MutableLiveData<>();
    private final MutableLiveData<String> errorResult = new MutableLiveData<>();

    public VerificationEmailViewModel(@NonNull Application application) {
        super(application);
        this.authApiService = ApiClient.getClient(getApplication()).create(AuthApiService.class);
    }

    public LiveData<String> getVerificationSuccessMessage() {
        return verificationSuccessMessage;
    }

    public LiveData<String> getResendSuccessMessage() {
        return resendSuccessMessage;
    }

    public LiveData<String> getErrorResult() {
        return errorResult;
    }

    public void verificationEmail(VerifyCodeDTO verifyCodeDTO) {
        if (verifyCodeDTO == null) {
            errorResult.setValue("Datos de verificación no válidos");
            return;
        }

        if (verifyCodeDTO.getEmail() == null || verifyCodeDTO.getEmail().trim().isEmpty()) {
            errorResult.setValue("Ingresa tu email");
            return;
        }

        if (verifyCodeDTO.getCode() == null || verifyCodeDTO.getCode().trim().isEmpty()) {
            errorResult.setValue("Ingresa el código de verificación");
            return;
        }

        authApiService.verifyCode(verifyCodeDTO).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    verificationSuccessMessage.setValue(response.body().getMessage());
                } else {
                    errorResult.setValue("Error al verificar el código");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable throwable) {
                errorResult.setValue(throwable.getMessage());
            }
        });
    }

    public void resendCodeVerification(ResendCodeDTO resendCodeDTO) {
        if (resendCodeDTO == null) {
            errorResult.setValue("Datos de verificación no válidos");
            return;
        }

        if (resendCodeDTO.getEmail() == null || resendCodeDTO.getEmail().trim().isEmpty()) {
            errorResult.setValue("Ingresa tu email");
            return;
        }

        authApiService.resendCode(resendCodeDTO).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    resendSuccessMessage.setValue(response.body().getMessage());
                } else {
                    errorResult.setValue("Error al reenviar el código");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable throwable) {
                errorResult.setValue(throwable.getMessage());
            }
        });
    }
}