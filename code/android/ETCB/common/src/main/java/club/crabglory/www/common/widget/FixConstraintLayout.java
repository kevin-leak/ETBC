package club.crabglory.www.common.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

public class FixConstraintLayout extends ConstraintLayout {
    public FixConstraintLayout(Context context) {
        super(context);
    }

    public FixConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected boolean fitSystemWindows(Rect insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            insets.left = 0;
            insets.top = 0;
            insets.right = 0;
        }
        return super.fitSystemWindows(insets);
    }
}
