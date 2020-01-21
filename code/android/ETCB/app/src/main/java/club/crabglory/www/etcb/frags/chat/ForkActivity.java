package club.crabglory.www.etcb.frags.chat;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.etcb.R;
import club.crabglory.www.data.model.view.FollowerModelView;

/**
 * 好友列表
 */
public class ForkActivity extends ToolbarActivity {


    @BindView(R.id.rv_container)
    RecyclerView rvContainer;
    @BindView(R.id.mrl_refresh)
    MaterialRefreshLayout mrlRefresh;
    private RecyclerAdapter<FollowerModelView> forkAdapter;

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
        forkAdapter = new RecyclerAdapter<FollowerModelView>() {

            @Override
            protected int getItemViewType(int position, FollowerModelView followerModelView) {
                return R.layout.holder_forker;
            }

            @Override
            protected ViewHolder<FollowerModelView> onCreateViewHolder(View root, int viewType) {
                return new FollowerHolder(root);
            }
        };
        rvContainer.setAdapter(forkAdapter);

        forkAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<FollowerModelView>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, FollowerModelView model) {
                // fixme
                ChatActivity.showUserChat(ForkActivity.this, model.getID());
            }
        });

    }

    class FollowerHolder extends RecyclerAdapter.ViewHolder<FollowerModelView> {
        @BindView(R.id.civ_avatar)
        RoundedImageView civAvatar;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_desc)
        TextView txtDesc;
        @BindView(R.id.txt_time)
        TextView txtTime;

        FollowerHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(FollowerModelView followerModelView) {
            civAvatar.setImageResource(followerModelView.getImage());
            txtName.setText(followerModelView.getName());
            txtDesc.setText(followerModelView.getDesc());
            txtTime.setText(followerModelView.getTime());
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
        ArrayList<FollowerModelView> followerModelViews = new ArrayList<>();
        FollowerModelView followerModelView = new FollowerModelView();
        followerModelView.setId(UUID.randomUUID().toString());
        followerModelView.setImage(R.mipmap.test_jane_eyre);
        followerModelView.setName("kevin");
        followerModelView.setDesc("hello i m kevin");
        followerModelView.setTime("2019-08-06");
        followerModelViews.add(followerModelView);
        FollowerModelView followerModelView1 = new FollowerModelView();
        followerModelView1.setId(UUID.randomUUID().toString());
        followerModelView1.setImage(R.mipmap.avatar);
        followerModelView1.setName("leak");
        followerModelView1.setDesc("hello i m leak");
        followerModelView1.setTime("2019-08-08");
        followerModelViews.add(followerModelView1);
        forkAdapter.add(followerModelViews);
    }
}
