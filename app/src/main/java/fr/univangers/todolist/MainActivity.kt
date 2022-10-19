package fr.univangers.todolist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import fr.angersuniv.mob.tp01.createlayoutandmenu.FakeData


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);

        val dataTextView = findViewById<TextView> (R.id.textview_data);
        for (text in FakeData.get_tasks()) {
            dataTextView.append(text)
            dataTextView.append("\n")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_task -> {
                val start_intent = Intent(this, AddTask::class.java)
                startActivity(start_intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}