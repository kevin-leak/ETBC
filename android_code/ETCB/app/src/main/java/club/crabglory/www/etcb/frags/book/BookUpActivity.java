package club.crabglory.www.etcb.frags.book;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.Application;
import club.crabglory.www.common.basic.view.PresentToolActivity;
import club.crabglory.www.common.widget.ImageSelector.GalleryFragment;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.MicroUpActivity;
import club.crabglory.www.factory.contract.BookUpContract;
import club.crabglory.www.factory.presenter.book.BookUpPresenter;

public class BookUpActivity extends PresentToolActivity<BookUpContract.Presenter>
        implements BookUpContract.View {

    @BindView(R.id.sp_category)
    Spinner spCategory;
    @BindView(R.id.ivGoodsImage)
    ImageView ivGoodsImage;
    @BindView(R.id.ibInfoImageAdd)
    ImageButton ibInfoImageAdd;
    @BindView(R.id.et_goods_name)
    EditText etGoodsName;
    @BindView(R.id.et_unit_price)
    EditText etUnitPrice;
    @BindView(R.id.et_quantity)
    EditText etQuantity;
    @BindView(R.id.etGoodsInfo)
    MultiAutoCompleteTextView etGoodsInfo;
    @BindView(R.id.rl_up_video)
    RelativeLayout rlUpVideo;
    @BindView(R.id.et_goods_author)
    EditText etGoodsAuthor;
    private String mAvatarPath = "";
    // todo to add video
    private String mVideoUrl = "";

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_upload_book;
    }

    @Override
    protected void initWindows() {
        super.initWindows();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            spCategory.setPopupBackgroundDrawable(getDrawable(R.drawable.shape_frame));
        }
    }


    @OnClick({R.id.ivGoodsImage, R.id.ibInfoImageAdd, R.id.rl_up_video, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivGoodsImage:
            case R.id.ibInfoImageAdd:
                selectAvatar();
                break;
            case R.id.rl_up_video:
                MicroUpActivity.show(BookUpActivity.this, MicroUpActivity.class);
                break;
            case R.id.btn_commit:
                // 后期录制视频的时候这里需要一个URL传进来
                mPresenter.upBook(mVideoUrl, mAvatarPath, etGoodsName.getText().toString(),
                        etGoodsAuthor.getText().toString(), etQuantity.getText().toString(),
                        etUnitPrice.getText().toString(), etGoodsInfo.getText().toString(),
                        spCategory.getSelectedItemPosition());
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
                                .start(BookUpActivity.this);
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
        Glide.with(BookUpActivity.this)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .into(ivGoodsImage);
        ibInfoImageAdd.setVisibility(View.GONE);
    }

    @Override
    protected BookUpContract.Presenter initPresenter() {
        return new BookUpPresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void upSuccess() {
        spCategory.setSelection(0);
        ivGoodsImage.setBackgroundColor(getResources().getColor(R.color.secondWindowBackgroundColor));
        ivGoodsImage.setBackground(getResources().getDrawable(0));
        ibInfoImageAdd.setVisibility(View.VISIBLE);
        etGoodsName.setText("");
        etUnitPrice.setText("");
        etQuantity.setText("");
        etGoodsInfo.setText("");
        etGoodsAuthor.setText("");
        mAvatarPath = "";
        mVideoUrl = "";
        hideLoading();
        Application.Companion.showToast(this, club.crabglory.www.factory.R.string.up_success);
    }
}
