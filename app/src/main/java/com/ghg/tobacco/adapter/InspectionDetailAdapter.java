package com.ghg.tobacco.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
public class InspectionDetailAdapter extends BaseRecyclerAdapter {
    public List<CheckReportDetail> mDatas;
    private LayoutInflater mInflater;
    private Context mContext;

    public InspectionDetailAdapter(Context context, List<CheckReportDetail> datas) {
        super(context, datas);
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        mContext = context;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, int position) {

        CheckReportDetail data = mDatas.get(position);
        ViewHolder mHolder = (ViewHolder) holder;
        String date = data.checktime;
        mHolder.mTvDate.setText(date.substring(5, 10));
        mHolder.mTvTime.setText(date.substring(11, 16));
        int picId = 0;
        switch (data.checkResult) {
            case Constants.SmokeType.fake:
                picId = R.mipmap.ic_fake;
                break;
            case Constants.SmokeType.mix:
                picId = R.mipmap.ic_mix;
                break;

        }
        Bitmap pic = BitmapFactory.decodeResource(mContext.getResources(), picId);
        mHolder.mIvType.setImageBitmap(pic);
        mHolder.mTvSmokeName.setText(data.cigaretteName);
        mHolder.mTvShopName.setText(data.shopName);
        mHolder.mTvShopAddress.setText(data.shopAddress);

    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_item_inspection_detail, null);
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
        ImageView mIvType;
        TextView mTvSmokeName;
        TextView mTvShopName;
        TextView mTvShopAddress;

        public ViewHolder(View view) {
            super(view);
            mTvDate = (TextView) view.findViewById(R.id.tv_date);
            mTvTime = (TextView) view.findViewById(R.id.tv_time);
            mIvType = (ImageView) view.findViewById(R.id.iv_type);
            mTvSmokeName = (TextView) view.findViewById(R.id.tv_smoke_name);
            mTvShopName = (TextView) view.findViewById(R.id.tv_shop_name);
            mTvShopAddress = (TextView) view.findViewById(R.id.tv_shop_address);
        }
    }

}
