package com.example.quizapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*


class QuizActivity : AppCompatActivity() {
    private var count = 0
    private var crct = -1
    private var selected = -1
    lateinit var question: TextView
    lateinit var option1: TextView
    lateinit var option2: TextView
    lateinit var option3: TextView
    lateinit var option4: TextView
    lateinit var title: TextView
    lateinit var ll: LinearLayout
    lateinit var pll: LinearLayout
    lateinit var finall: LinearLayout
    private var array = ArrayList<Model>()
    lateinit var submit: Button
    lateinit var finaliv: ImageView
    lateinit var wish: TextView
    lateinit var percentage: TextView
    lateinit var attempt: TextView
    lateinit var finish:ImageButton


    var i = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        initialize()
        val intent = intent
        var url = "https://opentdb.com/api.php?amount=10&type=multiple"
        val dif= intent.getStringExtra("difficulty")?.toLowerCase()
        val cat=intent.getStringExtra("category")
        title.text=intent.getStringExtra("categoryname")
        if (!cat.equals("&category=8"))
            url=url+cat
        if (!dif.equals("&difficulty=any difficulty"))
            url=url+dif
        val viewModel = ViewModel(this, url)
        CoroutineScope(Dispatchers.Main).launch {
            array = viewModel.fetch()
            showdata(array)
        }
        onclicklistener()
    }

    private fun initialize() {
        question = findViewById(R.id.questiontv)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)
        submit = findViewById(R.id.Submit)
        ll = findViewById(R.id.questionll)
        finall = findViewById(R.id.finalscreen)
        title = findViewById(R.id.titlequiztv)
        finaliv = findViewById(R.id.finaliv)
        wish = findViewById(R.id.wish)
        attempt = findViewById(R.id.attempt)
        percentage = findViewById(R.id.percent)
        finish=findViewById(R.id.finish)
        pll=findViewById(R.id.progress)
    }

    @SuppressLint("ResourceAsColor")
    fun onclicklistener() {
        submit.setOnClickListener {
            if (i < 10) {
                i++;
                CoroutineScope(Dispatchers.Main).launch {
                    if (selected == -1) {
                        Toast.makeText(
                            this@QuizActivity,
                            "Please select a option",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (crct == selected) {
                        if (selected == 0) option1.setBackgroundResource(R.drawable.optionbggreen)
                        else if (selected == 1) option2.setBackgroundResource(R.drawable.optionbggreen)
                        else if (selected == 2) option3.setBackgroundResource(R.drawable.optionbggreen)
                        else if (selected == 3) option4.setBackgroundResource(R.drawable.optionbggreen)
                        if(i<10)showdata(array)
                        count++
                    } else {
                        if (selected == 0) option1.setBackgroundResource(R.drawable.optionbgred)
                        else if (selected == 1) option2.setBackgroundResource(R.drawable.optionbgred)
                        else if (selected == 2) option3.setBackgroundResource(R.drawable.optionbgred)
                        else if (selected == 3) option4.setBackgroundResource(R.drawable.optionbgred)
                        if (crct == 0) option1.setBackgroundResource(R.drawable.optionbggreen)
                        else if (crct == 1) option2.setBackgroundResource(R.drawable.optionbggreen)
                        else if (crct == 2) option3.setBackgroundResource(R.drawable.optionbggreen)
                        else if (crct == 3) option4.setBackgroundResource(R.drawable.optionbggreen)
                        if(i<10)showdata(array)
                    }
                }
            } else {

                ll.visibility = GONE
                title.visibility= GONE
                finish.visibility= VISIBLE
                attempt.text = "from that ${count} answers are correct"
                percentage.text = "${count * 10}%"
                if (count >= 5) {
                    finaliv.setBackgroundResource(R.drawable.win)
                    wish.text = "Congrats!"
                    percentage.setTextColor(R.color.green)
                } else {
                    finaliv.setBackgroundResource(R.drawable.better)
                    wish.text = "Better luck next time!"
                    percentage.setTextColor(R.color.red)
                }
                finall.visibility = VISIBLE
            }
        }
        option1.setOnClickListener {
            option2.setBackgroundResource(R.drawable.optionbg)
            option3.setBackgroundResource(R.drawable.optionbg)
            option4.setBackgroundResource(R.drawable.optionbg)
            option1.setBackgroundResource(R.drawable.optionbgorange)
            selected = 0
        }
        option2.setOnClickListener {
            option1.setBackgroundResource(R.drawable.optionbg)
            option3.setBackgroundResource(R.drawable.optionbg)
            option4.setBackgroundResource(R.drawable.optionbg)
            option2.setBackgroundResource(R.drawable.optionbgorange)
            selected = 1
        }
        option3.setOnClickListener {
            option2.setBackgroundResource(R.drawable.optionbg)
            option1.setBackgroundResource(R.drawable.optionbg)
            option4.setBackgroundResource(R.drawable.optionbg)
            option3.setBackgroundResource(R.drawable.optionbgorange)
            selected = 2
        }
        option4.setOnClickListener {
            option2.setBackgroundResource(R.drawable.optionbg)
            option3.setBackgroundResource(R.drawable.optionbg)
            option1.setBackgroundResource(R.drawable.optionbg)
            option4.setBackgroundResource(R.drawable.optionbgorange)
            selected = 3
        }
        finish.setOnClickListener{
            finish()
        }
    }

    suspend fun showdata(array: ArrayList<Model>) {
        delay(1000)
        title.visibility = VISIBLE
        ll.visibility = VISIBLE
        pll.visibility= GONE
        option1.setBackgroundResource(R.drawable.optionbg)
        option2.setBackgroundResource(R.drawable.optionbg)
        option3.setBackgroundResource(R.drawable.optionbg)
        option4.setBackgroundResource(R.drawable.optionbg)
        question.text = (i + 1).toString() + " . " + array.get(i).question
        option1.text = "A: " + array.get(i).opt1
        option2.text = "B: " + array.get(i).opt2
        option3.text = "C: " + array.get(i).opt3
        option4.text = "D: " + array.get(i).opt4
        crct = Integer.parseInt(array.get(i).crct)

    }


}