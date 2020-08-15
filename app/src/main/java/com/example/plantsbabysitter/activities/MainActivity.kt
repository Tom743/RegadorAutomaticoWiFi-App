package com.example.plantsbabysitter.activities

import android.os.Bundle
import com.example.plantsbabysitter.R
import com.example.plantsbabysitter.baseactivity.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment1 = R.id.HomeFragment
        goToFragment(fragmentManager.findFragmentById(fragment1))
    }
}