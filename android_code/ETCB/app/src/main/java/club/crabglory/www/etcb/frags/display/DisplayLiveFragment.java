package club.crabglory.www.etcb.frags.display;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import club.crabglory.www.common.basic.view.BaseFragment;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.etcb.R;
import club.crabglory.www.data.model.db.Live;

public class DisplayLiveFragment extends BaseFragment {

    private final String TAG = "DisplayLiveFragment";
    @BindView(R.id.rv_sum)
    RecyclerView rvSum;
    private RecyclerAdapter<Live> sumLive;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_display_live;
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);

        rvSum.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        sumLive = new RecyclerAdapter<Live>() {

            @Override
            protected int getItemViewType(int position, Live live) {
                return R.layout.holder_live;
            }

            @Override
            protected ViewHolder<Live> onCreateViewHolder(View root, int viewType) {
                return new LiveHolder(root);
            }
        };
        rvSum.setAdapter(sumLive);
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
    protected void initData() {
        super.initData();
        testData();
    }

    private void testData() {

        Log.e(TAG, "testData: ");

        ArrayList<Live> lives = new ArrayList<>();

        Live live = new Live();
        live.setTitle("show some");
        live.setSubTitle("Plato (427-347 BC) was a great philosopher of ancient Greece");
        live.setTime("18:00");
        live.setState(true);
        lives.add(live);

        Live live1 = new Live();
        live1.setTitle("have you");
        live1.setSubTitle("come to here to see me , i will let you happy");
        live1.setTime("7:00");
        live1.setState(false);
        lives.add(live1);

        sumLive.add(lives);

    }
}
