package club.crabglory.www.etcb.frags.book;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import butterknife.BindView;
import club.crabglory.www.common.basic.ToolbarActivity;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.display.DisplayBooksFragment;

public class BooksActivity extends ToolbarActivity {
    public static final int RANDOM = 1;
    public static final int MINE = 2;
    public static final int RECORD = 3;
    @BindView(R.id.lay_container)
    FrameLayout flContainer;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private DisplayBooksFragment booksFragment;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activtiy_book;
    }

    @SuppressLint("CommitTransaction")
    @Override
    protected void initWidget() {
        super.initWidget();
        manager = getSupportFragmentManager();
        //开启事务
        transaction = manager.beginTransaction();
        //碎片
        booksFragment = new DisplayBooksFragment();
        //提交事务
        transaction.add(R.id.lay_container, booksFragment ).commit();
    }

    @Override
    protected void initData() {
        super.initData();
    }

}
