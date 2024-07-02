package Fragments

import Adapters.DateAdapter
import Enum.SelectedPriority
import Interfaces.OnDateClickListener
import Models.DataClass
import Models.DateItem
import Models.Task
import Util.UtilMethods
import android.app.ActivityManager.TaskDescription
import android.os.Bundle
import android.app.Fragment
import android.app.TimePickerDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CreateNewTaskFragment : Fragment(), OnDateClickListener {

    // back btn
    private lateinit var backBtn: ImageButton

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
    private lateinit var startTime: Time
    private lateinit var endTime: Time

    private lateinit var startTimeText: TextView
    private lateinit var endTimeText: TextView

    // get alert
    private lateinit var getAlert: Switch

    // create task btn
    private lateinit var createTask: Button

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DateAdapter
    private lateinit var calendar: Calendar
    private var dateList: MutableList<DateItem> = mutableListOf()
    private lateinit var firstDayOfWeek: Date
    private lateinit var lastDayOfWeek: Date

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
        setButtonState(buttonHigh)

        return view
    }

    private fun onClickListeners(view: View) {
        backBtn.setOnClickListener {
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
            setButtonState(buttonHigh)
            taskPriority = SelectedPriority.high
        }
        buttonMedium.setOnClickListener {
            setButtonState(buttonMedium)
            taskPriority = SelectedPriority.medium
        }
        buttonLow.setOnClickListener {
            setButtonState(buttonLow)
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
        backBtn = view.findViewById(R.id.backBtn)
        // Calender
        calendar = Calendar.getInstance()
        recyclerView = view.findViewById(R.id.calendar_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adapter = DateAdapter(dateList, this)
        recyclerView.adapter = adapter

        calendar_prev_button = view.findViewById(R.id.calendar_prev_button)
        calendar_next_button = view.findViewById(R.id.calendar_next_button)

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

        createTask = view.findViewById(R.id.createTaskBtn)

        taskPriority = SelectedPriority.high
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

    fun setButtonState(selectedButton: Button) {
        resetButtons()

        selectedButton.background = ContextCompat.getDrawable(context, R.drawable.selected_btn_style)
        selectedButton.setTextColor(Color.BLACK)
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

        view.findViewById<TextView>(R.id.txt_current_month).text = firstDayFormatted + " - " + lastDayFormatted
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
                date = "${taskDate.date} ${taskDate.month}",
                startTime = startTimeText.text.toString(),
                endTime = endTimeText.text.toString(),
                getAlert = getAlert.isChecked,
                priority = taskPriority,
                isDone = false
            )

            DataClass.addTask(newTask)
            Toast.makeText(context, "Task Created", Toast.LENGTH_SHORT).show()
            navigateToMainPageFrag()

        } else {
            Toast.makeText(context, "Fill All Data!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMainPageFrag() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainActivityLayout, UtilMethods.selectFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}