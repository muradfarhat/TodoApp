package Fragments

import Adapters.DateAdapter
import DAOs.DatabaseBuilder
import Enum.SelectedPriority
import Interfaces.OnDateClickListener
import Util.DataClass
import Models.DateItem
import Models.Task
import Util.UtilMethods
import android.os.Bundle
import android.app.Fragment
import android.app.TimePickerDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CreateNewTaskFragment : Fragment(), OnDateClickListener {

    // back btn
    private lateinit var backBtn: ImageButton
    private lateinit var createPageTitle: TextView

    // priority variables
    private lateinit var buttonHigh: Button
    private lateinit var buttonMedium: Button
    private lateinit var buttonLow: Button

    private lateinit var taskPriority: SelectedPriority
    // date variable
    private lateinit var taskDate: DateItem
    private var isDateSelected = false
    private lateinit var calendar_prev_button: ImageButton
    private lateinit var calendar_next_button: ImageButton

    // Name & description
    private lateinit var taskName: TextView
    private lateinit var taskDescription: TextView

    private var name: String = ""
    private var description: String = ""

    // start & end time

    private lateinit var startTimeText: TextView
    private lateinit var endTimeText: TextView

    // get alert
    private lateinit var getAlert: Switch

    // create task btn
    private lateinit var createTask: Button
    private lateinit var modifyTaskBtn: Button
    private lateinit var deleteTaskBtn: Button
    private lateinit var modifyAndDeleteLayout: LinearLayout

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DateAdapter
    private lateinit var calendar: Calendar
    private var dateList: MutableList<DateItem> = mutableListOf()
    private lateinit var firstDayOfWeek: Date
    private lateinit var lastDayOfWeek: Date
    private lateinit var txt_current_month: TextView

    // Bundle for pass data
    private lateinit var bundle: Bundle
    private var isCreate = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_new_task, container, false)

        initView(view)
        onClickListeners(view)

        updateDateList()

        // Back Btn
        updateDateText(view)
        setButtonState(SelectedPriority.high)

        setIsCreateView()

        return view
    }

    private fun setIsCreateView() {
        if(isCreate) {
            createTask.visibility = View.VISIBLE
            modifyAndDeleteLayout.visibility = View.INVISIBLE
        } else {
            createTask.visibility = View.INVISIBLE
            modifyAndDeleteLayout.visibility = View.VISIBLE
//            bundle.getParcelable<Task>("task")?.let { this.modifyAndDeleteView(it) }
            arguments?.let {
                var task: Task? = it.getParcelable("task")!!
                this.modifyAndDeleteView(task!!)
            }
        }
    }

    private fun onClickListeners(view: View) {
        backBtn.setOnClickListener {
            fragmentManager.popBackStack()
            navigateToMainPageFrag()
        }

        calendar_prev_button.setOnClickListener {
            calendar.add(Calendar.WEEK_OF_YEAR, -1)
            updateDateList()
            updateDateText(view)
        }

        calendar_next_button.setOnClickListener {
            calendar.add(Calendar.WEEK_OF_YEAR, 1)
            updateDateList()
            updateDateText(view)
        }

        buttonHigh.setOnClickListener {
            setButtonState(SelectedPriority.high)
            taskPriority = SelectedPriority.high
        }
        buttonMedium.setOnClickListener {
            setButtonState(SelectedPriority.medium)
            taskPriority = SelectedPriority.medium
        }
        buttonLow.setOnClickListener {
            setButtonState(SelectedPriority.low)
            taskPriority = SelectedPriority.low
        }

        startTimeText.setOnClickListener {
            showTimePickerDialog(startTimeText)
        }

        endTimeText.setOnClickListener {
            showTimePickerDialog(endTimeText)
        }

        createTask.setOnClickListener {
            createTaskOnClick()
        }
    }

    private fun initView(view: View) {
        //bundle = arguments
        //isCreate = bundle.getBoolean("isCreate")
        arguments?.let {
            isCreate = it.getBoolean("isCreate")
        }

        createPageTitle = view.findViewById(R.id.createPageTitle)
        backBtn = view.findViewById(R.id.backBtn)
        // Calender
        calendar = Calendar.getInstance()
        recyclerView = view.findViewById(R.id.calendar_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adapter = DateAdapter(dateList, this)
        recyclerView.adapter = adapter

        calendar_prev_button = view.findViewById(R.id.calendar_prev_button)
        calendar_next_button = view.findViewById(R.id.calendar_next_button)
        txt_current_month = view.findViewById(R.id.txt_current_month)

        // name & description
        taskName = view.findViewById(R.id.taskName)
        taskDescription = view.findViewById(R.id.taskDescription)

        // priority toggle btns
        buttonHigh = view.findViewById(R.id.buttonHigh)
        buttonMedium = view.findViewById(R.id.buttonMedium)
        buttonLow = view.findViewById(R.id.buttonLow)

        startTimeText = view.findViewById(R.id.startTimeText)
        endTimeText = view.findViewById(R.id.endTimeText)

        getAlert = view.findViewById(R.id.getAlertBtn)

        taskPriority = SelectedPriority.high

        createTask = view.findViewById(R.id.createTaskBtn)
        modifyAndDeleteLayout = view.findViewById(R.id.modifyAndDeleteLayout)
        modifyTaskBtn = view.findViewById(R.id.modifyTaskBtn)
        deleteTaskBtn = view.findViewById(R.id.deleteTaskBtn)
    }

    fun updateDateList() {
        dateList.clear()
        val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())
        val startOfWeek = calendar.clone() as Calendar
        startOfWeek.set(Calendar.DAY_OF_WEEK, startOfWeek.firstDayOfWeek)

        for (i in 0..6) {
            val date = startOfWeek.time
            val day = dateFormat.format(date)
            val month = monthFormat.format(date)
            val dateItem = DateItem(day, startOfWeek.get(Calendar.DAY_OF_MONTH).toString(), month.toString())
            dateList.add(dateItem)
            startOfWeek.add(Calendar.DAY_OF_YEAR, 1)
        }
        adapter.notifyDataSetChanged()
    }

    fun setButtonState(selectedButton: SelectedPriority) {
        resetButtons()
        val pButton: Button = when(selectedButton){
            SelectedPriority.high -> buttonHigh
            SelectedPriority.medium -> buttonMedium
            SelectedPriority.low -> buttonLow
        }

        pButton.background = ContextCompat.getDrawable(context, R.drawable.selected_btn_style)
        pButton.setTextColor(Color.BLACK)
    }

     fun resetButtons() {
        val defaultTextColor = Color.parseColor(getString(R.string.ffefe9))

        buttonHigh.background = ContextCompat.getDrawable(activity, R.drawable.toggle_btn)
        buttonHigh.setTextColor(defaultTextColor)

        buttonMedium.background = ContextCompat.getDrawable(context, R.drawable.toggle_btn)
        buttonMedium.setTextColor(defaultTextColor)

        buttonLow.background = ContextCompat.getDrawable(context, R.drawable.toggle_btn)
        buttonLow.setTextColor(defaultTextColor)
    }

     fun updateDateText(view: View) {
        // Get the first and last day of the week
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        firstDayOfWeek = calendar.time

        calendar.add(Calendar.DAY_OF_WEEK, 6)
        lastDayOfWeek = calendar.time

        // Format the dates
        val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
        val firstDayFormatted = dateFormat.format(firstDayOfWeek)
        val lastDayFormatted = dateFormat.format(lastDayOfWeek)

         txt_current_month.text = firstDayFormatted + " - " + lastDayFormatted
    }

    override fun onDateClick(dateItem: DateItem) {
        this.taskDate = dateItem
        isDateSelected = true
    }

     fun showTimePickerDialog(text: TextView) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(activity,
            R.style.timePickerStyle
            ,{ _, hourOfDay, minute ->
            updateTimeText(hourOfDay, minute, text)
        }, hour, minute, false)
        timePickerDialog.show()
    }

     fun updateTimeText(hourOfDay: Int, minute: Int, text: TextView) {
         var hour = hourOfDay
         val amPm: String = if (hourOfDay >= 12) {
             if (hourOfDay > 12) hour -= 12
             "PM"
         } else {
             if (hourOfDay == 0) hour = 12
             "AM"
         }

         val time = String.format("%02d:%02d %s", hour, minute, amPm)
         text.text = time
    }

    fun createTaskOnClick() {
        name = taskName.text.toString()
        description = taskDescription.text.toString()

        if(isDateSelected && name.isNotEmpty() && description.isNotEmpty())
        {
            val newTask = Task(
                tittle = name,
                description = description,
                date = taskDate.date,
                day = taskDate.day,
                month = taskDate.month,
                startTime =  startTimeText.text.toString(),
                endTime = endTimeText.text.toString(),
                getAlert = getAlert.isChecked,
                priority = taskPriority,
                isDone = false
            )

            DataClass.addTask(newTask)
            DatabaseBuilder.database.taskDao().insertTask(newTask)
            Toast.makeText(context, "Task Created", Toast.LENGTH_SHORT).show()
            for(i in 0 until fragmentManager.backStackEntryCount)
                fragmentManager.popBackStack()
            navigateToMainPageFrag()

        } else {
            Toast.makeText(context, "Fill All Data!", Toast.LENGTH_SHORT).show()
        }
    }

    fun navigateToMainPageFrag() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainActivityLayout, UtilMethods.selectFragment())
            .addToBackStack(null)
            .commit()
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
    }

    fun modifyAndDeleteView(task: Task) {
        createPageTitle.text = "Mobile App Research"
        taskName.text = task.tittle
        taskDescription.text = task.description
        startTimeText.text = task.startTime
        endTimeText.text = task.endTime
        setButtonState(task.priority)
        getAlert.isChecked = task.getAlert
    }
}