package com.ghg.tobacco.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghg.tobacco.MyApplication;
import com.ghg.tobacco.R;
import com.ghg.tobacco.bean.FormData;
import com.ghg.tobacco.utils.ResUtils;
import com.ghg.tobacco.utils.StatusBarUtil;
import com.ghg.tobacco.utils.UnitUtils;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.renderer.LineChartRenderer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener {

    private WeakReference<Activity> task;
    private MyApplication application;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }



    /**
     * 设置状态栏颜色
     */
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(bindLayout());
        initView();
        initListener();
        initData();
        application = (MyApplication) getApplication();
        task = new WeakReference<Activity>(this);
        application.pushTask(task);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stop();
    }


    @Override
    protected void onPause() {
        super.onPause();
        pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        application.removeTask(task);
        destroy();
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    /**
     * 控件 findViewById
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T findViewId(int id) {
        //return返回view时,加上泛型T
        return (T) findViewById(id);
    }

    /**
     * 返回按钮点击监听
     */
    public void setBtnBack() {
        ImageView btnBack = (ImageView) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置Activity标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(title);
    }

    public ImageView setBtnMenu() {
        ImageView btnMenu = (ImageView) findViewById(R.id.btn_menu);
        btnMenu.setVisibility(View.VISIBLE);
        btnMenu.setOnClickListener(this);
        return btnMenu;
    }


    // 初始化图表
    public void initLineChartChart(LineChart mChart, int[] mColors) {


        mChart.setDescription("");
        mChart.setNoDataTextDescription("暂无数据");
        mChart.setTouchEnabled(true);
        // 不可拖曳
        mChart.setDragEnabled(false);
        // 不可缩放
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setPinchZoom(false);
        // 设置图表的背景颜色
        mChart.setBackgroundColor(getResources().getColor(R.color.color_transparent));

        // 图表的注解(只有当数据集存在时候才生效)
        Legend l = mChart.getLegend();
        // 线性，也可是圆
        l.setForm(Legend.LegendForm.CIRCLE);
        // 可以修改图表注解部分的位置
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        // 图表的注解的字体颜色
        l.setTextColor(getResources().getColor(R.color.color_969696));
        // x坐标轴
        XAxis xl = mChart.getXAxis();
        xl.setTextColor(getResources().getColor(R.color.color_969696));
        xl.setAvoidFirstLastClipping(true);
        xl.setSelectedIndex(0);
        // 几个x坐标轴之间才绘制？
        xl.setSpaceBetweenLabels(0);
        xl.setDrawAxisLine(true);
        xl.setAxisLineColor(getResources().getColor(R.color.color_3f969696));
        xl.setTextColor(getResources().getColor(R.color.color_969696));
        // 如果false，那么x坐标轴将不可见
        xl.setEnabled(true);
        // 将X坐标轴放置在底部，默认是在底部。
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        LineChartRenderer chartRenderer = mChart.getDataRenderer();
        chartRenderer.setXAxis(xl);

        //        xl.setXOffset(30);
        //        xl.setYOffset(30);
        xl.setSelectedIndex(0);
        xl.setFirstOffest(true);
        chartRenderer.setSelectedIndex(0);
        //        xl.setGridColor(
        //                getResources().getColor(R.color.color_transparent));
        // 图表左边的y坐标轴线
        YAxis leftAxis = mChart.getAxisLeft();
        //        leftAxis.setDrawZeroLine(true);
        leftAxis.setTextColor(getResources().getColor(R.color.color_969696));
        //        leftAxis.setGridColor(
        //                getResources().getColor(R.color.color_transparent));
        chartRenderer.setYAxis(leftAxis);
        //        隐藏左边坐标轴横网格线
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridColor(getResources().getColor(R.color.color_3f969696));
        //隐藏右边坐标轴横网格线
        mChart.getAxisRight().setDrawGridLines(false);
        //隐藏X轴竖网格线
        xl.setDrawGridLines(false);
        //隐藏左边Y轴
        leftAxis.setDrawAxisLine(false);
        leftAxis.setEnabled(true);
        leftAxis.setYOffset(-10);
        //        leftAxis.setXOffset(5);
        //        // 最大值
        //        leftAxis.setAxisMaxValue(Integer.MAX_VALUE);
        //        // 最小值
        //        leftAxis.setAxisMinValue(0f);
        // 不一定要从0开始
        //        leftAxis.setStartAtZero(false);
        //        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        // 不显示图表的右边y坐标轴线
        rightAxis.setEnabled(false);


    }


    public void setLineData(final LineChart mChart, FormData formData, int[] mColors, float lastAnchorX) {
        //        mChart.getXAxis().lastAnchorX = lastAnchorX;
        ArrayList<String> xVals = new ArrayList<>();
        List<String> xLabel = formData.label;
        String[] dataKey = formData.dataKey;
        Map<String, List<Double>> datas = formData.data;//Y轴数据
        mChart.getAxisLeft().setLabelCount(formData.space + 1, true);
        for (int i = 0; i < xLabel.size(); i++) {
            xVals.add((xLabel.get(i)));
        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();


        for (int i = 0; i < dataKey.length; i++) {
            ArrayList<Entry> values = new ArrayList<>();
            List<Double> yValues = datas.get(dataKey[i]);

            for (int j = 0; j < xLabel.size(); j++) {
                double value = yValues.get(j);
                Entry entry = new Entry((float) value, j);
                values.add(entry);
            }
            LineDataSet d = new LineDataSet(values, dataKey[i]);
            d.setLineWidth(1f);
            d.setCircleRadius(3f);
            int color = mColors[i % mColors.length];
            d.setColor(color);
            d.setCircleColor(color);
            d.setValueTextColor(color);
            d.setValueTextSize(10);
            //是否显示点上的值
            d.setDrawValues(true);
            dataSets.add(d);

        }


        LineData data = new LineData(xVals, dataSets);
        //        mChart.setData(data);
        mChart.animateX(1500);
        //        mChart.invalidate();
        //默认选中第一个
        if (mChart.getHighlighted() == null) {
            Highlight[] highlights = new Highlight[1];
            Highlight highlight = new Highlight(0, 0, 0, 0);
            highlights[0] = highlight;
            mChart.setHighlight(highlights);
        }
        mChart.setData(data);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

                XAxis mXAxis = mChart.getXAxis();
                if (mXAxis.getSelectedIndex() != e.getXIndex()) {
                    mXAxis.setSelectedIndex(e.getXIndex());
                    LineChartRenderer mRenderer = mChart.getDataRenderer();
                    mRenderer.setSelectedIndex(mXAxis.getSelectedIndex());
                    mChart.invalidate();
                }
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }

    public void setChartHeight(LinearLayout layout, int height, int space) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
        height = UnitUtils.dip2px(this, space * 40 + 40 + 31) + height;
        layoutParams.height = height;
        layout.setLayoutParams(layoutParams);
    }


    /**
     * 初始化水平柱状图
     */
    public void initHorizontalBarChart(HorizontalBarChart mHorizontalBarChart, int[] mBarColors) {
        if (mBarColors == null) {
            mBarColors = new int[]{
                    0xfffd7484,
                    0xffffbd3a,
                    0xff72a1e5,
                    0xff60ddf1,
                    0xffc675f5,
            };
        }


        mHorizontalBarChart.setDrawBarShadow(false);
        mHorizontalBarChart.setDrawValueAboveBar(true);
        mHorizontalBarChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        //        mHorizontalBarChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mHorizontalBarChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mHorizontalBarChart.setDrawBarShadow(true);

        // mHorizontalBarChart.setDrawXLabels(false);

        mHorizontalBarChart.setDrawGridBackground(false);

        // mHorizontalBarChart.setDrawYLabels(false);
        mHorizontalBarChart.setScaleEnabled(false);
        mHorizontalBarChart.setDragEnabled(false);

        XAxis xl = mHorizontalBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);
        xl.setTextColor(ResUtils.getColor(this, R.color.color_323232));
        xl.setTextSize(14);

        YAxis yl = mHorizontalBarChart.getAxisLeft();
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.3f);
        yl.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        //        yl.setInverted(true);
        yl.setDrawLabels(false);

        YAxis yr = mHorizontalBarChart.getAxisRight();
        yr.setDrawAxisLine(false);
        yr.setDrawGridLines(false);
        yr.setDrawLabels(false);
        yr.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        //        yr.setInverted(true);


        mHorizontalBarChart.setHighlightFullBarEnabled(false);
        Legend l = mHorizontalBarChart.getLegend();
        // 线性，也可是圆
        l.setForm(Legend.LegendForm.CIRCLE);
        // 可以修改图表注解部分的位置
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);
        // 图表的注解的字体颜色
        l.setTextColor(getResources().getColor(R.color.color_969696));
        l.setTextSize(12);
        l.setEnabled(true);

    }
}
