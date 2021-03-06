package com.ghg.tobacco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ghg.tobacco.R;
import com.ghg.tobacco.base.BaseRecyclerAdapter;
import com.ghg.tobacco.base.BaseRecyclerViewHolder;
import com.ghg.tobacco.bean.Cigarette;

import java.util.List;

/**
 */
public class CigaretterContentAdapter extends BaseRecyclerAdapter {
    private LayoutInflater mInflater;
    private List<Cigarette> datas;
    private Context mContext;
    private int selecteId;
    public CigaretterContentAdapter(Context context, List<Cigarette> datas, int selecteId) {
        super(context, datas);
        this.mContext=context;
        this.datas=datas;
        this.selecteId=selecteId;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, int position) {
        ViewHolder h= (ViewHolder) holder;
        Cigarette cigarette=datas.get(position);
        h.tv_cigarette_brand.setText(cigarette.name);
        if (selecteId==cigarette.id){
            h.tv_cigarette_brand.setSelected(true);
        }else {
            h.tv_cigarette_brand.setSelected(false);
        }
    }
    @Override
    public int getItemCount() {
        int count = 0;
        if (mDatas!=null&&mDatas.size() > 0) {
            count = mDatas.size();
        }
//        Toast.makeText(mContext, "count="+count, Toast.LENGTH_SHORT).show();
        return count;
    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.adapter_cigarette_content,parent,false);
        return new ViewHolder(view);
    }


    public void setSelecteId(int selecteId){
        this.selecteId=selecteId;
    }
    public int  getSelecteId( ){
        return selecteId;
    }

    public class  ViewHolder extends BaseRecyclerViewHolder{
        TextView tv_cigarette_brand;
        public ViewHolder(final View view) {
            super(view);
            tv_cigarette_brand= (TextView) view.findViewById(R.id.tv_cigarette_brand);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    mOnItemClickListener.onItemClick(view,position,datas.get(position));
                }
            });
        }
    }
}
