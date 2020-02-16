package club.crabglory.www.common.widget.micro.widget;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewOutlineProvider;
import club.crabglory.www.common.widget.micro.carmera.CameraHelper;
import club.crabglory.www.common.widget.micro.filter.ScreenFilter;
import club.crabglory.www.common.widget.micro.record.MediaRecorder;

public class MicroVideoSurface extends GLSurfaceView{

    private CameraHelper mCameraHelper;
    private int[] mTextures;
    private SurfaceTexture mSurfaceTexture;
    private ScreenFilter mScreenFilter;
    private float[] mtx = new float[16];
    private MicroRenderer mRenderer;

    public MicroVideoSurface(Context context) {
        super(context);
        init();
    }

    public MicroVideoSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        /**
         * 配置GLSurfaceView
         */
        //设置EGL版本
        setEGLContextClientVersion(2);
        mRenderer = new MicroRenderer(this);
        setRenderer(mRenderer);
        //设置按需渲染 当我们调用 requestRender 请求GLThread 回调一次 onDrawFrame
        // 连续渲染 就是自动的回调onDrawFrame
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    public void enableBeauty(boolean isChecked) {
        mRenderer.enableBeauty(isChecked);
    }

    public void enableBigEye(boolean isChecked) {
        mRenderer.enableBigEye(isChecked);
    }

    public void enableSoul(boolean isChecked) {
        mRenderer.enableSoul(isChecked);
    }

    public void enableStick(boolean isChecked) {
        mRenderer.enableStick(isChecked);
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
        mRenderer.onSurfaceDestroyed();
    }

    public void switchCamera() {
        mRenderer.switchCamera();
    }

    public void stopRecord() {
        mRenderer.stopRecord();
    }

    public void startRecord(float speed) {
        mRenderer.startRecord(speed);
    }

    public boolean isRecord() {
        return mRenderer.isRecord();
    }

    public void setOnRecordFinishListener(MediaRecorder.OnRecordFinishListener mListener){
        mRenderer.setOnRecordFinishListener(mListener);
    }


    public static class OutlineProvider extends ViewOutlineProvider {
        private float mRadius;

        public OutlineProvider(float radius) {
            this.mRadius = radius;
        }


        @Override
        public void getOutline(View view, Outline outline) {
            Rect rect = new Rect();
            view.getGlobalVisibleRect(rect);
            int leftMargin = 0;
            int topMargin = 0;
            Rect selfRect = new Rect(leftMargin, topMargin,
                    rect.right - rect.left - leftMargin, rect.bottom - rect.top - topMargin);
            outline.setRoundRect(selfRect, mRadius);
        }
    }
}
