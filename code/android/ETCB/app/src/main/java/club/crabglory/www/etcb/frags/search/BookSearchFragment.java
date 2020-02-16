package club.crabglory.www.etcb.frags.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import club.crabglory.www.common.basic.view.BasePresenterFragment;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.account.AccountActivity;
import club.crabglory.www.etcb.frags.book.BooksActivity;
import club.crabglory.www.etcb.frags.book.BooksShopActivity;
import club.crabglory.www.etcb.holders.BookRandomHolder;
import club.crabglory.www.factory.contract.BookSearchContract;

public class BookSearchFragment extends BasePresenterFragment<BookSearchContract.Presenter>
        implements BookSearchContract.View, SearchActivity.SearchContract {
    @BindView(R.id.rv_book)
    RecyclerView rvBook;
    private RecyclerAdapter<Book> bookAdapter;

    @Override
    public void toSearch(String s) {
        presenter.search(s);
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);

        rvBook.setLayoutManager(
                new LinearLayoutManager(this.getActivity()));

        bookAdapter = new RecyclerAdapter<Book>() {
            @Override
            protected int getItemViewType(int position, Book book) {
                return R.layout.holder_random;
            }

            @Override
            protected ViewHolder<Book> onCreateViewHolder(View root, int viewType) {
                return new BookRandomHolder(root);
            }
        };
        rvBook.setAdapter(bookAdapter);
        bookAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Book>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Book book) {
                if (!Account.isLogin()) {
                    Intent intent = new Intent(BookSearchFragment.this.getActivity(), AccountActivity.class);
                    startActivityForResult(intent, AccountActivity.requestCode);
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(BooksShopActivity.KEY, book.getId());
                BooksActivity.show(BookSearchFragment.this.getActivity(),
                        BooksShopActivity.class, bundle,
                        false);
            }
        });
    }

    @Override
    public RecyclerAdapter<Book> getRecyclerAdapter() {
        return bookAdapter;
    }

    @Override
    public void onAdapterDataChanged() {

    }

    @Override
    protected BookSearchContract.Presenter initPresent() {
        return null;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_show_books;
    }
}
