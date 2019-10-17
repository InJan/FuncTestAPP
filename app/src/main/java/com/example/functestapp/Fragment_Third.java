package com.example.functestapp;


import android.animation.FloatArrayEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Third extends Fragment {


    public Fragment_Third() {
        // Required empty public constructor
    }

    private static String TAG= Fragment_Third.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG,"onCreateView");
        Fragment_Third.PaintingView myView = new Fragment_Third.PaintingView(getContext());
        return myView;
    }
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){

    }
    class PaintingView extends View{

        float verticalBigCellNum = 6f,horizontalBigCellNum = 8f;//垂直、水平线数量
        float height = 640f,width = 480f;
        float widthOfSmallGird = width/(verticalBigCellNum*5);

        //心电
        private float MAX_VALUE = 20f;
        private float HEART_LINE_STROKE_WIDTH = 1f;

        //网格
        private float GRID_LINE_STROKE_WIDTH = 1f;
        private float GRID_WIDTH_AND_HEIGHT = 10f;


        Paint curvePaint,paint;
        Path path;
        Float nowX,nowY;
        Float[] data={0f,5f,-5f,15f,-15f,20f,-20f,10f,-5f,5f,0f,0f,-2.5f,2.5f,-1f,1f,-5f,5f,-15f,15f,-10f,10f,-20f,15f,-10f,5f,-2.5f,2.5f,0f,0f,0f,0f};

        float intervalNumHeart = data.length;
        float intervalRowHeart = width / intervalNumHeart;
        float intervalColumnHeart = height / (MAX_VALUE * 2);

        public PaintingView(Context context) {
            super(context);
        }
        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);

//            canvas.drawBitmap(machineBitmap,0,0,bmpPaint);
//            canvas.drawCircle(100, 100, 90, curvePaint);
            drawBackGrid(canvas);
            drawCurve(canvas);
        }
        private void drawCurve(Canvas canvas){
            path = new Path();
            curvePaint = new Paint();
            curvePaint.setStyle(Paint.Style.STROKE);
//            curvePaint.setColor(Color.parseColor("#E51C23"));
            curvePaint.setColor(Color.BLACK);
            curvePaint.setStrokeJoin(Paint.Join.ROUND);//拐角
            curvePaint.setStrokeCap(Paint.Cap.ROUND);//线帽
            curvePaint.setStrokeWidth(1);
            curvePaint.setAntiAlias(true);

            path.moveTo(width, height / 2);
            Float startLocationX = width-widthOfSmallGird;
            path.lineTo(startLocationX,height / 2);
            for(int i =0;i<data.length;i++){
                nowX = startLocationX- i* widthOfSmallGird/2;
                float dataValue = data[i];
                nowY = height / 2 - dataValue * intervalColumnHeart;
                path.lineTo(nowX,nowY);
            }
//            curvePaint.setColor(Color.BLACK);
            canvas.drawPath(path,curvePaint);
            Log.d(TAG,""+data.length);
        }

        private void drawBackGrid(Canvas canvas){

            paint = new Paint();
            paint.setStrokeJoin(Paint.Join.BEVEL);
            paint.setStrokeCap(Paint.Cap.ROUND);

//            paint.setColor(Color.BLACK);
//            canvas.drawRect(0,0,width,height, paint);
            paint.reset();
            paint.setColor(Color.RED);
            paint.setAlpha(30);
            for(int i = 0;i <= verticalBigCellNum *5; i++){
                if(i % 5 == 0){
                    paint.setStrokeWidth(2);
                }else{
                    paint.setStrokeWidth(1);
                }
                canvas.drawLine(i*widthOfSmallGird,0,i*widthOfSmallGird,height,paint);
            }
            for(int i = 0;i <= horizontalBigCellNum *5; i++){
                if(i % 5 == 0){
                    paint.setStrokeWidth(3);
                }else{
                    paint.setStrokeWidth(1);
                }
                canvas.drawLine(0,i*widthOfSmallGird,width,i*widthOfSmallGird,paint);
            }
        }
    }

}
