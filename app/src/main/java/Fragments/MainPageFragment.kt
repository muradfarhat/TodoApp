package Fragments

import Adapters.CustomAdapter
import Models.DataClass
import Models.Task
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainPageFragment : Fragment() {

    private lateinit var floatingBtn: FloatingActionButton
    private lateinit var seeAll: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_page, container, false)

        val dailyRecyclerView: RecyclerView = view.findViewById(R.id.todayTasksRecyclerView)
        dailyRecyclerView.layoutManager = LinearLayoutManager(activity)

        val allRecyclerView: RecyclerView = view.findViewById(R.id.allTasksRecyclerView)
        allRecyclerView.layoutManager = LinearLayoutManager(activity)

        val adapter = CustomAdapter(DataClass.data())
        dailyRecyclerView.adapter = adapter
        allRecyclerView.adapter = adapter

        floatingBtn = view.findViewById(R.id.floatingBtn)
        floatingBtn.setOnClickListener {
            navigateToCreateTaskFrag(CreateNewTaskFragment())
        }

        seeAll = view.findViewById(R.id.seeAllTasks)
        seeAll.setOnClickListener {
            navigateToCreateTaskFrag(SeeAllTasksFragment())
        }

        return view
    }

    private fun navigateToCreateTaskFrag(fragment: android.app.Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainActivityLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}