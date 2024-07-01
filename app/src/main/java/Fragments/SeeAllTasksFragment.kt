package Fragments

import Adapters.CustomAdapter
import Models.DataClass
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R

class SeeAllTasksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_see_all_tasks, container, false)

        val allRecyclerView: RecyclerView = view.findViewById(R.id.allTasksRecycler)
        allRecyclerView.layoutManager = LinearLayoutManager(activity)

        val adapter = CustomAdapter(DataClass.data())
        allRecyclerView.adapter = adapter

        return view
    }
}