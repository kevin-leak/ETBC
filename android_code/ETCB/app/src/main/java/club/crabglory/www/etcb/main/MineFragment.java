package club.crabglory.www.etcb.main;

import android.support.v4.app.Fragment;

import club.crabglory.www.common.basic.BaseFragment;
import club.crabglory.www.etcb.R;

public class MineFragment extends BaseFragment {

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
}
