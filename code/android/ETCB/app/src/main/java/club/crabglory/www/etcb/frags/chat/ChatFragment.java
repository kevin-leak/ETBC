package club.crabglory.www.etcb.frags.chat;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.io.File;
import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.BasePresenterFragment;
import club.crabglory.www.common.widget.AvatarView;
import club.crabglory.www.common.widget.FixLinearLayout;
import club.crabglory.www.common.widget.adapter.TextWatcherAdapter;
import club.crabglory.www.common.widget.airpanel.AirPanel;
import club.crabglory.www.common.widget.airpanel.Util;
import club.crabglory.www.common.widget.record.VoiceImageView;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.data.model.db.Message;
import club.crabglory.www.data.model.db.User;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.data.model.view.MessageViewModel;
import club.crabglory.www.etcb.APP;
import club.crabglory.www.etcb.R;
import club.crabglory.www.factory.contract.ChatContract;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 因为发送消息，不管在group, tag, user,三方面都有共性， 所以先抽象，功能一个个添加
 */
public abstract class ChatFragment<InitModel> extends BasePresenterFragment<ChatContract.Presenter>
        implements ChatContract.View<InitModel>, PanelFragment.PanelCallback {

    final String TAG = getClass().getName();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_face)
    ImageView btnFace;
    @BindView(R.id.btn_record)
    ImageView btnRecord;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.btn_submit)
    ImageView btnSubmit;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.lay_content)
    FixLinearLayout layContent;

    private Adapter mAdapter;
    protected String receiveId;

    private AirPanel.Boss mPanelBoss;
    private PanelFragment mPanelFragment;
    private View currentPanel = null;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void intiArgs(Bundle arguments) {
        if (arguments != null) {
            receiveId = arguments.getString(ChatActivity.KEY_RECEIVE_ID);
        }
        if (TextUtils.isEmpty(receiveId)) receiveId = UUID.randomUUID().toString();
        EasyPermissions.requestPermissions(this, "申请权限", 0,
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO, Manifest.permission.WAKE_LOCK);
        super.intiArgs(arguments);
    }

    @Override
    protected void initWidgets(View root) {
        ViewStub stub = root.findViewById(R.id.view_stub_header);
        stub.setLayoutResource(getHeaderLayoutId());
        stub.inflate();
        // 控件进行绑定
        super.initWidgets(root);
        mToolbar.setNavigationIcon(R.drawable.ic_back);// 设置左上角的返回按钮为实际的返回效果

        // 初始化面板操作
        mPanelBoss = root.findViewById(R.id.lay_content);
        mPanelBoss.setup(new AirPanel.PanelListener() {
            @Override
            public void requestHideSoftKeyboard() {
                // 请求隐藏软键盘
                Util.hideKeyboard(editContent);
            }
        });
        mPanelBoss.setOnStateChangedListener(new AirPanel.OnStateChangedListener() {
            @Override
            public void onPanelStateChanged(boolean isOpen) {
            }

            @Override
            public void onSoftKeyboardStateChanged(boolean isOpen) {
            }
        });
        mPanelFragment = (PanelFragment) getChildFragmentManager().findFragmentById(R.id.airPanelLayout);
        mPanelFragment.setup(this);

        // RecyclerView基本设置
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);

        editContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                btnSubmit.setActivated(!TextUtils.isEmpty(editContent.getText()));
            }
        });
    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        presenter.start();
    }

    protected abstract int getHeaderLayoutId();

    @Override
    public RecyclerAdapter<Message> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @OnClick({R.id.btn_face, R.id.btn_record, R.id.btn_submit})
    public void onClick(View view) {
        if (currentPanel == view && mPanelBoss.isOpen()) {
            mPanelBoss.closePanel();
            return;
        } else mPanelBoss.openPanel();
        switch (view.getId()) {
            case R.id.btn_submit:
                if (btnSubmit.isActivated()) {
                    // todo 发送其他信息的动作
                    if (!mPanelBoss.isOpen()) {
                        String content = editContent.getText().toString();
                        editContent.setText("");
                        presenter.pushText(content);
                    }
                } else {
                    mPanelFragment.openMoreList();
                    break;
                }
            case R.id.btn_face:
                mPanelFragment.openFaceList();
                break;
            case R.id.btn_record:
                mPanelFragment.openSoundList();
                break;
        }
        currentPanel = view;
    }


    @Override
    public EditText getInputEditText() {
        return null;
    }

    @Override
    public void onSendGallery(String[] paths) {

    }

    @Override
    public void onRecordDone(File file, long time) {
        presenter.pushAudio(file.getPath(), time);
    }

    private class Adapter extends RecyclerAdapter<Message> {
        @Override
        protected int getItemViewType(int position, Message msg) {
            //  区分左右消息,用当前用户对 发送者的id进行一个对比就可以知道
//            boolean isRight = Account.getUserId().equals(msg.getSender().getId());
            Log.e("chat", msg.toString());
            // fixme for test
            boolean isRight = msg.getSender() != null && Account.getUserId().equals(msg.getSender().getId());
            switch (msg.getType()) {
                case Message.TYPE_STR: // 文字内容
                    return isRight ? R.layout.message_text_right : R.layout.message_text_left;
                case Message.TYPE_AUDIO: // 语音内容
                    return isRight ? R.layout.message_audio_right : R.layout.message_audio_left;
                case Message.TYPE_PIC: // 图片内容
                    return isRight ? R.layout.message_pic_right : R.layout.message_pic_left;
                default: // 其他内容：文件
                    return isRight ? R.layout.message_text_right : R.layout.message_text_left;
            }
        }

        @Override
        protected ViewHolder<Message> onCreateViewHolder(View root, int viewType) {
            switch (viewType) {
                // 左右都是同一个
                case R.layout.message_text_right:
                case R.layout.message_text_left:
                    return new TextHolder(root);

                case R.layout.message_audio_right:
                case R.layout.message_audio_left:
                    return new AudioHolder(root);

                case R.layout.message_pic_right:
                case R.layout.message_pic_left:
                    return new PicHolder(root);

                // 默认情况下，返回的就是Text类型的Holder进行处理
                // 文件的一些实现
                default:
                    return new TextHolder(root);
            }
        }

    }

    class BaseHolder extends RecyclerAdapter.ViewHolder<Message> {

        @BindView(R.id.av_avatar)
        AvatarView avatar;


        // 这里进行抽象，总体处理时，重新加载的按钮，只有右边有，所以设置可为空
        @Nullable
        @BindView(R.id.iv_reload)
        ImageView reLoad;

        BaseHolder(View root) {

            super(root);
        }

        @Override
        protected void onBind(Message msg) {
            // 设置头像，无论是左边的 界面还是右边的界面，都设置发送者的id
            // 加入是左边的界面，那么一定是别发给我的消息， 则别人是发送者
            // 如果是右边的界面，那么是我发的消息，要再消息栏中显示，那么我是发送者
            // 即使，当前我们要显示的对话条目，都是发送者的条目
            User send = msg.getSender();
            avatar.setup(Glide.with(ChatFragment.this),
                    R.mipmap.avatar, send == null ? null : send.getAvatar());

            // 获取当前消息的发送转态，来确定，是否显示或者取消重新发送图标
            if (reLoad != null) {
                if (msg.getStatus() == Message.STATUS_DONE) {
                    reLoad.setVisibility(View.GONE);
                } else {
                    reLoad.setVisibility(View.VISIBLE);
                }

                reLoad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 重新发送

                        if (reLoad != null && presenter.rePush(mData)) {
                            // 必须是右边的才有可能需要重新发送
                            // 状态改变需要重新刷新界面当前的信息
                            updateData(mData);
                        }
                    }
                });
            }

        }


    }

    class TextHolder extends BaseHolder {

        @BindView(R.id.tv_chat_content)
        TextView mContent;

        public TextHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message msg) {
            super.onBind(msg);

            Spannable spannable = new SpannableString(msg.getContent());

            // todo 表情的解析

            // 把内容设置到布局上
            mContent.setText(spannable);
        }
    }

    // 语音的Holder
    class AudioHolder extends BaseHolder implements VoiceImageView.AudioPlayCallback {
        @BindView(R.id.tv_voice_second)
        TextView mContent;
        @BindView(R.id.viv_voice)
        VoiceImageView voiceImageView;
        private Message message;

        public AudioHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            super.onBind(message);
            this.message = message;             // long 30000
            String attach = TextUtils.isEmpty(message.getAttach()) ? "0" :
                    this.message.getAttach();
            mContent.setText(formatTime(attach));
        }

        @OnClick(R.id.viv_voice)
        void onClick() {
            try {
                voiceImageView.bindAudioSource(this, message.getContent());
            } catch (IOException e) {
                if (ChatFragment.this.getActivity() == null) return;
                APP.Companion.showToast(ChatFragment.this.getActivity(), R.string.error_media);
                this.playDone();
            }
            onPlayStart();
        }

        void onPlayStart() {
            voiceImageView.startPlay();
        }

        void onPlayStop() {
            voiceImageView.stopPlay();
        }

        private String formatTime(String attach) {
            float time;
            try {
                // 毫秒转换为秒
                time = Float.parseFloat(attach) / 1000f;
            } catch (Exception e) {
                time = 0;
            }
            // 12000/1000f = 12.0000000
            // 取整一位小数点 1.234 -> 1.2 1.02 -> 1.0
            String shortTime = String.valueOf(Math.round(time * 10f) / 10f);
            // 1.0 -> 1     1.2000 -> 1.2
            shortTime = shortTime.replaceAll("[.]0+?$|0+?$", "");
            return String.format("%s″", shortTime);
        }

        @Override
        public void playDone() {
            voiceImageView.stopPlay();
        }
    }

    // 图片的Holder
    class PicHolder extends BaseHolder {
        @BindView(R.id.im_image)
        ImageView mContent;


        PicHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message message) {
            super.onBind(message);
            // 当是图片类型的时候，Content中就是具体的地址
            String content = message.getContent();

            Glide.with(ChatFragment.this)
                    .load(content)
                    .fitCenter()
                    .into(mContent);

        }
    }


}
