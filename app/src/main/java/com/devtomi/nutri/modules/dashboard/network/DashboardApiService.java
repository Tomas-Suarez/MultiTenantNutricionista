package com.devtomi.nutri.modules.dashboard.network;

import com.devtomi.nutri.modules.dashboard.dto.response.DashboardSummaryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DashboardApiService {

    @GET("api/v1/dashboard")
    Call<DashboardSummaryResponse> getDashboardSummary(
            @Query("period") String period,
            @Query("date") String date
    );
}
