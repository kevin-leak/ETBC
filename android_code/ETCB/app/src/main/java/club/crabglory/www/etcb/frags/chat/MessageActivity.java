package club.crabglory.www.etcb.frags.chat;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.etcb.R;
import club.crabglory.www.data.model.db.Message;

public class MessageActivity extends ToolbarActivity {


    @BindView(R.id.rv_container)
    RecyclerView rvContainer;
    @BindView(R.id.mrl_refresh)
    MaterialRefreshLayout mrlRefresh;
    private RecyclerAdapter<Message> messageAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_mine_message;
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
        messageAdapter = new RecyclerAdapter<Message>() {

            @Override
            protected int getItemViewType(int position, Message message) {
                return R.layout.holder_chat_list;
            }

            @Override
            protected ViewHolder<Message> onCreateViewHolder(View root, int viewType) {
                return new MessageHolder(root);
            }
        };
        rvContainer.setAdapter(messageAdapter);

        messageAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Message>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Message message) {
                // todo 聊天
            }
        });

    }


    class MessageHolder extends RecyclerAdapter.ViewHolder<Message> {
        @BindView(R.id.civ_avatar)
        RoundedImageView civAvatar;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_content)
        TextView txtContent;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.btn_chat_count)
        Button btnChatCount;

        public MessageHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            civAvatar.setImageResource(message.getImage());
            txtName.setText(message.getName());
            txtContent.setText(message.getContent());
            txtTime.setText(message.getTime());
            btnChatCount.setText(message.getMsgCount()+"");
        }

        @OnClick(R.id.fl_chat_enter)
        public void onClick() {
            ChatActivity.show(MessageActivity.this, ChatActivity.class);
        }
    }

    @Override
    protected void initData() {
        super.initData();

        testData();
    }

    private void testData() {
        ArrayList<Message> messages = new ArrayList<>();
        Message message = new Message();
        message.setImage(R.mipmap.test_jane_eyre);
        message.setName("kevin");
        message.setContent("come to play ball");
        message.setTime("2019-08-06");
        message.setMsgCount(2);
        messages.add(message);
        Message message1 = new Message();
        message1.setImage(R.mipmap.avatar);
        message1.setName("leak");
        message1.setContent("how do you do");
        message1.setTime("2019-08-08");
        message1.setMsgCount(0);
        messages.add(message1);
        messageAdapter.add(messages);
    }
}
