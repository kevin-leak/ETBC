package club.crabglory.www.etcb.main;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import club.crabglory.www.common.basic.BaseActivity;
import club.crabglory.www.common.basic.BaseFragment;
import club.crabglory.www.common.view.recycler.RecyclerAdapter;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.frags.book.BooksActivity;
import club.crabglory.www.etcb.frags.book.BooksShopActivity;
import club.crabglory.www.etcb.frags.SearchActivity;
import club.crabglory.www.etcb.holders.RandomHolder;
import club.crabglory.www.data.db.Book;

public class BookFragment extends BaseFragment {

    private final String TAG = "BaseFragment";

    @BindView(R.id.rv_daily)
    RecyclerView rvDaily;
    @BindView(R.id.rv_random)
    RecyclerView rvRandom;
    @BindView(R.id.mrl_refresh)
    MaterialRefreshLayout mrlRefresh;
    @BindView(R.id.sv_Search)
    SearchView svSearch;
    @BindView(R.id.ll_select)
    LinearLayout llSelect;
    @BindView(R.id.rl_random)
    RelativeLayout rlRandom;
    private RecyclerAdapter<Book> dailyAdapter;
    private RecyclerAdapter<Book> randomAdapter;

    /**
     * 1. 搜索
     * 2. 类别
     * 3. 新书阁
     * 4. 百书阁
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_nav_book;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);
        rvDaily.setNestedScrollingEnabled(false);
        rvRandom.setNestedScrollingEnabled(false);

        for (int i = 0; i < 4; i++) {
            final Bundle bundle = new Bundle();
            bundle.putInt(Book.TYPE, i);
            llSelect.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick: ");
                    BooksActivity.show((BaseActivity) BookFragment.this.getActivity(),
                            BooksActivity.class, bundle, false);
                }
            });
        }

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

        rlRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Bundle bundle = new Bundle();
                bundle.putInt(Book.TYPE, BooksActivity.RANDOM);
                BooksActivity.show((BaseActivity) BookFragment.this.getActivity(),
                        BooksActivity.class, bundle, false);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();

        // todo
        dailyRecycle();

        randomRecycle();
        testDataRandom();

    }

    private void testDataRandom() {

        ArrayList<Book> bookList = new ArrayList<>();
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
        bookList.add(book);
        bookList1.add(book);

        Book book1 = new Book();
        book1.setImage(R.mipmap.test_jane_eyre);
        book1.setName("Jane Eyre");
        book1.setDescription("Jane Eyre (Mia Wasikowska), who lost her parents from a young age, " +
                "lived in her aunt's house and was deceived and aunted by her cousin (Craig Roberts).");
        book1.setPrice(26);
        bookList.add(book1);
        bookList1.add(book1);

        Book book2 = new Book();
        book2.setImage(R.mipmap.test_effective_manager);
        book2.setName("Effective Manager");
        book2.setPrice(45);
        book2.setDescription("Please update ISBN's on OBC and imprint pages: " +
                "C 978-0-7619-5110-0 P 978-0-7619-5111-7");
        bookList.add(book2);
        bookList1.add(book2);

        Book book3 = new Book();
        book3.setImage(R.mipmap.test_psychoanalysis);
        book3.setName("Psychoanalysis");
        book3.setPrice(256);
        book3.setDescription("When this best-seller was published");
        bookList.add(book3);
        bookList1.add(book3);

        dailyAdapter.add(bookList);


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
        book5.setImage(R.mipmap.test_effective_manager);
        book5.setName("Pride and Prejudice");
        book5.setDescription("Jane Eyre is the representative work of British woman writer " +
                "Charlotte Bronte. The heroine Jane Eyre is a female intellectual image" +
                " that pursues equality and autonomy. The novel wins with a " +
                "touching sacred history of a \"Cinderella-style\" character.\n" +
                "        \"Jane Eyre\" is also a representative work of female literature.");
        book5.setUpper("kevin");
        book5.setPrice(88);
        bookList1.add(book5);

        Book book6 = new Book();
        book6.setImage(R.mipmap.test_harry_potter);
        book6.setName("Harry Potter");
        book6.setDescription("J. K. Rowling (1965- ), a British female writer who loved writing " +
                "since childhood and worked as a teacher and secretary for a short time. " +
                "At the age of twenty-four, she was born with the idea of creating a series " +
                "of \"Harry Potter\" novels on her train journey to London. Seven years later, " +
                "\"Harry Potter and the Sorcerer's Stone\" came out, after which she successively" +
                " created \"Harry Potter and the Chamber of Secrets\", " +
                "\"Harry Potter and the Prisoner of Azkaban\", \"Harry Potter and Flame Cup");
        book6.setUpper("jack");
        book6.setPrice(7);
        bookList1.add(book6);


        randomAdapter.add(bookList1);
    }

    private void randomRecycle() {
        rvRandom.setLayoutManager(new LinearLayoutManager(getContext()));
        randomAdapter = new RecyclerAdapter<Book>() {
            @Override
            protected int getItemViewType(int position, Book event) {
                return R.layout.holder_random;
            }

            @Override
            protected ViewHolder<Book> onCreateViewHolder(View root, int viewType) {
                return new RandomHolder(root);
            }
        };
        rvRandom.setAdapter(randomAdapter);

        randomAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Book>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Book book) {
                // todo book 信息处理
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", book);
                BooksActivity.show((BaseActivity) BookFragment.this.getActivity(),
                        BooksShopActivity.class, bundle, false);
            }
        });
    }

    private void dailyRecycle() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        rvDaily.setLayoutManager(layoutManager);
        dailyAdapter = new RecyclerAdapter<Book>() {
            @Override
            protected int getItemViewType(int position, Book event) {
                return R.layout.holder_daily;
            }

            @Override
            protected ViewHolder<Book> onCreateViewHolder(View root, int viewType) {
                return new DailyHolder(root);
            }
        };
        rvDaily.setAdapter(dailyAdapter);

        dailyAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Book>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Book book) {
                // todo book 处理
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", book);
                BooksActivity.show((BaseActivity) BookFragment.this.getActivity(),
                        BooksShopActivity.class, bundle, false);
            }
        });
    }


    @OnClick(R.id.sv_Search)
    public void onClick() {
        SearchActivity.show(BookFragment.this.getActivity(), SearchActivity.class);
    }

    class DailyHolder extends RecyclerAdapter.ViewHolder<Book> {

        @BindView(R.id.daily_image)
        ImageView dailyImage;
        @BindView(R.id.daily_text)
        TextView dailyText;

        DailyHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Book book) {
            dailyImage.setImageResource(book.getImage());
            dailyText.setText(book.getName());
        }
    }

}

