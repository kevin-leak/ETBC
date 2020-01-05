package club.crabglory.www.etcb.frags.account;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import club.crabglory.www.common.Application;
import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.common.utils.StatusBarUtils;
import club.crabglory.www.common.widget.AvatarView;
import club.crabglory.www.common.widget.ImageSelector.GalleryFragment;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.hepler.ViewPageHelper;

public class AccountActivity extends ToolbarActivity
        implements ViewPageHelper.ViewPagerCallback<Fragment> {

    // 从其他界面，跳转请求码，用来处理没有登入注册而返回的情况
    public static final int requestCode = 0x0001;

    @BindViews({R.id.tv_login, R.id.tv_sign_up})
    List<TextView> navigationList;
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
    @BindView(R.id.im_portrait)
    AvatarView imPortrait;
    @BindView(R.id.iv_change_avatar)
    ImageView ivChangeAvatar;
    private Fragment currentFragment;
    private String mAvatarPath;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    @Override
    protected void initWindows() {
        super.initWindows();
        StatusBarUtils.setLightColor(getWindow());
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        return !Account.isLogin();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initWidget() {
        super.initWidget();
        ViewPageHelper<View, Fragment> helper = new ViewPageHelper<>(vpContainer, this, this);
        helper.setNavBackground(R.color.alpha);
        helper.setNavNegativeColor(R.color.alpha);
        helper.addItem(navigationList.get(0), new LoginFragment())
                .addItem(navigationList.get(1), new RegisterFragment());
        currentFragment = helper.getCurrent();
    }

    @Override
    public void onChangedFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
        ConstraintLayout.LayoutParams param = (ConstraintLayout.LayoutParams) linearLayout.getLayoutParams();
        if (currentFragment.getClass() == LoginFragment.class) {
            btnSubmit.setText(R.string.login);
            tvLogin.setTextColor(getResources().getColor(R.color.white));
            tvSignUp.setTextColor(getResources().getColor(R.color.whiteGray));
            ivChangeAvatar.setVisibility(View.GONE);
            param.matchConstraintPercentHeight = 0.4F;
            linearLayout.setLayoutParams(param);
        } else {
            btnSubmit.setText(R.string.signUp);
            tvSignUp.setTextColor(getResources().getColor(R.color.white));
            tvLogin.setTextColor(getResources().getColor(R.color.whiteGray));
            ivChangeAvatar.setVisibility(View.VISIBLE);
            param.matchConstraintPercentHeight = 0.6F;
            linearLayout.setLayoutParams(param);
        }
    }


    @OnClick({R.id.btn_submit, R.id.iv_change_avatar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_change_avatar:
                if (currentFragment.getClass() == LoginFragment.class) break;
                selectAvatar();
                break;
            case R.id.btn_submit:
                if (currentFragment.getClass() == LoginFragment.class) {
                    ((LoginFragment) currentFragment).preLogin();
                } else {
                    ((RegisterFragment) currentFragment).preRegister(mAvatarPath);
                }
                break;
        }
    }

    private void selectAvatar() {
        new GalleryFragment()
                .setListener(new GalleryFragment.OnSelectedListener() {
                    @Override
                    public void onSelectedImage(String path) {
                        UCrop.Options options = new UCrop.Options();
                        // 设置图片处理的格式JPEG
                        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                        // 设置压缩后的图片精度
                        options.setCompressionQuality(96);

                        // 得到头像的缓存地址
                        File dPath = Application.Companion.getAvatarTmpFile();

                        // 发起剪切
                        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                                .withAspectRatio(1, 1) // 1比1比例
                                .withMaxResultSize(520, 520) // 返回最大的尺寸
                                .withOptions(options) // 相关参数
                                .start(AccountActivity.this);
                    }
                }).show(getSupportFragmentManager(), GalleryFragment.class.getName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /// 收到从Activity传递过来的回调，然后取出其中的值进行图片加载
        // 如果是我能够处理的类型
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            // 通过UCrop得到对应的Uri
            assert data != null;
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
                Log.e("AccountActivity", resultUri.toString());
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Application.Companion.showToast(this, R.string.data_rsp_error_unknown);
        }
    }

    private void loadPortrait(Uri uri) {
        // 得到头像地址
        mAvatarPath = uri.getPath();
        Glide.with(AccountActivity.this)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(imPortrait);
    }

}
