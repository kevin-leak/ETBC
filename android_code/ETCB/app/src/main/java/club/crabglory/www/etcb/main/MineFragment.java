package club.crabglory.www.etcb.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.BaseActivity;
import club.crabglory.www.common.basic.view.BaseFragment;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.book.BooksActivity;
import club.crabglory.www.etcb.frags.book.UploadBookActivity;
import club.crabglory.www.etcb.frags.display.MineDisplayActivity;
import club.crabglory.www.etcb.frags.chat.MessageActivity;
import club.crabglory.www.etcb.frags.MicroUpActivity;
import club.crabglory.www.etcb.frags.consume.PayActivity;
import club.crabglory.www.etcb.frags.display.MineProfileActivity;
import club.crabglory.www.etcb.frags.chat.ForkActivity;
import club.crabglory.www.etcb.frags.consume.CarActivity;
import club.crabglory.www.etcb.frags.SettingsActivity;
import club.crabglory.www.data.db.Book;
import club.crabglory.www.etcb.view.AvatarView;

public class MineFragment extends BaseFragment {


    @BindView(R.id.civ_avatar)
    AvatarView civAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    /**
     * 1. 购物车
     * 2. 支付
     * 3. 录微读
     * 4. 收藏
     * 6. 关注
     * 5. 阅读记录
     * 6. 沟通记录
     * 7. 商品
     * 8. 直播
     */

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_nav_mine;
    }


    @OnClick({R.id.civ_avatar, R.id.rl_profile,
            R.id.rl_fork, R.id.rl_car, R.id.rl_book, R.id.rl_collect, R.id.iv_settings,
            R.id.cv_pay, R.id.cv_micro, R.id.cv_message, R.id.cv_live, R.id.cv_books})
    public void onClick(View view) {
        FragmentActivity activity = MineFragment.this.getActivity();
        switch (view.getId()) {
            case R.id.civ_avatar:
                MineDisplayActivity.show(activity, MineDisplayActivity.class);
                break;
            case R.id.rl_profile:
                MineProfileActivity.show(activity, MineProfileActivity.class);
                break;
            case R.id.rl_fork:
                ForkActivity.show(activity, ForkActivity.class);
                break;
            case R.id.rl_car:
                CarActivity.show(activity, CarActivity.class);
                break;
            case R.id.rl_book:
                Bundle bundle = new Bundle();
                bundle.putInt(Book.TYPE, BooksActivity.RECORD);
                BooksActivity.show((BaseActivity) activity, BooksActivity.class,bundle, false );
                break;
            case R.id.iv_settings:
                SettingsActivity.show(activity, SettingsActivity.class);
                break;
            case R.id.cv_pay:
                PayActivity.show(activity, PayActivity.class);
                break;
            case R.id.cv_micro:
                MicroUpActivity.show( activity, MicroUpActivity.class);
                break;
            case R.id.cv_message:
                MessageActivity.show(activity, MessageActivity.class);
                break;
            case R.id.cv_live:
//                LiveActivity.show(activity, LiveActivity.class);
                break;
            case R.id.cv_books:
                UploadBookActivity.show(activity, UploadBookActivity.class);
                break;
        }
    }
}
