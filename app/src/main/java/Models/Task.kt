package Models

import Enum.SelectedPriority
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tasks")
data class Task (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "tittle") var tittle: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "day") var day: String,
    @ColumnInfo(name = "month") var month: String,
    @ColumnInfo(name = "start_time") var startTime: String,
    @ColumnInfo(name = "end_time") var endTime: String,
    @ColumnInfo(name = "get_alert") var getAlert: Boolean,
    @ColumnInfo(name = "priority") var priority: SelectedPriority,
    @ColumnInfo(name = "is_done") var isDone: Boolean
): Parcelable