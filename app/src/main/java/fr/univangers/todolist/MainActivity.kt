package fr.univangers.todolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.angersuniv.mob.tp01.createlayoutandmenu.FakeData


class MainActivity : AppCompatActivity() {
    private val adapter = RecyclerviewTasksAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview_tasks)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.itemView.tag as Int
                adapter.delete(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        if (savedInstanceState != null) {
            adapter.setTasks(savedInstanceState.getParcelableArrayList<RecyclerviewTasksAdapter.Task>("tasksList")!!)
        }
        else {
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
    }

    private val addTaskResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result -> when(result.resultCode){
            Activity.RESULT_OK->{
                val intent = result.data
                val name : String? = intent?.getStringExtra("NAME")
                val priority = when(intent?.getIntExtra("PRIORITY", 0)){
                    0 -> Priorities.LOW
                    1 -> Priorities.MEDIUM
                    else -> Priorities.HIGH
                }

                adapter.add(name!!, priority)
            }
            else -> {}
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList("tasksList", adapter.tasksList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_task -> {
                val startIntent = Intent(this, AddTask::class.java)
                addTaskResult.launch(startIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onTaskLinePressed(view: View) {
        val task = view.findViewById<TextView>(R.id.textview_task).text
        Toast.makeText(this@MainActivity, task, Toast.LENGTH_SHORT).show()
    }
}