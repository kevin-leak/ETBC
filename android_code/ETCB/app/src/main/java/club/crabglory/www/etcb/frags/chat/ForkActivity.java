package club.crabglory.www.etcb.frags.chat;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.ToolbarActivity;
import club.crabglory.www.common.view.recycler.RecyclerAdapter;
import club.crabglory.www.etcb.R;
import club.crabglory.www.data.db.Follower;

public class ForkActivity extends ToolbarActivity {


    @BindView(R.id.rv_container)
    RecyclerView rvContainer;
    @BindView(R.id.mrl_refresh)
    MaterialRefreshLayout mrlRefresh;
    private RecyclerAdapter<Follower> forkAdapter;

    @Override
    protected int getContentLayoutId() {

        return R.layout.activity_record_fork;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

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

        rvContainer.setLayoutManager(new LinearLayoutManager(this));
        forkAdapter = new RecyclerAdapter<Follower>() {

            @Override
            protected int getItemViewType(int position, Follower follower) {
                return R.layout.holder_forker;
            }

            @Override
            protected ViewHolder<Follower> onCreateViewHolder(View root, int viewType) {
                return new FollowerHolder(root);
            }
        };
        rvContainer.setAdapter(forkAdapter);

        forkAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Follower>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Follower book) {
                // todo 聊天
            }
        });

    }

    class FollowerHolder extends RecyclerAdapter.ViewHolder<Follower> {
        @BindView(R.id.civ_avatar)
        RoundedImageView civAvatar;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_desc)
        TextView txtDesc;
        @BindView(R.id.txt_time)
        TextView txtTime;

        public FollowerHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Follower follower) {
            civAvatar.setImageResource(follower.getImage());
            txtName.setText(follower.getName());
            txtDesc.setText(follower.getDesc());
            txtTime.setText(follower.getTime());
        }

        @OnClick(R.id.fl_chat_enter)
        public void onClick() {
            ChatActivity.show(ForkActivity.this, ChatActivity.class);
        }
    }

    @Override
    protected void initData() {
        super.initData();

        testData();
    }

    private void testData() {
        ArrayList<Follower> followers = new ArrayList<>();
        Follower follower = new Follower();
        follower.setImage(R.mipmap.test_jane_eyre);
        follower.setName("kevin");
        follower.setDesc("hello i m kevin");
        follower.setTime("2019-08-06");
        followers.add(follower);
        Follower follower1 = new Follower();
        follower1.setImage(R.mipmap.avatar);
        follower1.setName("leak");
        follower1.setDesc("hello i m leak");
        follower1.setTime("2019-08-08");
        followers.add(follower1);
        forkAdapter.add(followers);
    }
}
