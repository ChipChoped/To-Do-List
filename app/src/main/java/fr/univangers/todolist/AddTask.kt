package fr.univangers.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import fr.angersuniv.mob.tp01.createlayoutandmenu.FakeData

class AddTask : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
    }

    fun onButtonAddPressed(view: View) {
        var dataEditText = findViewById<EditText>(R.id.edittext_task).text.toString()
        if (dataEditText == "")
            Toast.makeText(this@AddTask, "Veillez donner un nom à votre tâche", Toast.LENGTH_SHORT)
                .show()
        else {
            val radioButtonGroup = findViewById<RadioGroup>(R.id.radiogroup_priority)
            when (radioButtonGroup.checkedRadioButtonId) {
                0 -> dataEditText = "<1> $dataEditText"
                1 -> dataEditText = "<2> $dataEditText"
                2 -> dataEditText = "<3> $dataEditText"
            }

            FakeData.tasks_list.add(dataEditText)
            Toast.makeText(this@AddTask, FakeData.get_tasks().last(), Toast.LENGTH_SHORT).show()
        }
    }
}