package Interfaces

import Models.Task
import android.icu.text.Transliterator.Position

interface OnCheckBoxClickListener {
    fun onClickCheckBox(position: Long)
    fun onDeleteClick(position: Long)
    fun deleteTaskCallBack()
    fun onClickTaskCard(task: Task)
}