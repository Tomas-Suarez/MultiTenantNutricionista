package com.devtomi.nutri.modules.dashboard.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devtomi.nutri.core.network.ApiClient;
import com.devtomi.nutri.modules.dashboard.dto.response.DashboardSummaryResponse;
import com.devtomi.nutri.modules.dashboard.network.DashboardApiService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardViewModel extends AndroidViewModel {

    private static final String PERIOD_WEEK = "WEEK";
    private static final String PERIOD_MONTH = "MONTH";

    private final DashboardApiService dashboardApiService;
    private final MutableLiveData<DashboardSummaryResponse> dashboard = new MutableLiveData<>();
    private final MutableLiveData<String> errorResult = new MutableLiveData<>();

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        this.dashboardApiService = ApiClient.getClient(application).create(DashboardApiService.class);
    }

    public LiveData<DashboardSummaryResponse> getDashboard() {
        return dashboard;
    }

    public LiveData<String> getErrorResult() {
        return errorResult;
    }

    public void loadWeeklyDashboard() {
        loadDashboard(PERIOD_WEEK);
    }

    public void loadMonthlyDashboard() {
        loadDashboard(PERIOD_MONTH);
    }

    private void loadDashboard(String period) {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        dashboardApiService.getDashboardSummary(period, date).enqueue(new Callback<DashboardSummaryResponse>() {
            @Override
            public void onResponse(@NonNull Call<DashboardSummaryResponse> call,
                                   @NonNull Response<DashboardSummaryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dashboard.setValue(response.body());
                } else {
                    errorResult.setValue("Error al obtener el dashboard");
                }
            }

            @Override
            public void onFailure(@NonNull Call<DashboardSummaryResponse> call,
                                  @NonNull Throwable throwable) {
                errorResult.setValue("Error de conexión: " + throwable.getMessage());
            }
        });
    }
}
