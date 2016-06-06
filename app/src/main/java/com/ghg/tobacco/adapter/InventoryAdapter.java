package com.ghg.tobacco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ghg.tobacco.R;
import com.ghg.tobacco.base.BaseRecyclerAdapter;
import com.ghg.tobacco.base.BaseRecyclerViewHolder;
import com.ghg.tobacco.bean.Invoicing;
import com.ghg.tobacco.custom_view.MyProgressBar;
import com.ghg.tobacco.utils.ResUtils;
import com.ghg.tobacco.utils.TextParserUtils;

import java.util.List;

/**
 * @author $Author
 * @version $Rev$
 * @time 2016/5/23 16:03
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class InventoryAdapter extends BaseRecyclerAdapter {
    public List<String>mDatas;
    private LayoutInflater mInflater;
    List<Invoicing> datas;
    private Context mContext;
    private TextParserUtils mTextParserUtils;
    private int inventory;
    public InventoryAdapter(Context context, List<Invoicing> datas,int inventory) {
        super(context, datas);
        this.mContext=context;
        this.datas=datas;
        this.inventory=inventory;
        mInflater=LayoutInflater.from(context);
        mTextParserUtils=new TextParserUtils(context);
    }

    @Override
    protected void bindHolder(BaseRecyclerViewHolder holder, int position) {
        Invoicing invoicing=datas.get(position);
        ViewHolder h= (ViewHolder) holder;
        h.tv_batch.setText(invoicing.batch);

        if (position==0){
            h.container_all_inventory.setVisibility(View.VISIBLE);
            h.tv_total_inventory.setText(String.valueOf(inventory));
        }else{
            h.container_all_inventory.setVisibility(View.GONE);
        }

        mTextParserUtils.clear();
        mTextParserUtils.append(ResUtils.getString(mContext,R.string.inventory),16,0xff323232);
        mTextParserUtils.append(String.valueOf(invoicing.inventory),16,0xff38c4a9);
        mTextParserUtils.append("箱",16,0xff323232);
        mTextParserUtils.parse(h.tv_inventory);
        String produceAccount=String.format(ResUtils.getString(mContext,R.string.product_account),invoicing.produce);
        String saleAccount=String.format(ResUtils.getString(mContext,R.string.sale_account),invoicing.sale);
        h.tv_product_account.setText(produceAccount);
        h.tv_sale_account.setText(saleAccount);
        h.myProgressBar.setMaxProgress(invoicing.produce);
        h.myProgressBar.setProgress(invoicing.sale);
        h.myProgressBar.invalidate();
    }

    @Override
    protected BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.adapter_inventory,parent,false);
        return new ViewHolder(view);
    }


    public class  ViewHolder extends BaseRecyclerViewHolder{

        TextView tv_inventory;//库存
        TextView tv_batch;//批次
        TextView tv_product_account;//生产数
        TextView tv_sale_account;//销售数
        MyProgressBar myProgressBar;//显示进度
        TextView tv_total_inventory;
        View container_all_inventory;
        public ViewHolder(View view) {
            super(view);
            tv_inventory = (TextView) view.findViewById(R.id.tv_inventory);
            tv_batch = (TextView) view.findViewById(R.id.tv_batch);
            tv_product_account = (TextView) view.findViewById(R.id.tv_product_account);
            tv_sale_account = (TextView) view.findViewById(R.id.tv_sale_account);
            tv_total_inventory = (TextView) view.findViewById(R.id.tv_all_inventory);
            myProgressBar = (MyProgressBar) view.findViewById(R.id.myProgressBar);
            container_all_inventory =  view.findViewById(R.id.container_all_inventory);
        }
    }
}
