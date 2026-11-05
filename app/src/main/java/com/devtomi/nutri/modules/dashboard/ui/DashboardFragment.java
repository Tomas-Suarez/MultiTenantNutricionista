package com.devtomi.nutri.modules.dashboard.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.devtomi.nutri.R;
import com.devtomi.nutri.databinding.FragmentDashboardBinding;
import com.devtomi.nutri.modules.dashboard.chart.DashboardChart;
import com.devtomi.nutri.modules.dashboard.dto.response.DashboardSummaryResponse;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private DashboardChart chart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        chart = new DashboardChart(binding.barChartWeights);

        setupListeners();
        configObservers();

        if (binding.btnFilterMonth.isChecked()) {
            dashboardViewModel.loadMonthlyDashboard();
        } else {
            dashboardViewModel.loadWeeklyDashboard();
        }
    }

    private void setupListeners() {
        binding.toggleGroupFilter.addOnButtonCheckedListener((group, checkedId, isChecked) -> {

            if (checkedId == R.id.btnFilterMonth) {
                dashboardViewModel.loadMonthlyDashboard();
            } else {
                dashboardViewModel.loadWeeklyDashboard();
            }
        });
    }

    private void configObservers() {
        dashboardViewModel.getDashboard().observe(getViewLifecycleOwner(), this::showDashboard);

        dashboardViewModel.getErrorResult().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showDashboard(DashboardSummaryResponse dashboard) {

        binding.tvRegisteredCount.setText(String.valueOf(dashboard.getRegisteredPatients()));
        binding.tvLinkedCount.setText(String.valueOf(dashboard.getLinkedPatients()));
        chart.render(dashboard.getWeighingsByDay());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        chart = null;
    }
}
