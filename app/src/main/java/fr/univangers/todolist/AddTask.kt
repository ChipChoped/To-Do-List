package fr.univangers.todolist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import fr.angersuniv.mob.tp01.createlayoutandmenu.FakeData


class AddTask : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
    }

    @SuppressLint("ResourceType")
    fun onButtonAddPressed(view: View) {
        val dataEditText : String = findViewById<EditText>(R.id.edittext_task).text.toString()
        if (dataEditText == "")
            Toast.makeText(this@AddTask, "Veillez donner un nom à votre tâche", Toast.LENGTH_SHORT)
                .show()
        else {
            val radioHigh = findViewById<RadioButton>(R.id.radio_high)
            val radioMedium = findViewById<RadioButton>(R.id.radio_medium)

            val priority : String = if (radioHigh.isChecked) {
                "<1> "
            } else if (radioMedium.isChecked) {
                "<2> "
            } else {
                "<3> "
            }

            FakeData.tasks_list.add(priority + dataEditText)
            Toast.makeText(this@AddTask, "Nouvelle tâche ajoutée !", Toast.LENGTH_SHORT).show()
        }
    }
}