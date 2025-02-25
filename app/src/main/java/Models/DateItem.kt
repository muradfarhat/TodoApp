package Models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DateItem(val day: String, val date: String, var month: String): Parcelable