package club.crabglory.www.etcb.frags.display;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.BaseFragment;
import club.crabglory.www.common.view.recycler.RecyclerAdapter;
import club.crabglory.www.etcb.R;
import club.crabglory.www.data.db.MicroVideo;

public class DisplayMicroFragment extends BaseFragment {
    @BindView(R.id.rv_sum)
    RecyclerView rvSum;
    @BindView(R.id.mrl_refresh)
    MaterialRefreshLayout mrlRefresh;
    private RecyclerAdapter<MicroVideo> sumVideoAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_display_micro;
    }

    @Override
    protected void initData() {
        super.initData();
        testData();
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);

        mrlRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //下拉刷新...
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //todo
                        mrlRefresh.finishRefresh();
                    }
                }, 1500);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //todo
                        mrlRefresh.finishRefreshLoadMore();
                    }
                }, 1500);
                //上拉加载更多...

            }
        });

        rvSum.setLayoutManager(new GridLayoutManager(this.getActivity(), 4));

        sumVideoAdapter = new RecyclerAdapter<MicroVideo>() {
            @Override
            protected int getItemViewType(int position, MicroVideo video) {
                return R.layout.holder_display_micro;
            }

            @Override
            protected ViewHolder<MicroVideo> onCreateViewHolder(View root, int viewType) {
                return new  VideoHolder(root);
            }
        };
        rvSum.setAdapter(sumVideoAdapter);
    }

    class VideoHolder  extends RecyclerAdapter.ViewHolder<MicroVideo>{
        @BindView(R.id.iv_background)
        ImageView ivBackground;

        public VideoHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(MicroVideo microVideo) {
            // todo  修改Bean
            ivBackground.setImageResource(microVideo.getImg());

        }

        @OnClick(R.id.cl_video)
        public void onClick(View view) {
            // todo 修改传入的信息
            VideoShowActivity.show(DisplayMicroFragment.this.getActivity(), VideoShowActivity.class);
        }

    }

    private void testData() {
        int[] imgs = {R.mipmap.img_video_1, R.mipmap.img_video_2, R.mipmap.img_video_3, R.mipmap.img_video_4, R.mipmap.img_video_5, R.mipmap.img_video_6, R.mipmap.img_video_7, R.mipmap.img_video_8};
        int[] videos = {R.raw.video_1, R.raw.video_2, R.raw.video_3, R.raw.video_4, R.raw.video_5, R.raw.video_6, R.raw.video_7, R.raw.video_8};

        ArrayList<MicroVideo> microVideos = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            MicroVideo microVideo = new MicroVideo();
            microVideo.setImg(imgs[i]);
            microVideo.setVideo(videos[i]);
            microVideos.add(microVideo);
        }
        sumVideoAdapter.add(microVideos);
    }


}
