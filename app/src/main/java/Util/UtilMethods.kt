package Util

import DAOs.DatabaseBuilder
import Fragments.MainPageFragment
import Fragments.NoTasksFragment
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UtilMethods {
    companion object {
        fun selectFragment(): android.app.Fragment {
            if(DataClass.data().isEmpty())
                return NoTasksFragment()
            else return MainPageFragment()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getCurrentDate(): String {
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("d MMM")
            val formattedDate = currentDate.format(formatter).toString()

            return formattedDate
        }

        fun getDataFromDB() {
            DataClass.setData(DatabaseBuilder.database.taskDao().getAllTasks())
        }

        fun deleteTask(taskId: Long) {
            DataClass.data().removeIf { it.id == taskId }
        }

        fun isListEmpty(): Boolean {
            return DataClass.data().isEmpty()
        }
    }
}