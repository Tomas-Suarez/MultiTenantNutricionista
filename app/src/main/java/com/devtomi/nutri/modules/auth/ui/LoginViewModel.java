package com.devtomi.nutri.modules.auth.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devtomi.nutri.core.local.SessionManager;
import com.devtomi.nutri.core.network.ApiClient;
import com.devtomi.nutri.modules.auth.dto.request.LoginDTO;
import com.devtomi.nutri.modules.auth.dto.response.TokenResponse;
import com.devtomi.nutri.modules.auth.network.AuthApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private final AuthApiService authApiService;
    private final SessionManager sessionManager;
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorResult = new MutableLiveData<>();


    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.authApiService = ApiClient.getClient(getApplication()).create(AuthApiService.class);
        this.sessionManager = new SessionManager(application);
    }

    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    public LiveData<String> getErrorResult() {
        return errorResult;
    }

    public void login(String email, String password) {
        LoginDTO request = new LoginDTO(password, email);

        authApiService.login(request).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(@NonNull Call<TokenResponse> call, @NonNull Response<TokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TokenResponse tokens = response.body();
                    sessionManager.saveToken(tokens.getToken());
                    sessionManager.saveRefreshToken(tokens.getRefresh());
                    loginSuccess.setValue(true);
                } else {
                    errorResult.setValue("Credenciales inválidas o error en el servidor");
                }
            }

            @Override
            public void onFailure(@NonNull Call<TokenResponse> call, @NonNull Throwable t) {
                errorResult.setValue("Error de conexión: " + t.getMessage());
            }
        });
    }
}
