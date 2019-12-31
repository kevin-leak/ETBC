package club.crabglory.www.factory.presenter.live;

import android.util.Log;

import java.util.List;

import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.basic.presenter.RecyclerSourcePresenter;
import club.crabglory.www.data.helper.LiveDataHelper;
import club.crabglory.www.data.model.db.Live;
import club.crabglory.www.factory.Factory;
import club.crabglory.www.factory.contract.DisplayLiveContract;
import club.crabglory.www.factory.contract.LiveDataSource;
import club.crabglory.www.factory.repository.DisplayLiveRepository;

public class DisplayLivePresenter extends
        RecyclerSourcePresenter<Live, Live, LiveDataSource, DisplayLiveContract.View>
        implements DisplayLiveContract.Presenter, DataSource.Callback<List<Live>> {

    private String displayId;

    public DisplayLivePresenter(DisplayLiveContract.View view, String displayId) {
        super(new DisplayLiveRepository(displayId), view);
        this.displayId = displayId;
    }

    @Override
    public void onDataLoaded(List<Live> lives) {
        super.onDataLoaded(lives);
        Log.e("DisplayLivePresenter", "micros size : " + lives.size());
        refreshData(lives);
    }

    @Override
    public void onDataNotAvailable(int strRes) {
        mView.showError(strRes);
        // 由于一开始在本地刷新过，所以不用再次在本地拉去
    }

    @Override
    public void toRefresh(boolean isMore) {
        Factory.Companion.runOnAsync(new Runnable() {
            @Override
            public void run() {
                LiveDataHelper.getLive(displayId, DisplayLivePresenter.this);
            }
        });
    }
}
