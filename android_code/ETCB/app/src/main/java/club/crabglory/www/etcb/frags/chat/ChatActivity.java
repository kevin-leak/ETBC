package club.crabglory.www.etcb.frags.chat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.crabglory.www.common.basic.ToolbarActivity;
import club.crabglory.www.common.view.recycler.RecyclerAdapter;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.book.BooksActivity;
import club.crabglory.www.etcb.frags.book.BooksShopActivity;
import club.crabglory.www.etcb.frags.consume.CarActivity;
import club.crabglory.www.factory.db.Chat;
import club.crabglory.www.factory.db.Goods;

public class ChatActivity extends ToolbarActivity {

    @BindView(R.id.rv_container)
    RecyclerView rvContainer;
    @BindView(R.id.btn_face)
    ImageView btnFace;
    @BindView(R.id.btn_record)
    ImageView btnRecord;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.btn_submit)
    ImageView btnSubmit;
    private RecyclerAdapter<Chat> chatAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        chatRecycle();
    }

    private void chatRecycle() {
        rvContainer.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new RecyclerAdapter<Chat>() {
            @Override
            protected int getItemViewType(int position, Chat chat) {
                return R.layout.holder_car_book;
            }

            @Override
            protected ViewHolder<Chat> onCreateViewHolder(View root, int viewType) {
                return new ChatHolder(root);
            }
        };
        rvContainer.setAdapter(chatAdapter);

        chatAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Chat>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Chat chat) {
                // todo  信息处理

            }
        });
    }

    @OnClick({R.id.btn_face, R.id.btn_record, R.id.edit_content, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_face:
                break;
            case R.id.btn_record:
                break;
            case R.id.edit_content:
                break;
            case R.id.btn_submit:
                break;
        }
    }

    class ChatHolder extends RecyclerAdapter.ViewHolder<Chat> {
        public ChatHolder(View root) {
            super(root);
        }

        @Override
        protected void onBind(Chat chat) {

        }
    }
}
