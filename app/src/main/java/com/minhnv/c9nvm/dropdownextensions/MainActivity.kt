package com.minhnv.c9nvm.dropdownextensions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.minhnv.c9nvm.dropdownextension.DropDown

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val dpDemo: DropDown = findViewById(R.id.dpDemo)


        val list = mutableListOf(
            DropDownObj("1"), DropDownObj("2"), DropDownObj("3"), DropDownObj("4"), DropDownObj("5")
        )


        //not needed
        dpDemo.setListData(list)



        dpDemo.addOnSelectedChangeListener(object : DropDown.OnSelectedListener {
            override fun onSelected(text: String, position: Int) {
                Toast.makeText(this@MainActivity, "value selected $text", Toast.LENGTH_SHORT).show()
            }


        })


    }



}