package com.example.onsite3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyCanvas extends View {

    Paint paint;
    float x,  y , xPos , yPos , radius;
    int width;
    String action = "";

    float pxConversion = getResources().getDisplayMetrics().density;

    public MyCanvas(Context context) {
        super(context);

        paint = new Paint();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        x = displayMetrics.widthPixels / 2 ;
        y = displayMetrics.heightPixels / 2;

        xPos = x;
        yPos = y;
        radius = 40;
        width = 26;

    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);

        postInvalidate();
    }

    private void drawCircle(Canvas canvas) {

        RadialGradient radial = new RadialGradient(xPos , yPos, radius * pxConversion ,
                getResources().getColor(R.color.darkBlue) , getResources().getColor(R.color.lightBlue) , Shader.TileMode.MIRROR );

        paint.setShader(radial);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        canvas.drawCircle(xPos , yPos, radius * pxConversion,  paint);

        SweepGradient sweep = new SweepGradient(xPos, yPos,
                new int [] { getResources().getColor(R.color.darkBlue) , getResources().getColor(R.color.lightBlue)
                        , getResources().getColor(R.color.darkBlue) , getResources().getColor(R.color.lightBlue)
                        , getResources().getColor(R.color.darkBlue) , getResources().getColor(R.color.lightBlue)
                        , getResources().getColor(R.color.darkBlue) , getResources().getColor(R.color.lightBlue)
                        , getResources().getColor(R.color.darkBlue) , getResources().getColor(R.color.lightBlue)
                        , getResources().getColor(R.color.darkBlue) , getResources().getColor(R.color.lightBlue)
                        , getResources().getColor(R.color.darkBlue) , getResources().getColor(R.color.lightBlue)
                        , getResources().getColor(R.color.darkBlue) , getResources().getColor(R.color.lightBlue)
                        , getResources().getColor(R.color.darkBlue) , getResources().getColor(R.color.lightBlue)
                        , getResources().getColor(R.color.darkBlue)} , null);
        paint.reset();
        paint.setShader(sweep);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(width);

        canvas.drawCircle(xPos , yPos, (radius * pxConversion) + (width/2),  paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int xCor= (int) event.getX();
        int yCor = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (distance(xPos , yPos,  xCor,  yCor) < (radius * pxConversion)){
                    xPos = xCor;
                    yPos=  yCor;
                    action = "move";
                    return true;
                } else if (distance(xPos , yPos,  xCor,  yCor) < ((radius * pxConversion) + 20)){
                    width = 40;
                    action = "expand";
                    return true;
                }
            }
            case MotionEvent.ACTION_MOVE:{
                if (action.equals("move")){
                    xPos = xCor;
                    yPos=  yCor;
                    return true;
                } else if (action.equals("expand")){
                    radius = distance(xPos , yPos,  xCor,  yCor) / pxConversion;
                    return true;
                }
            }
            case MotionEvent.ACTION_UP:{
                if (action.equals("move")){
                    xPos = xCor;
                    yPos=  yCor;
                    action = "";
                    return true;
                } else if (action.equals("expand")){
                    width = 20;
                    action = "";
                    return true;
                }
            }
        }
        return false;
    }

    private float distance(float xPos, float yPos, int xCor, int yCor) {
        float temp1 = xPos - xCor;
        float temp2 = yPos - yCor;
        temp1 = temp1 * temp1;
        temp2 = temp2*temp2;

        float temp = temp1 + temp2;
        float distance = (float) Math.sqrt(temp);
        return distance;
    }
}
