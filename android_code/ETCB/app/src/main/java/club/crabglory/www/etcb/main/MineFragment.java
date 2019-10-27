package club.crabglory.www.etcb.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.BaseActivity;
import club.crabglory.www.common.basic.BaseFragment;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.BooksActivity;
import club.crabglory.www.etcb.frags.UploadBookActivity;
import club.crabglory.www.etcb.frags.mine.MineDisplayActivity;
import club.crabglory.www.etcb.frags.LiveActivity;
import club.crabglory.www.etcb.frags.MessageActivity;
import club.crabglory.www.etcb.frags.MicroActivity;
import club.crabglory.www.etcb.frags.PayActivity;
import club.crabglory.www.etcb.frags.mine.MineProfileActivity;
import club.crabglory.www.etcb.frags.record.RecordCarActivity;
import club.crabglory.www.etcb.frags.record.RecordCollectActivity;
import club.crabglory.www.etcb.frags.record.RecordForkActivity;
import club.crabglory.www.etcb.frags.SettingsActivity;
import club.crabglory.www.factory.db.Book;

public class MineFragment extends BaseFragment {


    @BindView(R.id.civ_avatar)
    RoundedImageView civAvatar;
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


    @OnClick({R.id.civ_avatar, R.id.rl_user_info,
            R.id.rl_fork, R.id.rl_car, R.id.rl_book, R.id.rl_collect, R.id.iv_settings,
            R.id.cv_pay, R.id.cv_micro, R.id.cv_message, R.id.cv_live, R.id.cv_books})
    public void onClick(View view) {
        FragmentActivity activity = MineFragment.this.getActivity();
        switch (view.getId()) {
            case R.id.civ_avatar:
                MineDisplayActivity.show(activity, MineDisplayActivity.class);
                break;
            case R.id.rl_user_info:
                MineProfileActivity.show(activity, MineProfileActivity.class);
                break;
            case R.id.rl_fork:
                RecordForkActivity.show(activity, RecordForkActivity.class);
                break;
            case R.id.rl_car:
                RecordCarActivity.show(activity, RecordCarActivity.class);
                break;
            case R.id.rl_book:
                Bundle bundle = new Bundle();
                bundle.putInt(Book.TYPE, BooksActivity.RECORD);
                BooksActivity.show((BaseActivity) activity, BooksActivity.class,bundle, false );
                break;
            case R.id.rl_collect:
                RecordCollectActivity.show(activity, RecordCollectActivity.class);
                break;
            case R.id.iv_settings:
                SettingsActivity.show(activity, SettingsActivity.class);
                break;
            case R.id.cv_pay:
                PayActivity.show(activity, PayActivity.class);
                break;
            case R.id.cv_micro:
                MicroActivity.show( activity, MicroActivity.class);
                break;
            case R.id.cv_message:
                MessageActivity.show(activity, MessageActivity.class);
                break;
            case R.id.cv_live:
                LiveActivity.show(activity, LiveActivity.class);
                break;
            case R.id.cv_books:
                UploadBookActivity.show(activity, UploadBookActivity.class);
                break;
        }
    }
}
