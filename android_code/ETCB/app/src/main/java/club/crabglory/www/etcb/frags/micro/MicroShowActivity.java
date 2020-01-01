package club.crabglory.www.etcb.frags.micro;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import club.crabglory.www.common.basic.view.BaseActivity;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.main.AtticFragment;

public class MicroShowActivity extends BaseActivity {

    public static final String KEY_TYPE = "MicroShowActivity";
    /*
     * 1. 书籍视频展示
     * 2. micro乱流展示
     * 3. 个人音视频展示
     * 4. up book 音视频选择
     */
    public static final int TYPE_ATTIC = 0x000;
    public static final int TYPE_BOOK = 0x001;
    public static final int TYPE_PERSON = 0x002;
    public static final int TYPE_UP_BOOK = 0x003;

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private AtticFragment atticFragment;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_video_show;
    }


    @Override
    protected boolean initArgs(Bundle bundle) {
        return super.initArgs(bundle);
    }

    @SuppressLint("CommitTransaction")
    @Override
    protected void initWidget() {
        super.initWidget();
        manager = getSupportFragmentManager();
        //开启事务
        transaction = manager.beginTransaction();
        //碎片
        atticFragment = new AtticFragment();
        //提交事务
        transaction.add(R.id.lay_container, atticFragment ).commit();


    }

}
