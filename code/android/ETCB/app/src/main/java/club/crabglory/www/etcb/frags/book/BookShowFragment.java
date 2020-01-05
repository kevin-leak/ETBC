package club.crabglory.www.etcb.frags.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjj.MaterialRefreshLayout;

import butterknife.BindView;
import club.crabglory.www.common.basic.view.BasePresenterFragment;
import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.net.MaterialRspModel;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.account.AccountActivity;
import club.crabglory.www.etcb.frags.search.SearchActivity;
import club.crabglory.www.etcb.holders.BookDailyHolder;
import club.crabglory.www.etcb.holders.BookRandomHolder;
import club.crabglory.www.factory.Factory;
import club.crabglory.www.factory.contract.BookShowContract;
import club.crabglory.www.factory.presenter.book.BookShowPresenter;

public class BookShowFragment extends BasePresenterFragment<BookShowContract.Presenter>
        implements BookShowContract.View, SearchActivity.SearchContract {

    @BindView(R.id.rv_book)
    RecyclerView rvBook;
    private RecyclerAdapter<Book> bookAdapter;
    // AtticFragment中的Daily和Random、BookShowFragment通过新建对象设置type来传递;
    // AtticFragment的四个类别通过调转传值，如果加载AtticFragment中的Random更多，则通过的是传值
    private int type;
    private MaterialRefreshLayout mrlRefresh;

    @Override
    public BookShowFragment obtainType(int type) {
        this.type = type;
        return this;
    }

    // 根据类型来初始化toolbar的名字
    private void initToolBar(int type) {
        FragmentActivity activity = getActivity();
        if (activity instanceof ToolbarActivity)
            if (type == MaterialRspModel.TYPE_RANDOM && ((ToolbarActivity) activity).mToolbar != null) {
                ((ToolbarActivity) activity).mToolbar.setTitle("Random");
            } else if (type != MaterialRspModel.TYPE_SEARCH && type != MaterialRspModel.TYPE_MY_UP) {
                int strRes = Book.getTypeString(type);
                if (strRes != -1)
                    ((ToolbarActivity) activity).mToolbar.setTitle(Book.getTypeString(type));
            }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_show_books;
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);
        if (MaterialRspModel.TYPE_DAILY == type || MaterialRspModel.TYPE_RANDOM == type) {
            rvBook.setNestedScrollingEnabled(false);
        }
        rvBook.setLayoutManager(type == MaterialRspModel.TYPE_DAILY ?
                new GridLayoutManager(getContext(), 4) :
                new LinearLayoutManager(this.getActivity()));

        bookAdapter = new RecyclerAdapter<Book>() {
            @Override
            protected int getItemViewType(int position, Book book) {
                return type == MaterialRspModel.TYPE_DAILY ?
                        R.layout.holder_daily : R.layout.holder_random;
            }

            @Override
            protected ViewHolder<Book> onCreateViewHolder(View root, int viewType) {
                return type == MaterialRspModel.TYPE_DAILY ?
                        new BookDailyHolder(root) :
                        new BookRandomHolder(root);
            }
        };
        rvBook.setAdapter(bookAdapter);
        bookAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Book>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Book book) {
                if (!Account.isLogin()) {
                    Intent intent = new Intent(BookShowFragment.this.getActivity(), AccountActivity.class);
                    startActivityForResult(intent, AccountActivity.requestCode);
                    return;
                }
                Bundle bundle = new Bundle();
                if (type == MaterialRspModel.TYPE_MY_UP){
                    // 进入修改界面
                    bundle.putString(BookUpActivity.KEY, book.getId());
                    BooksActivity.show(BookShowFragment.this.getActivity(),
                            BookUpActivity.class, bundle,
                            false);
                }else {
                    bundle.putString(BooksShopActivity.KEY, book.getId());
                    BooksActivity.show(BookShowFragment.this.getActivity(),
                            BooksShopActivity.class, bundle,
                            false);
                }
            }
        });
        initToolBar(type);
    }


    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        presenter.start();
    }


    public void notifyRefresh(MaterialRefreshLayout mrlRefresh, boolean isMore) {
        this.mrlRefresh = mrlRefresh;
        presenter.toRefresh(isMore);
    }

    @Override
    public RecyclerAdapter<Book> getRecyclerAdapter() {
        return bookAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        if (mrlRefresh != null) {
            mrlRefresh.finishRefreshLoadMore();
            mrlRefresh.finishRefresh();
        }
        hideLoading();
    }

    @Override
    protected BookShowPresenter initPresent() {
        return new BookShowPresenter(this, type);
    }

    @Override
    public void showError(int error) {
        super.showError(error);
        if (mrlRefresh == null)  return;
        mrlRefresh.finishRefresh();
        mrlRefresh.finishRefreshLoadMore();
    }

    @Override
    public void toSearch(final String s) {
        Factory.Companion.runOnAsync(new Runnable() {
            @Override
            public void run() {
                presenter.searchBook(s);
            }
        });
    }
}
