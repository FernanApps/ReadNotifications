package pe.fernan.apps.notifications.service

import android.app.Notification
import android.icu.text.CaseMap.Title
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pe.fernan.apps.notifications.database.DatabaseProvider
import pe.fernan.apps.notifications.database.NotificationEntity


class NotificationListener : NotificationListenerService() {

    private val scope = CoroutineScope(Dispatchers.IO)


    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        val notiInfo = sbn?.notification ?: return


        val title = notiInfo.extras.getString(Notification.EXTRA_TITLE)
        val text = notiInfo.extras.getString(Notification.EXTRA_TEXT)
        val subText = notiInfo.extras.getString(Notification.EXTRA_SUB_TEXT)

        /*
        val notificationIcon: Int = extras.getInt(Notification.EXTRA_SMALL_ICON)
        val notificationLargeIcon =
            extras.getParcelable(Notification.EXTRA_LARGE_ICON)
        */
        val packageName = sbn.packageName

        val notificationEntity = NotificationEntity(
            title = title ?: "",
            text = text ?: "",
            subText = subText ?: "",
            packageName = packageName
        )

        scope.launch {
            val db = DatabaseProvider.getDatabase(applicationContext)
            db.notificationDao().insertNotification(notificationEntity)
            Log.d("NotificationListener", "Notification added: $notificationEntity")
        }        /*
        if(!mapMessages.containsKey(notificationData.id)){
            mapMessages[notificationData.id] = notificationData
        }


         */


    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)

        val notiInfo = sbn?.notification ?: return

        val title = notiInfo.extras.getString(Notification.EXTRA_TITLE)
        val text = notiInfo.extras.getString(Notification.EXTRA_TEXT)
        val subText = notiInfo.extras.getString(Notification.EXTRA_SUB_TEXT)

        return
        Log.d(
            "NotificationListener",
            "onNotificationRemoved - title : $title, text : $text, subText : $subText"
        )
    }
}