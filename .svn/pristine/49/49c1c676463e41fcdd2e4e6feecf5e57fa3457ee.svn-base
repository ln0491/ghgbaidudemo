package com.ghg.tobacco.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.ghg.tobacco.R;
import com.ghg.tobacco.bean.RegionJson;
import com.ghg.tobacco.utils.FileUtils;
import com.ghg.tobacco.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yicen.wang on 2016/5/6.
 */
public class AreaDialog extends Dialog {

    public AreaDialog(Context context) {
        super(context);
    }

    public AreaDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public String getProvince() {
        return Builder.province;
    }

    public String getCity() {
        return Builder.city;
    }

    public String getArea() {
        return Builder.area;
    }

    public static class Builder {

        private Context context;
        private boolean cancelable=true;
        private static String province;
        private static String city;
        private static String area;
        private View.OnClickListener saveClickListener;

        private String regionStr;
        private List<RegionJson> regionList;

        private List<String> provinceList= new ArrayList<>();
        private Map<String,List<String>> cityMap= new HashMap<>();
        private Map<String,List<String>> areaMap= new HashMap<>();

        private int cl_text_important;
        private int cl_text_normal;
        public Builder(Context context) {
            this.context = context;
            regionStr = FileUtils.readAssetsFile(context,"china-city-area-zip.min.json");
            if(StringUtils.isNotEmpty(regionStr)){
                regionList= JSONArray.parseArray(regionStr,RegionJson.class);

                for (RegionJson t_province:regionList) {
                    //省
                    List<String> cityList= new ArrayList<>();
                    cityList.add("全部");
                    for (RegionJson t_city: t_province.child) {
                        //市
                        cityList.add(t_city.name);
                        List<String> areaList= new ArrayList<>();
                        areaList.add("全部");
                        for (RegionJson t_area: t_city.child) {
                            //区
                            areaList.add(t_area.name);
                        }
                        areaMap.put(t_city.name,areaList);
                    }
                    provinceList.add(t_province.name);
                    cityMap.put(t_province.name,cityList);
                }
            }
            cl_text_important = context.getResources().getColor(R.color.color_38c4a9);
            cl_text_normal = context.getResources().getColor(R.color.color_333333);

        }

        public Builder setProvince(String province) {
            this.province = province;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setArea(String area) {
            this.area = area;
            return this;
        }

        public Builder setSaveClickListener(View.OnClickListener listener) {
            this.saveClickListener = listener;
            return this;
        }

        public Builder setCancelable(boolean cancelable){

            this.cancelable = cancelable;
            return this;
        }

        public AreaDialog show() {
            AreaDialog dialog = create();
            dialog.show();
            return dialog;
        }

        private Button btn_province;
        private Button btn_city;
        private Button btn_area;

        private ImageView tab_province;
        private ImageView tab_city;
        private ImageView tab_area;

        private LinearLayout llyt_choose;

        private ImageButton ibtn_close;
        private Button btn_save;

        public AreaDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final AreaDialog dialog = new AreaDialog(context, R.style.Dialog);

            Window win = dialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);

            View layout = inflater.inflate(R.layout.dialog_address_choose, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ibtn_close = (ImageButton) layout.findViewById(R.id.ibtn_close);
            btn_save = (Button) layout.findViewById(R.id.btn_save);

            btn_province = (Button) layout.findViewById(R.id.btn_province);
            btn_city = (Button) layout.findViewById(R.id.btn_city);
            btn_area = (Button) layout.findViewById(R.id.btn_area);
            tab_province = (ImageView) layout.findViewById(R.id.tab_province);
            tab_city = (ImageView) layout.findViewById(R.id.tab_city);
            tab_area = (ImageView) layout.findViewById(R.id.tab_area);

            llyt_choose = (LinearLayout) layout.findViewById(R.id.llyt_choose);

            if(saveClickListener!=null){
                btn_save.setOnClickListener(saveClickListener);
            }

            ibtn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btn_province.setOnClickListener(onClickListener);
            btn_city.setOnClickListener(onClickListener);
            btn_area.setOnClickListener(onClickListener);

            dialog.setCancelable(cancelable);


            if (StringUtils.isNotEmpty(province)) {
                btn_province.setText(province);

                if (StringUtils.isNotEmpty(city)) {
                    btn_city.setText(city);

                    findView(2,areaMap.get(city));

                    tab_province.setVisibility(View.GONE);
                    tab_city.setVisibility(View.GONE);
                    tab_area.setVisibility(View.VISIBLE);

                    btn_area.setTextColor(cl_text_important);
                    if (StringUtils.isNotEmpty(area)) {
                        btn_area.setText(area);

                    }else{
                        btn_area.setText("全部");
                    }
                }else{
                    findView(1,cityMap.get(province));
                    btn_city.setText("全部");
                    btn_area.setVisibility(View.GONE);

                    btn_city.setTextColor(cl_text_important);

                    tab_province.setVisibility(View.GONE);
                    tab_city.setVisibility(View.VISIBLE);
                    tab_area.setVisibility(View.GONE);
                }
            }else{
                findView(0,provinceList);
                btn_province.setText("请选择");
                btn_city.setVisibility(View.GONE);
                btn_area.setVisibility(View.GONE);

                btn_province.setTextColor(cl_text_important);

                tab_province.setVisibility(View.VISIBLE);
                tab_city.setVisibility(View.GONE);
                tab_area.setVisibility(View.GONE);

            }


            dialog.setContentView(layout);
            return dialog;
        }

        private void findView(int type, List<String> list) {
            for (int i = 0; i < list.size(); i++) {
                llyt_choose.addView(getView(type,list.get(i)));
            }
        }

        private TextView text1;
        private ImageView img1;

        public View getView(int type, final String dataValue) {
            LayoutInflater mInflater = LayoutInflater.from(context);
            View convertView = mInflater.inflate(R.layout.list_area_choose, null);

            text1 = (TextView) convertView.findViewById(R.id.tv_text1);
            img1 = (ImageView) convertView.findViewById(R.id.iv_img);

            text1.setText(StringUtils.isEmpty(dataValue) ? "" : dataValue);


            switch (type){
                case 0:
                    if(StringUtils.isNotEmpty(province)&&dataValue.equals(province)){
                        text1.setTextColor(cl_text_important);
                        img1.setVisibility(View.VISIBLE);

                    }else{
                        img1.setVisibility(View.GONE);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                province=dataValue;
                                btn_province.setText(dataValue);
                                //刷新 市、区
                                city = "";
                                area = "";
                                btn_city.setText("全部");

                                btn_city.setTextColor(cl_text_important);
                                btn_province.setTextColor(cl_text_normal);
                                btn_area.setTextColor(cl_text_normal);

                                tab_province.setVisibility(View.INVISIBLE);
                                tab_city.setVisibility(View.VISIBLE);
                                tab_area.setVisibility(View.INVISIBLE);

                                btn_area.setVisibility(View.GONE);
                                llyt_choose.removeAllViews();
                                findView(1,cityMap.get(province));

                            }
                        });
                    }
                break;
                case 1:
                    if((StringUtils.isNotEmpty(city)&&dataValue.equals(city))||(StringUtils.isEmpty(city)&&dataValue.equals("全部"))){
                        text1.setTextColor(cl_text_important);
                        img1.setVisibility(View.VISIBLE);

                    }else{
                        img1.setVisibility(View.GONE);


                    }
                    if(dataValue.equals("全部")){
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                city = "";
                                area = "";
                                btn_city.setText(dataValue);

                                btn_city.setTextColor(cl_text_important);
                                btn_province.setTextColor(cl_text_normal);
                                btn_area.setTextColor(cl_text_normal);

                                tab_province.setVisibility(View.INVISIBLE);
                                tab_city.setVisibility(View.VISIBLE);
                                tab_area.setVisibility(View.INVISIBLE);

                                btn_area.setVisibility(View.GONE);
                                llyt_choose.removeAllViews();
                                findView(1,cityMap.get(province));

                            }
                        });
                    }else{
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                city=dataValue;
                                btn_city.setText(dataValue);

                                //刷新 区
                                area = "";
                                btn_area.setText("全部");

                                btn_province.setTextColor(cl_text_normal);
                                btn_city.setTextColor(cl_text_normal);
                                btn_area.setTextColor(cl_text_important);

                                tab_province.setVisibility(View.INVISIBLE);
                                tab_city.setVisibility(View.INVISIBLE);
                                tab_area.setVisibility(View.VISIBLE);

                                btn_area.setVisibility(View.VISIBLE);
                                llyt_choose.removeAllViews();
                                findView(2,areaMap.get(city));
                            }
                        });
                    }

                    break;
                case 2:
                    if(StringUtils.isNotEmpty(area)&&dataValue.equals(area)||(StringUtils.isEmpty(area)&&dataValue.equals("全部"))){
                        text1.setTextColor(cl_text_important);
                        img1.setVisibility(View.VISIBLE);

                    }else{
                        img1.setVisibility(View.GONE);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                area=dataValue.equals("全部")?"":dataValue;
                                btn_area.setText(dataValue);

                                btn_province.setTextColor(cl_text_normal);
                                btn_city.setTextColor(cl_text_normal);
                                btn_area.setTextColor(cl_text_important);

                                tab_province.setVisibility(View.INVISIBLE);
                                tab_city.setVisibility(View.INVISIBLE);
                                tab_area.setVisibility(View.VISIBLE);

                                llyt_choose.removeAllViews();
                                findView(2,areaMap.get(city));
                            }
                        });
                    }
                    break;
            }

            return convertView;
        }

        View.OnClickListener onClickListener = new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                llyt_choose.removeAllViews();
                switch (v.getId()){
                    case R.id.btn_province:

                        findView(0,provinceList);

                        btn_province.setTextColor(cl_text_important);
                        btn_city.setTextColor(cl_text_normal);
                        btn_area.setTextColor(cl_text_normal);

                        tab_province.setVisibility(View.VISIBLE);
                        tab_city.setVisibility(View.INVISIBLE);
                        tab_area.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.btn_city:
                        if(StringUtils.isNotEmpty(province)){
                            findView(1,cityMap.get(province));

                            btn_province.setTextColor(cl_text_normal);
                            btn_city.setTextColor(cl_text_important);
                            btn_area.setTextColor(cl_text_normal);

                            tab_province.setVisibility(View.INVISIBLE);
                            tab_city.setVisibility(View.VISIBLE);
                            tab_area.setVisibility(View.INVISIBLE);
                        }
                        break;
                    case R.id.btn_area:
                        if(StringUtils.isNotEmpty(city)){
                            findView(2,areaMap.get(city));

                            btn_province.setTextColor(cl_text_normal);
                            btn_city.setTextColor(cl_text_normal);
                            btn_area.setTextColor(cl_text_important);

                            tab_province.setVisibility(View.INVISIBLE);
                            tab_city.setVisibility(View.INVISIBLE);
                            tab_area.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }
        };
    }
}
