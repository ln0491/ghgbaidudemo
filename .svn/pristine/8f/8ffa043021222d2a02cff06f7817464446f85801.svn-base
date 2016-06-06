package com.ghg.tobacco.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * @author $Author
 * @version $Rev$
 * @time 2016/5/19 13:55
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */
public class MapZoomImageView extends ImageView {
    private final int normal=1;
    private final int zoom_in=2;
    private final int zoom_out=3;
    private int currentState=1;
    private ImageView imageZoomIn,imageZoomOut;
    public MapZoomImageView(Context context) {
        super(context);
    }
    public MapZoomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setZoomInOut(ImageView zoomIn,ImageView zoomOut){
        imageZoomIn=zoomIn;
        imageZoomOut=zoomOut;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float downX,upX,downY,upY;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX=event.getX();
                downY=event.getY();

                setCurrentState(downY);
                setImageState();
                break;
            case MotionEvent.ACTION_MOVE:
                downX=event.getX();
                downY=event.getY();
                setCurrentState(downY);
                setImageState();

                break;
            case MotionEvent.ACTION_UP:
                upX=event.getX();
                upY=event.getY();

                if (upY<=getHeight()/2){

                    if (onClickListener!=null){
                        onClickListener.zoomInClick();
                    }
                }else{
                    if (onClickListener!=null){
                        onClickListener.zoomOutClick();
                    }
                }
                currentState=normal;
                setImageState();
                break;
            case MotionEvent.ACTION_CANCEL:
                currentState=normal;
                setImageState();
                break;
        }
        return true;
    }


    private void setCurrentState(float y){

        if (y<=getHeight()/2){
            currentState=zoom_in;

        }else{
            currentState=zoom_out;
        }
    }
    private void setImageState(){
        if (currentState==zoom_in){
            if (imageZoomIn!=null){
                imageZoomIn.setVisibility(View.VISIBLE);
            }

            if (imageZoomOut!=null){
                imageZoomOut.setVisibility(View.GONE);
            }
        }else if (currentState==zoom_out){
            if (imageZoomIn!=null){
                imageZoomIn.setVisibility(View.GONE);
            }

            if (imageZoomOut!=null){
                imageZoomOut.setVisibility(View.VISIBLE);
            }
        }else {
            if (imageZoomIn!=null){
                imageZoomIn.setVisibility(View.GONE);
            }

            if (imageZoomOut!=null){
                imageZoomOut.setVisibility(View.GONE);
            }
        }
    }

    private  OnClickListener onClickListener;
    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }
    public interface OnClickListener{
        void zoomInClick();
        void zoomOutClick();
    }
}
