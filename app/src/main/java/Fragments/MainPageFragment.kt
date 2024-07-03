package Fragments

import Adapters.CustomAdapter
import DAOs.AppDatabase
import Interfaces.OnCheckBoxClickListener
import Models.DataClass
import Models.Task
import Util.UtilMethods
import android.annotation.SuppressLint
import android.os.Bundle
import android.app.Fragment
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.todoapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainPageFragment : Fragment(), OnCheckBoxClickListener {

    private lateinit var floatingBtn: FloatingActionButton
    private lateinit var seeAll: TextView
    private lateinit var numOfDoneTasks: TextView
    private lateinit var ratio: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var mainPageTitle: TextView

    private lateinit var dailyRecyclerView: RecyclerView
    private lateinit var allRecyclerView: RecyclerView

    private var todayTasks = emptyList<Task>()

    // For Room DataBase
    companion object {
        lateinit var database: AppDatabase
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_page, container, false)

        // build my database
        dataBaseBuilder()

        initView(view)
        onClickListeners()

        // Ratio between daily tasks and all tasks
        dailyTasksProgress()

        // Recycler views
        setRecyclerViewAdapters()


        return view
    }

    private fun dataBaseBuilder() {
        database = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "my_tasksDB"
        ).build()
    }

    private fun dailyTasksProgress() {
        val ratioNum = (todayTasks.size.toDouble() / DataClass.data().size.toDouble()) * 100.0
        numOfDoneTasks.text = "${todayTasks.size}/${DataClass.data().size}"
        ratio.text = "${ratioNum.toInt()}%"
        progressBar.progress = ratioNum.toInt()
        mainPageTitle.text = "You have got ${todayTasks.size} tasks today to complete"
    }

    private fun setRecyclerViewAdapters() {
        dailyRecyclerView.layoutManager = LinearLayoutManager(activity)

        val dailyAdapter = CustomAdapter(todayTasks, this)
        dailyRecyclerView.adapter = dailyAdapter

        allRecyclerView.layoutManager = LinearLayoutManager(activity)

        val adapter = CustomAdapter(DataClass.data(), this)
        allRecyclerView.adapter = adapter
    }

    private fun onClickListeners() {
        floatingBtn.setOnClickListener {
            navigateToCreateTaskFrag(CreateNewTaskFragment())
        }

        seeAll.setOnClickListener {
            navigateToCreateTaskFrag(SeeAllTasksFragment())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView(view: View) {
        // Get current day in month
        todayTasks = DataClass.data().filter { it.date.equals(UtilMethods.getCurrentDate()) }
        numOfDoneTasks = view.findViewById(R.id.numOfDoneTasks)
        ratio = view.findViewById(R.id.ratio)
        progressBar = view.findViewById(R.id.progressBar)
        mainPageTitle = view.findViewById(R.id.mainPageTitle)
        dailyRecyclerView = view.findViewById(R.id.todayTasksRecyclerView)
        allRecyclerView = view.findViewById(R.id.allTasksRecyclerView)
        floatingBtn = view.findViewById(R.id.floatingBtn)
        seeAll = view.findViewById(R.id.seeAllTasks)
    }


    private fun navigateToCreateTaskFrag(fragment: android.app.Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainActivityLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onClickCheckBox(isChecked: Boolean, position: Int) {
        DataClass.data()[position].isDone = isChecked
    }
}