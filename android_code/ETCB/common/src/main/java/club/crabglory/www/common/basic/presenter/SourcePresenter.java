package club.crabglory.www.common.basic.presenter;

import android.view.View;

import java.util.List;

import club.crabglory.www.common.basic.contract.BaseContract;
import club.crabglory.www.common.basic.model.DataSource;
import club.crabglory.www.common.basic.model.DbDataSource;
import club.crabglory.www.common.basic.view.BasePresenterFragment;

public abstract class SourcePresenter<Data, ViewModel, Source extends DbDataSource<Data>,
        View extends BaseContract.View> extends BasePresenter<View>
        implements DataSource.SucceedCallback<List<Data>> {

    protected Source mSource;
    public SourcePresenter(Source source, View view) {
        super(view);
        this.mSource = source;
    }

    public void start() {
        if (mSource != null)
            mSource.load(this);
    }

    public void destroy() {
        mSource.dispose();
        mSource = null;
    }

    @Override
    public void onDataLoaded(List<Data> data) {

    }
}


