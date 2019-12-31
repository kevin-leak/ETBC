package club.crabglory.www.etcb.frags.display;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.BaseFragment;
import club.crabglory.www.common.basic.view.BasePresenterFragment;
import club.crabglory.www.common.widget.AvatarView;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.data.model.db.Micro;
import club.crabglory.www.data.model.view.MicroViewModel;
import club.crabglory.www.etcb.R;
import club.crabglory.www.factory.contract.DisplayMicroContract;
import club.crabglory.www.factory.presenter.micro.DisplayMicroPresenter;

public class DisplayMicroFragment extends BasePresenterFragment<DisplayMicroContract.Presenter>
        implements DisplayMicroContract.View {
    @BindView(R.id.rv_sum)
    RecyclerView rvSum;
    private RecyclerAdapter<MicroViewModel> sumVideoAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_display_micro;
    }


    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);
        rvSum.setLayoutManager(new GridLayoutManager(this.getActivity(), 4));
        sumVideoAdapter = new RecyclerAdapter<MicroViewModel>() {
            @Override
            protected int getItemViewType(int position, MicroViewModel video) {
                return R.layout.holder_display_micro;
            }

            @Override
            protected ViewHolder<MicroViewModel> onCreateViewHolder(View root, int viewType) {
                return new VideoHolder(root);
            }
        };
        sumVideoAdapter.setListener(new RecyclerAdapter.AdapterListener<MicroViewModel>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, MicroViewModel microViewModel) {
                Bundle bundle = new Bundle();
                bundle.putInt(VideoShowActivity.KEY_TYPE, VideoShowActivity.TYPE_PERSON);
                bundle.putString(VideoShowActivity.KEY_TYPE, microViewModel.getId());
                VideoShowActivity.show(DisplayMicroFragment.this.getActivity(),
                        VideoShowActivity.class, bundle, false);
            }

            @Override
            public void onItemLongClick(RecyclerAdapter.ViewHolder holder, MicroViewModel microViewModel) {

            }
        });

        rvSum.setAdapter(sumVideoAdapter);
    }

    @Override
    public RecyclerAdapter<MicroViewModel> getRecyclerAdapter() {
        return sumVideoAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        hideDialog();
    }

    @Override
    protected DisplayMicroContract.Presenter initPresent() {
        return new DisplayMicroPresenter(this, ((DisplayActivity)this.getActivity()).getUserId());
    }

    class VideoHolder extends RecyclerAdapter.ViewHolder<MicroViewModel> {
        @BindView(R.id.avi_micro)
        AvatarView ivBackground;

        public VideoHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(MicroViewModel microVideo) {
            ivBackground.setup(Glide.with(DisplayMicroFragment.this), 0,
                    microVideo.getAvatarUrl());
        }
    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        presenter.start();
    }
}
