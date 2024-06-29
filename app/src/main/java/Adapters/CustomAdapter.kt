package Adapters

import Models.Task
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R

class CustomAdapter(private val data: List<Task>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cardTittle: TextView = itemView.findViewById(R.id.cardTittle)
        val cardDate: TextView = itemView.findViewById(R.id.cardDate)
        val isDone: CheckBox = itemView.findViewById(R.id.isDone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardTittle.text = data[position].tittle
        holder.cardDate.text = data[position].date
        holder.isDone.isChecked = data[position].isDone
    }

    override fun getItemCount() = data.size
}