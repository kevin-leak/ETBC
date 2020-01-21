package club.crabglory.www.common.widget.airpanel;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import club.crabglory.www.common.R;

/**
 * 设置空气面板的长宽高
 */

class AirAttribute {
    int panelMinHeight;
    int panelMaxHeight;

    static AirAttribute obtain(final View view, final AttributeSet attrs,
                               final int defStyleAttr, final int defStyleRes,
                               int[] attrValues, int attrMinIndex, int attrMaxIndex) {
        TypedArray a = view.getContext().obtainStyledAttributes(attrs, attrValues, defStyleAttr, defStyleRes);
        Resources resources = view.getResources();
        int minHeight = a.getDimensionPixelOffset(attrMinIndex,
                resources.getDimensionPixelOffset(R.dimen.airPanelMinHeight));
        int maxHeight = a.getDimensionPixelOffset(attrMaxIndex,
                resources.getDimensionPixelOffset(R.dimen.airPanelMaxHeight));
        a.recycle();
        AirAttribute attribute = new AirAttribute();
        attribute.panelMinHeight = minHeight;
        attribute.panelMaxHeight = maxHeight;
        return attribute;
    }
}
