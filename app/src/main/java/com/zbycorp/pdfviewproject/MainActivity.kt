package com.zbycorp.pdfviewproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onBasicClick(v: View) {
        navigate(BasicUseActivity::class.java)
    }

    fun onComplexClick(v: View) {
        navigate(ComplexUseActivity::class.java)
    }


    fun Activity.navigate(clazz: Class<*>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }
}
