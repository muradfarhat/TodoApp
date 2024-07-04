package Fragments

import Adapters.CustomAdapter
import DAOs.DatabaseBuilder
import Interfaces.OnCheckBoxClickListener
import Util.DataClass
import Util.UtilMethods
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R

class SeeAllTasksFragment : Fragment(), OnCheckBoxClickListener {

    private lateinit var backBtn: ImageButton
    private lateinit var allRecyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_see_all_tasks, container, false)

        initView(view)
        addRecyclerView()

        backBtn = view.findViewById(R.id.backBtn)
        backBtn.setOnClickListener {
            navigateToMainPageFrag()
        }

        return view
    }

    private fun initView(view: View) {
        allRecyclerView = view.findViewById(R.id.allTasksRecycler)
    }

    private fun addRecyclerView() {
        allRecyclerView.layoutManager = LinearLayoutManager(activity)

        adapter = CustomAdapter(DataClass.data(), this)
        allRecyclerView.adapter = adapter
    }

    private fun navigateToMainPageFrag() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainActivityLayout, UtilMethods.selectFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onClickCheckBox(isChecked: Boolean, position: Long) {
        val task = DataClass.data().first { it.id == position }
        task.isDone = !task.isDone
    }

    override fun onDeleteClick(position: Long) {
        val toDeleteTask = DataClass.data().first { it.id == position }
        DatabaseBuilder.database.taskDao().delete(toDeleteTask)
        UtilMethods.deleteTask(position)
    }

    override fun deleteTaskCallBack() {
        this.addRecyclerView()
    }
}