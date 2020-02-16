package club.crabglory.www.common.widget.micro.carmera;

import android.hardware.Camera;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class CameraConfig {
    public float rate; //宽高比
    public int minPreviewWidth;
    public int minPictureWidth;

    public CameraConfig() {
        /**初始化一个默认的格式大小*/
        this.minPreviewWidth = 640;
        this.minPictureWidth = 480;
        this.rate = 1.7477777600288395333f;
    }

    private Camera.Size getPropPreviewSize(List<Camera.Size> list, float th, int minWidth) {
        Collections.sort(list, sizeComparator);

        int i = 0;
        for (Camera.Size s : list) {
            if ((s.height >= minWidth) && equalRate(s, th)) {
                break;
            }
            i++;
        }
        if (i == list.size()) {
            i = 0;
        }
        return list.get(i);
    }

    private Comparator<Camera.Size> sizeComparator = (lhs, rhs) -> {
        if (lhs.height == rhs.height) {
            return 0;
        } else if (lhs.height > rhs.height) {
            return 1;
        } else {
            return -1;
        }
    };

    private static boolean equalRate(Camera.Size s, float rate) {
        float r = (float) (s.width) / (float) (s.height);
        if (Math.abs(r - rate) <= 0.03) {
            return true;
        } else {
            return false;
        }
    }

    public Camera.Size getPropPreviewSize(Camera.Parameters parameters) {
        return getPropPreviewSize(parameters.getSupportedPreviewSizes(), this.rate, this.minPreviewWidth);
    }
}
