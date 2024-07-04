package Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimeItem(val hour: Int, val min: Int, val pmAm: String): Parcelable
