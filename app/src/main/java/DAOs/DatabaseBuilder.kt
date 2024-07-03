package DAOs

import android.content.Context
import androidx.room.Room

class DatabaseBuilder {
    companion object {
        lateinit var database: AppDatabase

        fun dataBaseBuilder(context: Context) {
            database = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "my_tasksDB"
            ).allowMainThreadQueries().build()
        }
    }
}