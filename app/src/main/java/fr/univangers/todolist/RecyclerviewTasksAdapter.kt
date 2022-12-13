package fr.univangers.todolist

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerviewTasksAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerviewTasksAdapter.TaskViewHolder>() {
    var cursor : Cursor? = null
    class Task(var name: String, var priority: Priorities) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            Priorities.valueOf(parcel.readString()!!)
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(priority.toString())
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Task> {
            override fun createFromParcel(parcel: Parcel): Task {
                return Task(parcel)
            }

            override fun newArray(size: Int): Array<Task?> {
                return arrayOfNulls(size)
            }
        }
    }

    class TaskViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val wName = itemView.findViewById<TextView>(R.id.textview_task)
        val wPriority = itemView.findViewById<View>(R.id.view_priority)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.line, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        if(cursor!!.moveToPosition(position)) {
            holder.wName.text = cursor!!.getString(cursor!!.getColumnIndexOrThrow(TasksDBHelper.NAME_COL))

            when (cursor!!.getInt(cursor!!.getColumnIndexOrThrow(TasksDBHelper.PRIORITY_COL))) {
                1 -> holder.wPriority.setBackgroundColor(ContextCompat.getColor(context, R.color.high_red))
                2 -> holder.wPriority.setBackgroundColor(ContextCompat.getColor(context, R.color.medium_orange))
                3 -> holder.wPriority.setBackgroundColor(ContextCompat.getColor(context, R.color.low_yellow))
            }
        }

        holder.itemView.tag = position
    }

    override fun getItemCount(): Int {
        return cursor!!.count
    }

    @SuppressLint("NotifyDataSetChanged")
    fun swapCursor(new_cursor : Cursor) {
        cursor?.close()
        cursor = new_cursor
        notifyDataSetChanged()
    }
}