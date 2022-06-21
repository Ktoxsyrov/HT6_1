package com.example.ht6_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ht6_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container1, FragmentOne.newInstance())
            .commit()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container2, FragmentTwo.newInstance())
            .commit()

    }
}