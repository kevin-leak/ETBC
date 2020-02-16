package club.crabglory.www.common.widget.micro.selector;


import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import club.crabglory.www.common.Application;

import static club.crabglory.www.common.widget.micro.Constants.getMicroVideoList;

public class VideoSearcher {

    private final LoaderCallback mLoaderCallback;
    private int LOADER_ID = 0x0100;
    private boolean onlyFirst = false;
    private DataNotifyListener mListener;
    private static LoaderManager loaderManager;
    private static VideoSearcher instance;

    private VideoSearcher() {
        mLoaderCallback = new LoaderCallback();
    }

    public static VideoSearcher getInstance(LoaderManager loaderManager) {
        VideoSearcher.loaderManager = loaderManager;
        if (instance == null) instance = new VideoSearcher();
        return instance;
    }

    public int setup(DataNotifyListener listener, boolean onlyFirst) {
        mListener = listener;
        this.onlyFirst = onlyFirst;
        loaderManager.initLoader(LOADER_ID, null, mLoaderCallback);
        File[] microVideoList = getMicroVideoList();
        ArrayList<Video> videos = new ArrayList<>();
        for (File file : microVideoList) {
            Video video = new Video();
            video.name = file.getName();
            video.path = file.getAbsolutePath();
            videos.add(video);
            if (onlyFirst) {
                listener.firstData(video);
                this.onlyFirst = false;
            }
            MediaPlayer player = new MediaPlayer();
            try {
                player.setDataSource(file.getAbsolutePath());  //recordingFilePath（）为音频文件的路径
                player.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
            video.duration = player.getDuration();//获取音频的时间
            player.release();//记得释放资源
        }
        Collections.reverse(videos);
        listener.dataArrivals(videos);
        return LOADER_ID;
    }

    /**
     * 用于实际的数据加载的Loader Callback
     */
    private class LoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {


        private final String[] IMAGE_PROJECTION = new String[]{
                MediaStore.Video.Media._ID, // Id
                MediaStore.Video.Media.DATA, // 视频路径
                MediaStore.Video.Media.DATE_ADDED, // 视频的创建时间ø
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DISPLAY_NAME,
        };

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            // 创建一个Loader
            if (id == LOADER_ID) {
                // 如果是我们的ID则可以进行初始化
                return new CursorLoader(Application.Companion.getInstance(),
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_PROJECTION,
                        null,
                        null,
                        IMAGE_PROJECTION[2] + " DESC"); // 倒序查询
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            // 当Loader加载完成时
            List<Video> videos = new ArrayList<>();
            // 判断是否有数据
            if (data != null) {
                int count = data.getCount();
                if (count > 0) {
                    // 移动游标到开始
                    data.moveToFirst();
                    // 得到对应的列的Index坐标
                    int indexId = data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]);
                    int indexPath = data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]);
                    int indexDate = data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]);
                    int indexDuration = data.getColumnIndex(IMAGE_PROJECTION[3]);
                    int indexName = data.getColumnIndex(IMAGE_PROJECTION[4]);
                    do {
                        // 循环读取，直到没有下一条数据
                        int id = data.getInt(indexId);
                        String path = data.getString(indexPath);
                        long dateTime = data.getLong(indexDate);
                        String name = data.getString(indexName);
                        long duration = data.getLong(indexDuration);
                        // 添加一条新的数据
                        Video video = new Video();
                        video.name = name;
                        video.id = id;
                        video.path = path;
                        video.date = dateTime;
                        video.duration = duration;
                        videos.add(video);
                        if (videos.size() == 1 && mListener != null && onlyFirst) {
                            onlyFirst = false;
                            mListener.firstData(video);
                            Log.e("VideoSearcher", "onLoadFinished: " + "come");
                        }

                    } while (data.moveToNext());
                }
            }
            if (mListener != null) {
                mListener.dataArrivals(videos);
            }
        }


        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            // 当Loader销毁或者重置了, 进行界面清空
            if (mListener != null) {
                mListener.dataArrivals(null);
            }
        }
    }


    public class Video implements DiffUiDataCallback.UiDataDiffer<Video> {
        public int id;
        long duration;
        String name;
        String path;
        long date;
        boolean isSelect;


        /**
         * 为了使得list里面不重复加入当前的image对象,重写，同时为了保持一致，重写hashcode
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Video video = (Video) o;

            return path != null ? path.equals(video.path) : video.path == null;
        }

        @Override
        public int hashCode() {
            return path != null ? path.hashCode() : 0;
        }

        public Bitmap getPreview() {
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(this.path);// videoPath 本地视频的路径
            return media.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        }

        @Override
        public boolean isSame(Video old) {
            return old.path.equals(this.path);
        }

        @Override
        public boolean isUiContentSame(Video old) {
            return old.path.equals(this.path);
        }
    }

    private interface Listener {

        void dataArrivals(List<Video> dataList);

        void firstData(Video video);
    }

    public static class DataNotifyListener implements Listener {
        @Override
        public void dataArrivals(List<Video> dataList) {

        }

        @Override
        public void firstData(Video video) {

        }
    }
}
