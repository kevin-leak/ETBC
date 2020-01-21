package club.crabglory.www.common.widget.record;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import club.crabglory.www.common.R;

public class WaveCurtain extends View {

    private ValueAnimator mAnimator;

    private float mWidth;
    private float mHeight;
    private float mCenterY;

    private int mColor;//波浪的颜色
    private int mWaveNum;//波浪的个数
    private float mOffset = 0;//偏移量
    private float mWaveLen;//一个波浪的长度
    private int mDuringTime;//持续的时间

    private Paint mWavePaint;
    private Path mWavePath;
    private Paint mTextPaint;

    public WaveCurtain(Context context) {
        this(context, null);
        init(context, null);
    }

    public WaveCurtain(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

        init(context, attrs);
    }

    public WaveCurtain(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    //初始化画笔路径以及样式
    private void init(Context context, AttributeSet attrs) {
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setColor(mColor);
        mWavePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mWavePath = new Path();
        if (attrs == null) return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveCurtain);
        mColor = typedArray.getColor(R.styleable.WaveCurtain_wave_color, Color.LTGRAY);
        mWaveNum = typedArray.getInt(R.styleable.WaveCurtain_wave_num, 2);
        mWaveLen = typedArray.getDimension(R.styleable.WaveCurtain_wave_len, 1000f);
        mDuringTime = typedArray.getInt(R.styleable.WaveCurtain_duration_time, 1000);
        typedArray.recycle();//释放资源
    }

    //开启值动画
    public void startAnim() {
        mAnimator = ValueAnimator.ofFloat(0, mWaveLen);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(mDuringTime);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mAnimator.start();
    }

    public void closeAnim() {
        if (mAnimator != null) {
            mAnimator.end();
            mAnimator.cancel();
            mAnimator = null;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        // 设置海面的高度
        mCenterY = 0.85f * h;
    }

    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWavePath.reset();
        mWavePath.moveTo(-mWaveLen + mOffset, mCenterY);
        for (int i = 0; i < mWaveNum; i++) {
            //(-mWaveLen / 4) + (i * mWaveLen)+mOffset和(i-1/4)*mWaveLen+mOffset
            //正弦曲线路径
            mWavePath.quadTo((-mWaveLen * 3 / 4) + (i * mWaveLen) + mOffset, mCenterY + mWaveLen * 0.07f, -0.5f * mWaveLen + (i * mWaveLen) + mOffset, mCenterY);
            mWavePath.quadTo((-mWaveLen / 4) + (i * mWaveLen) + mOffset, mCenterY - mWaveLen * 0.07f, i * mWaveLen + mOffset, mCenterY);
        }
        mWavePath.lineTo(mWidth, mHeight);
        mWavePath.lineTo(0, mHeight);
        mWavePath.close();//闭合路径
        canvas.drawPath(mWavePath, mWavePaint);
    }
}
