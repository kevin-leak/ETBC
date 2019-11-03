package club.crabglory.www.etcb.frags.display;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import club.crabglory.www.common.basic.BaseActivity;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.display.DisplayBooksFragment;
import club.crabglory.www.etcb.main.AtticFragment;

public class VideoShowActivity extends BaseActivity {
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
