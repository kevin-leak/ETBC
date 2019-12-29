package club.crabglory.www.factory.contract;

import club.crabglory.www.common.basic.contract.BaseContract;
import club.crabglory.www.data.model.db.Book;

public interface BooksShopContract {
    interface View extends BaseContract.View<Presenter> {
        void refreshData(Book book);

        void addCarSuccess();
    }


    interface Presenter extends BaseContract.Presenter {

        void getGoods(String goodsId);

        void saveCarGoods(Book book, int sales);

        void prePay(Book goods, int sales);
    }
}
