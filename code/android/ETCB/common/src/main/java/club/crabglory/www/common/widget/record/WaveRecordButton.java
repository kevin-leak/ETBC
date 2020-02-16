package club.crabglory.www.common.widget.record;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 录制按钮，点击事件与RecordHelper相结合
 */
public class WaveRecordButton extends FloatingActionButton {
    public static final String TAG = "WaveRecordButton";
    private WaveCurtain curtain;
    private float top = 0;

    public WaveRecordButton(Context context) {
        super(context);
    }

    public WaveRecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveRecordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bindCurtain(WaveCurtain curtain) {
        this.curtain = curtain;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (curtain == null) return super.onTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                curtain.closeAnim();
                ((WaveAudioRecord) getParent().getParent()).cancelOut();
                if (top > 20){
                    ((WaveAudioRecord) getParent().getParent()).recordCancel();
                }else {
                    ((WaveAudioRecord) getParent().getParent()).recordRelease();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                ((WaveAudioRecord) getParent().getParent()).recordHolder();
                curtain.startAnim();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_MOVE:
                float topY = ev.getY();
                if (topY < 20) {
                    ((WaveAudioRecord) getParent().getParent()).showCancel();
                } else {
                    ((WaveAudioRecord) getParent().getParent()).cancelOut();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

}
