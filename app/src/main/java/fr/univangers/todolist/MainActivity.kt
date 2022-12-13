package fr.univangers.todolist

import android.app.Activity
import android.content.ContentValues
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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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
                delete(position)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        createCursor()

        if (adapter.itemCount == 0) {
            val dbHelper = TasksDBHelper(this)
            FakeData.insert_fake_data(dbHelper.writableDatabase)
            dbHelper.close()
        }
    }

    private val addTaskResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result -> when(result.resultCode){
            Activity.RESULT_OK->{
                val intent = result.data
                val name : String? = intent?.getStringExtra("NAME")
                val priority : Int? = intent?.getIntExtra("PRIORITY", 2)

                add(name!!, priority!!)
                //Toast.makeText(this@MainActivity, "Nouvelle tâche ajoutée !", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    fun add(name: String, priority: Int) {
        val dbHelper = TasksDBHelper(this)
        val db = dbHelper.writableDatabase

        val cv = ContentValues()
        cv.put(TasksDBHelper.NAME_COL, name)
        cv.put(TasksDBHelper.PRIORITY_COL, priority)

        db.insert(TasksDBHelper.TABLE_NAME, null, cv)
        dbHelper.close()
        createCursor()
    }

    fun delete(position: Int) {
        val dbHelper = TasksDBHelper(this)
        val db = dbHelper.writableDatabase
        val cursor = adapter.cursor

        if (cursor!!.moveToPosition(position)) {
            val id = cursor.getString(cursor.getColumnIndexOrThrow(TasksDBHelper.ID_COL))
            db.delete(TasksDBHelper.TABLE_NAME, "ID = ?", arrayOf(id))
        }

        dbHelper.close()
        createCursor()
    }

    fun createCursor() {
        val dbHelper = TasksDBHelper(this)
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            TasksDBHelper.TABLE_NAME,
            arrayOf(TasksDBHelper.ID_COL, TasksDBHelper.NAME_COL, TasksDBHelper.PRIORITY_COL),
            "",
            arrayOf(),
            "",
            "",
            ""
        )
        adapter.swapCursor(cursor)
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