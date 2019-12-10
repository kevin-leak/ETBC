package club.crabglory.www.etcb.frags.book;

import android.os.Bundle;

import club.crabglory.www.common.basic.view.ToolbarActivity;
import club.crabglory.www.etcb.R;

public class BooksShopActivity extends ToolbarActivity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_shop_books;
    }



    @Override
    protected boolean initArgs(Bundle bundle) {
//        if (bundle != null && bundle.get("book") != null) {
//            Toast.makeText(this, ((Book) bundle.get("book")).getName(), Toast.LENGTH_SHORT).show();
            return super.initArgs(bundle);
//        } else {
//            return false;
//        }
    }

    @Override
    protected boolean needBack() {
        return false;
    }
}
