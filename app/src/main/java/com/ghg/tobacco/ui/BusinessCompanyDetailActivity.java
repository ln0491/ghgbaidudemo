package com.ghg.tobacco.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ghg.tobacco.R;
import com.ghg.tobacco.base.BaseActivity;
import com.ghg.tobacco.bean.BusinessCompany;
import com.ghg.tobacco.utils.ResUtils;
import com.ghg.tobacco.utils.StatusBarUtil;
import com.ghg.tobacco.utils.UnitUtils;

/**
 * 商业公司详情
 */
public class BusinessCompanyDetailActivity extends BaseActivity {
    private TextView tv_company_name, tv_stock_nums, tv_purchase_nums, tv_sales_nums,tv_on_the_way_nums;
    private ImageView btn_back;
    private ImageView companyImageView;
    private ImageView image_infor;
    private View container_purchase,container_sale,container_inventory;
    private BusinessCompany mBusinessCompany;
    private int type;
    private  View container_on_the_way;
    @Override
    public int bindLayout() {
        return R.layout.activity_province_company;
    }

    @Override
    public void initView() {
        tv_company_name = findViewId(R.id.tv_company_name);
        tv_stock_nums = findViewId(R.id.tv_stock_nums);
        tv_purchase_nums = findViewId(R.id.tv_purchase_nums);
        tv_sales_nums = findViewId(R.id.tv_sales_nums);
        tv_on_the_way_nums=findViewId(R.id.tv_on_the_way_nums);
        btn_back = findViewId(R.id.btn_back);
        image_infor = findViewId(R.id.image_infor);
        companyImageView = findViewId(R.id.image_company);
        container_on_the_way=findViewId(R.id.container_on_the_way);
        container_purchase=findViewId(R.id.container_purchase);
        container_sale=findViewId(R.id.container_sale);
        container_inventory=findViewId(R.id.container_inventory);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) companyImageView.getLayoutParams();
        int screenWidth = UnitUtils.getScreenWidth(this);
        params.width = screenWidth;
        params.height = (int) (screenWidth*0.7);
        companyImageView.setLayoutParams(params);
    }

    @Override
    public void initListener() {
        btn_back.setOnClickListener(this);
        image_infor.setOnClickListener(this);
        container_purchase.setOnClickListener(this);
        container_sale.setOnClickListener(this);
        container_inventory.setOnClickListener(this);
        container_on_the_way.setOnClickListener(this);
    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        type=intent.getIntExtra("type",1);//1.省级公司 2.市级公司
        mBusinessCompany = (BusinessCompany) intent.getSerializableExtra("company");
        String million_boxes = ResUtils.getString(this, R.string.million_boxes);
        if (mBusinessCompany != null) {
            tv_company_name.setText(mBusinessCompany.name);
            tv_sales_nums.setText(String.format(million_boxes, mBusinessCompany.sale));
            tv_stock_nums.setText(String.format(million_boxes, mBusinessCompany.inventory));
            tv_purchase_nums.setText(String.format(million_boxes, mBusinessCompany.purchase));
            if (type==2){
                container_on_the_way.setVisibility(View.VISIBLE);
                tv_on_the_way_nums.setText(mBusinessCompany.inTransitCar+"辆/"+mBusinessCompany.inTransitNum+"箱");
            }
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
        Intent intent=null;
        switch (v.getId()) {
            case R.id.btn_back:

                finish();
                break;

            case R.id.image_infor:
                if (mBusinessCompany != null) {
                    intent = new Intent(BusinessCompanyDetailActivity.this, CompanyYellowPagesActivity.class);
                    intent.putExtra("company", mBusinessCompany);
                    intent.putExtra("type",1);
                    startActivity(intent);
                }
                break;
            case R.id.container_purchase:
                    intent = new Intent(BusinessCompanyDetailActivity.this, BusinessCompanyPurchaseActivity.class);
                    intent.putExtra("flag",1);
                    intent.putExtra("type",type);
                    startActivity(intent);
                break;
            case R.id.container_sale:
                intent = new Intent(BusinessCompanyDetailActivity.this, BusinessCompanyPurchaseActivity.class);
                intent.putExtra("flag",2);
                intent.putExtra("type",type);
                startActivity(intent);
                break;
            case R.id.container_inventory:
                intent = new Intent(BusinessCompanyDetailActivity.this, InventoryActivity.class);
                intent.putExtra("type",type);
                startActivity(intent);
                break;
            case R.id.container_on_the_way:
                intent = new Intent(BusinessCompanyDetailActivity.this, BusinessCompayOnTheWayActivity.class);
                intent.putExtra("companyName",mBusinessCompany.name);
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setImageStatusBar(BusinessCompanyDetailActivity.this);
    }
}
