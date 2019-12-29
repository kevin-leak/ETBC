package club.crabglory.www.data.model;

import com.raizlabs.android.dbflow.structure.BaseModel;

import club.crabglory.www.data.model.db.utils.DiffUiDataCallback;

/**
 * 基础DBflow的数据模型， 同时实现对各种表的分发，采用泛型
 * 实现DiffUiDataCallback，用来辨别信息是否相同
 */
public abstract class BaseDdModel<Model> extends BaseModel
        implements DiffUiDataCallback.UiDataDiffer<Model>{

}
