package club.crabglory.www.factory.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import javax.security.auth.login.LoginException;

import club.crabglory.www.common.basic.model.DbDataSource;
import club.crabglory.www.common.utils.CollectionUtil;
import club.crabglory.www.data.helper.DbHelper;
import club.crabglory.www.data.model.BaseDdModel;

/**
 * 1. 注册绑定DbHelper 里面的反馈通知
 * 2. 建立需要通知的数据类型的扫描器
 * 3. 数据缓存与过滤
 */
public abstract class BaseDbRepository<Data extends BaseDdModel<Data>> implements DbDataSource<Data>,
        DbHelper.DataChangeListener<Data>, QueryTransaction.QueryResultListCallback<Data>{

    final static String TAG = "BaseDbRepository";

    /**
     * 被扫描出的数据类型
     */
    private Class<Data> tClass;

    private SucceedCallback<List<Data>> callback;
    /**
     * 用于缓存的数组， 链表操作, 便于对数据的处理，可以在开头插入数据，在更新到ui
     */
    public LinkedList<Data> dataList =  new LinkedList<>();

    public BaseDbRepository() {
        // 拿当前类的范型数组信息
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        if (type != null) {
            Type[] actualTypeArguments = type.getActualTypeArguments();
            tClass = (Class<Data>) actualTypeArguments[0];
        }
    }


    @Override
    public void load(SucceedCallback<List<Data>> callback) {
        this.callback = callback;
        DbHelper.addChangeListener(tClass, this);
    }

    @Override
    public void dispose() {
        this.callback = null;
        DbHelper.removeChangeListener(tClass, this);
        dataList.clear();
    }

    @Override
    public void onDataSave(Data[] list) {
        boolean isChanged = false;
        // 当数据库数据变更的操作
        for (Data data : list) {
            if (isRequired(data)) {
                insertOrUpdate(data);
                isChanged = true;
            }
        }
        if (list.length == 0) isChanged = true; // 为了避免第一次加载本地没有数据的情况
        // 有数据变更，则进行界面刷新
        if (isChanged)
            notifyDataChange();
    }

    // 通知界面刷新的方法
    private void notifyDataChange() {
        SucceedCallback<List<Data>> callback = this.callback;
        if (callback != null)
            callback.onDataLoaded(dataList);
    }

    /**
     * 对于不同的数据进行过滤
     * @param data 需要使用的数据
     * @return 是否被需要
     */
    public abstract boolean isRequired(Data data);


    // 插入或者更新
    private void insertOrUpdate(Data data) {
        int index = indexOf(data);
        if (index >= 0) {
            replace(index, data);
        } else {
            insert(data);
        }
    }

    // 更新操作，更新某个坐标下的数据
    protected void replace(int index, Data data) {
        dataList.remove(index);
        dataList.add(index, data);
    }

    // 添加方法
    protected void insert(Data data) {
        dataList.add(data);
    }


    // 查询一个数据是否在当前的缓存数据中，如果在则返回坐标
    protected int indexOf(Data newData) {
        int index = -1;
        for (Data data : dataList) {
            index++;
            if (data.isSame(newData)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public void onDataDelete(Data[] list) {
        // 在删除情况下不用进行过滤判断
        // 但数据库数据删除的操作
        boolean isChanged = false;
        for (Data data : list) {
            if (dataList.remove(data))
                isChanged = true;
        }

        // 有数据变更，则进行界面刷新
        if (isChanged)
            notifyDataChange();
    }

    /**
     * 基类统一处理，自我查询的信息的更新
     */
    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Data> tResult) {
        if (tResult.size() == 0) {
            Log.e("BaseDbRepository", "tResult size : " + tResult.size());
            dataList.clear();
            notifyDataChange();
            return;
        }
        Data[] data = CollectionUtil.toArray(tResult, tClass);
        onDataSave(data);
    }
}
