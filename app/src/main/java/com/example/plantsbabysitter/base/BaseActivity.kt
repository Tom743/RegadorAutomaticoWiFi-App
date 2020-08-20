package com.example.plantsbabysitter.base

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.plantsbabysitter.R

open class BaseActivity : AppCompatActivity() {

    protected val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    fun goToFragment(fragment: Fragment?) {
        val transaction = fragmentManager.beginTransaction()
        if (fragment != null) {
            transaction.replace(getVisibleFragment().id, fragment)
        }
        transaction.commit()
    }

    private fun getVisibleFragment(): Fragment {
        val fragments = fragmentManager.fragments
        for (fragment in fragments) {
            if (fragment != null && fragment.isVisible) {
                return fragment
            }
        }
        return fragments[0]
    }

    private var progressBar: ProgressBar? = null

    fun setProgressBar(bar: ProgressBar) {
        progressBar = bar
    }

    fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar?.visibility = View.INVISIBLE
    }

    public override fun onStop() {
        super.onStop()
        hideProgressBar()
    }

}