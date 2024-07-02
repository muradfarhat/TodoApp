package Util

import Fragments.MainPageFragment
import Fragments.NoTasksFragment
import Models.DataClass

class UtilMethods {
    companion object {
        fun selectFragment(): android.app.Fragment {
            if(DataClass.data().isEmpty())
                return NoTasksFragment()
            else return MainPageFragment()
        }
    }
}