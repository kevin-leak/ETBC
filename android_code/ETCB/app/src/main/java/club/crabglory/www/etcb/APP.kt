package club.crabglory.www.etcb

import club.crabglory.www.common.Application
import club.crabglory.www.data.DataKit

class APP : Application() {
    override fun onCreate() {
        super.onCreate()
        DataKit.initDb()
    }
}