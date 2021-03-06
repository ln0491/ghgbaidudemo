package com.ghg.tobacco.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ghg.tobacco.Data;
import com.ghg.tobacco.GetStaticData;
import com.ghg.tobacco.R;
import com.ghg.tobacco.base.BaseFragment;
import com.ghg.tobacco.bean.response.ResponseCheck;
import com.ghg.tobacco.ui.InspectionDetailActivity;
import com.ghg.tobacco.utils.DialogUtils;
import com.ghg.tobacco.utils.StringUtils;
import com.ghg.tobacco.utils.UnitUtils;
import com.ghg.tobacco.view.AreaDialog;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class InspectionFragment extends BaseFragment implements View.OnClickListener {
    private View mView;
    private RadioButton rb_month, rb_year;
    private LineChart mChart;
    private TextView tvMonth, tvDay;
    private TextView tv_inspection_nums, tv_false_smoke_nums, tv_Chuanhuo_nums;
    private RadioGroup mRadioGroup;
    private String[] months;
    private int[] mColors;
    private ArrayList<ILineDataSet> dataSets;
    private Button btn_detail;
    private LinearLayout container_chart, container_tab;
    private TextView mTvAreaChoose;

    private int space = 0;

    private String[] address;
    private AreaDialog areaDialog;
    ResponseCheck checkData = null;

    @Override
    public View bindLayout(LayoutInflater inflater) {
        return mView = inflater.inflate(R.layout.fragment_inspection, null);
    }

    @Override
    public void initView() {
        mRadioGroup = (RadioGroup) mView.findViewById(R.id.radioGroup);
        btn_detail = (Button) mView.findViewById(R.id.btn_detail);
        mChart = (LineChart) mView.findViewById(R.id.lineChart);
        rb_month = (RadioButton) mView.findViewById(R.id.rb_month);
        rb_year = (RadioButton) mView.findViewById(R.id.rb_year);
        tv_inspection_nums = (TextView) mView.findViewById(R.id.tv_inspection_nums);
        tv_false_smoke_nums = (TextView) mView.findViewById(R.id.tv_false_smoke_nums);
        tv_Chuanhuo_nums = (TextView) mView.findViewById(R.id.tv_Chuanhuo_nums);
        tvMonth = (TextView) mView.findViewById(R.id.tv_month);
        tvDay = (TextView) mView.findViewById(R.id.tv_day);

        container_chart = (LinearLayout) mView.findViewById(R.id.container_chart);
        container_tab = (LinearLayout) mView.findViewById(R.id.container_tab);


        mTvAreaChoose = (TextView) mView.findViewById(R.id.tv_area_choose);
        initListener();
    }

    private void initData(ResponseCheck checkData) {
        String[] date = checkData.date.split("-");
        tvMonth.setText(String.valueOf(months[Integer.parseInt(date[0]) - 1]));
        tvDay.setText(String.valueOf(date[1]));
        tv_false_smoke_nums.setText(String.valueOf(checkData.fakes));
        tv_Chuanhuo_nums.setText(String.valueOf(checkData.mix));
        tv_inspection_nums.setText(String.valueOf(checkData.check));
        address = new String[]{"广东", "", ""};
        mTvAreaChoose.setText(address[0] + " " + address[1] + " " + address[2]);
    }

    @Override
    public void initData() {
        super.initData();
        months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        mColors = new int[]{
                getResources().getColor(R.color.color_blue),
                getResources().getColor(R.color.color_f9435c),
                getResources().getColor(R.color.color_fdbc3b),
        };
        initialChart(mChart, mColors);
        rb_month.setChecked(true);

    }

    private void initListener() {
        container_tab.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (isFrist) {
                    if (space != 0) {
                        setChartHeight(container_chart, container_tab.getHeight() + UnitUtils.dip2px(getActivity(), 9), space);
                    }
                    isFrist = false;
                }
            }
        });


        btn_detail.setOnClickListener(this);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_month:
                        checkData = Data.getResCheck(GetStaticData.TYPE_MONTH);
                        initData(checkData);
                        setLineData(mChart, checkData.formData, mColors, 0);

                        break;
                    case R.id.rb_year:
                        checkData = Data.getResCheck(GetStaticData.TYPE_YEAR);
                        setLineData(mChart, checkData.formData, mColors, 0.2f);
                        break;
                }

                space = checkData.formData.space;
                int height = container_tab.getHeight();
                if (height != 0) {
                    setChartHeight(container_chart, height + UnitUtils.dip2px(getActivity(), 9), space);
                }
            }
        });
        mTvAreaChoose.setOnClickListener(this);
    }

    boolean isFrist = true;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_detail:
                Intent intent = new Intent(getActivity(), InspectionDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_area_choose:


                areaDialog = DialogUtils.createAreaDialog(getActivity(), new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        address[0] = StringUtils.isNotEmpty(areaDialog.getProvince()) ? areaDialog.getProvince() : "";
                        address[1] = StringUtils.isNotEmpty(areaDialog.getCity()) ? areaDialog.getCity() : "";
                        address[2] = StringUtils.isNotEmpty(areaDialog.getArea()) ? areaDialog.getArea() : "";
                        mTvAreaChoose.setText(address[0] + " " + address[1] + " " + address[2]);
                        areaDialog.dismiss();


                        if (checkData!=null){
                            float anchorX=0.0f;
                            if (rb_year.isChecked()){
                                anchorX=0.2f;
                            }
                            setLineData(mChart,checkData.formData,mColors,anchorX);
                            space = checkData.formData.space;
                            int height = container_tab.getHeight();
                            if (height != 0) {
                                setChartHeight(container_chart, height + UnitUtils.dip2px(getActivity(), 9), space);
                            }
                        }

                    }
                }, address);

                break;
        }
    }

}