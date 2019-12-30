package club.crabglory.www.factory

import club.crabglory.www.data.DataKit
import club.crabglory.www.data.model.db.utils.DBFlowExclusionStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.concurrent.Executors

class Factory{
    companion object {
        private var executor = Executors.newFixedThreadPool(4)!!
        private val gson: Gson

        init {
            executor = Executors.newFixedThreadPool(4)
            gson = GsonBuilder() // 设置时间格式
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss") // 设置一个过滤器，数据库级别的Model不进行Json转换
                    .setExclusionStrategies(DBFlowExclusionStrategy())
                    .create()
        }

        fun runOnAsync(runnable: Runnable) {
            executor.submit(runnable)
        }

        fun getGson(): Gson {
            return gson
        }
    }
}