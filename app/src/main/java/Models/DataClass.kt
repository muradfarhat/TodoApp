package Models

class DataClass {

    companion object {
        private var tasks = mutableListOf<Task>()

        fun data(): MutableList<Task> = tasks

        fun addTask(task: Task) {
            tasks.add(task)
        }

        fun setData(list: List<Task>) {
            tasks = list.toMutableList()
        }
    }
}