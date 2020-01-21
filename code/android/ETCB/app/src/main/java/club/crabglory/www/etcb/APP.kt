package club.crabglory.www.etcb

import android.content.Intent
import android.graphics.BitmapFactory
import android.text.TextUtils
import club.crabglory.www.common.Application
import club.crabglory.www.common.utils.MessageNotificationUtils
import club.crabglory.www.data.DataKit
import club.crabglory.www.data.R
import club.crabglory.www.data.model.db.Message
import club.crabglory.www.etcb.frags.chat.ChatActivity

open class APP : Application() {
    override fun onCreate() {
        super.onCreate()
        DataKit.initDb()
    }
}