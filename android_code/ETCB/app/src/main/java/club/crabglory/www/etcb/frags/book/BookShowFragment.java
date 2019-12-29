package club.crabglory.www.etcb.frags.book;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.cjj.MaterialRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import club.crabglory.www.common.basic.view.BasePresenterFragment;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.holders.BookDailyHolder;
import club.crabglory.www.etcb.holders.BookRandomHolder;
import club.crabglory.www.factory.contract.BookShowContract;
import club.crabglory.www.factory.presenter.book.BookShowPresenter;

public class BookShowFragment extends BasePresenterFragment<BookShowContract.Presenter>
        implements BookShowContract.View {

    @BindView(R.id.rv_sum)
    RecyclerView rvBook;
    private RecyclerAdapter<Book> bookAdapter;
    // AtticFragment中的Daily和Random、BookShowFragment通过新建对象设置type来传递;
    // AtticFragment的四个类别通过调转传值，如果加载AtticFragment中的Random更多，则通过的是传值
    private int type;
    private MaterialRefreshLayout mrlRefresh;

    public BookShowFragment setType(int type) {
        this.type = type;
        return this;
    }

    @Override
    protected void intiArgs(Bundle arguments) {
        super.intiArgs(arguments);
        if (arguments != null)
            type = arguments.getInt(Book.TYPE_KEY);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_books;
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);
        if (Book.TYPE_DAILY == type || Book.TYPE_RANDOM == type) {
            rvBook.setNestedScrollingEnabled(false);
        }
        rvBook.setLayoutManager(type == Book.TYPE_DAILY ?
                new GridLayoutManager(getContext(), 4) :
                new LinearLayoutManager(this.getActivity()));

        bookAdapter = new RecyclerAdapter<Book>() {
            @Override
            protected int getItemViewType(int position, Book book) {
                return type == Book.TYPE_DAILY ?
                        R.layout.holder_daily : R.layout.holder_random;
            }

            @Override
            protected ViewHolder<Book> onCreateViewHolder(View root, int viewType) {
                return type == Book.TYPE_DAILY ?
                        new BookDailyHolder(root) :
                        new BookRandomHolder(root);
            }
        };
        rvBook.setAdapter(bookAdapter);
        bookAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Book>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Book book) {
                Bundle bundle = new Bundle();
                bundle.putString(BooksShopActivity.KEY, book.getId());
                BooksActivity.show(BookShowFragment.this.getActivity(),
                        BooksShopActivity.class, bundle,
                        false);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        //todo load book
        presenter.start();
    }


    public void preRefresh(MaterialRefreshLayout mrlRefresh, boolean isMore) {
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
        mrlRefresh.finishRefresh();
        mrlRefresh.finishRefreshLoadMore();
    }

}
