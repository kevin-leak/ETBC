package club.crabglory.www.etcb.frags.book;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.PresentToolActivity;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.chat.ChatActivity;
import club.crabglory.www.etcb.frags.display.DisplayActivity;
import club.crabglory.www.etcb.frags.display.VideoShowActivity;
import club.crabglory.www.common.widget.AvatarView;
import club.crabglory.www.factory.contract.BooksShopContract;
import club.crabglory.www.factory.presenter.book.BookShopPresenter;

public class BooksShopActivity extends PresentToolActivity<BooksShopContract.Presenter>
        implements BooksShopContract.View {
    public static final String KEY = "BooksShopActivity";
    @BindView(R.id.et_quantity)
    EditText etQuantity;
    @BindView(R.id.riv_goods)
    AvatarView rivGoods;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.avi_upper)
    AvatarView aviUpper;
    @BindView(R.id.tv_author_name)
    TextView tvAuthorName;
    @BindView(R.id.tv_upper_name)
    TextView tvUpperName;
    @BindView(R.id.iv_background)
    ImageView ivBackground;
    private String goodsId;
    private Book goods;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_shop_books;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        if (bundle != null) {
            goodsId = bundle.getString(BooksShopActivity.KEY);
            return !TextUtils.isEmpty(goodsId);
        }
        return false;
    }

    @Override
    protected void initData() {
        mPresenter.getGoods(goodsId);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        // todo 不显示视频播放按钮
    }

    @Override
    protected boolean needBack() {
        return false;
    }

    @Override
    protected BooksShopContract.Presenter initPresenter() {
        return new BookShopPresenter(this);
    }

    @Override
    public void refreshData(Book book) {
        this.goods = book;
        if (book == null) return;
        Log.e("BooksShopActivity", book.toString());
        rivGoods.setup(Glide.with(this), 0, book.getImage());
        tvTitle.setText(book.getName());
        tvType.setText(book.getTypeString());
        tvCount.setText(String.format("%s", book.getCount()));
        tvDescription.setText(book.getDescription());
        tvUpperName.setText(book.getUpper().getName());
        tvAuthorName.setText(book.getAuthor());
        aviUpper.setup(Glide.with(this), 1, book.getUpper().getAvatar());
    }

    @OnClick({R.id.cv_video_show, R.id.cv_author, R.id.cv_message, R.id.ib_add_car, R.id.btn_pay})
    public void onClick(View view) {
        // fixme to open login limit
        if (!Account.isLogin()) {
            DisplayActivity.show(BooksShopActivity.this, DisplayActivity.class);
        }
        Bundle bundle;
        switch (view.getId()) {
            case R.id.cv_video_show:
                if (goods == null || TextUtils.isEmpty(goods.getVideo())) break;
                bundle = new Bundle();
                bundle.putString(VideoShowActivity.KEY_Books, goodsId);
                VideoShowActivity.show(BooksShopActivity.this,
                        VideoShowActivity.class, bundle, false);
                break;
            case R.id.cv_author:
                bundle = new Bundle();
                bundle.putString(DisplayActivity.KEY, Account.getUserId());
                DisplayActivity.show(BooksShopActivity.this,
                        DisplayActivity.class, bundle, false);
                break;
            case R.id.cv_message:
                if (goods == null || goods.getUpper() == null) break;
                String useID = goods.getUpper().getId();
                bundle = new Bundle();
                bundle.putString(ChatActivity.KEY_CHATER, useID);
                ChatActivity.show(BooksShopActivity.this,
                        ChatActivity.class, bundle, false);
                break;
            case R.id.ib_add_car:
                int sales = Integer.parseInt(etQuantity.getText().toString());
                mPresenter.saveCarGoods(goods, sales);
                break;
            case R.id.btn_pay:
                int s = Integer.parseInt(etQuantity.getText().toString());
                mPresenter.prePay(goods, s);
                break;
        }
    }

    @Override
    public void addCarSuccess() {
        finish();
    }
}
