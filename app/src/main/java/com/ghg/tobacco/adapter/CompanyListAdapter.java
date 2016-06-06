package com.ghg.tobacco.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ghg.tobacco.R;
import com.ghg.tobacco.bean.BusinessCompany;

import java.util.HashMap;
import java.util.List;

/**
 * @author $Author
 * @version $Rev$
 * @time 2016/5/20 11:23
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class CompanyListAdapter extends BaseExpandableListAdapter {
    private List<BusinessCompany> mProvinceCompanies;
    private HashMap<String,List<BusinessCompany>>mChilds;
    private Context mContext;
    private LayoutInflater mInflater;

    public CompanyListAdapter(Context context,List<BusinessCompany> provinceCompanies, HashMap<String, List<BusinessCompany>> childs) {
        mProvinceCompanies = provinceCompanies;
        mChilds = childs;
        this.mContext=context;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getGroupCount() {
        return mProvinceCompanies.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        BusinessCompany businessCompany= mProvinceCompanies.get(groupPosition);

        if (mChilds==null||!mChilds.containsKey(businessCompany.province)){
            return 0;
        }
       List<BusinessCompany>childs= mChilds.get(businessCompany.province);
        if (childs!=null&&childs.size()!=0){
            return childs.size();
        }
        return 0;
    }

    @Override
    public BusinessCompany getGroup(int groupPosition) {
        return mProvinceCompanies.get(groupPosition);
    }

    @Override
    public BusinessCompany getChild(int groupPosition, int childPosition) {
        return mChilds.get(mProvinceCompanies.get(groupPosition).province).get(childPosition);
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

        BusinessCompany company=mProvinceCompanies.get(groupPosition);
        mHolder.mGroupProvince.setText(company.provinceName);

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

        BusinessCompany pCompany=mProvinceCompanies.get(groupPosition);
        BusinessCompany company=mChilds.get(pCompany.province).get(childPosition);
//        BusinessCompany company=mChilds.get(mProvinceCompanies.get(groupPosition)).get(childPosition);
        mHolder.tvCompanyName.setText(company.name+"");
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
