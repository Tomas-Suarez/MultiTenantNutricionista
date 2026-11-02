package com.devtomi.nutri.core.network;

import android.content.Context;

import com.devtomi.nutri.BuildConfig;
import com.devtomi.nutri.core.local.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            SessionManager sessionManager = new SessionManager(context);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request original = chain.request();

                        if (original.url().encodedPath().contains("/auth/")) {
                            return chain.proceed(original);
                        }

                        Request.Builder builder = original.newBuilder();

                        String token = sessionManager.getToken();
                        if (token != null) {
                            builder.header("Authorization", "Bearer " + token);
                        }

                        String tenantId = sessionManager.getTenantId();
                        if (tenantId != null) {
                            builder.header("X-Tenant-ID", tenantId);
                        }

                        return chain.proceed(builder.build());
                    })
                    .authenticator(new TokenAuthenticator(sessionManager))
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
