package com.ghg.tobacco.base;

import android.support.v7.app.AppCompatActivity;

import com.ghg.tobacco.R;
import com.ghg.tobacco.utils.StatusBarUtil;

/**
 * Created by weizhi.zhu on 2016/5/9.
 */
public class BaseTest extends AppCompatActivity {
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary),0);
    }

}
