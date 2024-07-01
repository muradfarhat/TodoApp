package Fragments

import Adapters.DateAdapter
import Models.DateItem
import android.os.Bundle
import android.app.Fragment
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

enum class SelectedPriority {
    high,
    medium,
    low
}

class CreateNewTaskFragment : Fragment() {

    private lateinit var buttonHigh: Button
    private lateinit var buttonMedium: Button
    private lateinit var buttonLow: Button

    private lateinit var taskPriority: SelectedPriority

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_new_task, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.calendar_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val dateList = generateDateList()
        val adapter = DateAdapter(dateList)
        recyclerView.adapter = adapter

        // priority toggle btns
        buttonHigh = view.findViewById(R.id.buttonHigh)
        buttonMedium = view.findViewById(R.id.buttonMedium)
        buttonLow = view.findViewById(R.id.buttonLow)

        setButtonState(buttonHigh)
        taskPriority = SelectedPriority.high

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

        return view
    }

    private fun setButtonState(selectedButton: Button) {
        resetButtons()

        selectedButton.background = ContextCompat.getDrawable(context, R.drawable.selected_btn_style)
        selectedButton.setTextColor(Color.BLACK)
    }

    private fun resetButtons() {
        val defaultTextColor = Color.parseColor("#FFEFE9")

        buttonHigh.background = ContextCompat.getDrawable(context, R.drawable.toggle_btn)
        buttonHigh.setTextColor(defaultTextColor)

        buttonMedium.background = ContextCompat.getDrawable(context, R.drawable.toggle_btn)
        buttonMedium.setTextColor(defaultTextColor)

        buttonLow.background = ContextCompat.getDrawable(context, R.drawable.toggle_btn)
        buttonLow.setTextColor(defaultTextColor)
    }

    private fun generateDateList(): List<DateItem> {
        // Generate a list of DateItem objects for demonstration
        val dateList = mutableListOf<DateItem>()
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())
        for (i in 0..30) {
            val date = calendar.time
            val day = dateFormat.format(date)
            val dateItem = DateItem(day, calendar.get(Calendar.DAY_OF_MONTH).toString())
            dateList.add(dateItem)
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        return dateList
    }
}