package club.crabglory.www.etcb.frags;

import android.Manifest;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.widget.RadioGroup;

//import com.wkp.runtimepermissions.callback.PermissionCallBack;
//import com.wkp.runtimepermissions.util.RuntimePermissionUtil;

import butterknife.BindView;
import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.common.widget.recordView.widget.DouyinView;
import club.crabglory.www.common.widget.recordView.widget.RecordButton;
import club.crabglory.www.etcb.R;
import pub.devrel.easypermissions.EasyPermissions;

public class MicroUpActivity extends ToolbarActivity {

    @BindView(R.id.douyinView)
    DouyinView douyinView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_mine_micro;
    }


    @Override
    protected void initWidget() {
        super.initWidget();

        EasyPermissions.requestPermissions(this, "录像权限申请", 0 ,
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        RecordButton recordButton = findViewById(R.id.btn_record);
        recordButton.setOnRecordListener(new RecordButton.OnRecordListener() {
            /**
             * 开始录制
             */
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onRecordStart() {
                douyinView.startRecord();
            }

            /**
             * 停止录制
             */
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onRecordStop() {
                douyinView.stopRecord();
            }
        });
        RadioGroup radioGroup = findViewById(R.id.rg_speed);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             * 选择录制模式
             * @param group
             * @param checkedId
             */
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_extra_slow: //极慢
                        douyinView.setSpeed(DouyinView.Speed.MODE_EXTRA_SLOW);
                        break;
                    case R.id.rb_slow:
                        douyinView.setSpeed(DouyinView.Speed.MODE_SLOW);
                        break;
                    case R.id.rb_normal:
                        douyinView.setSpeed(DouyinView.Speed.MODE_NORMAL);
                        break;
                    case R.id.rb_fast:
                        douyinView.setSpeed(DouyinView.Speed.MODE_FAST);
                        break;
                    case R.id.rb_extra_fast: //极快
                        douyinView.setSpeed(DouyinView.Speed.MODE_EXTRA_FAST);
                        break;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
