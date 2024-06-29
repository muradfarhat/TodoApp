package Fragments

import Adapters.CustomAdapter
import Models.Task
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R

class MainPageFragment : Fragment() {

    private val data = listOf(
        Task(
            1,
            tittle = "First Task",
            date = "4 June",
            isDone = true,
            description = "My first task in app"
        ),
        Task(
            2,
            tittle = "Second Task",
            date = "26 June",
            isDone = false,
            description = "My second task in app"
        ),
        Task(
            3,
            tittle = "Third Task",
            date = "6 July",
            isDone = false,
            description = "My third task in app"
        ),
        Task(
            4,
            tittle = "Fourth Task",
            date = "17 July",
            isDone = true,
            description = "My fourth task in app"
        )
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_page, container, false)

        val dailyRecyclerView: RecyclerView = view.findViewById(R.id.todayTasksRecyclerView)
        dailyRecyclerView.layoutManager = LinearLayoutManager(activity)

        val allRecyclerView: RecyclerView = view.findViewById(R.id.allTasksRecyclerView)
        allRecyclerView.layoutManager = LinearLayoutManager(activity)

        val adapter = CustomAdapter(data)
        dailyRecyclerView.adapter = adapter
        allRecyclerView.adapter = adapter

        return view
    }
}