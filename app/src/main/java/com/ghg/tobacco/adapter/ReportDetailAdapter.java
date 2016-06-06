package com.ghg.tobacco.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghg.tobacco.Constants;
import com.ghg.tobacco.R;
import com.ghg.tobacco.base.BaseRecyclerAdapter;
import com.ghg.tobacco.base.BaseRecyclerViewHolder;
import com.ghg.tobacco.bean.CheckReportDetail;

import java.util.List;

/**
 * Created by yicen.wang on 2016/5/13.
 */
public class ReportDetailAdapter extends BaseRecyclerAdapter {
    public List<CheckReportDetail> mDatas;
    private LayoutInflater mInflater;
    private Context mContext;

    public ReportDetailAdapter(Context context, List<CheckReportDetail> datas) {
        super(context, datas);
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        mContext = context;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, int position) {

        final CheckReportDetail data = mDatas.get(position);
        ViewHolder mHolder = (ViewHolder) holder;
        String date = data.checktime;
        mHolder.mTvDate.setText(date.substring(5, 10));
        mHolder.mTvTime.setText(date.substring(11, 16));

        //mHolder.mIvMobile.setImageBitmap(pic);
        mHolder.mTvSmokeName.setText(data.cigaretteName);
        mHolder.mTvShopAddress.setText(data.shopAddress);
        mHolder.mIvMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.shopMobile));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_item_report_detail, null);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mDatas.size() > 0) {
            count = mDatas.size();
        }
        return count;
    }

    public class ViewHolder extends BaseRecyclerViewHolder {

        TextView mTvDate;
        TextView mTvTime;
        ImageView mIvMobile;
        TextView mTvSmokeName;
        TextView mTvShopAddress;

        public ViewHolder(View view) {
            super(view);
            mTvDate = (TextView) view.findViewById(R.id.tv_date);
            mTvTime = (TextView) view.findViewById(R.id.tv_time);
            mIvMobile = (ImageView) view.findViewById(R.id.iv_mobile);
            mTvSmokeName = (TextView) view.findViewById(R.id.tv_smoke_name);
            mTvShopAddress = (TextView) view.findViewById(R.id.tv_shop_address);
        }
    }

}
