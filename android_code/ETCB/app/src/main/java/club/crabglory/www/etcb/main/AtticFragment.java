package club.crabglory.www.etcb.main;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.BaseFragment;
import club.crabglory.www.common.widget.AvatarView;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.common.widget.video.FullWindowVideoView;
import club.crabglory.www.common.widget.video.MyLayoutManager;
import club.crabglory.www.common.widget.video.OnViewPagerListener;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.account.AccountActivity;
import club.crabglory.www.data.model.db.MicroVideo;

/**
 * 微读阁
 * 关注，收藏，特效
 * 直播
 * 商品弹出
 */
public class AtticFragment extends BaseFragment {

    final String TAG = "AtticFragment";

    @BindView(R.id.rv_micro)
    RecyclerView rvMicro;
    private RecyclerAdapter<MicroVideo> videoAdapter;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private ArrayList<MicroVideo> microVideos;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_nav_micro;
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);
        MyLayoutManager myLayoutManager = new MyLayoutManager(this.getActivity(),
                OrientationHelper.VERTICAL, false);
        rvMicro.setLayoutManager(myLayoutManager);
        videoAdapter = new RecyclerAdapter<MicroVideo>() {
            @Override
            protected int getItemViewType(int position, MicroVideo video) {
                return R.layout.hodler_video;
            }

            @Override
            protected ViewHolder<MicroVideo> onCreateViewHolder(View root, int viewType) {
                return new VideoHolder(root);
            }
        };
        rvMicro.setAdapter(videoAdapter);
        myLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                Log.e(TAG, "释放位置:" + position + " 下一页:" + isNext);
                int index = 0;
                if (isNext) {
                    index = 0;
                } else {
                    index = 1;
                }
                View itemView = rvMicro.getChildAt(index);
                final VideoView videoView = itemView.findViewById(R.id.video_view);
                final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
                final ImageView imgPlay = itemView.findViewById(R.id.img_play);
                videoView.stopPlayback();
                imgThumb.animate().alpha(1).start();
                imgPlay.animate().alpha(0f).start();
            }

            @Override
            public void onPageSelected(int position, boolean bottom) {
                Log.e(TAG, "选择位置:" + position + " 下一页:" + bottom);
                View itemView = rvMicro.getChildAt(position);
                if (itemView != null) {
                    final FullWindowVideoView videoView = itemView.findViewById(R.id.video_view);
                    videoView.start();
                }

            }
        });
    }


    class VideoHolder extends RecyclerAdapter.ViewHolder<MicroVideo> {

        @BindView(R.id.video_view)
        FullWindowVideoView videoView;
        @BindView(R.id.img_thumb)
        ImageView imgThumb;
        @BindView(R.id.img_play)
        ImageView imgPlay;
        @BindView(R.id.root_view)
        RelativeLayout rootView;
        @BindView(R.id.civ_avatar)
        AvatarView civ_avatar;

        public VideoHolder(View root) {
            super(root);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        protected void onBind(MicroVideo microVideo) {
            Log.e(TAG, "onBind: " + " image and view to load");
            imgThumb.setImageResource(microVideo.getImg());
            civ_avatar.setImageResource(microVideo.getImg());
            videoView.setVideoURI(Uri.parse("android.resource://" +
                    AtticFragment.this.getActivity().getPackageName() + "/" + microVideo.getVideo()));
            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    mp.setLooping(true);
                    imgThumb.animate().alpha(0).setDuration(200).start();
                    return false;
                }
            });

            rootView.setOnClickListener(new View.OnClickListener() {
                boolean isPlaying = true;

                @Override
                public void onClick(View v) {
                    if (videoView.isPlaying()) {
                        imgPlay.animate().alpha(0.7f).start();
                        videoView.pause();
                        isPlaying = false;
                    } else {
                        imgPlay.animate().alpha(0f).start();
                        videoView.start();
                        isPlaying = true;
                    }
                }
            });

            imgPlay.setOnClickListener(new View.OnClickListener() {
                boolean isPlaying = true;

                @Override
                public void onClick(View v) {
                    if (videoView.isPlaying()) {
                        imgPlay.animate().alpha(0.7f).start();
                        videoView.pause();
                        isPlaying = false;
                    } else {
                        imgPlay.animate().alpha(0f).start();
                        videoView.start();
                        isPlaying = true;
                    }
                }
            });
            videoView.start();
        }
        @OnClick(R.id.civ_avatar)
        public void onClick() {
            AccountActivity.show(AtticFragment.this.getActivity(), AccountActivity.class);
        }

    }

    @Override
    protected void initData() {
        super.initData();
        Log.e(TAG, "refreshData: ");
        testData();
    }

    private void testData() {
        int[] imgs = {R.mipmap.img_video_1, R.mipmap.img_video_2, R.mipmap.img_video_3, R.mipmap.img_video_4, R.mipmap.img_video_5, R.mipmap.img_video_6, R.mipmap.img_video_7, R.mipmap.img_video_8};
        int[] videos = {R.raw.video_1, R.raw.video_2, R.raw.video_3, R.raw.video_4, R.raw.video_5, R.raw.video_6, R.raw.video_7, R.raw.video_8};

        microVideos = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            MicroVideo microVideo = new MicroVideo();
            microVideo.setImg(imgs[i]);
            microVideo.setVideo(videos[i]);
            microVideos.add(microVideo);
            Log.e(TAG, "refreshData: ");
        }
        videoAdapter.add(microVideos);
    }



}
