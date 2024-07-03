package Util

import DAOs.DatabaseBuilder
import Fragments.MainPageFragment
import Fragments.NoTasksFragment
import Models.DataClass
import Models.DateItem
import Models.Task
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

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
    }
}