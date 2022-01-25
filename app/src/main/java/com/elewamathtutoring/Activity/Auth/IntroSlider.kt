package com.elewamathtutoring.Activity.Auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.elewamathtutoring.R
import com.nelyan.adapter.ImageSliderCustomAdapter
import me.relex.circleindicator.CircleIndicator
import java.util.*

class IntroSlider : AppCompatActivity() {


    var mContext: Context? = null
    var indicator: CircleIndicator? = null
    var mPager: ViewPager? = null
    var list= ArrayList<Int>()
    var listtext: ArrayList<Int>? = null
    var text: ArrayList<Int>? = null
    lateinit var tvBack: TextView
    lateinit var tvSkip: TextView
    lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_slider)
      /*  val w: Window = window
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );*/
        mContext = this
        setView()


    }

    private fun setView() {
        mPager = findViewById(R.id.view_pager)
        indicator = findViewById(R.id.indicator_product)
        btnNext = findViewById(R.id.btnNext)
        tvBack = findViewById(R.id.tvBack)
        tvSkip = findViewById(R.id.tvSkip)

        list.add(R.drawable.icon01)
        list.add(R.drawable.icon02)
        list.add(R.drawable.icon03)

        text = ArrayList()
        text!!.add(R.string.heading1)
        text!!.add(R.string.heading2)
        text!!.add(R.string.heading3)

        listtext = ArrayList()
        listtext!!.add(R.string.page1)
        listtext!!.add(R.string.page1)
        listtext!!.add(R.string.page1)


        tvSkip.setOnClickListener {
            startActivity(Intent(this@IntroSlider, LoginScreen::class.java))


        }
        tvBack.setOnClickListener {
            if (mPager!!.currentItem == 0) {
              //  finish()
                finishAffinity()

            } else {
                mPager!!.setCurrentItem((mPager!!.currentItem - 1))

            }

        }
        btnNext.setOnClickListener {

            if (mPager!!.currentItem == 2) {
                startActivity(Intent(this@IntroSlider, LoginScreen::class.java))

            } else {
                mPager!!.setCurrentItem((mPager!!.currentItem + 1))
            }
        }



        mPager!!.setAdapter(ImageSliderCustomAdapter(this, list, text!!, listtext!!))
        indicator!!.setViewPager(mPager)
        mPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if(mPager!!.currentItem == 2)
                {
                  //  btnNext.text = "Get Started"
                    btnNext.text = "Next"
                }
                else{
                    btnNext.text = "Next"
                }

            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })

    }


    fun getitem(i: Int): Int {
        return mPager!!.getCurrentItem() + i

    }

    override fun onDestroy() {
        super.onDestroy()
    }


}


