package club.crabglory.www.etcb.frags.chat;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.io.File;

import club.crabglory.www.common.Application;
import club.crabglory.www.common.basic.view.BaseFragment;
import club.crabglory.www.common.widget.record.WaveAudioRecord;
import club.crabglory.www.etcb.R;

/**
 * 底部面板实现
 */
public class PanelFragment extends BaseFragment {

    private final String TAG = this.getClass().getName();
    /**
     * 回调到ChatFragment中
     */
    private PanelCallback mCallback;
    private View soundPanel;
    private View morePanel;


    public PanelFragment() {
        // Required empty public constructor
    }

    // 开始初始化方法
    public void setup(PanelCallback callback) {
        mCallback = callback;
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_panel;
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);

        initRecordPanel(root);
        initMorePanel(root);
    }

    private void initMorePanel(View root) {
        morePanel = root.findViewById(R.id.more_panel);
    }

    private void initRecordPanel(View root) {
        View panel = soundPanel = root.findViewById(R.id.soundPanel);
        WaveAudioRecord recorder = ((FrameLayout) panel).findViewById(R.id.war_record);
        recorder.setRecordListener(new WaveAudioRecord.RecordCallback() {
            File fileTmpFile = null;

            @Override
            public void cancelRecord() {
                if (fileTmpFile != null) fileTmpFile.delete();
            }

            @Override
            public void beginRecord() {
            }

            @Override
            public void finishRecord(File file, long time) {
                Application.Companion.showToast(PanelFragment.this.getActivity(), "kevin");
                if (file != null && PanelFragment.this.getActivity() != null) {
                    Application.Companion.showToast(PanelFragment.this.getActivity(), file.getAbsolutePath());
                    mCallback.onRecordDone(file, time);
                }
            }

            @Override
            public File getAttach() {
                if (fileTmpFile == null)
                    fileTmpFile = Application.Companion.getFileTmpFile(false);
                return fileTmpFile;
            }
        });
    }

    void openFaceList() {
        soundPanel.setVisibility(View.GONE);
        morePanel.setVisibility(View.GONE);
    }

    void openSoundList() {
        soundPanel.setVisibility(View.VISIBLE);
        morePanel.setVisibility(View.GONE);
    }

    void openMoreList() {
        Log.e(TAG, "onClick: " + "open more list" );
        soundPanel.setVisibility(View.GONE);
        morePanel.setVisibility(View.VISIBLE);
    }


    // 回调聊天界面的Callback
    public interface PanelCallback {
        EditText getInputEditText();

        // 返回需要发送的图片
        void onSendGallery(String[] paths);

        // 返回录音文件和时常
        void onRecordDone(File file, long time);
    }

}
