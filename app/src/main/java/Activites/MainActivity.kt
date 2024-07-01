package Activites

import Fragments.CreateNewTaskFragment
import Fragments.MainPageFragment
import Fragments.NoTasksFragment
import Fragments.SeeAllTasksFragment
import Models.DataClass
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todoapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val fragment =
            if(DataClass.data().isEmpty())
                NoTasksFragment()
            else MainPageFragment()

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.mainActivityLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}