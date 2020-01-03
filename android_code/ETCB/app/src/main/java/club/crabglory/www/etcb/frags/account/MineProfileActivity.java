package club.crabglory.www.etcb.frags.account;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.Application;
import club.crabglory.www.common.basic.view.PresentToolActivity;
import club.crabglory.www.common.utils.StatusBarUtils;
import club.crabglory.www.common.widget.AvatarView;
import club.crabglory.www.common.widget.ImageSelector.GalleryFragment;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.net.ModifyRspModel;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.etcb.R;
import club.crabglory.www.factory.contract.ModifyProfileContract;
import club.crabglory.www.factory.presenter.account.ModifyProfilePresenter;

public class MineProfileActivity extends PresentToolActivity<ModifyProfileContract.Presenter>
        implements ModifyProfileContract.View {


    @BindView(R.id.riv_avatar)
    AvatarView rivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    private String mAvatarPath;
    public static ModifyProfilePresenter presenter;

    @Override
    protected void initWindows() {
        super.initWindows();
        StatusBarUtils.setDarkColor(getWindow());
    }

    @Override
    protected void initData() {
        super.initData();
        User user = Account.getUser();
        rivAvatar.setup((Glide.with(MineProfileActivity.this)), user);
        tvName.setText(user.getName());
        tvSex.setText(user.getSex() == 0 ? "Male" : "Female");
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_mine_profile;
    }

    @OnClick({R.id.cv_name, R.id.cv_sex, R.id.cv_address})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.cv_name:
                bundle.putInt(ModifyProfileActivity.KEY, ModifyRspModel.VALUE_NAME);
                break;
            case R.id.cv_sex:
                bundle.putInt(ModifyProfileActivity.KEY, ModifyRspModel.VALUE_SEX);
                break;
            case R.id.cv_address:
                bundle.putInt(ModifyProfileActivity.KEY, ModifyRspModel.VALUE_ADDRESS);
                break;
        }
        ModifyProfileActivity.show(MineProfileActivity.this,
                ModifyProfileActivity.class, bundle, false);
    }


    @OnClick(R.id.riv_avatar)
    void onClick() {
        selectAvatar();
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
                                .start(MineProfileActivity.this);
                    }
                }).show(getSupportFragmentManager(), GalleryFragment.class.getName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /// 收到从Activity传递过来的回调，然后取出其中的值进行图片加载
        // 如果是我能够处理的类型
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            // 通过UCrop得到对应的Uri
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null) {
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Application.Companion.showToast(this, R.string.data_rsp_error_unknown);
        }
    }

    private void loadPortrait(Uri uri) {
        // 得到头像地址
        mAvatarPath = uri.getPath();
        mPresenter.modifyAvatar(mAvatarPath);
    }

    @Override
    protected ModifyProfilePresenter initPresenter() {
        presenter = new ModifyProfilePresenter(this);
        return presenter;
    }

    @Override
    public void modifySuccess() {
        initData();
    }
}
