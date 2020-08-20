package com.example.plantsbabysitter.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
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

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    public override fun onStop() {
        super.onStop()
        hideProgressBar()
    }

}