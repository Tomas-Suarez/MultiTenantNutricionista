package com.devtomi.nutri.modules.auth.network;

import com.devtomi.nutri.modules.auth.dto.request.LoginDTO;
import com.devtomi.nutri.modules.auth.dto.request.RefreshTokenRequestDTO;
import com.devtomi.nutri.modules.auth.dto.response.TokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {

    @POST("api/v1/auth/refresh-token")
    Call<TokenResponse> refreshToken(@Body RefreshTokenRequestDTO request);

    @POST("api/v1/auth/login")
    Call<TokenResponse> login(@Body LoginDTO loginDTO);
}
