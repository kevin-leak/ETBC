package club.crabglory.www.etcb.frags.display;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import club.crabglory.www.common.basic.view.BasePresenterFragment;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.etcb.R;
import club.crabglory.www.data.model.db.Live;
import club.crabglory.www.factory.contract.DisplayLiveContract;
import club.crabglory.www.factory.presenter.live.DisplayLivePresenter;

public class DisplayLiveFragment extends BasePresenterFragment<DisplayLiveContract.Presenter>
        implements DisplayLiveContract.View {
    @BindView(R.id.rv_live)
    RecyclerView rvLive;
    private RecyclerAdapter<Live> liveAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_display_live;
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);

        rvLive.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        liveAdapter = new RecyclerAdapter<Live>() {

            @Override
            protected int getItemViewType(int position, Live live) {
                return R.layout.holder_live;
            }

            @Override
            protected ViewHolder<Live> onCreateViewHolder(View root, int viewType) {
                return new LiveHolder(root);
            }
        };
        rvLive.setAdapter(liveAdapter);
    }

    @Override
    public RecyclerAdapter<Live> getRecyclerAdapter() {
        return liveAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        hideDialog();
    }

    @Override
    protected DisplayLiveContract.Presenter initPresent() {
        return new DisplayLivePresenter(this, ((DisplayActivity)this.getActivity()).getUserId());
    }

    class LiveHolder extends RecyclerAdapter.ViewHolder<Live> {
        @BindView(R.id.tv_type)
        TextView tv_time;
        @BindView(R.id.iv_play)
        ImageView iv_play;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_sub_title)
        TextView tv_sub_title;

        public LiveHolder(View root) {
            super(root);
        }

        @Override
        protected void onBind(Live live) {
            if (live.isState()) {
                iv_play.setImageResource(R.drawable.display_play_on);
            }
            tv_title.setText(live.getTitle());
            tv_sub_title.setText(live.getTitle());
            tv_time.setText(live.getTime());
        }
    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        presenter.start();
    }
}
