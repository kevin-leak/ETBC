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

public class BookDailyHolder extends RecyclerAdapter.ViewHolder<Book> {

    @BindView(R.id.daily_image)
    AvatarView dailyImage;
    @BindView(R.id.daily_text)
    TextView dailyText;
    private View rootView;

    public BookDailyHolder(View rootView) {
        super(rootView);
        this.rootView = rootView;
    }

    @Override
    protected void onBind(Book book) {
        dailyImage.setup( Glide.with(rootView.getContext()),0, book.getImage());
        dailyText.setText(book.getName());
    }
}