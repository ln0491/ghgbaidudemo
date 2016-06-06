package com.ghg.tobacco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ghg.tobacco.R;
import com.ghg.tobacco.bean.CarForm;

import java.util.List;

/**
 */
public class CarFormAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;

    private List<CarForm>mDatas;

    public CarFormAdapter(Context context, List<CarForm> datas) {
        mContext = context;
        mDatas = datas;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public CarForm getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.adapter_carform,null);
            holder=new ViewHolder(convertView);

        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        CarForm carForm= mDatas.get(position);
        holder.tvNum.setText(carForm.num+"箱");
        holder.tv_cigarette_name.setText(carForm.name+"");
        return convertView;
    }


    public class  ViewHolder{
        TextView tv_cigarette_name;
        TextView tvNum;
        public ViewHolder(View view) {
            tv_cigarette_name= (TextView) view.findViewById(R.id.tv_cigarette_name);
            tvNum= (TextView) view.findViewById(R.id.tv_nums);
            view.setTag(this);
        }
    }
}
