package club.crabglory.www.etcb.holders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.utils.DateTimeUtil;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.data.model.db.Goods;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.view.AvatarView;
import club.crabglory.www.factory.contract.GoodsShowContract;

public class GoodsRandomHolder extends RecyclerAdapter.ViewHolder<Goods> {

    private final GoodsShowContract.CheckGoodsListener listener;
    @BindView(R.id.iv_goodsImage)
    AvatarView ivGoodsImage;
    @BindView(R.id.tv_goodsName)
    TextView tvGoodsName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_goodsPrice)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_info)
    TextView tvInfo;

    @BindView(R.id.tv_select_account)
    TextView tvSelectAccount;
    private View root;
    private Goods goods;

    public GoodsRandomHolder(View root, GoodsShowContract.CheckGoodsListener listener,
                             List<CheckBox> checkBoxes) {
        super(root);
        this.root = root;
        this.listener = listener;
        CheckBox box = root.findViewById(R.id.cb_check);
        checkBoxes.add(box);
    }

    @Override
    protected void onBind(Goods goods) {
        this.goods = goods;
        ivGoodsImage.setup(Glide.with(root.getContext()), 0, goods.getBook().getImage());
        tvGoodsName.setText(goods.getBook().getName());
        if (goods.getCreateAt() != null)
            tvTime.setText(DateTimeUtil.getSampleDate(goods.getCreateAt()));
        tvGoodsPrice.setText(String.format("%s$", goods.getBook().getPrice()));
        tvInfo.setText(goods.getBook().getDescription());
        tvSelectAccount.setText(String.format("%s", goods.getCount()));
    }


    @OnClick(R.id.cb_check)
    public void onClick(View view) {
        listener.checkDelete(goods);
    }
}