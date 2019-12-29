package club.crabglory.www.etcb.frags.display;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import club.crabglory.www.common.basic.view.BaseActivity;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.main.AtticFragment;

public class VideoShowActivity extends BaseActivity {

    public static final String KEY_Books = "VideoShowActivity";

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private AtticFragment atticFragment;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_video_show;
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
