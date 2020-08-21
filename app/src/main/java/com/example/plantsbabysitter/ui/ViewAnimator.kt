package com.example.plantsbabysitter.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

object ViewAnimator {
    fun viewInvisibleAnimator(view: View) {
        view.animate()
            .alpha(0.0f)
            .setDuration(500)/*
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    view.visibility = View.INVISIBLE
                }
            })*/
    }

    fun viewVisibleAnimator(view: View) {
        view.animate()
            .alpha(1.0f)
            .setDuration(500)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    view.visibility = View.VISIBLE
                }
            })
    }
}