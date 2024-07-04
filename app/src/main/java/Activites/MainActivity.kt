package Activites

import DAOs.DatabaseBuilder
import Fragments.MainPageFragment
import Util.UtilMethods
import android.app.Fragment
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todoapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // build my database
        DatabaseBuilder.dataBaseBuilder(this)
        UtilMethods.getDataFromDB()

        val fragment = UtilMethods.selectFragment()

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.mainActivityLayout, fragment)
        fragmentTransaction.addToBackStack("fragment1")
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        val backStackEntryCount = fragmentManager.backStackEntryCount
        if (backStackEntryCount > 1) {
            super.onBackPressed()
        } else {
            finish()
        }
    }
}