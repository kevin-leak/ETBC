package club.crabglory.www.data

import club.crabglory.www.common.Application
import club.crabglory.www.data.model.StaticData
import club.crabglory.www.data.model.db.utils.DBFlowExclusionStrategy
import club.crabglory.www.data.model.persistence.Account
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowLog
import com.raizlabs.android.dbflow.config.FlowManager
import java.util.concurrent.Executors

class DataKit {
    companion object {
        private val gson: Gson = GsonBuilder() // 设置时间格式
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss") // 设置一个过滤器，数据库级别的Model不进行Json转换
                .setExclusionStrategies(DBFlowExclusionStrategy())
                .create()

        var instance: DataKit? = null
            get() {
                if (field == null) field = DataKit()
                return field
            }

        fun app(): Application {
            return Application.instance
        }

        fun initDb() {
            FlowManager.init(FlowConfig.Builder(app())
                    .openDatabasesOnInit(true) // 数据库初始化的时候就开始打开
                    .build())
            FlowLog.setMinimumLoggingLevel(FlowLog.Level.V)
            if (Account.isLogin(app()))
                Account.load(app())
            // fixme just for local test
            StaticData.getBook(app())

        }

        fun getGson(): Gson {
            return gson
        }
    }
}