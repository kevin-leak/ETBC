package club.crabglory.www.etcb.holders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.data.model.db.Goods;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.view.AvatarView;
import club.crabglory.www.factory.contract.GoodsCarContract;

public class GoodsCarHolder extends RecyclerAdapter.ViewHolder<Goods> {

    @BindView(R.id.cb_check)
    CheckBox checkBox;
    @BindView(R.id.iv_goodsImage)
    AvatarView ivGoodsImage;
    @BindView(R.id.tv_goodsName)
    TextView tvGoodsName;
    @BindView(R.id.tv_type)
    TextView tvSort;
    @BindView(R.id.tv_goodsPrice)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_info)
    TextView tvInfo;

    @BindView(R.id.tv_select_account)
    TextView tvSelectAccount;
    private Goods goods;
    private View root;
    private GoodsCarContract.CheckGoodsListener listener;

    public GoodsCarHolder(View root, GoodsCarContract.CheckGoodsListener listener,
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
        tvSort.setText(goods.getBook().getTypeString());
        tvGoodsPrice.setText(String.format("%s$", goods.getBook().getPrice()));
        tvInfo.setText(goods.getBook().getDescription());
        tvSelectAccount.setText(String.format("%s", goods.getCount()));
    }


    @OnClick({R.id.cb_check, R.id.cut_count, R.id.add_count})
    public void onClick(View view) {
        /*
         * 有两个维度：
         * 1) 选择或取消
         * 2) 增加或减少
         * 只有，选择了的时候需要作出操作，在选择内作出操作。
         * 1. 取消：idAddNew：false， addSum ： 0
         * 2. 选择：需要添加Sum，idAddNew：true
         * 3. 选择内减小：idAddNew：false, addSum: -1
         * 4. 选择内增加：idAddNew：false，addSum: 1
         * 5. 取消内减小：不调用
         * 6. 取消内减小：不调用
         * */
        int count = Integer.parseInt((String) tvSelectAccount.getText());
        switch (view.getId()) {
            case R.id.cb_check:
                float add = goods.getBook().getPrice() * goods.getCount();
                listener.checkBuy(goods, checkBox.isChecked() ? add : 0, checkBox.isChecked());
                break;
            case R.id.cut_count:
                if (goods.getCount() <= 1) return;
                goods.setCount(goods.getCount() - 1);
                tvSelectAccount.setText(String.valueOf(count - 1));
                if (checkBox.isChecked())
                    listener.checkBuy(goods, -1, false);
                break;
            case R.id.add_count:
                if (goods.getCount() >= goods.getBook().getCount()) return;
                goods.setCount(goods.getCount() + 1);
                tvSelectAccount.setText(String.valueOf(count + 1));
                if (checkBox.isChecked())
                    listener.checkBuy(goods, 1, false);
                break;
        }
    }
}
