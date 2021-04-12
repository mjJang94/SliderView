package com.mj.sliderview

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager

class RollingViewPager constructor(context: Context, attrs: AttributeSet? = null) :
    ViewPager(context, attrs) {

    private val ACTION_UP = 1
    private val ACTION_MOVE = 2

    private var flingAble = true
    private var smoothScroll = true
    private var scrollHandler: Handler = Handler()
    private var delay = 0L
    private var lastEnableRolling: Boolean = false

    private val autoScrolling = Runnable {
        setCurrentItem(currentItem + 1, smoothScroll)
        startAutoScrolling()
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return try {

            this.flingAble && super.onInterceptTouchEvent(event)
        } catch (var3: Exception) {
            false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when(event.action){

            ACTION_MOVE -> {
                stopAutoScrolling()
            }


            ACTION_UP -> {
                startAutoScrolling()
            }
        }

        return this.flingAble && super.onTouchEvent(event)
    }


    private fun startAutoScrolling() {
        this.scrollHandler.removeCallbacks(this.autoScrolling)
        this.scrollHandler.postDelayed(this.autoScrolling, this.delay)
    }

    private fun stopAutoScrolling() {
        this.scrollHandler.removeCallbacks(this.autoScrolling)
    }

    internal fun setScrollingDelay(millis: Int) {
        val viewpager = ViewPager::class.java
        val scroller = viewpager.getDeclaredField("mScroller")
        scroller.isAccessible = true
        scroller.set(this, DelayScroller(context, millis))

    }

    internal fun setFlingAble(flag: Boolean) {
        this.flingAble = flag
    }

    internal fun enableRolling(enable: Boolean) {
        lastEnableRolling = enable
        if (enable) {
            this.startAutoScrolling()
        } else {
            this.stopAutoScrolling()
        }
    }

    internal fun notifyDataSetChanged() {
        adapter?.notifyDataSetChanged()
        enableRolling(lastEnableRolling)
    }

    internal fun setAutoScrollingSmooth(smoothScroll: Boolean) {
        this.smoothScroll = smoothScroll
    }

    internal fun setDelay(delay: Long) {
        this.delay = delay
    }

    private inner class DelayScroller(context: Context, val durationScroll: Int = 250) :
        Scroller(context, DecelerateInterpolator()) {
        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
            super.startScroll(startX, startY, dx, dy, durationScroll)
        }

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, durationScroll)
        }
    }
}
