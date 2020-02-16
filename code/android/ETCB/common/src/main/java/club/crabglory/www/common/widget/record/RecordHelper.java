package club.crabglory.www.common.widget.record;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import club.crabglory.www.common.Application;

/**
 * 处理录制的实际的过程，这里主要是配置MediaRecorder的参数
 */
class RecordHelper {

    private final static String TAG = "RecordHelper";

    private static MediaRecorder recorder;
    private static WaveAudioRecord.RecordCallback callback;
    private static CountDownLatch latch;
    private static long startTime;
    private static long endTime;

    public static void postAsynRecord(final CountDownLatch latch) {
        RecordHelper.latch = latch;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                latch.countDown();
                record();
            }
        });
        thread.start();
    }

    private static void record() {
        startTime = SystemClock.uptimeMillis();
        if (recorder == null)
            recorder = new MediaRecorder();
        try {
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                recorder.setOutputFile(callback.getAttach());
            }
            if (callback.getAttach() == null) Log.e(TAG, "record: " + "====");
            recorder.prepare();
            recorder.start();
            callback.beginRecord();
        } catch (IllegalStateException e) {
            Log.i("failed!", e.getMessage());
        } catch (IOException e) {
            Log.i("failed!", e.getMessage());
        }
    }

    public static void toRelease() {
        try {
            if (recorder != null) {
                recorder.stop();
                recorder.release();
                recorder = null;
            }
        } catch (RuntimeException e) {
            recorder.reset();
            recorder.release();
            recorder = null;
        } finally {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            endTime = SystemClock.uptimeMillis();
            callback.finishRecord(callback.getAttach(), endTime - startTime);
        }
    }

    public static void bindWaveAudioRecord(WaveAudioRecord.RecordCallback callback) {
        RecordHelper.callback = callback;
    }

    /**
     * 对外开发，监听helper的状况
     */
    interface RecordHelperListener {
        void recordHolder();
        void recordCancel();
        void recordRelease();
    }


}
