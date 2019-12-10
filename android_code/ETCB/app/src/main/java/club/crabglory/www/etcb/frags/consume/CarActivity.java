package club.crabglory.www.etcb.frags.consume;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.common.utils.StatusBarUtils;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.book.BooksActivity;
import club.crabglory.www.etcb.frags.book.BooksShopActivity;
import club.crabglory.www.data.db.Goods;

public class CarActivity extends ToolbarActivity {


    @BindView(R.id.rv_goods_list)
    RecyclerView rvGoods;
    private RecyclerAdapter<Goods> goodsAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_record_car;
    }

    @Override
    protected void initWindows() {
        super.initWindows();
        StatusBarUtils.setLightColor(getWindow());
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        goodsRecycle();
    }

    @Override
    protected void initData() {
        super.initData();
        testData();
    }

    private void goodsRecycle() {
        rvGoods.setLayoutManager(new LinearLayoutManager(this));
        goodsAdapter = new RecyclerAdapter<Goods>() {
            @Override
            protected int getItemViewType(int position, Goods goods) {
                return R.layout.holder_car_book;
            }

            @Override
            protected ViewHolder<Goods> onCreateViewHolder(View root, int viewType) {
                return new GoodsHolder(root);
            }
        };
        rvGoods.setAdapter(goodsAdapter);

        goodsAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Goods>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Goods goods) {
                // todo book 信息处理
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", goods);
                BooksActivity.show(CarActivity.this,
                        BooksShopActivity.class, bundle, false);
            }
        });
    }


    @OnClick(R.id.cb_all)
    public void onClick() {
    }

    class GoodsHolder extends RecyclerAdapter.ViewHolder<Goods> {
        @BindView(R.id.cb_check)
        CheckBox checkBox;
        @BindView(R.id.iv_goodsImage)
        ImageView ivGoodsImage;
        @BindView(R.id.tv_goodsName)
        TextView tvGoodsName;
        @BindView(R.id.tv_sort)
        TextView tvSort;
        @BindView(R.id.tv_goodsPrice)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_info)
        TextView tvInfo;

        @BindView(R.id.tv_select_account)
        TextView tvSelectAccount;

        public GoodsHolder(View root) {
            super(root);
        }

        @Override
        protected void onBind(Goods goods) {
            ivGoodsImage.setImageResource(goods.getImage());
            tvGoodsName.setText(goods.getName());
            tvSort.setText(goods.getTYPE());
            tvGoodsPrice.setText(goods.getPrice());
            tvInfo.setText(goods.getDescription());
        }


        @OnClick({R.id.cb_check, R.id.cut_count, R.id.add_count})
        public void onClick(View view) {
            int count = Integer.parseInt((String) tvSelectAccount.getText());
            switch (view.getId()) {
                case R.id.cb_check:
                    checkBox.setActivated(!checkBox.isActivated());
                    break;
                case R.id.cut_count:
                    tvSelectAccount.setText(String.valueOf(count - 1));
                    break;
                case R.id.add_count:
                    tvSelectAccount.setText(String.valueOf(count + 1));
                    break;
            }
        }
    }

    private void testData() {

        ArrayList<Goods> bookList1 = new ArrayList<>();

        Goods goods = new Goods();
        goods.setImage(R.mipmap.test_republic);
        goods.setName("Republic");
        goods.setDescription("Plato (427-347 BC) was a great philosopher of ancient Greece, " +
                "a student of Socrates (469-399 BC), and a teacher of Aristotle (384-322 BC). " +
                "He spent most of his life in Athens, the center of the ancient Greek national " +
                "culture. He loves the motherland and loves philosophy. His highest ideal, " +
                "philosophers should be politicians, politicians should be philosophers. " +
                "The philosopher is not a stalker hiding in the ivory tower. He should apply " +
                "what he has learned and practice. A person with a philosophical mind must " +
                "have a political power and a political person with a philosophical mind.");
        goods.setPrice(70);
        goods.setCheck(true);
        goods.setTYPE("Education");
        goods.setCurrentCount(5);
        bookList1.add(goods);

        Goods goods1 = new Goods();
        goods1.setImage(R.mipmap.test_jane_eyre);
        goods1.setName("Jane Eyre");
        goods1.setDescription("Jane Eyre (Mia Wasikowska), who lost her parents from a young age, " +
                "lived in her aunt's house and was deceived and aunted by her cousin (Craig Roberts).");
        goods1.setPrice(26);
        goods1.setCheck(false);
        goods1.setTYPE("Technology");
        goods1.setCurrentCount(5);
        bookList1.add(goods1);


        goodsAdapter.add(bookList1);
    }
}
