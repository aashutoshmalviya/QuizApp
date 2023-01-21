package com.example.quizapp

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

data class Model(val question:String, val opt1:String, val opt2:String, val opt3:String, val opt4:String, val crct:String){
    
}