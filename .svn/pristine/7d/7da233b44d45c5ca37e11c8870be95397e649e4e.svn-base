package com.ghg.tobacco.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.ghg.tobacco.utils.MathUtils;
import com.ghg.tobacco.utils.UnitUtils;

/**
 * @author $Author
 * @version $Rev$
 * @time 2016/5/23 14:02
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class MyProgressBar extends View {

    private Paint paintProgress;
    private Paint paintBg;
    private Paint paintText;
    private int colorBg = 0xffeeeeee;
    private int colorProgress = 0xff92b2e6;
    private int colorText = 0xffffffff;
    private double maxProgress;
    private double progress;


    public MyProgressBar(Context context) {
        super(context);
        init();
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        paintProgress = new Paint();
        paintProgress.setAntiAlias(true);
        paintProgress.setColor(colorProgress);
        paintProgress.setStrokeWidth(2);
        paintProgress.setStyle(Paint.Style.FILL);//设置实心

        paintBg = new Paint();
        paintBg.setAntiAlias(true);
        paintBg.setColor(colorBg);
        paintBg.setStrokeWidth(2);
        paintBg.setStyle(Paint.Style.FILL);//设置实心

        paintText = new Paint();
        paintText.setAntiAlias(true);
        paintText.setColor(colorText);
        paintText.setStrokeWidth(2);
        paintText.setTextSize(32);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        double rate=progress/maxProgress;
        RectF rectFBg=new RectF(0,0,getWidth(),getHeight());
        canvas.drawRoundRect(rectFBg,getHeight()/2,getHeight()/2,paintBg);
        RectF rectFProgress=new RectF(0,0, (float) (getWidth()*rate),getHeight());
        canvas.drawRoundRect(rectFProgress,getHeight()/2,getHeight()/2,paintProgress);
        String text= MathUtils.formatDouble(rate*100)+"%";
        float width=paintText.measureText(text);
        Paint.FontMetricsInt fontMetrics = paintText.getFontMetricsInt();
        int baseline = (int) ((rectFProgress.bottom + rectFProgress.top - fontMetrics.bottom - fontMetrics.top) / 2);
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        paintText.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, rectFProgress.right-width+ UnitUtils.dip2px(getContext(),5), baseline, paintText);
    }

    public void setMaxProgress(double maxProgress){
        this.maxProgress=maxProgress;
    }
    public void setProgress(double progress){
        this.progress=progress;
    }
}
