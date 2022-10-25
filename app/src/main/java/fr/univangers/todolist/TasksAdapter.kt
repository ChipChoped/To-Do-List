package fr.univangers.todolist

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

class TasksAdapter(private val context: Context) : BaseAdapter() {
    class Task(var name: String, var priority: Priorities)
    private var tasksList: ArrayList<Task> = ArrayList()

    override fun getCount(): Int {
        return tasksList.size
    }

    override fun getItem(position: Int): Task {
        return tasksList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getView(position: Int, convertView: View?, vGroup: ViewGroup?): View {
        var nView: View? = convertView
        if (nView == null)
            nView = LayoutInflater.from(context).inflate(R.layout.line, vGroup, false)

        val wName = nView!!.findViewById<TextView>(R.id.textview_task)
        val wPriority = nView.findViewById<View>(R.id.view_priority)

        wName.text = getItem(position).name;
        when (getItem(position).priority) {
            Priorities.HIGH -> wPriority.setBackgroundColor(ContextCompat.getColor(context, R.color.high_red))
            Priorities.MEDIUM -> wPriority.setBackgroundColor(ContextCompat.getColor(context, R.color.medium_orange))
            Priorities.LOW -> wPriority.setBackgroundColor(ContextCompat.getColor(context, R.color.low_yellow))
        }

        return nView
    }

    fun add(name: String, priority: Priorities) {
        tasksList.add(Task(name, priority))
        notifyDataSetChanged()
    }
}