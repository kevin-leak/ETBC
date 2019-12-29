package club.crabglory.www.etcb.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.makeramen.roundedimageview.RoundedImageView;

import club.crabglory.www.common.Common;
import club.crabglory.www.common.basic.model.Author;
import club.crabglory.www.etcb.R;

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

    public void setup(RequestManager manager, Author author) {
        if (author == null)
            return;
        // 进行显示
        setup(manager, author.getAvatar());
    }


    public void setup(RequestManager manager, String url) {
        if (url != null && url.contains("media")) {
            url = url.replace("media/", "");
        }
        url = Common.Constance.BASE_PHONE_UTL + url;
        setup(manager, R.mipmap.avatar, url);
    }


    public void setup(RequestManager manager, int resourceId, String url) {
        if (url == null)
            url = "";
        if (resourceId == 0) resourceId = R.color.image_placeholder;
        if (resourceId == 1) resourceId = R.mipmap.launch_back;
        manager.load(url)
                .placeholder(resourceId)
                .centerCrop()
                .dontAnimate() // CircleImageView 控件中不能使用渐变动画，会导致显示延迟
                .into(this);

    }
}
