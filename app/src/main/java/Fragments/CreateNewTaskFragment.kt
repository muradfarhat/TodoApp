package Fragments

import android.os.Bundle
import android.app.Fragment
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.todoapp.R

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
}