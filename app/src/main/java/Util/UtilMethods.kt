package Util

import DAOs.DatabaseBuilder
import Fragments.MainPageFragment
import Fragments.NoTasksFragment
import Models.Task
import android.os.Build
import android.os.Bundle
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

        fun deleteTask(task: Task): Boolean {
            return DataClass.data().remove(task)
        }

        fun isListEmpty(): Boolean {
            return DataClass.data().isEmpty()
        }

        fun bundleValue(isCreate: Boolean, task: Task?): Bundle {
            val bundle = Bundle()
            bundle.putBoolean("isCreate", isCreate)
            bundle.putParcelable("task", task)
            return bundle
        }
    }
}