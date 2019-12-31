package club.crabglory.www.factory.presenter.micro;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.basic.presenter.RecyclerSourcePresenter;
import club.crabglory.www.data.helper.DbHelper;
import club.crabglory.www.data.helper.LiveDataHelper;
import club.crabglory.www.data.helper.MicroDataHelper;
import club.crabglory.www.data.model.db.Live;
import club.crabglory.www.data.model.db.Micro;
import club.crabglory.www.data.model.db.utils.DiffUiDataCallback;
import club.crabglory.www.data.model.view.MicroViewModel;
import club.crabglory.www.factory.Factory;
import club.crabglory.www.factory.contract.DisplayLiveContract;
import club.crabglory.www.factory.contract.DisplayMicroContract;
import club.crabglory.www.factory.contract.LiveDataSource;
import club.crabglory.www.factory.contract.MicroDataSource;
import club.crabglory.www.factory.repository.DisplayLiveRepository;
import club.crabglory.www.factory.repository.DisplayMicroRepository;

public class DisplayMicroPresenter extends
        RecyclerSourcePresenter<Micro, MicroViewModel, MicroDataSource, DisplayMicroContract.View>
        implements DisplayMicroContract.Presenter, DataSource.Callback<List<Micro>> {

    private String displayId;

    public DisplayMicroPresenter(DisplayMicroContract.View view, String displayId) {
        super(new DisplayMicroRepository(displayId), view);
        this.displayId = displayId;
    }

    @Override
    public void onDataNotAvailable(int strRes) {
        mView.showError(strRes);
        Log.e("DisplayMicroPresenter", "kevin");
        // 由于一开始在本地刷新过，所以不用再次在本地拉去
    }

    @Override
    public void onDataLoaded(List<Micro> micros) {
        Log.e("DisplayMicroPresenter", "micros size : " + micros.size());
        // 1. 来自本地数据库的数据到达的时候
        List<MicroViewModel> viewModels = new ArrayList<>();
        for (Micro micro : micros) {
            viewModels.add(micro.toViewModel());
        }
        refreshData(viewModels);
    }

    @Override
    public void toRefresh(boolean isMore) {
        MicroDataHelper.getMicro(displayId, DisplayMicroPresenter.this);
    }
}
