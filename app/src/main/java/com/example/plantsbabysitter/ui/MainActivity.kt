package com.example.plantsbabysitter.ui

import android.os.Bundle
import com.example.plantsbabysitter.R
import com.example.plantsbabysitter.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = R.id.HomeFragment
        goToFragment(fragmentManager.findFragmentById(homeFragment))
    }
}