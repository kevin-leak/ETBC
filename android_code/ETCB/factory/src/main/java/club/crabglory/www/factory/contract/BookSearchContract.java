package club.crabglory.www.factory.contract;

import club.crabglory.www.common.basic.contract.BaseContract;
import club.crabglory.www.data.model.db.Book;

public interface BookSearchContract {

    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter {
        void search(String  text);
    }

    // 都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter, Book> {

    }
}
