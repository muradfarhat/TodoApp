package Interfaces

import android.icu.text.Transliterator.Position

interface OnCheckBoxClickListener {
    fun onClickCheckBox(isChecked: Boolean, position: Int)
}