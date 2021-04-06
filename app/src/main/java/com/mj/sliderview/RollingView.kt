package com.mj.sliderview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.slider_view.view.*

@Suppress("MemberVisibilityCanPrivate")
class RollingView constructor(context: Context, val attrs: AttributeSet? = null) : FrameLayout(context, attrs) {
    private var enableLooping = true
    private var enableIndicator = true


    init {
        initView()
    }

    private fun initView() {
        inflate(context, R.layout.slider_view, this)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SliderView)
        val indicatorRes = typedArray.getResourceId(R.styleable.SliderView_indicatorRes, R.drawable.default_indicator)
        var enableRolling = typedArray.getBoolean(R.styleable.SliderView_enableRolling, false)
        val flingAble = typedArray.getBoolean(R.styleable.SliderView_flingAble, true)
        val smoothScroll = typedArray.getBoolean(R.styleable.SliderView_smoothScroll, true)
        val rollingDelay = typedArray.getInt(R.styleable.SliderView_rollingDelay, 3000)
        val scrollingDelay = typedArray.getInt(R.styleable.SliderView_scrollingDelay, 250)
        enableIndicator = typedArray.getBoolean(R.styleable.SliderView_enableIndicator, true)
        val indicatorMargin = typedArray.getDimensionPixelSize(R.styleable.SliderView_indicatorMargin, resources.getDimensionPixelSize(R.dimen.default_indicator_margin))
        val bottomMargin = typedArray.getDimensionPixelSize(R.styleable.SliderView_bottomMargin, resources.getDimensionPixelSize(R.dimen.default_bottom_margin))
        val topMargin = typedArray.getDimensionPixelSize(R.styleable.SliderView_topMargin, resources.getDimensionPixelSize(R.dimen.default_top_margin))
        enableLooping = typedArray.getBoolean(R.styleable.SliderView_enableLooping, true)

        if (!enableLooping) {
            enableRolling = false
        }

        setIndicatorResources(indicatorRes, indicatorMargin)
        setEnableRolling(enableRolling)
        setFlingAble(flingAble)
        setSmoothScroll(smoothScroll)
        setRollingDelay(rollingDelay)
        setScrollingDelay(scrollingDelay)
        setMargins(topMargin, bottomMargin)

        typedArray.recycle()
    }

    /**
     * getting [ViewPager] object
     */
    fun getViewPager() = viewPager

    /**
     * set Indicator resource property
     * @param [resId] resource id to show as indicator
     * @param [margin] margin of each indicator
     */
    fun setIndicatorResources(resId: Int, margin: Int) {
        indicator.setIndicatorResource(resId, margin)
    }

    /**
     * enable rolling feature
     * @param [enableRolling] enable flag of rolling
     */
    fun setEnableRolling(enableRolling: Boolean) {
        viewPager.enableRolling(enableRolling)
    }

    /**
     * set FlingAble of ViewPager
     * @param [flingAble] true - user can move by touch
     */
    fun setFlingAble(flingAble: Boolean) {
        viewPager.setFlingAble(flingAble)
    }

    /**
     * set Smooth animation when scrolling of ViewPager
     * @param[smoothScroll] true - enable Smooth animation
     */
    fun setSmoothScroll(smoothScroll: Boolean) {
        viewPager.setAutoScrollingSmooth(smoothScroll)
    }

    /**
     * set Rolling Delay
     * @param[rollingDelay] each page will move within this delay
     */
    fun setRollingDelay(rollingDelay: Int) {
        viewPager.setDelay(rollingDelay.toLong())
    }

    /**
     * set Scrolling Delay
     * Scrolling Delay is scroll delay affect on user move handle or rolling with smooth animation
     *
     * @param[scrollingDelay] each page will move within this delay
     */
    fun setScrollingDelay(scrollingDelay: Int) {
        viewPager.setScrollingDelay(scrollingDelay)
    }

    /**
     * set enabled state of looping feature
     * warning, you need call this methods before call [setAdapter]
     *
     * @param[enable] true - enable looping feature, default is true.
     */
    fun setEnableLooping(enable: Boolean) {
        enableLooping = enable
    }

    /**
     * set Adapter of ViewPager
     * Adapter will extend RollingViewPagerAdapter
     *
     * @param [adapter] adapter object
     */
    fun <T> setAdapter(adapter: RollingViewPagerAdapter<T>) {
        adapter.enableLooping(enableLooping)

        viewPager.adapter = adapter
        viewPager.notifyDataSetChanged()

        indicator.setViewPager(viewPager)
        indicator.setHideIndicator(!enableIndicator)
        indicator.notifyDataSetChanged()
    }

    /**
     * set Visible of indicator
     * @param[enable] true - VISIBLE
     */
    fun setEnableIndicator(enable: Boolean) {
        enableIndicator = enable
    }

    /**
     * set margin between bottom and indicator
     * @param[margin] bottomMargin of LayoutParams of indicator
     */
    fun setMargins(topMargin: Int, bottomMargin: Int) {
        val params = indicator.layoutParams as FrameLayout.LayoutParams
        params.setMargins(0,topMargin,0,bottomMargin)
        indicator.layoutParams = params
    }

    /**
     * move to prev pages
     */
    @JvmOverloads
    fun movePrevPage(animate: Boolean = true) {
        if (viewPager.currentItem == 0) return
        viewPager.setCurrentItem(viewPager.currentItem - 1, animate)
    }

    /**
     * move to next pages
     */
    @JvmOverloads
    fun moveNextPage(animate: Boolean = true) {
        if (viewPager.currentItem == viewPager.adapter?.count ?: 0 - 1) return
        viewPager.setCurrentItem(viewPager.currentItem + 1, animate)
    }
}