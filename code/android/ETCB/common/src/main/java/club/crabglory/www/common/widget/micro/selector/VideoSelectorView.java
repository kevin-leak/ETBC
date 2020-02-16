package club.crabglory.www.common.widget.micro.selector;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

import club.crabglory.www.common.R;
import club.crabglory.www.common.widget.micro.selector.recycler.RecyclerAdapter;


/**
 * 1. 可选项的布局
 * 2. holder的指定
 * 3. 资源加载抽象封装
 */
public class VideoSelectorView extends RecyclerView {

    private Adapter mAdapter = new Adapter();
    private SelectedChangeListener mListener;
    private List<VideoSearcher.Video> mSelectedVideo = new LinkedList<>();
    private static final int MAX_VIDEO_COUNT = 3; // 最大选中图片数量

    public VideoSelectorView(@NonNull Context context) {
        super(context);
        init();
    }

    public VideoSelectorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoSelectorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    /**
     * 用于建立起加载器
     *
     * @param loaderManager 加载管理器
     * @param listener      监听选择的状态
     * @return loader id
     */
    public int setup(LoaderManager loaderManager, SelectedChangeListener listener) {
        this.mListener = listener;
        VideoSearcher instance = VideoSearcher.getInstance(loaderManager);
        return instance.setup(mDataListener, false);
    }

    private VideoSearcher.DataNotifyListener mDataListener = new VideoSearcher.DataNotifyListener() {
        @Override
        public void dataArrivals(List<VideoSearcher.Video> dataList) {
            mAdapter.add(dataList);
//            RecyclerAdapter<VideoSearcher.Video> adapter = mAdapter;
//            List<VideoSearcher.Video> old = adapter.getItems();
//            DiffUtil.Callback callback = new DiffUiDataCallback<>(old, dataList);
//            DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
//            adapter.getItems().clear();
//            adapter.getItems().addAll(dataList);
//            adapter.notifyDataSetChanged();
//            result.dispatchUpdatesTo(adapter);
        }
    };

    void init() {
        setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter(mAdapter);
        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<VideoSearcher.Video>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, VideoSearcher.Video video) {
                mListener.openVideo(video);
            }
        });
    }


    /**
     * 得到选中的图片的全部地址
     *
     * @return 返回一个数组
     */
    public String[] getSelectedPath() {
        String[] paths = new String[mSelectedVideo.size()];
        int index = 0;
        for (VideoSearcher.Video image : mSelectedVideo) {
            paths[index++] = image.path;
        }
        return paths;
    }


    private class ViewHolder extends RecyclerAdapter.ViewHolder<VideoSearcher.Video>
            implements View.OnClickListener {

        private ImageView mPic;
        private CheckBox mSelected;
        private final TextView time;
        private final TextView name;
        private VideoSearcher.Video video;

        public ViewHolder(View itemView) {
            super(itemView);
            mPic = itemView.findViewById(R.id.im_image);
            mSelected = itemView.findViewById(R.id.cb_select);
            name = itemView.findViewById(R.id.tv_name);
            time = itemView.findViewById(R.id.tv_time);
        }

        @Override
        protected void onBind(VideoSearcher.Video video) {
            this.video = video;
            mPic.setImageBitmap(video.getPreview());//对应的ImageView
            mSelected.setChecked(video.isSelect);
            mSelected.setVisibility(VISIBLE);
            mSelected.setOnClickListener(this);
            name.setText(video.name.substring(0, video.name.lastIndexOf(".")));
            time.setText(getTime(video.duration));
        }

        private String getTime(long duration) {

            return duration / (60 * 60 * 60) + ":" + duration / (60 * 60);
        }

        @Override
        public void onClick(View v) {
            onCheck(this, video);
        }
    }


    private void onCheck(RecyclerAdapter.ViewHolder holder, VideoSearcher.Video video) {
        // Cell点击操作，如果说我们的点击是允许的，那么更新对应的Cell的状态
        // 然后更新界面，同理；如果说不能允许点击（已经达到最大的选中数量）那么就不刷新界面
        if (onItemSelectClick(video)) {
            //noinspection unchecked
            holder.updateData(video);
        }
    }

    /**
     * Cell点击的具体逻辑
     *
     * @param video Image
     * @return True，代表我进行了数据更改，你需要刷新；反之不刷新
     */
    private boolean onItemSelectClick(VideoSearcher.Video video) {
        // 是否需要进行刷新
        boolean notifyRefresh;
        if (mSelectedVideo.contains(video)) {
            // 如果之前在那么现在就移除
            mSelectedVideo.remove(video);
            video.isSelect = false;
            // 状态已经改变则需要更新
            notifyRefresh = true;
        } else {
            if (mSelectedVideo.size() >= MAX_VIDEO_COUNT) {
                // 得到提示文字
                String str = getResources().getString(R.string.label_gallery_select_max_size);
                // 格式化填充
                str = String.format(str, MAX_VIDEO_COUNT);
                Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
                notifyRefresh = false;
            } else {
                mSelectedVideo.add(video);
                video.isSelect = true;
                notifyRefresh = true;
            }
        }

        // 如果数据有更改，
        // 那么我们需要通知外面的监听者我们的数据选中改变了
        if (notifyRefresh)
            notifySelectChanged();
        return true;
    }

    /**
     * 通知选中状态改变
     */
    private void notifySelectChanged() {
        // 得到监听者，并判断是否有监听者，然后进行回调数量变化
        SelectedChangeListener listener = mListener;
        if (listener != null) {
            listener.onSelectedCountChanged(mSelectedVideo.size());
        }
    }

    private class Adapter extends RecyclerAdapter<VideoSearcher.Video> {

        @Override
        protected int getItemViewType(int position, VideoSearcher.Video image) {
            return R.layout.micro_video_cell_galley;
        }

        @Override
        protected ViewHolder<VideoSearcher.Video> onCreateViewHolder(View root, int viewType) {
            return new VideoSelectorView.ViewHolder(root);
        }
    }


    public interface SelectedChangeListener {
        void onSelectedCountChanged(int count);

        void openVideo(VideoSearcher.Video video);
    }
}
