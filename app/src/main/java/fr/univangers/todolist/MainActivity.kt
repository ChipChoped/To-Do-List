package fr.univangers.todolist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.angersuniv.mob.tp01.createlayoutandmenu.FakeData


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        displayTasks(this)
    }

    override fun onResume() {
        super.onResume()

        displayTasks(this)
    }

    private fun displayTasks(context: Context) {
        val lv = findViewById<ListView>(R.id.listview_tasks)
        val adapter = TasksAdapter(this)
        lv.adapter = adapter

        for (text in FakeData.getTasks()) {
            val priority: Priorities = when (text.substring(1, 2)) {
                "1" -> Priorities.HIGH
                "2" -> Priorities.MEDIUM
                "3" -> Priorities.LOW
                else -> Priorities.MEDIUM
            }

            adapter.add(text.substring(4), priority)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_task -> {
                val startIntent = Intent(this, AddTask::class.java)
                startActivity(startIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}