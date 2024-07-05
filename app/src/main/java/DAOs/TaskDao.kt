package DAOs

import Models.Task
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<Task>

    @Query("DELETE FROM tasks WHERE id = :id")
    fun delete(id: Long)

    @Update
    fun update(task: Task)

    @Query("UPDATE tasks SET is_done = :isDone WHERE id = :taskId")
    fun updateTaskStatus(taskId: Long, isDone: Boolean)
}