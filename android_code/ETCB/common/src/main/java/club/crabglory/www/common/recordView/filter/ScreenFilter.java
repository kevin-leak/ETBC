package club.crabglory.www.common.recordView.filter;

import android.content.Context;

import club.crabglory.www.common.R;

/**
 * 负责往屏幕上渲染
 */
public class ScreenFilter extends AbstractFilter {

    public ScreenFilter(Context context) {
        super(context,R.raw.base_vertex, R.raw.base_frag);
    }

}
