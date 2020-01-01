package club.crabglory.www.factory

import club.crabglory.www.data.DataKit
import club.crabglory.www.data.model.db.utils.DBFlowExclusionStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.concurrent.Executors

class Factory {
    companion object {
        private var executor = Executors.newFixedThreadPool(4)!!
        init {
            executor = Executors.newFixedThreadPool(4)
        }

        fun runOnAsync(runnable: Runnable) {
            executor.submit(runnable)
        }
    }
}