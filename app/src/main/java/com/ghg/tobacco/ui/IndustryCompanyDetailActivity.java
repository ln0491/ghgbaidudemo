package com.ghg.tobacco.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ghg.tobacco.R;
import com.ghg.tobacco.base.BaseActivity;
import com.ghg.tobacco.bean.Industry;
import com.ghg.tobacco.utils.ResUtils;
import com.ghg.tobacco.utils.StatusBarUtil;
import com.ghg.tobacco.utils.UnitUtils;

/**
 * 工业公司详情
 */
public class IndustryCompanyDetailActivity extends BaseActivity {
    private TextView tv_company_name, tv_stock_nums, tv_produce_nums, tv_sales_nums, tv_on_the_way_nums;
    private ImageView btn_back;
    private ImageView companyImageView;
    private ImageView image_infor;
    private View container_output, container_sale, container_inventory;
    private Industry mIndustry;

    private View container_on_the_way;

    @Override
    public int bindLayout() {
        return R.layout.activity_industry_company_detail;
    }

    @Override
    public void initView() {
        tv_company_name = findViewId(R.id.tv_company_name);
        tv_stock_nums = findViewId(R.id.tv_stock_nums);
        tv_produce_nums = findViewId(R.id.tv_produce_nums);
        tv_sales_nums = findViewId(R.id.tv_sales_nums);
        tv_on_the_way_nums = findViewId(R.id.tv_on_the_way_nums);
        btn_back = findViewId(R.id.btn_back);
        image_infor = findViewId(R.id.image_infor);
        companyImageView = findViewId(R.id.image_company);
        container_on_the_way = findViewId(R.id.container_on_the_way);
        container_output = findViewId(R.id.container_output);
        container_sale = findViewId(R.id.container_sale);
        container_inventory = findViewId(R.id.container_inventory);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) companyImageView.getLayoutParams();
        int screenWidth = UnitUtils.getScreenWidth(this);
        params.width = screenWidth;
        params.height = (int) (screenWidth * 0.7);
        companyImageView.setLayoutParams(params);
    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(this);
        image_infor.setOnClickListener(this);
        container_output.setOnClickListener(this);
        container_sale.setOnClickListener(this);
        container_inventory.setOnClickListener(this);
        container_on_the_way.setOnClickListener(this);
    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        mIndustry = (Industry) intent.getSerializableExtra("industry");
        String million_boxes = ResUtils.getString(this, R.string.million_boxes);
        if (mIndustry != null) {
            tv_company_name.setText(mIndustry.name);
            tv_sales_nums.setText(String.format(million_boxes, mIndustry.sale));
            tv_stock_nums.setText(String.format(million_boxes, mIndustry.inventory));
            tv_produce_nums.setText(String.format(million_boxes, mIndustry.produce));
            container_on_the_way.setVisibility(View.VISIBLE);
            tv_on_the_way_nums.setText(mIndustry.inTransitCar + "辆/" + mIndustry.inTransitNum + "箱");
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
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_back:

                finish();
                break;

            case R.id.image_infor:
                if (mIndustry != null) {
                    intent = new Intent(IndustryCompanyDetailActivity.this, CompanyYellowPagesActivity.class);
                    intent.putExtra("industry", mIndustry);
                    intent.putExtra("type",2);
                    startActivity(intent);
                }
                break;
            case R.id.container_output:
                intent = new Intent(IndustryCompanyDetailActivity.this, IndustryCompanySalePlanActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.container_sale:
                intent = new Intent(IndustryCompanyDetailActivity.this, IndustryCompanySalePlanActivity.class);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.container_inventory:
                intent = new Intent(IndustryCompanyDetailActivity.this, InventoryActivity.class);
                intent.putExtra("type", 3);
                startActivity(intent);
                break;
            case R.id.container_on_the_way:
                intent = new Intent(IndustryCompanyDetailActivity.this, BusinessCompayOnTheWayActivity.class);
                intent.putExtra("type", 2);
                intent.putExtra("companyName",mIndustry.name);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setImageStatusBar(IndustryCompanyDetailActivity.this);
    }
}
