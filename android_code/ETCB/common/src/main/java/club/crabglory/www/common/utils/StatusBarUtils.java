package club.crabglory.www.common.utils;

import android.view.View;
import android.view.Window;

public class StatusBarUtils {

    public static void setDarkColor(Window window){
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public static void setLightColor(Window window){
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }


}
