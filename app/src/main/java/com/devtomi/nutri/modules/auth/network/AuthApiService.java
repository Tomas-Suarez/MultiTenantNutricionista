package com.devtomi.nutri.modules.auth.network;

import com.devtomi.nutri.modules.auth.dto.request.LoginDTO;
import com.devtomi.nutri.modules.auth.dto.request.NutritionistRegisterDTO;
import com.devtomi.nutri.modules.auth.dto.request.PatientRegisterDTO;
import com.devtomi.nutri.modules.auth.dto.request.RefreshTokenRequestDTO;
import com.devtomi.nutri.modules.auth.dto.request.ResendCodeDTO;
import com.devtomi.nutri.modules.auth.dto.request.VerifyCodeDTO;
import com.devtomi.nutri.modules.auth.dto.response.TokenResponse;
import com.devtomi.nutri.shared.dto.response.MessageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {

    @POST("api/v1/auth/refresh-token")
    Call<TokenResponse> refreshToken(@Body RefreshTokenRequestDTO request);

    @POST("api/v1/auth/login")
    Call<TokenResponse> login(@Body LoginDTO loginDTO);

    @POST("api/v1/auth/nutritionist")
    Call<MessageResponse> registerNutritionist(@Body NutritionistRegisterDTO nutritionistRegisterDTO);

    @POST("api/v1/auth/register/patient")
    Call<MessageResponse> registerPatient(@Body PatientRegisterDTO patientRegisterDTO);

    @POST("api/v1/auth/verify-code")
    Call<MessageResponse> verifyCode(@Body VerifyCodeDTO verifyCodeDTO);

    @POST("api/v1/auth/resend-code")
    Call<MessageResponse> resendCode(@Body ResendCodeDTO resendCodeDTO);
}
