package club.crabglory.www.data

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.annotation.RequiresApi
import club.crabglory.www.common.Application
import club.crabglory.www.common.basic.view.BaseActivity
import club.crabglory.www.common.utils.HashUtils
import club.crabglory.www.common.utils.MessageNotificationUtils
import club.crabglory.www.data.model.StaticData
import club.crabglory.www.data.model.db.Message
import club.crabglory.www.data.model.db.utils.DBFlowExclusionStrategy
import club.crabglory.www.data.model.dispatch.MessageCenter
import club.crabglory.www.data.model.dispatch.MessageDispatch
import club.crabglory.www.data.model.persistence.Account
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowLog
import com.raizlabs.android.dbflow.config.FlowManager

class DataKit {
    companion object {
        private val gson: Gson = GsonBuilder() // 设置时间格式
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss") // 设置一个过滤器，数据库级别的Model不进行Json转换
                .setExclusionStrategies(DBFlowExclusionStrategy())
                .create()

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

        fun getMessageCenter(): MessageCenter? {
            return MessageDispatch.instance()
        }

        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        fun messageArrivals(msg: Message, cls: Class<out BaseActivity>) {
            val notificationUtils = MessageNotificationUtils(
                    app(), HashUtils.toHash(msg.id), R.mipmap.etcb_icon,
                    if (msg.sender == null) "易书阁" else msg.sender.name,
                    if (msg.type == Message.TYPE_STR)  msg.content else "语言消息")
            val intent = Intent(app().baseContext, cls)
            val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(app().baseContext)
            stackBuilder.addParentStack(cls)
            stackBuilder.addNextIntent(intent)
            val pendingIntent: PendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            notificationUtils.builder
                    .setLargeIcon(BitmapFactory.decodeResource(app().resources, R.mipmap.avatar))
                    .build()
            notificationUtils.notifyAndCancel(pendingIntent)
        }
    }
}