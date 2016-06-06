package com.ghg.tobacco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ghg.tobacco.R;
import com.ghg.tobacco.base.BaseRecyclerAdapter;
import com.ghg.tobacco.base.BaseRecyclerViewHolder;

import java.util.List;

/**
 * Created by weizhi.zhu on 2016/5/5.
 */
public class RecyclerAdapter extends BaseRecyclerAdapter{
    public List<String>mDatas;
    private LayoutInflater mInflater;
    public RecyclerAdapter(Context context, List<String> datas) {
        super(context, datas);
        mInflater=LayoutInflater.from(context);
        mDatas=datas;
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, int position) {

        String data=  mDatas.get(position);
        ViewHolder mHolder= (ViewHolder) holder;
        mHolder.textView.setText(data);

    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.adapter_item_test,null);
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

    public class  ViewHolder extends BaseRecyclerViewHolder{

        TextView textView;
        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
        }
    }

}
