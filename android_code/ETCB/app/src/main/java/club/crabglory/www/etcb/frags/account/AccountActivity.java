package club.crabglory.www.etcb.frags.account;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.crabglory.www.common.basic.BaseActivity;
import club.crabglory.www.common.basic.ToolbarActivity;
import club.crabglory.www.common.utils.StatusBarUtils;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.hepler.ViewPageHelper;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends ToolbarActivity
        implements ViewPageHelper.ViewPagerCallback {


    @BindViews({R.id.tv_login, R.id.tv_sign_up})
    List<TextView> navigationList;
    @BindView(R.id.im_portrait)
    CircleImageView imPortrait;
    @BindView(R.id.vp_container)
    ViewPager vpContainer;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_sign_up)
    TextView tvSignUp;
    private ViewPageHelper<View, Fragment> helper;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWindows() {
        super.initWindows();
        StatusBarUtils.setLightColor(getWindow());
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        // todo  设置提交相关的信息
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initWidget() {
        super.initWidget();
        helper = new ViewPageHelper<>(vpContainer, this, this);
        helper.setNavBackground(R.color.alpha);
        helper.setNavNegativeColor(R.color.alpha);
        helper.addItem(navigationList.get(0), new LoginFragment())
                .addItem(navigationList.get(1), new RegisterFragment());


    }

    @Override
    public void onChangedFragment(Fragment currentFragment) {

        ConstraintLayout.LayoutParams param = (ConstraintLayout.LayoutParams) linearLayout.getLayoutParams();
        if (currentFragment.getClass() == LoginFragment.class) {
            btnSubmit.setText(R.string.login);
            tvLogin.setTextColor(getResources().getColor(R.color.white));
            tvSignUp.setTextColor(getResources().getColor(R.color.whiteGray));
            param.matchConstraintPercentHeight = 0.4F;
            linearLayout.setLayoutParams(param);
        }else {
            btnSubmit.setText(R.string.signUp);
            tvSignUp.setTextColor(getResources().getColor(R.color.white));
            tvLogin.setTextColor(getResources().getColor(R.color.whiteGray));
            param.matchConstraintPercentHeight = 0.6F;
            linearLayout.setLayoutParams(param);
        }
    }

}
