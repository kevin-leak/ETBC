package club.crabglory.www.etcb.main;

import android.support.v4.app.Fragment;

import club.crabglory.www.common.basic.BaseFragment;
import club.crabglory.www.etcb.R;

public class BookFragment extends BaseFragment {

    /**
     * 1. 搜索
     * 2. 类别
     * 3. 新书阁
     * 4. 百书阁
     * */
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_nav_book;
    }

}
