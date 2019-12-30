package club.crabglory.www.factory.presenter.book;

import android.text.TextUtils;

import java.util.Date;
import java.util.UUID;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.basic.presenter.BasePresenter;
import club.crabglory.www.data.helper.BookDataHelper;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.helper.DbHelper;
import club.crabglory.www.data.model.persistence.Account;
import club.crabglory.www.factory.R;
import club.crabglory.www.factory.contract.BookUpContract;

public class BookUpPresenter extends BasePresenter<BookUpContract.View>
        implements BookUpContract.Presenter, DataSource.FailedCallback {


    public BookUpPresenter(BookUpContract.View view) {
        super(view);
    }

    @Override
    public void upBook(String mVideoUrl, String mAvatarPath, String bookName, String bookAuthor,
                       String count, String price, String info) {
        if (TextUtils.isEmpty(mAvatarPath) || TextUtils.isEmpty(bookName)
                || TextUtils.isEmpty(bookAuthor) || TextUtils.isEmpty(count)
                || TextUtils.isEmpty(price) || info.length() <= 0 || info.length() > 300) {
            mView.showError(R.string.error_form_data);
            return;
        }
        int c = Integer.parseInt(count);
        float p = Float.parseFloat(price);
        if (c <= 0 || p <= 0) {
            mView.showError(R.string.error_form_data);
            return;
        }
        Book book = new Book();
        book.setId(UUID.randomUUID().toString());
        book.setName(bookName);
        book.setAuthor(bookAuthor);
        book.setCount(c);
        book.setPrice(p);
        book.setDescription(info);
        book.setUpper(Account.getUser());
        book.setUpTime(new Date());
        book.setModifyAt(new Date());
        book.setImage(mAvatarPath);
        book.setVideo(mVideoUrl);
        // fixme 先保存在本地, 后期修改
        DbHelper.save(Book.class, book);
        BookDataHelper.upBook(book, this);
    }


    @Override
    public void onDataNotAvailable(int strRes) {

    }
}
