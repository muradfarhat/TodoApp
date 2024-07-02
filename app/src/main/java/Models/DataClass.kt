package Models

class DataClass {

    companion object {
        private val tasks = mutableListOf<Task>()

        fun data(): MutableList<Task> = tasks

        fun addTask(task: Task) {
            tasks.add(task)
        }
    }
}