package Models

data class Task (
    var id: Int,
    var tittle: String,
    var date: String,
    var isDone: Boolean,
    var description: String
)