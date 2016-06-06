package com.ghg.tobacco.custom_view;

import android.content.Context;
import android.widget.TextView;

import com.ghg.tobacco.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

/**
 * Created by weizhi.zhu on 2016/5/13.
 */
public class CustomMarkerView extends MarkerView {
    private TextView tvChartValue;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvChartValue= (TextView) findViewById(R.id.tv_chart_value);

    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvChartValue.setText(String.valueOf(e.getVal()));

    }

    @Override
    public int getXOffset(float xpos) {
        return  -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        return -(getHeight() / 2);
    }
}
