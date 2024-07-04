package Interfaces

import android.icu.text.Transliterator.Position

interface OnCheckBoxClickListener {
    fun onClickCheckBox(isChecked: Boolean, position: Long)
    fun onDeleteClick(position: Long)
    fun deleteTaskCallBack()
}