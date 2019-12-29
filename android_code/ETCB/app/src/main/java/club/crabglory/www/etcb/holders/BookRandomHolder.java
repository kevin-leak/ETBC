package club.crabglory.www.etcb.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.view.BookModelView;
import club.crabglory.www.etcb.R;
import club.crabglory.www.etcb.view.AvatarView;

public class BookRandomHolder extends RecyclerAdapter.ViewHolder<Book> {

    @BindView(R.id.iv_book)
    AvatarView ivBook;
    @BindView(R.id.tv_book_name)
    TextView tvBookName;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_up)
    TextView tvUp;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    private View rootView;


    public BookRandomHolder(View rootView) {
        super(rootView);
        this.rootView = rootView;
    }

    @Override
    protected void onBind(Book book) {
        ivBook.setup( Glide.with(rootView.getContext()), 0,book.getImage());
        tvBookName.setText(book.getName());
        tvDescription.setText(book.getDescription());
        tvUp.setText(book.getAuthor());
        tvPrice.setText(String.format("%s$", book.getPrice()));
    }
}