package club.crabglory.www.etcb.frags.display;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import club.crabglory.www.common.basic.view.BaseFragment;
import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.common.utils.StatusBarUtils;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.book.BookShowFragment;
import club.crabglory.www.etcb.hepler.ViewPageHelper;
import club.crabglory.www.common.widget.AvatarView;

public class DisplayActivity extends ToolbarActivity implements ViewPageHelper.ViewPagerCallback {
    public static String KEY = "DisplayActivity";

    @BindViews({R.id.tv_micro, R.id.tv_books, R.id.tv_live})
    List<TextView> navigationList;
    @BindView(R.id.vp_container)
    ViewPager vpContainer;
    @BindView(R.id.cv_fork)
    CardView cvFork;
    @BindView(R.id.riv_avatar)
    AvatarView rivAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvName;
    @BindView(R.id.tv_forks)
    TextView tvForks;
    @BindView(R.id.tv_favorite)
    TextView tvFavorite;
    private ViewPageHelper<TextView, BaseFragment> helper;
    private String userId;

    @Override
    protected boolean initArgs(Bundle bundle) {
        if (bundle == null) return false;
        userId = bundle.getString(DisplayActivity.KEY);
        return !TextUtils.isEmpty(userId);
    }

    @Override
    protected void initWindows() {
        super.initWindows();
        StatusBarUtils.setDarkColor(getWindow());
    }


    @Override
    protected void initData() {
        super.initData();
        User user = Account.getUser();
        rivAvatar.setup((Glide.with(DisplayActivity.this)), user);
        tvName.setText(user.getName());
        tvForks.setText(String.format("%s", user.getFollows()));
        tvFavorite.setText(String.format("%s w", user.getFavorite()));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_mine_display;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initWidget() {
        super.initWidget();
        helper = new ViewPageHelper<>(vpContainer, this, this);
        helper.addItem(navigationList.get(0), new DisplayMicroFragment())
                .addItem(navigationList.get(1), new BookShowFragment().setType(Book.TYPE_MY_UP))
                .addItem(navigationList.get(2), new DisplayLiveFragment());
    }

    @Override
    public void onChangedFragment(Fragment currentFragment) {

    }

    public String getUserId() {
        return userId;
    }
}
