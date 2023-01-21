package com.example.quizapp

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.Display.Mode
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.delay
import org.json.JSONArray
import kotlin.math.log

class ViewModel(val context: Context, val url: String) {

// ...

    // Instantiate the RequestQueue.

    private val queue = Volley.newRequestQueue(context)
    private var arrayList = ArrayList<Model>(10)

    suspend fun fetch(): ArrayList<Model> {
        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val jsonArray = response.getJSONArray("results")
                for (i in 0..9) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val crctans = jsonObject.getString("correct_answer")
                    var mutableList = mutableListOf(crctans)
                    val jsonoption: JSONArray = jsonObject.getJSONArray("incorrect_answers")
                    for (i in 0..jsonoption.length() - 1) {
                        mutableList.add(jsonoption.getString(i))
                    }

                    mutableList.shuffle()

//                Toast.makeText(
//                    context,
//                    mutableList.toString(),
//                    Toast.LENGTH_SHORT
//                ).show()
                    val question = jsonObject.getString("question")
                    val option1 = mutableList.get(0)
                    val option2 = mutableList.get(1)
                    val option3 = mutableList.get(2)
                    val option4 = mutableList.get(3)
                    val crctopt =
                        mutableList.indexOf(jsonObject.getString("correct_answer")).toString()
                    arrayList.add(Model(question, option1, option2, option3, option4, crctopt))
                    Log.d("waring", arrayList.get(i).toString())
                    Log.d("waring", arrayList.size.toString())

                }


            },
            { error ->
                Toast.makeText(
                    context,
                    "Some error occured please try after some time",
                    Toast.LENGTH_LONG
                ).show()
                (context as Activity).finish()
            }
        )
        queue.add(jsonObjectRequest)
        delay(2000)
        Log.d("waringa", arrayList.size.toString())
        return arrayList
    }

}