package club.crabglory.www.etcb.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.BasePresenterFragment;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.MicroUpActivity;
import club.crabglory.www.etcb.frags.SettingsActivity;
import club.crabglory.www.etcb.frags.account.AccountActivity;
import club.crabglory.www.etcb.frags.book.BookUpActivity;
import club.crabglory.www.etcb.frags.book.BooksActivity;
import club.crabglory.www.etcb.frags.chat.ForkActivity;
import club.crabglory.www.etcb.frags.chat.MessageActivity;
import club.crabglory.www.etcb.frags.goods.GoodsCarActivity;
import club.crabglory.www.etcb.frags.goods.GoodsActivity;
import club.crabglory.www.etcb.frags.goods.GoodsPayActivity;
import club.crabglory.www.etcb.frags.display.DisplayActivity;
import club.crabglory.www.etcb.frags.display.MineProfileActivity;
import club.crabglory.www.common.widget.AvatarView;
import club.crabglory.www.factory.contract.MineContract;
import club.crabglory.www.factory.presenter.account.MinePresenter;

public class MineFragment extends BasePresenterFragment<MineContract.Presenter>
        implements MineContract.View {


    @BindView(R.id.civ_avatar)
    AvatarView civAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_favorite)
    TextView tvFavorite;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_nav_mine;
    }

    @OnClick({R.id.civ_avatar, R.id.rl_profile,
            R.id.rl_fork, R.id.rl_car, R.id.rl_goods, R.id.rl_collect, R.id.iv_settings,
            R.id.cv_pay, R.id.cv_micro, R.id.cv_message, R.id.cv_live, R.id.cv_books})
    public void onClick(View view) {
        FragmentActivity activity = MineFragment.this.getActivity();
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.civ_avatar:
                // fixme to not let it to open
//                if (!Account.isLogin()) AccountActivity.show(activity, AccountActivity.class);
                bundle.putString(DisplayActivity.KEY, Account.getUserId());
                DisplayActivity.show(activity, DisplayActivity.class, bundle, false);
                break;
            case R.id.rl_profile:
                // fixme to open limit of modify account
                if (!Account.isLogin()) AccountActivity.show(activity, AccountActivity.class);
                MineProfileActivity.show(activity, MineProfileActivity.class);
                break;
            case R.id.rl_fork:
                ForkActivity.show(activity, ForkActivity.class);
                break;
            case R.id.rl_car:
                GoodsCarActivity.show(activity, GoodsCarActivity.class);
                break;
            case R.id.rl_goods:
                bundle.putInt(Book.TYPE_KEY, Book.TYPE_MY_BUY);
                BooksActivity.show(activity, GoodsActivity.class, bundle, false);
                break;
            case R.id.iv_settings:
                SettingsActivity.show(activity, SettingsActivity.class);
                break;
            case R.id.cv_pay:
                GoodsPayActivity.show(activity, GoodsPayActivity.class);
                break;
            case R.id.cv_micro:
                MicroUpActivity.show(activity, MicroUpActivity.class);
                break;
            case R.id.cv_message:
                MessageActivity.show(activity, MessageActivity.class);
                break;
            case R.id.cv_live:
//                LiveActivity.show(activity, LiveActivity.class);
                break;
            case R.id.cv_books:
                BookUpActivity.show(activity, BookUpActivity.class);
                break;
        }
    }


    @Override
    protected void initData() {
        super.initData();
        // 这里表示加载登入信息，并且提醒登入
        presenter.start();
    }

    @Override
    protected MineContract.Presenter initPresent() {
        return new MinePresenter(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void loadUserInfo(User user) {
        civAvatar.setup(Glide.with(this.getActivity()), user);
        tvName.setText(user.getName());
        tvPhone.setText(user.getPhone());
        tvFavorite.setText(user.getFavorite() + "w");
    }
}
