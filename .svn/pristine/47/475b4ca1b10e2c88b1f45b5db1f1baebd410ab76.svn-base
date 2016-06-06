package com.ghg.tobacco.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ghg.tobacco.R;
import com.ghg.tobacco.base.BaseActivity;
import com.ghg.tobacco.bean.BusinessCompany;

import java.util.List;

public class MainActivity extends BaseActivity {
    private ImageView ibtn_scan,ibtn_setting;
    private LinearLayout container_inspection;
    private LinearLayout container_business_company;
    private LinearLayout container_big_data;
    private LinearLayout container_shop;

    List<BusinessCompany> companies;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (companies!=null&&companies.size()!=0){
                        Log.d("vivi","companies="+companies.get(0).toString());
                    }
                    break;
            }

        }
    };
    private View container_industry;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ibtn_scan=findViewId(R.id.btn_scan);
        ibtn_setting=findViewId(R.id.btn_setting);
        container_inspection=findViewId(R.id.container_inspection);
        container_business_company=findViewId(R.id.container_business_company);
        container_big_data=findViewId(R.id.container_big_data);
        container_industry=findViewId(R.id.container_industry);
        container_shop=findViewId(R.id.container_shop);


    }

    @Override
    public void initListener() {
        ibtn_scan.setOnClickListener(this);
        ibtn_setting.setOnClickListener(this);
        container_inspection.setOnClickListener(this);
        container_business_company.setOnClickListener(this);
        container_big_data.setOnClickListener(this);
        container_industry.setOnClickListener(this);
        container_shop.setOnClickListener(this);
    }

    @Override
    public void initData() {

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
    protected void setStatusBar() {
        super.setStatusBar();

    }

    @Override
    public void onClick(View v) {

        Intent intent=null;
        switch (v.getId()){
            case R.id.btn_scan:
                intent=new Intent(MainActivity.this,MipcaActivityCapture.class);
                startActivity(intent);
                break;
            case R.id.container_inspection:
                intent=new Intent(MainActivity.this,InspectionActivity.class);
                startActivity(intent);
                break;

            case R.id.container_business_company:
                intent=new Intent(MainActivity.this,BusinessCompanyActivity.class);
                startActivity(intent);
                break;
            case R.id.container_big_data:
                intent=new Intent(MainActivity.this,BigDataActivity.class);
                startActivity(intent);
                break;
            case R.id.container_industry:
                intent=new Intent(MainActivity.this,IndustryCompanyActivity.class);
                startActivity(intent);
                break;
            case R.id.container_shop:
                intent=new Intent(MainActivity.this,BusinessActivity.class);
                startActivity(intent);
                break;
        }
    }
}
