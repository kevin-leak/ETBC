package club.crabglory.www.common.widget.record;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Message;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;

import java.io.IOException;

import club.crabglory.www.common.R;


public class VoiceImageView extends AppCompatImageView implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private final String TAG = getClass().getName();

    private Context mContext;
    private AudioPlayCallback callback;
    private String source = null;
    private MediaPlayer mediaPlayer;

    public VoiceImageView(Context context) {
        super(context);
    }

    public VoiceImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VoiceImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 开始播放
     */
    public void startPlay() {
        AnimationDrawable animationDrawable = (AnimationDrawable) getDrawable();
        if (animationDrawable != null)
            animationDrawable.start();
        this.mediaPlayer.start();
    }

    /**
     * 结束播放
     */
    public void stopPlay() {
        AnimationDrawable animationDrawable = (AnimationDrawable) getDrawable();
        if (animationDrawable != null) {
            animationDrawable.stop();
            animationDrawable.selectDrawable(0);
        }
        if (mediaPlayer != null)
            this.mediaPlayer.stop();

        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        } catch (RuntimeException e) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            source = null;
        } finally {
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        stopPlay();
        super.onDetachedFromWindow();
    }

    public void bindAudioSource(final AudioPlayCallback callback, String content) throws IOException {
        this.callback = callback;
        this.source = content;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(getContext(), Uri.parse(source));
        mediaPlayer.setAudioStreamType(3);
        mediaPlayer.prepare();
        // 不循环播放
        mediaPlayer.setLooping(false);
        // 设置播放错误时的回调为当前类
        mediaPlayer.setOnErrorListener(this);
        // 设置播放完成的相关监听
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        callback.playDone();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        callback.playDone();
        return true;
    }

    public interface AudioPlayCallback {
        void playDone();
    }
}
