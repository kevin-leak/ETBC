package club.crabglory.www.common.widget.micro.filter;

import android.content.Context;

import club.crabglory.www.common.R;
import club.crabglory.www.common.widget.micro.AbstractFilter;

/**
 * 负责往屏幕上渲染
 */
public class ScreenFilter extends AbstractFilter {

    public ScreenFilter(Context context) {
        super(context, R.raw.base_vertex,R.raw.base_frag);
    }

}
