package club.crabglory.www.data.helper;

import android.util.Log;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import club.crabglory.www.data.model.db.AppDatabase;
import club.crabglory.www.data.model.db.Book;
import club.crabglory.www.data.model.db.Goods;

/**
 * 建立对增删查改处理与监听，并反馈回给Dbdatabase，可有结构图分析
 * <p>
 * 0. 建立单例模式，以防容器的添加的紊乱
 * 1. 设置数据存储容器
 * 2. 设置数据监听者的添加与移除
 * 3. 设置数据的通知与存储
 * 4. 设置数据的存储与删除
 *
 * @author KevinLeak
 */
public class DbHelper {

    final static String TAG = "DbHelper";


    private static final DbHelper instance;

    static {
        instance = new DbHelper();
    }

    private DbHelper() {
    }


    /**
     * @param modelClass 指的model类型的class，也指信息表
     * @param models     数据库表的集合
     * @param <Model>    开启一个事务： 数据的实际存储，数据通知
     */
    public static <Model extends BaseModel> void save(final Class<Model> modelClass, final Model... models) {

        if (models == null || models.length == 0) return;
        // 当前数据库的一个管理者
        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
        // 提交一个事物
        definition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                // 执行
                ModelAdapter<Model> adapter = FlowManager.getModelAdapter(modelClass);
                // 保存
                adapter.saveAll(Arrays.asList(models));
                Log.e(TAG, "save: " + " 数据开始存储: " + models[0].toString());
                // 唤起通知
                instance.notifySave(modelClass, models);
            }

        }).build().execute();

    }


    // 设置监听器容器
    /**
     * 通过不同类型的model，来确定应该通过哪些回调来更新
     * Class<?> 为不同类型model
     * Set<DataChangeListener>> 需要回调的集合
     * <p>
     * 不同的listener 来自不同的仓库，有不同的presenter来取
     */
    private final Map<Class<?>, Set<DataChangeListener>> changedListeners = new HashMap<>();


    // 设置添加、删除监听器

    /**
     * 规定我们的键必须BaseModel 类型的
     */
    public static <model extends BaseModel> void addChangeListener(Class<model> cls,
                                                                   DataChangeListener<model> listener) {
        Set<DataChangeListener> dataChangeListeners = instance.getChangedListeners(cls);
        if (dataChangeListeners == null) {
            dataChangeListeners = new HashSet<>();
            instance.changedListeners.put(cls, dataChangeListeners);
        }
        dataChangeListeners.add(listener);
    }


    public static <model extends BaseModel> void removeChangeListener(Class<model> cls,
                                                                      DataChangeListener<model> listener) {
        Set<DataChangeListener> dataChangeListeners = instance.getChangedListeners(cls);
        if (dataChangeListeners != null) {
            dataChangeListeners.remove(listener);
        }
    }

    // 指的是支付之后的产品保存过程
    public static <Model extends BaseModel> void updateGoods(final Class<Goods> goodsClass, final Goods... goodsList) {
        /*
         * 1. 设置goods的产生时间
         * 2. 更新book的销量与数量，通知并保存
         * 2. 修改goods的状态
         * */
        // 通知进行清空操作
        instance.notifyDelete(Goods.class, goodsList);
        List<Book> books = new ArrayList<>();
        for (Goods goods : goodsList) {
            Book book = goods.getBook();
            goods.setCreateAt(new Date());
            book.setSales(book.getSales() + goods.getCount());
            book.setCount(book.getCount() - goods.getCount());
            books.add(book);
            goods.setState(true);
        }
        // fixme 这里如果有后台的话应该由后台通知，所有的数据建议都由后台通知
        DbHelper.save(Book.class, books.toArray(new Book[0]));
        DbHelper.save(Goods.class, goodsList);
    }


    /**
     * 获取监听者对应的列表
     */
    public <model extends BaseModel> Set<DataChangeListener> getChangedListeners(Class<model> cls) {
        if (changedListeners.containsKey(cls)) {
            return changedListeners.get(cls);
        }
        return null;
    }

    // 设置监听通知
    private <model extends BaseModel> void notifySave(Class<model> clsType, model... models) {
        Set<DataChangeListener> dataChangeListeners = getChangedListeners(clsType);
        if (dataChangeListeners != null && dataChangeListeners.size() > 0) {
            for (DataChangeListener<model> listener : dataChangeListeners) {
                listener.onDataSave(models);
            }
        }

        // 处理特殊情况，例如当前的present 并不是需要作出改变的

    }

    private <model extends BaseModel> void notifyDelete(Class<model> clsType, model... models) {
        Set<DataChangeListener> dataChangeListeners = getChangedListeners(clsType);
        if (dataChangeListeners != null && dataChangeListeners.size() > 0) {
            for (DataChangeListener<model> listener : dataChangeListeners) {
                listener.onDataDelete(models);
            }
        }
        // 处理特殊情况，例如当前的present 并不是需要作出改变的
    }


    /**
     * 进行删除数据库的统一封装方法
     *
     * @param tClass  传递一个Class信息
     * @param models  这个Class对应的实例的数组
     * @param <Model> 这个实例的范型，限定条件是BaseModel
     */
    public static <Model extends BaseModel> void delete(final Class<Model> tClass,
                                                        final Model... models) {
        if (models == null || models.length == 0)
            return;

        // 当前数据库的一个管理者
        DatabaseDefinition definition = FlowManager.getDatabase(AppDatabase.class);
        // 提交一个事物
        definition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                // 执行
                ModelAdapter<Model> adapter = FlowManager.getModelAdapter(tClass);
                // 删除
                adapter.deleteAll(Arrays.asList(models));
                // 唤起通知
                instance.notifyDelete(tClass, models);
            }
        }).build().execute();
    }


    /**
     * 数据更新后，提供回调接口，来通知其他仓库
     * 对于数据的操作：删除，存储（更新），设计数据为数组模式
     * 数据类型为数据model 模式
     */
    @SuppressWarnings({"unused", "unchecked"})
    public interface DataChangeListener<Data extends BaseModel> {
        void onDataSave(Data... list);

        void onDataDelete(Data... list);
    }
}
