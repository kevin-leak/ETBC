package club.crabglory.www.etcb.frags.search;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.net.MaterialRspModel;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.book.BookShowFragment;

public class SearchActivity extends ToolbarActivity {

    @BindView(R.id.sv_Search)
    SearchView svSearch;
    @BindView(R.id.btn_ack)
    TextView btnAck;
    private BookShowFragment booksFragment;
    private SearchContract searchShowView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_search;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initWidget() {
        super.initWidget();
        // 设置取消的图标，换成自己的
        ImageView closeViewIcon = svSearch.findViewById(R.id.search_close_btn);
        closeViewIcon.setImageDrawable(ContextCompat
                .getDrawable(this, R.drawable.ic_cancel_input));
        // 设置下边线
        svSearch.findViewById(R.id.search_plate).setBackground(null);
        svSearch.findViewById(R.id.submit_area).setBackground(null);

        // 设置search空间默认为展开且聚焦
        svSearch.setFocusable(true);
        svSearch.onActionViewExpanded();

        final FragmentManager manager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = manager.beginTransaction();
        //碎片
        BookShowFragment booksFragment = new BookShowFragment();
        booksFragment.obtainType(MaterialRspModel.TYPE_SEARCH);
        //提交事务
        transaction.add(R.id.lay_container, booksFragment).commit();
        searchShowView = booksFragment;

        SearchView.SearchAutoComplete textView = svSearch.findViewById(R.id.search_src_text);
        textView.setTextColor(getResources().getColor(R.color.textSecond));
    }

    @OnClick(R.id.btn_ack)
    public void onClick() {
        String searText = svSearch.getQuery().toString();
        if (!TextUtils.isEmpty(searText))
            searchShowView.toSearch(searText);
    }

    public interface SearchContract {
        void toSearch(String s);
    }
}
