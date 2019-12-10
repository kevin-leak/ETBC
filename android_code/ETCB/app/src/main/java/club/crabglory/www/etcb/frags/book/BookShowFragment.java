package club.crabglory.www.etcb.frags.book;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import club.crabglory.www.common.basic.view.BaseActivity;
import club.crabglory.www.common.basic.view.BasePresenterFragment;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.holders.DailyHolder;
import club.crabglory.www.etcb.holders.RandomHolder;
import club.crabglory.www.data.db.Book;
import club.crabglory.www.factory.contract.BookShowContract;
import club.crabglory.www.factory.presenter.book.BookShowPresenter;

public class BookShowFragment extends BasePresenterFragment<BookShowContract.Presenter>
        implements BookShowContract.View{

    @BindView(R.id.rv_sum)
    RecyclerView rvBook;
    private RecyclerAdapter<Book> bookAdapter;
    public static final int TYPE_DAILY = 100;
    public static final int TYPE_RANDOM = 101;
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_display_books;
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);
        if (BookShowFragment.TYPE_DAILY == type || BookShowFragment.TYPE_RANDOM == type) {
            rvBook.setNestedScrollingEnabled(false);
        }
        rvBook.setLayoutManager(type == BookShowFragment.TYPE_DAILY ?
                new GridLayoutManager(getContext(), 4) :
                new LinearLayoutManager(this.getActivity()));
        bookAdapter = new RecyclerAdapter<Book>() {
            @Override
            protected int getItemViewType(int position, Book event) {
                return type == BookShowFragment.TYPE_DAILY ?
                        R.layout.holder_daily : R.layout.holder_random;
            }
            @Override
            protected ViewHolder<Book> onCreateViewHolder(View root, int viewType) {
                return type == BookShowFragment.TYPE_DAILY ?
                        new DailyHolder(root) :
                        new RandomHolder(root);
            }
        };
        rvBook.setAdapter(bookAdapter);
        bookAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Book>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Book book) {
                // todo book 信息处理
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", book);
                BooksActivity.show((BaseActivity) BookShowFragment.this.getActivity(),
                        BooksShopActivity.class, bundle,
                        false);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();

        testDataRandom();
    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        presenter.start();
    }

    private void testDataRandom() {

        ArrayList<Book> bookList1 = new ArrayList<>();
        ArrayList<Book> bookList2 = new ArrayList<>();

        Book book = new Book();
        book.setImage(R.mipmap.test_republic);
        book.setName("Republic");
        book.setUpper("Jerome");
        book.setDescription("Plato (427-347 BC) was a great philosopher of ancient Greece, " +
                "a student of Socrates (469-399 BC), and a teacher of Aristotle (384-322 BC). " +
                "He spent most of his life in Athens, the center of the ancient Greek national " +
                "culture. He loves the motherland and loves philosophy. His highest ideal, " +
                "philosophers should be politicians, politicians should be philosophers. " +
                "The philosopher is not a stalker hiding in the ivory tower. He should apply " +
                "what he has learned and practice. A person with a philosophical mind must " +
                "have a political power and a political person with a philosophical mind.");
        book.setPrice(70);
        bookList2.add(book);
        bookList1.add(book);

        Book book1 = new Book();
        book1.setImage(R.mipmap.test_jane_eyre);
        book1.setName("Jane Eyre");
        book1.setDescription("Jane Eyre (Mia Wasikowska), who lost her parents from a young age, " +
                "lived in her aunt's house and was deceived and aunted by her cousin (Craig Roberts).");
        book1.setPrice(26);
        bookList2.add(book1);
        bookList1.add(book1);

        Book book2 = new Book();
        book2.setImage(R.mipmap.test_effective_manager);
        book2.setName("Effective Manager");
        book2.setPrice(45);
        book2.setDescription("Please update ISBN's on OBC and imprint pages: " +
                "C 978-0-7619-5110-0 P 978-0-7619-5111-7");
        bookList2.add(book1);
        bookList1.add(book2);

        Book book3 = new Book();
        book3.setImage(R.mipmap.test_psychoanalysis);
        book3.setName("Psychoanalysis");
        book3.setPrice(256);
        book3.setDescription("When this best-seller was published");
        bookList2.add(book3);
        bookList1.add(book3);


        Book book4 = new Book();
        book4.setImage(R.mipmap.test_pride_and_prejudice);
        book4.setName("Pride and Prejudice");
        book4.setDescription("This book describes the arrogant single young Darcy and the " +
                "second lady of the prejudice, Elizabeth, the wealthy single noble Bingley " +
                "and the singer's great lady Ji Ying, fully expressing the author's own view " +
                "of marriage, paying attention to economic interests to love and The novel has" +
                " been adapted into films and TV series many times. The impact of marriage." +
                " The plot of the novel is full of comics, humorous language, " +
                "and is in Austin's novels.");
        book4.setUpper("leak");
        book4.setPrice(99.5);
        bookList1.add(book4);

        Book book5 = new Book();
        book5.setImage(R.mipmap.test_harry_potter);
        book5.setName("Harry Potter");
        book5.setDescription("J. K. Rowling (1965- ), a British female writer who loved writing " +
                "since childhood and worked as a teacher and secretary for a short time. " +
                "At the age of twenty-four, she was born with the idea of creating a series " +
                "of \"Harry Potter\" novels on her train journey to London. Seven years later, " +
                "\"Harry Potter and the Sorcerer's Stone\" came out, after which she successively" +
                " created \"Harry Potter and the Chamber of Secrets\", " +
                "\"Harry Potter and the Prisoner of Azkaban\", \"Harry Potter and Flame Cup");
        book5.setUpper("jack");
        book5.setPrice(7);
        bookList1.add(book5);

        if (type != 100) bookAdapter.add(bookList1);
        else bookAdapter.add(bookList2);
    }

    public void refresh() {
        // todo
    }

    @Override
    public RecyclerAdapter<Book> getRecyclerAdapter() {
        return bookAdapter;
    }

    @Override
    public void onAdapterDataChanged() {

    }

    @Override
    protected BookShowContract.Presenter initPresent() {
        return new BookShowPresenter(this);
    }
}
