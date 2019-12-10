package club.crabglory.www.etcb.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import club.crabglory.www.common.widget.recycler.RecyclerAdapter;
import club.crabglory.www.data.db.Book;
import club.crabglory.www.etcb.R;

public class DailyHolder extends RecyclerAdapter.ViewHolder<Book> {

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