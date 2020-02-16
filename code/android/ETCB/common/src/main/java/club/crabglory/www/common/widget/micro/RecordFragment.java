package club.crabglory.www.common.widget.micro;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import club.crabglory.www.common.R;
import club.crabglory.www.common.widget.micro.filter.SpeedFilter;
import club.crabglory.www.common.widget.micro.selector.GalleryFragment;
import club.crabglory.www.common.widget.micro.selector.VideoSearcher;
import club.crabglory.www.common.widget.micro.selector.recycler.RecyclerAdapter;
import club.crabglory.www.common.widget.micro.widget.CheckImageView;
import club.crabglory.www.common.widget.micro.widget.CircularProgressView;
import club.crabglory.www.common.widget.micro.widget.MicroVideoSurface;


public class RecordFragment extends Fragment implements View.OnClickListener,
        CircularProgressView.OnRecordListener {

    private final String TAG = "RecordFragment";

    private MicroVideoSurface mvuSurface;
    private CircularProgressView mRecordButton;
    private RoundedImageView upVideo;
    private View root;
    private int sumProcess = 90;
    private CountDownTimer timer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_micro_video, container, false);
        initWidget();
        return root;
    }

    private void initWidget() {
        mvuSurface = root.findViewById(R.id.mvu_record);
        mRecordButton = root.findViewById(R.id.mCapture);
        upVideo = root.findViewById(R.id.iv_preview);
        VideoSearcher.getInstance(LoaderManager.getInstance(this))
                .setup(new VideoSearcher.DataNotifyListener() {
                    @Override
                    public void firstData(VideoSearcher.Video video) {
                        upVideo.setImageBitmap(video.getPreview());//对应的ImageView
                    }
                }, true);
        root.findViewById(R.id.finish).setOnClickListener(this);
        root.findViewById(R.id.btn_camera_switch).setOnClickListener(this);
        root.findViewById(R.id.btn_camera_beauty).setOnClickListener(this);
        root.findViewById(R.id.btn_camera_soul).setOnClickListener(this);
        root.findViewById(R.id.btn_camera_speed).setOnClickListener(this);
        root.findViewById(R.id.btn_camera_filter).setOnClickListener(this);
        root.findViewById(R.id.iv_magic).setOnClickListener(this);
        initTimeSelect();
        mvuSurface.setOutlineProvider(new MicroVideoSurface.OutlineProvider(5));
        mRecordButton.setOnRecordListener(this);
        upVideo.setOnClickListener(this);
        mvuSurface.setOnRecordFinishListener(this::prepareForUpVideo);
    }

    private void initTimeSelect() {
        RecyclerView timeDelayList = root.findViewById(R.id.rv_container);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        timeDelayList.setLayoutManager(layoutManager);
        timeDelayList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int position = layoutManager.findLastVisibleItemPosition()
                        % timeDelayAdapter.getItemCount();
                RecyclerAdapter.ViewHolder holder =
                        (RecyclerAdapter.ViewHolder) timeDelayList
                                .findViewHolderForLayoutPosition(position);
                if (holder == null) return;
                TextView timeView = holder.itemView.findViewById(R.id.tv_time);
                sumProcess = Integer.parseInt(timeView.getText().toString());
                changeRecord(sumProcess);
            }
        });
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(timeDelayList);
        ArrayList<Integer> times = new ArrayList<>();
        for (int i = 1; i <= 5; i++)
            times.add(30 * i);
        times.add(0);
        times.add(0);
        Collections.reverse(times);
        timeDelayList.smoothScrollToPosition(times.size());
        timeDelayAdapter.add(times);
        timeDelayList.setAdapter(timeDelayAdapter);
    }

    private void changeRecord(int sumProcess) {
        this.sumProcess = sumProcess;
        if (!mvuSurface.isRecord()) mRecordButton.setTotal(sumProcess);
    }

    private RecyclerAdapter<Integer> timeDelayAdapter = new RecyclerAdapter<Integer>() {
        @Override
        protected int getItemViewType(int position, Integer time) {
            return R.layout.holder_time_delay;
        }

        @Override
        protected RecyclerAdapter.ViewHolder<Integer> onCreateViewHolder(View root, int viewType) {
            return new RecyclerAdapter.ViewHolder<Integer>(root) {
                @Override
                protected void onBind(Integer time) {
                    TextView holder = root.findViewById(R.id.tv_time);
                    if (time == 0) return;
                    holder.setText(String.format("%s", time));
                }
            };
        }
    };


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_camera_switch) {
            mvuSurface.switchCamera();
        } else if (id == R.id.iv_preview) {
            showVideoList();
        } else if (id == R.id.btn_camera_beauty) {
            mvuSurface.enableBeauty(((CheckImageView) v).isChecked());
        } else if (id == R.id.btn_camera_soul) {
            mvuSurface.enableSoul(((CheckImageView) v).isChecked());
        } else if (id == R.id.btn_camera_filter) {
            mvuSurface.enableBigEye(((CheckImageView) v).isChecked());
        } else if (id == R.id.btn_camera_speed) {
        } else if (id == R.id.iv_magic) {
            mvuSurface.enableStick(((CheckImageView) v).isChecked());
        } else if (id == R.id.finish) {
            if (getActivity() != null)
                getActivity().finish();
        }
    }

    private void showVideoList() {
        if (getFragmentManager() == null) return;
        new GalleryFragment()
                .setListener(this::prepareForUpVideo)
                .show(getFragmentManager(), this.getClass().getName());
    }

    private void prepareForUpVideo(String path) {
        // 两种情况
        // 1. 选择列表中选中的video
        // 2. 拍摄的video，直接导入
        Objects.requireNonNull(RecordFragment.this.getActivity()).runOnUiThread(() -> {
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(path);// videoPath 本地视频的路径
            Bitmap bi = media.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            upVideo.setImageBitmap(bi);
            Toast.makeText(RecordFragment.this.getActivity(), path, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onRecordStart() {
        mRecordButton.setTotal(sumProcess);
        Toast.makeText(RecordFragment.this.getActivity(), "开始录制", Toast.LENGTH_SHORT).show();
        mvuSurface.startRecord(SpeedFilter.getSpeed());
        timer = new CountDownTimer(mRecordButton.getTotal() * 1000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                mRecordButton.setProcess(mRecordButton.getTotal() - (int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                cancel();
                mRecordButton.setProcess(0);
                mvuSurface.stopRecord();
            }
        };
        timer.start();
    }

    @Override
    public void onRecordStop() {
        mvuSurface.stopRecord();
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            mvuSurface.stopRecord();
        }
        super.onDestroy();
    }

}
