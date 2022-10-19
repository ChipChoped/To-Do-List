package fr.univangers.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class AddTask : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
    }

    fun onButtonAddPressed(view: View) {
        Toast.makeText(this@AddTask, "Tâche ajoutée", Toast.LENGTH_SHORT).show()
    }
}