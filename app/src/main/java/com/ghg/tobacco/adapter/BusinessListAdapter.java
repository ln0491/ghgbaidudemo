package com.ghg.tobacco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ghg.tobacco.R;
import com.ghg.tobacco.base.BaseRecyclerAdapter;
import com.ghg.tobacco.base.BaseRecyclerViewHolder;
import com.ghg.tobacco.bean.Store;

import java.util.List;

/**
 * Created by yicen.wang on 2016/5/31.
 */
public class BusinessListAdapter extends BaseRecyclerAdapter {
    public List<Store> mDatas;
    private LayoutInflater mInflater;
    private Context mContext;

    public BusinessListAdapter(Context context, List<Store> datas) {
        super(context, datas);
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        mContext = context;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, int position) {

        Store data = mDatas.get(position);
        ViewHolder mHolder = (ViewHolder) holder;

        mHolder.mTvShopName.setText(data.name);
        mHolder.mTvShopAddress.setText(data.address);

    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_item_business_list, null);
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


        TextView mTvShopName;
        TextView mTvShopAddress;

        public ViewHolder(View view) {
            super(view);

            mTvShopName = (TextView) view.findViewById(R.id.tv_shop_name);
            mTvShopAddress = (TextView) view.findViewById(R.id.tv_shop_address);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    mOnItemClickListener.onItemClick(view,position,mDatas.get(position));
                }
            });
        }
    }

}
