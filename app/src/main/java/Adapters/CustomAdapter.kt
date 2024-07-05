package Adapters

import Interfaces.OnCheckBoxClickListener
import Models.Task
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R

class CustomAdapter(private val data: List<Task>, private val listener: OnCheckBoxClickListener): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val taskCard: CardView = itemView.findViewById(R.id.recycler_view_card)
        val cardTittle: TextView = itemView.findViewById(R.id.cardTittle)
        val cardDate: TextView = itemView.findViewById(R.id.cardDate)
        val isDone: CheckBox = itemView.findViewById(R.id.isDone)
        val delete: ImageButton = itemView.findViewById(R.id.deleteTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_card, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardTittle.text = data[position].tittle
        holder.cardDate.text = "${data[position].date} ${data[position].month}"
        holder.isDone.isChecked = data[position].isDone
        holder.isDone.setOnClickListener {
            listener.onClickCheckBox(data[position].id)
        }
        holder.delete.setOnClickListener {
            listener.onDeleteClick(data[position].id)
            listener.deleteTaskCallBack()
            notifyDataSetChanged()
        }
        holder.taskCard.setOnClickListener {
            listener.onClickTaskCard(data[position])
        }
    }

    override fun getItemCount() = data.size
}