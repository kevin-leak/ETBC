package club.crabglory.www.common.widget.micro.filter;

public class SpeedFilter {

    private static Speed speed = Speed.MODE_NORMAL;

    public static float getSpeed() {
        float ret;
        switch (speed) {
            case MODE_EXTRA_SLOW:
                ret = 0.3f;
                break;
            case MODE_SLOW:
                ret = 0.5f;
                break;
            case MODE_FAST:
                ret = 1.5f;
                break;
            case MODE_EXTRA_FAST:
                ret = 3.0f;
                break;
            default:
                ret = 1.0f;
                break;
        }
        return ret;
    }

    public static void setSpeed(Speed speed) {
        SpeedFilter.speed = speed;
    }

    public enum Speed {
        MODE_EXTRA_SLOW, MODE_SLOW, MODE_NORMAL, MODE_FAST, MODE_EXTRA_FAST
    }
}
