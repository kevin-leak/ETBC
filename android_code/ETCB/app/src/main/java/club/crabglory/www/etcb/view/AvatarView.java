package club.crabglory.www.etcb.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

public class AvatarView extends RoundedImageView {
    public AvatarView(Context context) {
        super(context);
    }

    public AvatarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AvatarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean performClick() {
        // todo 是否已经登入???
        Toast.makeText(this.getContext(), "dsafa", Toast.LENGTH_SHORT).show();
        return super.performClick();
    }
}
