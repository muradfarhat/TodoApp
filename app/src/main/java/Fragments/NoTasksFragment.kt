package Fragments

import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoTasksFragment : Fragment() {

    private lateinit var floatingBtn: FloatingActionButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_no_tasks, container, false)

        floatingBtn = view.findViewById(R.id.floatingBtn)
        floatingBtn.setOnClickListener {
            navigateToCreateTaskFrag()
        }

        return view
    }

    private fun navigateToCreateTaskFrag() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainActivityLayout, CreateNewTaskFragment())
            .addToBackStack(null)
            .commit()
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
    }
}