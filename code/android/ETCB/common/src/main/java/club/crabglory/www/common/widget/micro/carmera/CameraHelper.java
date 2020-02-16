package club.crabglory.www.common.widget.micro.carmera;

import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;

public class CameraHelper implements Camera.PreviewCallback {

    private static final String TAG = "CameraHelper";
    public static int WIDTH = 640;
    public static int HEIGHT = 480;
    private int mCameraId;
    private Camera mCamera;
    private byte[] buffer;
    private Camera.PreviewCallback mPreviewCallback;
    private SurfaceTexture mSurfaceTexture;

    private CameraConfig mConfig = new CameraConfig();

    public CameraHelper(int cameraId) {
        mCameraId = cameraId;
    }

    public void switchCamera() {
        mCameraId = (mCameraId == 0 ? 1 : 0);
        stopPreview();
        startPreview(mSurfaceTexture);
    }

    public int getCameraId() {
        return mCameraId;
    }

    public void stopPreview() {
        if (mCamera != null) {
            //预览数据回调接口
            mCamera.setPreviewCallback(null);
            //停止预览
            mCamera.stopPreview();
            //释放摄像头
            mCamera.release();
            mCamera = null;
        }
    }

    public void startPreview(SurfaceTexture surfaceTexture) {
        mSurfaceTexture = surfaceTexture;
        try {
            //获得camera对象
            mCamera = Camera.open(mCameraId);
            //配置camera的属性
            Camera.Parameters parameters = mCamera.getParameters();
            //设置预览数据格式为nv21
            parameters.setPreviewFormat(ImageFormat.NV21);
            Camera.Size pSize = mConfig.getPropPreviewSize(parameters);
            //这是摄像头宽、高
            parameters.setPreviewSize(pSize.width, pSize.height);
            WIDTH = pSize.width;
            HEIGHT = pSize.height;
            // 设置摄像头 图像传感器的角度、方向
            mCamera.setParameters(parameters);
            buffer = new byte[WIDTH * HEIGHT * 3 / 2];
            //数据缓存区
            mCamera.addCallbackBuffer(buffer);
            mCamera.setPreviewCallbackWithBuffer(this);
            //设置预览画面
            mCamera.setPreviewTexture(mSurfaceTexture);

            mCamera.startPreview();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




    public void setPreviewCallback(Camera.PreviewCallback previewCallback) {
        mPreviewCallback = previewCallback;
    }


    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        //SurfaceView 直接预览的  会影响到你的预览吗？
        //Thread.sleep(10_000);
        // data数据依然是倒的
        if (null != mPreviewCallback) {
            mPreviewCallback.onPreviewFrame(data, camera);
        }
        camera.addCallbackBuffer(buffer);
    }


}
