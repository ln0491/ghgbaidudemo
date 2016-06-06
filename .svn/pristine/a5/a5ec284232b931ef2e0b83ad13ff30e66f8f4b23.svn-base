package com.ghg.tobacco.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ghg.tobacco.Data;
import com.ghg.tobacco.GetStaticData;
import com.ghg.tobacco.R;
import com.ghg.tobacco.base.BaseFragment;
import com.ghg.tobacco.bean.FormData;
import com.ghg.tobacco.bean.response.ResponseCheck;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;


public class BigDataInspectionFragment extends BaseFragment {
    private View mView;
    private RadioButton rb_day, rb_month, rb_year;
    private LineChart mChartInspection, mChartReport;
    private RadioGroup mRadioGroup;
    private int[] mColors;
    private ArrayList<ILineDataSet> dataSets;
    private LinearLayout container_inspection_tab;
    private LinearLayout container_inspection;
    private TextView tv_report;
    private LinearLayout container_report;


    @Override
    public View bindLayout(LayoutInflater inflater) {
        return mView = inflater.inflate(R.layout.fragment_big_data_inspection, null);
    }

    @Override
    public void initView() {
        mRadioGroup = (RadioGroup) mView.findViewById(R.id.radioGroup);
        mChartInspection = (LineChart) mView.findViewById(R.id.lineChart_inspection);
        mChartReport = (LineChart) mView.findViewById(R.id.lineChart_report);
        rb_day = (RadioButton) mView.findViewById(R.id.rb_day);
        rb_month = (RadioButton) mView.findViewById(R.id.rb_month);
        rb_year = (RadioButton) mView.findViewById(R.id.rb_year);


        container_inspection_tab = (LinearLayout) mView.findViewById(R.id.container_inspection_tab);
        container_inspection = (LinearLayout) mView.findViewById(R.id.container_inspection);
        tv_report = (TextView) mView.findViewById(R.id.tv_report);
        container_report = (LinearLayout) mView.findViewById(R.id.container_report);
    }

    @Override
    public void initData() {
        super.initData();
        mColors = new int[]{
                getResources().getColor(R.color.color_blue),
                getResources().getColor(R.color.color_f9435c),
                getResources().getColor(R.color.color_fdbc3b),
        };

        initialChart(mChartInspection, mColors);
        initialChart(mChartReport, mColors);
        initListener();
        FormData formData = Data.getResReport(GetStaticData.TYPE_MONTH).formData;
        setLineData(mChartReport, formData, mColors, 0);

        spaceReport = formData.space;
        int height = tv_report.getHeight();
        if (height != 0) {
            setChartHeight(container_report, height, spaceReport);
        }
        rb_day.setChecked(true);

    }

    boolean isFristInspection = true;
    int spaceInspection = 0;
    boolean isFristReport = true;
    int spaceReport = 0;

    private void initListener() {

        container_inspection_tab.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (isFristInspection) {
                    if (spaceInspection != 0) {
                        setChartHeight(container_inspection, container_inspection_tab.getHeight(), spaceInspection);
                    }
                    isFristInspection = false;
                }
            }
        });

        tv_report.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (isFristReport) {
                    if (spaceReport != 0) {
                        setChartHeight(container_report, tv_report.getHeight(), spaceReport);
                    }
                    isFristReport = false;
                }
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ResponseCheck checkData = null;
                switch (checkedId) {
                    case R.id.rb_day:
                        checkData = Data.getResCheck(GetStaticData.TYPE_DAY);
                        setLineData(mChartInspection, checkData.formData, mColors, 0);
                        break;
                    case R.id.rb_month:
                        checkData = Data.getResCheck(GetStaticData.TYPE_MONTH);
                        setLineData(mChartInspection, checkData.formData, mColors, 0);
                        break;
                    case R.id.rb_year:
                        checkData = Data.getResCheck(GetStaticData.TYPE_YEAR);
                        setLineData(mChartInspection, checkData.formData, mColors, 0.2f);
                        break;
                }


                if (checkData != null) {
                    spaceInspection = checkData.formData.space;
                    int height = container_inspection_tab.getHeight();
                    if (height != 0) {
                        setChartHeight(container_inspection, height, spaceInspection);
                    }
                }


            }
        });
    }


    //
    //    private void setLineData(final LineChart mChart, FormData formData,float lastAnchorX) {
    //        mChart.getXAxis().lastAnchorX=lastAnchorX;
    //        ArrayList<String> xVals = new ArrayList<>();
    //        List<String> xLabel = formData.label;
    //        String[] dataKey = formData.dataKey;
    //        Map<String, List<Double>> datas = formData.data;//Y轴数据
    //        mChart.getAxisLeft().setLabelCount(formData.space+1, true);
    //
    //        if (mChart == mChartInspection) {
    //            spaceInspection = formData.space;
    //            int height = container_inspection_tab.getHeight();
    //            if (height != 0) {
    //                setChartHeight(container_inspection,height,spaceInspection);
    ////                setSaleChartHeight(height);
    //            }
    //        }else{
    //            if (mChart == mChartReport) {
    //                spaceReport = formData.space;
    //                int height = tv_report.getHeight();
    //                if (height != 0) {
    //                    setChartHeight(container_report,height,spaceReport);
    ////                setSaleChartHeight(height);
    //                }
    //            }
    //        }
    //
    //        for (int i = 0; i < xLabel.size(); i++) {
    //            xVals.add((xLabel.get(i)));
    //        }
    //
    //        dataSets = new ArrayList<>();
    //
    //
    //        for (int i = 0; i < dataKey.length; i++) {
    //            ArrayList<Entry> values = new ArrayList<>();
    //            List<Double> yValues = datas.get(dataKey[i]);
    //
    //            for (int j = 0; j < xLabel.size(); j++) {
    //                double value = yValues.get(j);
    //                Entry entry = new Entry((float) value, j);
    //                values.add(entry);
    //            }
    //            LineDataSet d = new LineDataSet(values, dataKey[i]);
    //            d.setLineWidth(1f);
    //            d.setCircleRadius(4f);
    //            int color = mColors[i % mColors.length];
    //            d.setColor(color);
    //            d.setCircleColor(color);
    //            d.setValueTextColor(color);
    //            d.setValueTextSize(10);
    //            //是否显示点上的值
    //            d.setDrawValues(true);
    //            dataSets.add(d);
    //
    //        }
    //
    //
    //        LineData data = new LineData(xVals, dataSets);
    //        mChart.setData(data);
    //        mChart.animateX(500);
    //        mChart.invalidate();
    //        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
    //            @Override
    //            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
    //
    //                XAxis mXAxis = mChart.getXAxis();
    //                if (mXAxis.getSelectedIndex() != e.getXIndex()) {
    //                    mXAxis.setSelectedIndex(e.getXIndex());
    //                    LineChartRenderer mRenderer = mChart.getDataRenderer();
    //                    mRenderer.setSelectedIndex(mXAxis.getSelectedIndex());
    //                    mChart.invalidate();
    //                }
    //            }
    //
    //            @Override
    //            public void onNothingSelected() {
    //
    //            }
    //        });
    //    }
}
