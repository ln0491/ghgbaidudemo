package com.ghg.tobacco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghg.tobacco.R;
import com.ghg.tobacco.bean.Industry;

import java.util.List;
import java.util.Map;

/**
 * @author $Author
 * @version $Rev$
 * @time 2016/5/20 11:23
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class IndustryListAdapter extends BaseExpandableListAdapter {
    private List<Industry> mcompanies;
    private Map<String,List<Industry>> mIndustries;
    private Context mContext;
    private LayoutInflater mInflater;

    public IndustryListAdapter(Context context, List<Industry> provinceCompanies, Map<String, List<Industry>> childs) {
        mcompanies = provinceCompanies;
        mIndustries = childs;
        this.mContext=context;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getGroupCount() {
        return mcompanies.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Industry businessCompany= mcompanies.get(groupPosition);

        if (mIndustries ==null||!mIndustries.containsKey(businessCompany.province)){
            return 0;
        }
       List<Industry>childs= mIndustries.get(businessCompany.province);
        if (childs!=null&&childs.size()!=0){
            return childs.size();
        }
        return 0;
    }

    @Override
    public Industry getGroup(int groupPosition) {
        return mcompanies.get(groupPosition);
    }

    @Override
    public Industry getChild(int groupPosition, int childPosition) {
        return mIndustries.get(mcompanies.get(groupPosition).province).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder mHolder;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.adapter_group_company_list,null);
            mHolder=new GroupHolder(convertView);
        }else{
            mHolder= (GroupHolder) convertView.getTag();
        }

        Industry industry= mcompanies.get(groupPosition);
        mHolder.mGroupProvince.setText(industry.name);

        if (isExpanded){
            mHolder.image_arrow_right.setImageResource(R.mipmap.right_arrow_expand);
        }else{
            mHolder.image_arrow_right.setImageResource(R.mipmap.btn_right_arrow_n);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder mHolder;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.adapter_business_company_child,null);
            mHolder=new ChildHolder(convertView);
        }else{
            mHolder= (ChildHolder) convertView.getTag();
        }

        Industry mCompany= mcompanies.get(groupPosition);
        Industry mindustry= mIndustries.get(mCompany.province).get(childPosition);
//        BusinessCompany company=mIndustries.get(mcompanies.get(groupPosition)).get(childPosition);
        mHolder.tvCompanyName.setText(mindustry.name+"");
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

     class GroupHolder{
         TextView mGroupProvince;
         ImageView image_arrow_right;
         public GroupHolder(View view){
             mGroupProvince= (TextView) view.findViewById(R.id.tv_group_province);
             image_arrow_right= (ImageView) view.findViewById(R.id.image_arrow_right);
             view.setTag(this);
         }
    }

    class ChildHolder{
        TextView tvCompanyName;
        public ChildHolder(View view){
            tvCompanyName= (TextView) view.findViewById(R.id.tv_company_name);
            view.setTag(this);
        }
    }



}
