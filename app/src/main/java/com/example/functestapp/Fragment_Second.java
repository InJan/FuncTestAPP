package com.example.functestapp;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Second extends Fragment {
    public static final String TAG=Fragment_Second.class.getSimpleName();
    private static List<Float> refreshList = new ArrayList<>();
    private static float[] data; //一排显示的数据
    private static int intervalNumHeart = 10;//数据个数
    private static int showIndex;


    public Fragment_Second() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment or Direct return new View
        Log.d(TAG,"Second_View");
        PaintingView PView = new PaintingView(getContext());
        return PView;
    }
    class PaintingView extends View {

        float verticalBigCellNum = 6f, horizontalBigCellNum = 8f;//垂直、水平线数量
        float height = 640f, width = 480f;
        float widthOfSmallGird = width / (verticalBigCellNum * 5);

        //心电
        private float MAX_VALUE = 20f;
        private float HEART_LINE_STROKE_WIDTH = 1f;

        //网格
//        private float GRID_LINE_STROKE_WIDTH = 1f;
//        private float GRID_WIDTH_AND_HEIGHT = 10f;

        Paint curvePaint, paint;
        Path path;
        Float nowX, nowY;
//        Float[] data = {0f, 5f, -5f, 15f, -15f, 20f, -20f, 10f, -5f, 5f, 0f, 0f, -2.5f, 2.5f, -1f, 1f, -5f, 5f, -15f, 15f, -10f, 10f, -20f, 15f, -10f, 5f, -2.5f, 2.5f, 0f, 0f, 0f, 0f};

//        float intervalRowHeart = width / intervalNumHeart;
        float intervalColumnHeart = height / (MAX_VALUE * 2);

        public PaintingView(Context context) {
            super(context);
        }

        Handler mHandler = new Handler();
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawBackGrid(canvas);
//            drawCurveStatic(canvas);
            data = new float[intervalNumHeart];
            Runnable runnable = new Runnable() {
                int i = 1;
                @Override
                public void run() {
                    if(i<20){
                        i++;
                        refreshList.add((float) i);
                        mHandler.postDelayed(this, 1000);
                    }
                    else{
                        return;
                    }
                }
            };
            drawCurveRefresh(canvas);
            mHandler.post(runnable);
//            for (int i = 1; i < 20; i++) {
//                refreshList.add((float) i);
//                drawCurveRefresh(canvas);
//            }

        }
        private void drawCurveRefresh(Canvas canvas){
            paint = new Paint();
            path = new Path();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.parseColor("#000000"));
            paint.isAntiAlias();
            paint.setStrokeWidth(HEART_LINE_STROKE_WIDTH);
            paint.setAntiAlias(true);

            int nowIndex;
            if(refreshList == null){
                nowIndex = 0;
            } else nowIndex=refreshList.size();

            if(nowIndex == 0) {
                return;
            }
            if(nowIndex < intervalNumHeart){
                showIndex = nowIndex - 1 ;
            }else{
                showIndex = (nowIndex - 1) % intervalNumHeart;
            }
            for(int i = 0 ; i <intervalNumHeart; i++){
                if(i>refreshList.size() - 1 ){
                    break;
                }
                if(nowIndex <= intervalNumHeart){
                    data[i] = refreshList.get(i);
                }else{
                    int times = (nowIndex - 1) / intervalNumHeart;
                    int temp = times * intervalNumHeart + i;
                    if(temp<nowIndex){
                        data[i]=refreshList.get(temp);
                    }
                }
            }

            logdata();

            path.moveTo(width,height / 2);
            Float startLocationX = width - widthOfSmallGird;
            for(float value : data){
                nowX = startLocationX = startLocationX - widthOfSmallGird;
                nowY = height / 2 -value*intervalColumnHeart;
                if(startLocationX==0){
                    path.moveTo(nowX,nowY);
                }else{
                    path.lineTo(nowX,nowY);
                }
            }
            canvas.drawPath(path,paint);
        }
        private void drawCurveStatic(Canvas canvas) {
            curvePaint = new Paint();
            curvePaint.setStyle(Paint.Style.STROKE);
            curvePaint.setColor(Color.BLACK);
            curvePaint.setStrokeJoin(Paint.Join.ROUND);
            curvePaint.setStrokeCap(Paint.Cap.ROUND);
            curvePaint.setStrokeWidth(1);
            curvePaint.setAntiAlias(true);

            path = new Path();
            path.moveTo(width, height / 2);
            Float startLocationX = width - widthOfSmallGird;
            path.lineTo(startLocationX, height / 2);
            for (int i = 0; i < data.length; i++) {
                nowX = startLocationX - i * widthOfSmallGird / 2;
                float dataValue = data[i];
                nowY = height / 2 - dataValue * intervalColumnHeart;
                path.lineTo(nowX, nowY);
            }
//            curvePaint.setColor(Color.BLACK);
            canvas.drawPath(path, curvePaint);
            Log.d(TAG, "" + data.length);
        }

        private void drawBackGrid(Canvas canvas) {
            paint = new Paint();
            paint.setStrokeJoin(Paint.Join.BEVEL);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.reset();
            paint.setColor(Color.RED);
            paint.setAlpha(30);
            for (int i = 0; i <= verticalBigCellNum * 5; i++) {
                if (i % 5 == 0) {
                    paint.setStrokeWidth(2);
                } else {
                    paint.setStrokeWidth(1);
                }
                canvas.drawLine(i * widthOfSmallGird, 0, i * widthOfSmallGird, height, paint);
            }
            for (int i = 0; i <= horizontalBigCellNum * 5; i++) {
                if (i % 5 == 0) {
                    paint.setStrokeWidth(3);
                } else {
                    paint.setStrokeWidth(1);
                }
                canvas.drawLine(0, i * widthOfSmallGird, width, i * widthOfSmallGird, paint);
            }
        }
    }
    private static void logdata() {
        String str = "";
        for (float temp : data) {
            int tempInt = (int) temp;
            str += tempInt + " , ";

        }
        Log( "第" +(refreshList.size()) + "次添加   " + str + "                     " );
    }

    private static void Log(String txt) {
        System.out.println(txt);
    }

}
