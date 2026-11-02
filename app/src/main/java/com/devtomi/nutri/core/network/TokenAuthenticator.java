package com.devtomi.nutri.core.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.devtomi.nutri.BuildConfig;
import com.devtomi.nutri.core.local.SessionManager;
import com.devtomi.nutri.modules.auth.dto.request.RefreshTokenRequestDTO;
import com.devtomi.nutri.modules.auth.dto.response.TokenResponse;
import com.devtomi.nutri.modules.auth.network.AuthApiService;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenAuthenticator implements Authenticator {

    private final SessionManager sessionManager;

    public TokenAuthenticator(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NonNull Response response) throws IOException {
        if (responseCount(response) >= 2) {
            sessionManager.closeSession();
            return null;
        }

        String refreshToken = sessionManager.getRefreshToken();

        if (refreshToken == null) {
            sessionManager.closeSession();
            return null;
        }

        Retrofit refreshRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthApiService authApi = refreshRetrofit.create(AuthApiService.class);

        retrofit2.Response<TokenResponse> refreshResponse = authApi
                .refreshToken(new RefreshTokenRequestDTO(refreshToken))
                .execute();

        if (refreshResponse.isSuccessful() && refreshResponse.body() != null) {
            TokenResponse newTokens = refreshResponse.body();

            sessionManager.saveToken(newTokens.getToken());
            sessionManager.saveRefreshToken(newTokens.getRefresh());

            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + newTokens.getToken())
                    .build();
        } else {
            sessionManager.closeSession();
            return null;
        }
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}
