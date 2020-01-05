package club.crabglory.www.common

import android.app.Activity
import android.os.SystemClock
import android.support.annotation.StringRes
import android.widget.Toast
import java.io.File

open class Application : android.app.Application() {
    companion object {
        lateinit var instance: Application

        private fun getCacheDirFile(): File {
            return instance.cacheDir
        }

        fun getAvatarTmpFile(): File {
            val dir = File(getCacheDirFile(), "avatar")
            dir.mkdir()
            val files = dir.listFiles()
            if (files != null && files.isNotEmpty())
                for (file in files) file.delete()
            return File(dir, SystemClock.uptimeMillis().toString() + ".jpg").absoluteFile
        }

        fun showToast(activity: Activity, msg: String?) {
            activity.runOnUiThread { Toast.makeText(instance, msg, Toast.LENGTH_LONG).show() }
        }

        fun showToast(activity: Activity, @StringRes msgId: Int) {
            showToast(activity, instance.getString(msgId))
        }

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}