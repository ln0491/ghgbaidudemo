package com.ghg.tobacco.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ghg.tobacco.Data;
import com.ghg.tobacco.GetStaticData;
import com.ghg.tobacco.R;
import com.ghg.tobacco.base.BaseFragment;
import com.ghg.tobacco.bean.FormData;
import com.ghg.tobacco.bean.response.ResponseBigData;
import com.github.mikephil.charting.charts.LineChart;

/**
 * Created by yicen.wang on 2016/5/19.
 */
public class BigDataBusinessCompanyFragment extends BaseFragment {

    private int[] mColors;
    private View mView;
    private RadioButton mRbtnSaleMonth, mRbtnSaleSeason, mRbtnSaleYear;
    private LineChart mChartSale, mChartInventory;
    private RadioGroup mRgSale;
    private LinearLayout container_sale, container_sale_tab;

    private LinearLayout container_inventory_tab, container_inventory;

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.fragment_big_data_business_company, null);
        return mView;
    }

    @Override
    public void initView() {
        mRgSale = (RadioGroup) mView.findViewById(R.id.rg_sale);
        mChartSale = (LineChart) mView.findViewById(R.id.lineChart_sale);
        mChartInventory = (LineChart) mView.findViewById(R.id.lineChart_inventory);
        mRbtnSaleMonth = (RadioButton) mView.findViewById(R.id.rb_sale_month);
        mRbtnSaleSeason = (RadioButton) mView.findViewById(R.id.rb_sale_season);
        mRbtnSaleYear = (RadioButton) mView.findViewById(R.id.rb_sale_year);

        container_sale_tab = (LinearLayout) mView.findViewById(R.id.container_sale_tab);
        container_inventory_tab = (LinearLayout) mView.findViewById(R.id.container_inventory_tab);
        container_sale = (LinearLayout) mView.findViewById(R.id.container_sale);
        container_inventory = (LinearLayout) mView.findViewById(R.id.container_inventory);
    }

    @Override
    public void initData() {
        super.initData();
        mColors = new int[]{
                getResources().getColor(R.color.color_blue),
                getResources().getColor(R.color.color_f9435c),
                getResources().getColor(R.color.color_fdbc3b),
        };

        initialChart(mChartSale,mColors);
        initialChart(mChartInventory,mColors);
        initListener();
        FormData formData= Data.getResInventory(GetStaticData.TYPE_MONTH, GetStaticData.TYPE_COMPANY).formData;
        setLineData(mChartInventory,formData,mColors, 0);
        spaceInventory=formData.space;
        int height=container_inventory_tab.getHeight();
        if (height!=0){
            setChartHeight(container_inventory, height, spaceInventory);
        }
        mRbtnSaleMonth.setChecked(true);
    }

    boolean isFrist = true;
    int spaceSale = 0;
    boolean isFristInventory = true;
    int spaceInventory = 0;

    private void initListener() {
        container_sale_tab.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (isFrist) {
                    if (spaceSale != 0) {
                        setChartHeight(container_sale, container_sale_tab.getHeight(), spaceSale);
                    }
                    isFrist = false;
                }
            }
        });

        container_inventory_tab.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (isFristInventory) {
                    if (spaceInventory != 0) {
                        setChartHeight(container_inventory, container_inventory_tab.getHeight(), spaceInventory);
                    }
                    isFristInventory = false;
                }
            }
        });

        mRgSale.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ResponseBigData data = null;
                switch (checkedId) {
                    case R.id.rb_sale_month:
                        data = Data.getResSale(GetStaticData.TYPE_MONTH, GetStaticData.TYPE_COMPANY);
                        setLineData(mChartSale, data.formData,mColors, 0);
                        spaceSale=data.formData.space;

                        break;
                    case R.id.rb_sale_season:
                        data = Data.getResSale(GetStaticData.TYPE_SEASON, GetStaticData.TYPE_COMPANY);
                        setLineData(mChartSale, data.formData,mColors, 0.2f);
                        spaceSale=data.formData.space;
                        break;
                    case R.id.rb_sale_year:
                        data = Data.getResSale(GetStaticData.TYPE_YEAR, GetStaticData.TYPE_COMPANY);
                        setLineData(mChartSale, data.formData, mColors,0.2f);
                        spaceSale=data.formData.space;
                        break;

                }
                if (container_sale_tab.getHeight()!=0){
                    setChartHeight(container_sale, container_sale_tab.getHeight(), spaceSale);
                }

            }
        });
    }


}
