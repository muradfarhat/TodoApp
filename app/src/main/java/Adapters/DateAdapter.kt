package Adapters

import Interfaces.OnDateClickListener
import Models.DateItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R

class DateAdapter(private val dateList: List<DateItem>, private val listener: OnDateClickListener) :
    RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    public var selectedPosition = RecyclerView.NO_POSITION
    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDay: TextView = itemView.findViewById(R.id.tvDay)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDateClick(dateList[position])
                    notifyItemChanged(selectedPosition)
                    selectedPosition = position
                    notifyItemChanged(selectedPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_calender_day, parent, false)
//        val screenWidth = parent.context.resources.displayMetrics.widthPixels
//        val itemWidth = screenWidth / 7
//        view.layoutParams = ViewGroup.LayoutParams(itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val dateItem = dateList[position]

        this.selectItemView(holder, position, dateItem)
    }

    fun selectItemView(holder: DateViewHolder, position: Int, dateItem: DateItem) {
        holder.tvDay.text = dateItem.day
        holder.tvDate.text = dateItem.date

        // Update the background color based on the selected state
        if (position == selectedPosition) {
            holder.itemView.setBackgroundResource(R.drawable.selected_date_item)
            holder.itemView.findViewById<TextView>(R.id.tvDate).setTextColor(
                holder.itemView.context.getColor(R.color.color_BA83DE)
            )
            holder.itemView.findViewById<TextView>(R.id.tvDay).setTextColor(
                holder.itemView.context.getColor(R.color.color_BA83DE)
            )
            holder.itemView.findViewById<View>(R.id.smallCircle).setBackgroundResource(R.drawable.small_circle)

        } else {
            holder.itemView.setBackgroundColor(holder.itemView.context.getColor(R.color.black))
            holder.itemView.findViewById<TextView>(R.id.tvDate).setTextColor(
                holder.itemView.context.getColor(R.color.white)
            )
            holder.itemView.findViewById<TextView>(R.id.tvDay).setTextColor(
                holder.itemView.context.getColor(R.color.white)
            )
            holder.itemView.findViewById<View>(R.id.smallCircle).setBackgroundColor(
                holder.itemView.context.getColor(R.color.black)
            )
        }
    }

    override fun getItemCount() = dateList.size
}