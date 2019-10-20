package club.crabglory.www.etcb.main;

import android.support.v4.app.Fragment;

import club.crabglory.www.common.basic.BaseFragment;
import club.crabglory.www.etcb.R;

public class MicroFragment extends BaseFragment {

    /**
     *
     * 微读阁
     * 关注，收藏，特效
     * 直播
     * 商品弹出
     */

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_nav_micro;
    }
}
