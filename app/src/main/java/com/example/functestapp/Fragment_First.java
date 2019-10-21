package com.example.functestapp;


import android.content.Context;
import android.graphics.Bitmap;
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
public class Fragment_First extends Fragment {
    public static final String TAG=Fragment_Second.class.getSimpleName();//Log.d TAG
    private static List<Float> refreshList = new ArrayList<>();//data source
    private static float[] data; //data display container
    private static int dataNumber = 30; //the counter of data in one frame
    private static int FrameCounter = 0; //frame animation counter
    private static int FrameAmount = 60; //the amount of frames
    private static int showIndex = 0; //the location of latest data show
    private static int delayTime = 100; //delayTimeBetweenFrame
    private static int temporaryAmount = 60; //for temporary FrameAmount

    public Fragment_First() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment or Direct return new View
        Log.d(TAG,"Second_View");
        Fragment_First.PaintingView PView = new PaintingView(getContext());
        return PView;
    }
    class PaintingView extends View {
        Paint paint;
        Path path;
        Float nowX, nowY; //the coordinates

        public PaintingView(Context context) {
            super(context);
            data = new float[dataNumber];
            drawCurveDelay();
        }

        Handler mHandler = new Handler();
        public void drawCurveDelay(){
            Runnable runDrawFresh = new Runnable() {
                @Override
                public void run() {
                    FrameCounter++;
                    if(FrameCounter<FrameAmount) {
                        invalidate();
                        mHandler.postDelayed(this, delayTime);
                    }else{
                        Log.d(TAG,"FrameCounter: Over"+FrameCounter+"FrameAmount is "+FrameAmount);
                    }
                }
            };
            mHandler.postDelayed(runDrawFresh, delayTime);
        }

        //Generate random array of positive and negative
        private void generateRandomArray(){
            double random;
            if(FrameCounter % 2 == 0){
                random = Math.random();
            }else {
                random = -Math.random();
            }
            float value = (float)(MAX_VALUE * random);
            refreshList.add(value);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawBackGrid(canvas);
//            refreshList.add((float) FrameCounter);
            generateRandomArray();
            //Ensure minimum displayed
            FrameAmount = refreshList.size();
            if(FrameAmount<temporaryAmount){ FrameAmount = temporaryAmount; } //due to the length of true data
//            drawCurve(canvas);
            drawCurveScroll(canvas);
        }

        // Scroll from left to right
        private void drawCurveScroll(Canvas canvas){
            paint = new Paint();
            path = new Path();
            paint.reset();
            path.reset();

            for(int i = 0 ; i< data.length; i++ ){
                data[i] = 1;
            }
            canvas.drawPath(path,paint);
        }

        // refresh from left to right
        private void drawCurve(Canvas canvas){
//            paint = new Paint();
            path = new Path();
            paint.reset();
            path.reset();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.parseColor("#000000"));
            paint.isAntiAlias();
            paint.setStrokeWidth(HEART_LINE_STROKE_WIDTH);
            paint.setAntiAlias(true);

            int nowIndex;//latest data index
            if(refreshList == null){
                nowIndex = 0;
            } else nowIndex=refreshList.size();

            if(nowIndex == 0) {
                return;
            }
            if(nowIndex < dataNumber){
                showIndex = nowIndex - 1 ;
            }else{
                showIndex = (nowIndex - 1) % dataNumber;
            }
            for(int i = 0 ; i <dataNumber; i++){
                if(i >refreshList.size() - 1 ){
                    break;
                }
                if(nowIndex <= dataNumber){
                    data[i] = refreshList.get(i);
                }else{
//                    int t = nowIndex - dataNumber;
//                    t %= (t+i)%dataNumber;
                    int times = (nowIndex - 1) / dataNumber;
                    int temp = times * dataNumber + i;
                    if(temp<nowIndex){
                        data[i]=refreshList.get(temp);
                        //data[i]=refreshList.get(t);
                    }else{
                        break;
                    }
                }
            }

            logdata();

            path.moveTo(0f,height / 2);
            for(int i=0;i<data.length;i++){
                nowX = i*widthOfSmallGird;

                //surpass limit
                float dataValue = data[i];
                if(dataValue > 0){
                    if(dataValue > MAX_VALUE * 0.8f){
                        dataValue = MAX_VALUE * 0.8f;
                    }
                }else{
                    if(dataValue < -MAX_VALUE * 0.8f){
                        dataValue = -MAX_VALUE * 0.8f;
                    }
                }
                float intervalColumnHeart = height / (MAX_VALUE * 2);//the temporary Column
                nowY = height / 2 -dataValue*intervalColumnHeart;

                //path continue
                if( i-1 == showIndex){
                    path.moveTo(nowX,nowY);
                }else{
                    path.lineTo(nowX,nowY);
                }
            }

            canvas.drawPath(path,paint);
        }

        float verticalBigCellNum = 6f, horizontalBigCellNum = 8f;//the amount of vertical and horizontal line and grid
        float height = 640f, width = 480f;
        float widthOfSmallGird = width / (verticalBigCellNum * 5);//the width of little grid
        private float MAX_VALUE = 20f;// the Max value of ecg
        private float HEART_LINE_STROKE_WIDTH = 1f;

        private void drawBackGrid(Canvas canvas) {
            paint = new Paint();
            paint.reset();
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
        Log( "the" +(refreshList.size()) + "times add data" + str + "                     " );
    }

    private static void Log(String txt) {
        System.out.println(txt);
    }

}