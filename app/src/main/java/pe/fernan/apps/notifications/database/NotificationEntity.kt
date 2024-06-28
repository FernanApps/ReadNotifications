package pe.fernan.apps.notifications.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(
    val title: String,
    val text: String,
    val subText: String,
    val packageName: String,
    @PrimaryKey val id: String = "$packageName|$title|$text|$subText"
)
