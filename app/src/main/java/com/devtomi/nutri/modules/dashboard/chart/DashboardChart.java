package com.devtomi.nutri.modules.dashboard.chart;

import android.graphics.Color;

import com.devtomi.nutri.modules.dashboard.dto.response.DailyWeighingResponse;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class DashboardChart {

    private static final String[] DAY_LABELS = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};

    private static final ValueFormatter INTEGER_FORMATTER = new ValueFormatter() {
        @Override
        public String getFormattedValue(float value) {
            return String.valueOf((int) value);
        }
    };

    private final BarChart chart;

    public DashboardChart(BarChart chart) {
        this.chart = chart;
        configure();
    }

    public void render(List<DailyWeighingResponse> weighingsByDay) {
        if (weighingsByDay == null || weighingsByDay.isEmpty()) {
            chart.clear();
            chart.invalidate();
            return;
        }

        List<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < weighingsByDay.size(); i++) {
            entries.add(new BarEntry(i, weighingsByDay.get(i).getCount()));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Pesajes");
        dataSet.setColor(Color.parseColor("#8BC34A"));
        dataSet.setValueTextColor(Color.parseColor("#0F172A"));
        dataSet.setValueTextSize(10f);
        dataSet.setValueFormatter(INTEGER_FORMATTER);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.6f);

        chart.setData(data);
        chart.invalidate();
    }

    private void configure() {
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setScaleEnabled(false);
        chart.setNoDataText("Sin datos para mostrar");

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(DAY_LABELS));
        xAxis.setTextColor(Color.parseColor("#64748B"));
        xAxis.setTextSize(11f);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setGranularity(1f);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setTextColor(Color.parseColor("#64748B"));
        leftAxis.setValueFormatter(INTEGER_FORMATTER);

        chart.getAxisRight().setEnabled(false);
    }
}
