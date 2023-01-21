package com.example.quizapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var start: Button
    lateinit var spinnercategory: Spinner
    lateinit var spinnerdifficulty: Spinner
    lateinit var nameev: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinnercategory = findViewById(R.id.spinnercategory)
        spinnerdifficulty = findViewById(R.id.spinnerdifficulty)
        start = findViewById(R.id.startbt)
        nameev = findViewById(R.id.nameev)
        ArrayAdapter.createFromResource(
            this,
            R.array.category_array,
            R.layout.color_spinner
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_color)
            // Apply the adapter to the spinner
            spinnercategory.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            this,
            R.array.difficulty_array,
            R.layout.color_spinner
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_color)
            // Apply the adapter to the spinner
            spinnerdifficulty.adapter = adapter
        }
        start.setOnClickListener {
            if (!nameev.text.toString().isEmpty()){
                intent = Intent(this, QuizActivity::class.java)
                intent.putExtra("name", nameev.text)
                intent.putExtra(
                    "category",
                    "&category=" + (8 + spinnercategory.selectedItemPosition).toString()
                )
                intent.putExtra("categoryname", spinnercategory.selectedItem.toString())
                intent.putExtra(
                    "difficulty",
                    "&difficulty=" + spinnerdifficulty.selectedItem.toString()
                )
            startActivity(intent)
            }else{
            nameev.error="Please enter your name"
            }

        }

    }
}