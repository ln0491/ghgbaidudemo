package com.ghg.tobacco.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghg.tobacco.R;
import com.ghg.tobacco.base.BaseActivity;
import com.ghg.tobacco.bean.Store;
import com.ghg.tobacco.utils.PhoneUtils;

public class StoreDetailActivity extends BaseActivity {

    private TextView tv_type;
    private TextView tv_business_type;
    private TextView tv_from_address;
    private TextView tv_address;
    private TextView tv_provide_company;
    private TextView tv_contacts;
    private TextView tv_tel;
    private TextView tv_permit_num;
    private TextView tv_scope_of_business;
    private TextView tv_date_of_issue;
    private TextView tv_validity_of_documents;
    private String tel;
    private Store store;

    @Override
    public int bindLayout() {
        return R.layout.activity_store_detail;
    }

    @Override
    public void initView() {

        setBtnBack();
        ImageView btnLocation = setBtnMenu();
        btnLocation.setImageResource(R.drawable.selector_btn_title_location);
        tv_type = findViewId(R.id.tv_type);
        tv_business_type = findViewId(R.id.tv_business_type);
        tv_from_address = findViewId(R.id.tv_from_address);
        tv_address = findViewId(R.id.tv_address);
        tv_provide_company = findViewId(R.id.tv_provide_company);
        tv_contacts = findViewId(R.id.tv_contacts);
        tv_tel = findViewId(R.id.tv_tel);
        tv_permit_num = findViewId(R.id.tv_permit_num);
        tv_scope_of_business = findViewId(R.id.tv_scope_of_business);
        tv_date_of_issue = findViewId(R.id.tv_date_of_issue);
        tv_validity_of_documents = findViewId(R.id.tv_validity_of_documents);

    }

    @Override
    public void initListener() {
        tv_tel.setOnClickListener(this);
    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        store = (Store) intent.getSerializableExtra("store");
        if (store != null) {
            setTitle(store.name);
            if (!TextUtils.isEmpty(store.priceType)) {
                tv_type.setText(store.priceType);
            }
            tv_from_address.setText(store.provinceName + store.cityName + store.areaName);
            if (!TextUtils.isEmpty(store.address)) {
                tv_address.setText(store.address);
            }


            if (!TextUtils.isEmpty(store.supplier)) {
                tv_provide_company.setText(store.supplier);
            }


            if (!TextUtils.isEmpty(store.contactName)) {
                tv_contacts.setText(store.contactName);
            }

            if (!TextUtils.isEmpty(store.tel)) {
                tel = store.tel;
                tv_tel.setText(store.tel);
            }
            if (!TextUtils.isEmpty(store.licence)) {
                tv_permit_num.setText(store.licence);
            }
            if (!TextUtils.isEmpty(store.businessScope)) {
                tv_scope_of_business.setText(store.businessScope);
            }

            if (!TextUtils.isEmpty(store.licenceIssueDate)) {
                tv_date_of_issue.setText(store.licenceIssueDate);
            }

            if (!TextUtils.isEmpty(store.licenceValidEnd)) {
                tv_validity_of_documents.setText(store.licenceValidEnd);
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

        switch (v.getId()) {
            case R.id.btn_menu:
                Intent intent=new Intent(StoreDetailActivity.this,BusinessYellowPageActivity.class);
                intent.putExtra("store",store);
                startActivity(intent);
                break;
            case R.id.tv_tel:
                if (!TextUtils.isEmpty(tel)) {
                    PhoneUtils.toCallPhoneActivity(StoreDetailActivity.this, tel);
                }
                break;
        }
    }
}
