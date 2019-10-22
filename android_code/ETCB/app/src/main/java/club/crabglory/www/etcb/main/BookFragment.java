package club.crabglory.www.etcb.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import club.crabglory.www.common.basic.BaseFragment;
import club.crabglory.www.common.view.recycler.RecyclerAdapter;
import club.crabglory.www.etcb.LaunchActivity;
import club.crabglory.www.etcb.MainActivity;
import club.crabglory.www.etcb.R;
import club.crabglory.www.factory.db.Book;

public class BookFragment extends BaseFragment {

    @BindView(R.id.rv_daily)
    RecyclerView rvDaily;
    @BindView(R.id.rv_random)
    RecyclerView rvRandom;
    @BindView(R.id.mrl_refresh)
    MaterialRefreshLayout mrlRefresh;
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

    @Override
    protected void initData() {
        super.initData();


        testDailyRecycle();

        testRandomRecycle();

        testDataDaily();

        testDataRandom();
        mrlRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //下拉刷新...
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mrlRefresh.finishRefresh();
                    }
                }, 1500);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mrlRefresh.finishRefreshLoadMore();
                    }
                }, 1500);
                //上拉加载更多...

            }
        });

    }

    private void testDataRandom() {

        ArrayList<Book> bookList = new ArrayList<>();
        Book book = new Book();
        book.setImage(R.mipmap.test_pride_and_prejudice);
        book.setName("Pride and Prejudice");
        book.setDescription("This book describes the arrogant single young Darcy and the " +
                "second lady of the prejudice, Elizabeth, the wealthy single noble Bingley " +
                "and the singer's great lady Ji Ying, fully expressing the author's own view " +
                "of marriage, paying attention to economic interests to love and The novel has" +
                " been adapted into films and TV series many times. The impact of marriage." +
                " The plot of the novel is full of comics, humorous language, " +
                "and is in Austin's novels.");
        book.setUpper("leak");
        book.setPrice(99.5);
        bookList.add(book);

        Book book1 = new Book();
        book1.setImage(R.mipmap.test_effective_manager);
        book1.setName("Pride and Prejudice");
        book1.setDescription("Jane Eyre is the representative work of British woman writer " +
                "Charlotte Bronte. The heroine Jane Eyre is a female intellectual image" +
                " that pursues equality and autonomy. The novel wins with a " +
                "touching sacred history of a \"Cinderella-style\" character.\n" +
                "        \"Jane Eyre\" is also a representative work of female literature.");
        book1.setUpper("kevin");
        book1.setPrice(88);
        bookList.add(book1);

        Book book2 = new Book();
        book2.setImage(R.mipmap.test_harry_potter);
        book2.setName("Harry Potter");
        book2.setDescription("J. K. Rowling (1965- ), a British female writer who loved writing " +
                "since childhood and worked as a teacher and secretary for a short time. " +
                "At the age of twenty-four, she was born with the idea of creating a series " +
                "of \"Harry Potter\" novels on her train journey to London. Seven years later, " +
                "\"Harry Potter and the Sorcerer's Stone\" came out, after which she successively" +
                " created \"Harry Potter and the Chamber of Secrets\", " +
                "\"Harry Potter and the Prisoner of Azkaban\", \"Harry Potter and Flame Cup");
        book2.setUpper("jack");
        book2.setPrice(7);
        bookList.add(book2);


        randomAdapter.add(bookList);
    }

    private void testRandomRecycle() {
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
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Book event) {
                // todo 跳转到具体信息页面
            }
        });
    }

    private void testDailyRecycle() {
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
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Book event) {
                // todo 跳转到具体信息页面
            }
        });
    }

    private void testDataDaily() {
        ArrayList<Book> bookList = new ArrayList<>();

        Book book = new Book();
        book.setImage(R.mipmap.test_republic);
        book.setName("Republic");
        bookList.add(book);

        Book book1 = new Book();
        book1.setImage(R.mipmap.test_jane_eyre);
        book1.setName("Jane Eyre");
        bookList.add(book1);

        Book book2 = new Book();
        book2.setImage(R.mipmap.test_effective_manager);
        book2.setName("Effective Manager");
        bookList.add(book2);

        Book book3 = new Book();
        book3.setImage(R.mipmap.test_psychoanalysis);
        book3.setName("Psychoanalysis");
        bookList.add(book3);

        dailyAdapter.add(bookList);
    }


    class DailyHolder extends RecyclerAdapter.ViewHolder<Book> {

        @BindView(R.id.daily_image)
        ImageView dailyImage;
        @BindView(R.id.daily_text)
        TextView dailyText;

        public DailyHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Book book) {
            dailyImage.setImageResource(book.getImage());
            dailyText.setText(book.getName());
        }
    }

    class RandomHolder extends RecyclerAdapter.ViewHolder<Book> {

        @BindView(R.id.iv_book)
        ImageView ivBook;
        @BindView(R.id.tv_book_name)
        TextView tvBookName;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.tv_up)
        TextView tvUp;
        @BindView(R.id.tv_price)
        TextView tvPrice;


        public RandomHolder(View rootView) {
            super(rootView);
        }

        @Override
        protected void onBind(Book book) {
            ivBook.setImageResource(book.getImage());
            tvBookName.setText(book.getName());
            tvDescription.setText(book.getDescription());
            tvUp.setText(book.getUpper());
            tvPrice.setText(book.getPrice());
        }
    }
}

