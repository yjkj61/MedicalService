package com.example.medicalservice.diyView;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.medicalservice.R;

public class QQStepView extends View {
    private int mOuterColor = Color.BLACK;
    private int mInnerColor = Color.BLUE;
    private int mBorderSize = 20;//20px
    private int topTextColor;
    private int topTextSize;
    private int bottomTextSize;

    private int bottomTextColor;
    private Paint mOuterPaint;
    private Paint mInnerPaint;
    private TextPaint topTextPain;
    private TextPaint bottomTextPain;

    //总共的步数
    private int mStepMax = 0;
    //当前的步数
    private float mCurrentStep = 0;
    private String bottomText = "";

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //1.分析效果
        //2.确定自定义属性，编译attrs.xml
        //3.在布局中使用
        //4.在自定义view中获取
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mBorderSize = (int) array.getDimension(R.styleable.QQStepView_borderWidth, mBorderSize);
        topTextColor = array.getColor(R.styleable.QQStepView_topTextColor, topTextColor);
        topTextSize = (int) array.getDimension(R.styleable.QQStepView_topTextSize, topTextSize);
        bottomTextSize = (int) array.getDimension(R.styleable.QQStepView_bottomTextSize, topTextSize);
        bottomTextColor = (int) array.getColor(R.styleable.QQStepView_bottomTextColor, bottomTextColor);

        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        //设置描边的宽度，也就是控制画笔的粗细
        int[] mColors = {Color.parseColor("#F2EEE4"),Color.parseColor("#A5DABC"),Color.parseColor("#F2EEE4")};
        float[] mGradientPositions = {0,1f,0.7f};
        //        new LinearGradient(0, 0, getWidth(), getHeight(), colors, null, Shader.TileMode.CLAMP);

        mOuterPaint.setStrokeWidth(mBorderSize);
        //mOuterPaint.setColor(mOuterColor);

        mOuterPaint.setShader(new LinearGradient(0,120,getWidth(),0,mColors,mGradientPositions, Shader.TileMode.CLAMP));
        //Fill画笔实心,Stroke描边
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setStrokeCap(Paint.Cap.ROUND);//线条尾部设置成圆角

        //内部画笔，画黑色圆弧的部分
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        //设置描边的宽度，也就是控制画笔的粗细
        mInnerPaint.setStrokeWidth(mBorderSize);
        int[] mColors2 = {Color.parseColor("#EDD4C3"),Color.parseColor("#CED7A5"),Color.parseColor("#A5DABC")};
        mInnerPaint.setShader(new LinearGradient(0,120,getWidth(),0,mColors2,mGradientPositions, Shader.TileMode.CLAMP));
        //Fill画笔实心,Stroke描边
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);//线条尾部设置成圆角

        //画shang文字的画笔
        topTextPain = new TextPaint();
        topTextPain.setAntiAlias(true);
        topTextPain.setColor(topTextColor);
        topTextPain.setTextSize(topTextSize);

        bottomTextPain = new TextPaint();
        bottomTextPain.setAntiAlias(true);
        bottomTextPain.setColor(bottomTextColor);
        bottomTextPain.setTextSize(bottomTextSize);
        array.recycle();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //5.onMeasure
        //宽高不一致，取小的，保证是个正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width > height ? height : width, width > height ? height : width);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //6.画外圆弧，内圆弧，文字
        int center = getWidth()/2;
        int radius = center - mBorderSize/2;
        RectF rectF = new RectF(center-radius, center-radius, center+radius,center+radius);
        canvas.drawArc(rectF,135,270,false,mOuterPaint);

        //画内圆弧，百分比，肯定不能写死，使用者从外面传
        if (mStepMax == 0) {
            return;
        }
        float sweepAngle = (float)mCurrentStep/mStepMax;
        canvas.drawArc(rectF, 135, sweepAngle * 270,false,mInnerPaint);
//画文字
        String stepText = mCurrentStep + "";
        Rect textBounds = new Rect();
        topTextPain.getTextBounds(stepText, 0, stepText.length(),textBounds);
        int dx = getWidth()/2 - textBounds.width()/2;

        //基线 baseline
        Paint.FontMetrics fontMetrics = topTextPain.getFontMetrics();
        int dy = (int) ((fontMetrics.bottom -  fontMetrics.top)/2 - fontMetrics.bottom);
        int baseLine = getHeight()/2+dy/2;
        canvas.drawText(stepText,dx,baseLine, topTextPain);

        Rect textBounds2 = new Rect();
        bottomTextPain.getTextBounds(bottomText, 0, bottomText.length(),textBounds2);
        int dx2 = getWidth()/2 - textBounds2.width()/2;

        canvas.drawText(bottomText,dx2,getHeight()-60, bottomTextPain);


        canvas.translate(getWidth()/2, getHeight()/2);//这时候的画布已经移动到了中心位置
        Paint paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.FILL);//设置填充样式

        paintCircle.setAntiAlias(true);//抗锯齿功能
        paintCircle.setColor(Color.BLACK);


        float angle = 45+ sweepAngle*270;
        canvas.rotate(angle);
        canvas.drawCircle( 0, (float) (getHeight()/2.15),10,paintCircle);

        Paint paintCirclePoint = new Paint();
        paintCirclePoint.setStyle(Paint.Style.FILL);//设置填充样式

        paintCirclePoint.setAntiAlias(true);//抗锯齿功能
        paintCirclePoint.setColor(Color.parseColor("#A5DABC"));


        //float angle = 45+ sweepAngle*270;
        canvas.rotate(0);
        canvas.drawCircle( 0, (float) (getHeight()/2.15),5,paintCirclePoint);



    }

    //7.其他，写几个方法要它动起来
    public void setStepMax(int stepMax) {
        this.mStepMax = stepMax;
    }

    public void setCurrentStep(float topText,String bottomText) {
        this.mCurrentStep = topText;
        this.bottomText = bottomText;
        //不断的绘制
        invalidate();
    }


}
