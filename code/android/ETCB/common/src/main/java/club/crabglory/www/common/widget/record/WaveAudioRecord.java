package club.crabglory.www.common.widget.record;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import club.crabglory.www.common.R;


public class WaveAudioRecord extends FrameLayout implements RecordHelper.RecordHelperListener {

    private WaveRecordButton recordButton;
    private WaveCurtain curtain;
    private View tvCancel;
    private RecordCallback callback;
    private MediaRecorder recorder;
    private CountDownLatch latch; // 用来处理异步请求

    public WaveAudioRecord(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public WaveAudioRecord(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WaveAudioRecord(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WaveAudioRecord(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.wave_record_audio, this, true);
        recordButton = findViewById(R.id.btn_record);
        curtain = findViewById(R.id.waveCurtain);
        recordButton.bindCurtain(curtain);
        tvCancel = findViewById(R.id.tv_cancel);
        latch = new CountDownLatch(1);
    }

    /**
     * 展示取消的文本
     */
    public void showCancel() {
        if (tvCancel.getVisibility() == VISIBLE) return;
        tvCancel.setVisibility(VISIBLE);
    }

    /**
     * 取消展示的文本
     */
    public void cancelOut() {
        if (tvCancel.getVisibility() == GONE) return;
        tvCancel.setVisibility(GONE);
    }


    public void setRecordListener(RecordCallback listener) {
        this.callback = listener;
        RecordHelper.bindWaveAudioRecord(listener);
    }

    @Override
    public void recordHolder() {
        RecordHelper.postAsynRecord(latch);
    }

    @Override
    public void recordCancel() {
        RecordHelper.toRelease();
    }

    @Override
    public void recordRelease() {
        RecordHelper.toRelease();
    }


    /**
     * 对外回调，录音的结果
     */
    public interface RecordCallback {
        void cancelRecord();

        void beginRecord();

        void finishRecord(File file, long time);

        File getAttach();
    }
}
