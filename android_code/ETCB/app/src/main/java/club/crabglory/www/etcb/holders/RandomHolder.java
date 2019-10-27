package club.crabglory.www.etcb.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import club.crabglory.www.common.view.recycler.RecyclerAdapter;
import club.crabglory.www.etcb.R;
import club.crabglory.www.factory.db.Book;

public class RandomHolder extends RecyclerAdapter.ViewHolder<Book> {

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