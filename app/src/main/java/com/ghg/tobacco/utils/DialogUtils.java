package com.ghg.tobacco.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.ghg.tobacco.GetStaticData;
import com.ghg.tobacco.R;
import com.ghg.tobacco.custom_view.wheel.ChangeBirthDialog;
import com.ghg.tobacco.ui.BusinessDetailActivity;
import com.ghg.tobacco.view.AreaDialog;
import com.ghg.tobacco.xrecyclerview.ProgressStyle;
import com.ghg.tobacco.xrecyclerview.SimpleViewSwithcer;
import com.ghg.tobacco.xrecyclerview.progressindicator.indicator.AVLoadingIndicatorView;

import java.util.Calendar;

/**
 * Created by weizhi.zhu on 2016/5/6.
 */
public class DialogUtils {


    public static Dialog loadingDialog(Context mContext){
        Dialog dialog=new Dialog(mContext, R.style.DialogLoading);
        View view=LayoutInflater.from(mContext).inflate(R.layout.dialog_loading,null);

        SimpleViewSwithcer progressBar= (SimpleViewSwithcer) view.findViewById(R.id.progressBar);
        AVLoadingIndicatorView progressView = new  AVLoadingIndicatorView(mContext);
        progressView.setIndicatorColor(0xffB5B5B5);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        progressBar.setView(progressView);
        dialog.setContentView(view);
//        progressBar.setVisibility(View.GONE);
        dialog.show();
        return  dialog;
    }


    public static void loadingDialogDismiss(Dialog dialog){
        if (dialog!=null){
            dialog.dismiss();
        }
        dialog=null;
    }

    public static AreaDialog createAreaDialog(Context ctx,View.OnClickListener listener,String...address) {
        AreaDialog.Builder builder = new AreaDialog.Builder(ctx);
        if(address!=null&&address.length>0){
            builder.setProvince(address[0]);
            if(address.length>1){
                builder.setCity(address[1]);
            }
            if(address.length>2){
                builder.setArea(address[2]);
            }
        }
        builder.setSaveClickListener(listener);

        return builder.setCancelable(false).show();
    }

    public static void showDatePicker(Context context,boolean isYear,ChangeBirthDialog.OnBirthListener  onBirthListener) {
        ChangeBirthDialog mChangeBirthDialog = new ChangeBirthDialog(
                context, isYear);
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        mChangeBirthDialog.setDate(year, month, 1);
        mChangeBirthDialog.show();
        mChangeBirthDialog.setBirthdayListener(onBirthListener) ;
    }
}
