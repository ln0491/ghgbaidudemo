package com.ghg.tobacco.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ghg.tobacco.Constants;
import com.ghg.tobacco.Data;
import com.ghg.tobacco.R;
import com.ghg.tobacco.adapter.CigaretterBrandAdapter;
import com.ghg.tobacco.adapter.CigaretterContentAdapter;
import com.ghg.tobacco.base.BaseActivity;
import com.ghg.tobacco.base.BaseRecyclerAdapter;
import com.ghg.tobacco.bean.Cigarette;
import com.ghg.tobacco.bean.FormData;
import com.ghg.tobacco.bean.response.ResponseCigarette;
import com.ghg.tobacco.bean.response.ResponseCompanyAmount;
import com.ghg.tobacco.custom_view.wheel.ChangeBirthDialog;
import com.ghg.tobacco.utils.DialogUtils;
import com.ghg.tobacco.utils.MathUtils;
import com.ghg.tobacco.utils.ResUtils;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 工业公司销售计划和产量统计图
 */
public class IndustryCompanySalePlanActivity extends BaseActivity {

    private LineChart mLineChart;
    private TextView tv_chart_describe;
    private TextView tv_compelete_rate, tv_real_account, tv_plan_account, tv_real, tv_plan, tv_year_plan;
    private LinearLayout container_chart;
    private int[] mColors;
    private int space;
    private boolean isFrist = true;
    private RadioGroup mRadioGroup;
    private RadioButton rb_season,rb_month;
    private View container_tab;

    private RecyclerView mRecyclerViewBrand;
    private RecyclerView mRecyclerViewContent;
    private PopupWindow mPopupWindow;
    private TextView tv_drop_down;
    private List<Cigarette> cigarettes;
    private ResponseCigarette cigaretteBrand;
    private ResponseCigarette responseCigaretteContent;
    private List<Cigarette> cigaretteContent;//所有烟
    private List<Cigarette> brandCigaretteContent;//品牌对应的烟
    private CigaretterBrandAdapter brandAdapter;
    private CigaretterContentAdapter contentAdapter;
    private int selectedCigaretteId;
    private int selectedCigaretteContentId = -1;
    private int preCigaretteId;
    private int type;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    showPopupWindow();
                    cigarettes = cigaretteBrand.lists;

                    getResCigaretteName();
                    break;
                case 2:
                    setBrandAdapter();
                    cigaretteContent = responseCigaretteContent.lists;
                    brandCigaretteContent=getBrandCigarette(preCigaretteId);
                    setCigaretteContentAdapter(brandCigaretteContent);
                    break;
            }
        }
    };



    private void setCigaretteContentAdapter(List<Cigarette>cigaretteContent) {
        contentAdapter = new CigaretterContentAdapter(IndustryCompanySalePlanActivity.this, cigaretteContent, selectedCigaretteContentId);
        mRecyclerViewContent.setAdapter(contentAdapter);
        contentAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
                popDimiss();
                Cigarette cigarette = (Cigarette) model;
                selectedCigaretteId = preCigaretteId;
                selectedCigaretteContentId = cigarette.id;
                tv_drop_down.setText(cigarette.name);
                updateBusinessCompanyAmount();
            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });
    }

    private void setBrandAdapter() {
        brandAdapter = new CigaretterBrandAdapter(IndustryCompanySalePlanActivity.this, cigarettes, selectedCigaretteId);
        mRecyclerViewBrand.setAdapter(brandAdapter);
        brandAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
                //                Toast.makeText(InventoryActivity.this, "onItemClick", Toast.LENGTH_SHORT).show();
                Cigarette cigarette = (Cigarette) model;
                int selecteId = brandAdapter.getSelecteId();
                preCigaretteId = cigarette.id;
                if (preCigaretteId != 0) {
                    brandAdapter.setSelecteId(cigarette.id);
                    brandAdapter.notifyDataSetChanged();
                    if (cigaretteContent == null) {
                        getResCigaretteName();
                    } else {
                        brandCigaretteContent = getBrandCigarette(preCigaretteId);
                        if (brandCigaretteContent != null && brandCigaretteContent.size() != 0) {
                            setCigaretteContentAdapter(brandCigaretteContent);
                        } else {
                            if (contentAdapter != null) {
                                contentAdapter.clear();
                                contentAdapter.notifyDataSetChanged();
                            }
                            Toast.makeText(IndustryCompanySalePlanActivity.this, ResUtils.getString(IndustryCompanySalePlanActivity.this, R.string.data_empty), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {//看全部
                    selectedCigaretteId = preCigaretteId;
                    selectedCigaretteContentId=-1;
                    popDimiss();
                    tv_drop_down.setText("全部品牌和规格");
                    updateBusinessCompanyAmount();
                }


            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_province_business_company_purchase;
    }

    @Override
    public void initView() {
        setBtnBack();
        mLineChart = findViewId(R.id.lineChart);
        tv_drop_down = findViewId(R.id.tv_drop_down);
        tv_chart_describe = findViewId(R.id.tv_chart_describe);
        container_chart = findViewId(R.id.container_chart);
        tv_compelete_rate = findViewId(R.id.tv_compelete_rate);
        tv_real_account = findViewId(R.id.tv_real_account);
        tv_plan_account = findViewId(R.id.tv_plan_account);
        tv_year_plan = findViewId(R.id.tv_year_plan);
        tv_real = findViewId(R.id.tv_real);
        tv_plan = findViewId(R.id.tv_plan);
        mRadioGroup = findViewId(R.id.radioGroup);
        rb_season = findViewId(R.id.rb_season);
        rb_month = findViewId(R.id.rb_month);
        container_tab = findViewId(R.id.container_tab);
        mColors = new int[]{
                getResources().getColor(R.color.color_blue),
                getResources().getColor(R.color.color_fdbc3b),
        };
        initLineChartChart(mLineChart, mColors);
    }

    @Override
    public void initListener() {
        tv_drop_down.setOnClickListener(this);
        tv_year_plan.setOnClickListener(this);
        container_tab.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (isFrist) {
                    if (space != 0) {
                        setChartHeight(container_chart, container_tab.getHeight(), space);
                    }
                    isFrist = false;
                }
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ResponseCompanyAmount companyAmount = null;
                switch (checkedId) {
                    case R.id.rb_day:
//                        companyAmount = Data.getResBusinessCompanyAmount(Constants.Area.AREA, 2, "");
//                        setLineData(companyAmount, 0);
                        getResBusinessCompanyAmount(Constants.Area.AREA, 2);
                        break;
                    case R.id.rb_month:
//                        companyAmount = Data.getResBusinessCompanyAmount(Constants.Area.CITY, 2, "");
//                        setLineData(companyAmount, 0);
                        getResBusinessCompanyAmount(Constants.Area.CITY, 2);
                        break;
                    case R.id.rb_season:
//                        companyAmount = Data.getResBusinessCompanyAmount(Constants.Area.PROVINCE, 2, "");
//                        setLineData(companyAmount, 0);
                        getResBusinessCompanyAmount(Constants.Area.PROVINCE, 2);
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
         type = intent.getIntExtra("type", 1);//1.产量 2.销售
        int year=Calendar.getInstance().get(Calendar.YEAR);
        if (type == 1) {//产量
            tv_chart_describe.setText(ResUtils.getString(this, R.string.industry_output_linechart_describe));
            tv_year_plan.setText(String.format(ResUtils.getString(this, R.string.year_output_plan),year));
            tv_real.setText(ResUtils.getString(this, R.string.actual_output));
            tv_plan.setText(ResUtils.getString(this, R.string.plan_output));
            mRadioGroup.setVisibility(View.VISIBLE);
            rb_season.setChecked(true);
        } else {//销售
            tv_real.setText(ResUtils.getString(this, R.string.actual_sale));
            tv_plan.setText(ResUtils.getString(this, R.string.plan_sale));
            tv_chart_describe.setText(ResUtils.getString(this, R.string.province_company_plan_sale_linechart_describe));
            tv_year_plan.setText(String.format(ResUtils.getString(this, R.string.year_sale_plan),year));
            getResBusinessCompanyAmount(Constants.Area.CITY, 1);
        }


    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_year_plan:
                DialogUtils.showDatePicker(IndustryCompanySalePlanActivity.this, true, new ChangeBirthDialog.OnBirthListener() {
                    @Override
                    public void onClick(String year, String month, String day) {


                        if (type == 1) {//产量
                            tv_year_plan.setText(String.format(ResUtils.getString(IndustryCompanySalePlanActivity.this, R.string.year_output_plan),year));
                            updateBusinessCompanyAmount();
                        } else {//销售
                            tv_year_plan.setText(String.format(ResUtils.getString(IndustryCompanySalePlanActivity.this, R.string.year_sale_plan),year));
                            getResBusinessCompanyAmount(Constants.Area.CITY, 1);
                        }

                    }
                });
                break;
            case R.id.tv_drop_down:

                if (cigarettes == null) {
                    getCigaretteBrand();
                } else {
                    showPopupWindow();
                    setBrandAdapter();
                    //                    if (selectedCigaretteId != 0) {
                    brandCigaretteContent = getBrandCigarette(selectedCigaretteId);
                    if (brandCigaretteContent != null && brandCigaretteContent.size() != 0) {
                        setCigaretteContentAdapter(brandCigaretteContent);
                    } else {
                        if (contentAdapter != null) {
                            contentAdapter.clear();
                            contentAdapter.notifyDataSetChanged();
                        }
                        Toast.makeText(IndustryCompanySalePlanActivity.this, ResUtils.getString(IndustryCompanySalePlanActivity.this, R.string.data_empty), Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            default:
                break;
        }
    }

    private void setLineData(ResponseCompanyAmount companyAmount, float lastAnchorX) {
        FormData formData = companyAmount.formData;
        setLineData(mLineChart, formData, mColors, lastAnchorX);
        space = formData.space;
        tv_real_account.setText(String.valueOf(companyAmount.fact));
        tv_plan_account.setText(String.valueOf(companyAmount.plan));
        tv_compelete_rate.setText(MathUtils.formatDouble(100 * companyAmount.fact / companyAmount.plan) + "%");
        if (companyAmount != null) {
            space = companyAmount.formData.space;
            int height = container_tab.getHeight();
            if (height != 0) {
                setChartHeight(container_chart, height, space);
            }
        }
    }


    private void getResBusinessCompanyAmount(int type, int isSale){
        ResponseCompanyAmount companyAmount = Data.getResBusinessCompanyAmount(type, isSale, "");
        setLineData(companyAmount, 0);
    }


    private void updateBusinessCompanyAmount(){
        if (type!=1){
            getResBusinessCompanyAmount(Constants.Area.CITY, 1);
        }else{
            if (rb_season.isChecked()){
                getResBusinessCompanyAmount(Constants.Area.PROVINCE, 2);
            }else if (rb_month.isChecked()){
                getResBusinessCompanyAmount(Constants.Area.CITY, 2);
            }else {
                getResBusinessCompanyAmount(Constants.Area.AREA, 2);
            }
        }
    }


    private void getCigaretteBrand() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                cigaretteBrand = Data.getResCigaretteBrand(IndustryCompanySalePlanActivity.this);
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    private void getResCigaretteName() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                responseCigaretteContent = Data.getResCigaretteName(IndustryCompanySalePlanActivity.this);
                mHandler.sendEmptyMessage(2);
            }
        }).start();
    }

    private void showPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_cigarette_category, null);
        mRecyclerViewBrand = (RecyclerView) view.findViewById(R.id.recyclerViewCategory);
        mRecyclerViewContent = (RecyclerView) view.findViewById(R.id.recyclerViewContent);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewBrand.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewContent.setLayoutManager(linearLayoutManager2);
        mPopupWindow = new PopupWindow(this);
        mPopupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x34b1988b));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setContentView(view);
        mPopupWindow.showAsDropDown(tv_drop_down);
        mPopupWindow.update();

    }


    private void popDimiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }


    private List<Cigarette> getBrandCigarette(int cigaretteId) {
        if (cigaretteId==0){
            return cigaretteContent;
        }
        List<Cigarette> brandCigarette = new ArrayList<Cigarette>();
        for (int i = 0; i < cigaretteContent.size(); i++) {
            Cigarette cigarette = cigaretteContent.get(i);
            if (cigarette.categoryId == cigaretteId) {
                brandCigarette.add(cigarette);
            }
        }
        return brandCigarette;
    }


}
