package fr.univangers.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerviewTasksAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerviewTasksAdapter.TaskViewHolder>() {
    class Task(var name: String, var priority: Priorities)

    class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val wName = itemView.findViewById<TextView>(R.id.textview_task)
        val wPriority = itemView.findViewById<View>(R.id.view_priority)
    }

    private var tasksList: ArrayList<Task> = ArrayList<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.line, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val item = tasksList[position]
        holder.wName.text = item.name

        when (item.priority) {
            Priorities.HIGH -> holder.wPriority.setBackgroundColor(ContextCompat.getColor(context, R.color.high_red))
            Priorities.MEDIUM -> holder.wPriority.setBackgroundColor(ContextCompat.getColor(context, R.color.medium_orange))
            Priorities.LOW -> holder.wPriority.setBackgroundColor(ContextCompat.getColor(context, R.color.low_yellow))
        }

        holder.itemView.tag = position
    }

    override fun getItemCount() = tasksList.size

    fun add(name: String, priority: Priorities) {
        tasksList.add(Task(name, priority))
        notifyItemInserted(tasksList.size-1)
    }

    fun delete(position: Int) {
        println(tasksList.size)
        tasksList.removeAt(position)
        println(tasksList.size)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, tasksList.size - position)
    }
}