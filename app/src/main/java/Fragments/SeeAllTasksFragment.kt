package Fragments

import Adapters.CustomAdapter
import DAOs.DatabaseBuilder
import Interfaces.OnCheckBoxClickListener
import Models.Task
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
        onClickListeners()

        return view
    }

    private fun initView(view: View) {
        allRecyclerView = view.findViewById(R.id.allTasksRecycler)
        backBtn = view.findViewById(R.id.backBtn)
    }

    fun onClickListeners() {
        backBtn.setOnClickListener {
            fragmentManager.popBackStack()
            navigateToMainPageFrag(UtilMethods.selectFragment())
        }
    }

    private fun addRecyclerView() {
        allRecyclerView.layoutManager = LinearLayoutManager(activity)

        adapter = CustomAdapter(DataClass.data(), this)
        allRecyclerView.adapter = adapter
    }

    private fun navigateToMainPageFrag(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainActivityLayout, fragment)
            .addToBackStack(null)
            .commit()
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
    }

    override fun onClickCheckBox(isChecked: Boolean, position: Long) {
        val task = DataClass.data().first { it.id == position }
        task.isDone = !task.isDone
    }

    override fun onDeleteClick(position: Long) {
        val toDeleteTask = DataClass.data().first { it.id == position }
        DatabaseBuilder.database.taskDao().delete(position)
        UtilMethods.deleteTask(toDeleteTask)
    }

    override fun deleteTaskCallBack() {
        this.addRecyclerView()
    }

    override fun onClickTaskCard(task: Task) {
        val fragment = CreateNewTaskFragment()
        fragment.setArguments(UtilMethods.bundleValue(false, task))
        this.navigateToMainPageFrag(fragment)
    }
}