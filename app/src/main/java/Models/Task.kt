package Models

import Enum.SelectedPriority

data class Task (
    var tittle: String,
    var description: String,
    var date: String,
    var startTime: String,
    var endTime: String,
    var getAlert: Boolean,
    var priority: SelectedPriority,
    var isDone: Boolean
)