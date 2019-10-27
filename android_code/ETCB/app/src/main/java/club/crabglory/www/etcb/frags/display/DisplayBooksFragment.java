package club.crabglory.www.etcb.frags.display;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import club.crabglory.www.common.basic.BaseActivity;
import club.crabglory.www.common.basic.BaseFragment;
import club.crabglory.www.common.view.recycler.RecyclerAdapter;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.BooksActivity;
import club.crabglory.www.etcb.frags.BooksShopActivity;
import club.crabglory.www.etcb.holders.RandomHolder;
import club.crabglory.www.factory.db.Book;

public class DisplayBooksFragment extends BaseFragment {

    @BindView(R.id.rv_sum)
    RecyclerView rvSumBook;
    @BindView(R.id.mrl_refresh)
    MaterialRefreshLayout mrlRefresh;
    private RecyclerAdapter<Book> sumBookAdapter;
    private int type;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_display_books;
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);

        mrlRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //下拉刷新...
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //todo
                        mrlRefresh.finishRefresh();
                    }
                }, 1500);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //todo
                        mrlRefresh.finishRefreshLoadMore();
                    }
                }, 1500);
                //上拉加载更多...

            }
        });

        rvSumBook.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        sumBookAdapter = new RecyclerAdapter<Book>() {
            @Override
            protected int getItemViewType(int position, Book event) {
                return R.layout.holder_random;
            }

            @Override
            protected ViewHolder<Book> onCreateViewHolder(View root, int viewType) {
                return new RandomHolder(root);
            }
        };
        rvSumBook.setAdapter(sumBookAdapter);

        sumBookAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Book>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Book book) {
                // todo book 信息处理
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", book);
                BooksActivity.show((BaseActivity) DisplayBooksFragment.this.getActivity(),
                        BooksShopActivity.class, bundle,
                        false);
            }
        });

        if (BooksActivity.RANDOM == type){
            rvSumBook.setNestedScrollingEnabled(false);
        }
    }

    @Override
    protected void initData() {
        super.initData();

        testDataRandom();
    }

    private void testDataRandom() {

        ArrayList<Book> bookList1 = new ArrayList<>();

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
        bookList1.add(book);

        Book book1 = new Book();
        book1.setImage(R.mipmap.test_jane_eyre);
        book1.setName("Jane Eyre");
        book1.setDescription("Jane Eyre (Mia Wasikowska), who lost her parents from a young age, " +
                "lived in her aunt's house and was deceived and aunted by her cousin (Craig Roberts).");
        book1.setPrice(26);
        bookList1.add(book1);

        Book book2 = new Book();
        book2.setImage(R.mipmap.test_effective_manager);
        book2.setName("Effective Manager");
        book2.setPrice(45);
        book2.setDescription("Please update ISBN's on OBC and imprint pages: " +
                "C 978-0-7619-5110-0 P 978-0-7619-5111-7");
        bookList1.add(book2);

        Book book3 = new Book();
        book3.setImage(R.mipmap.test_psychoanalysis);
        book3.setName("Psychoanalysis");
        book3.setPrice(256);
        book3.setDescription("When this best-seller was published");
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


        sumBookAdapter.add(bookList1);
    }


    public void setType(int type) {
        this.type = type;
    }
}
