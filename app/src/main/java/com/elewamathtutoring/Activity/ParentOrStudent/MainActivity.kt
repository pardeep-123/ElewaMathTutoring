package com.elewamathtutoring

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.elewamathtutoring.Fragment.ProfileFragment
import com.elewamathtutoring.Fragment.ScheduleFragment
import com.elewamathtutoring.Fragment.SearchFragment
import com.elewamathtutoring.Activity.Chat.MessagesTabFragment

import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    val context: Context = this
    lateinit var bottomBar: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT;
        bottomBar = findViewById(R.id.bottomNav)
        bottomBar.setOnNavigationItemSelectedListener(this)
        switchFragment(R.id.rframe,SearchFragment())
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val f = supportFragmentManager.findFragmentById(R.id.rframe)
        when (item.itemId) {
            R.id.menu_search -> if (f !is SearchFragment) {
                Log.d("CLICKED", "HOME")

                if (f !is SearchFragment) {
                    switchFragment(R.id.rframe, SearchFragment())
                }
            }
            R.id.menu_profile -> if (f !is ProfileFragment) {
                Log.d("CLICKED", "PROFILE")

                if (f !is ProfileFragment) {
                    switchFragment(R.id.rframe, ProfileFragment())
                }
            }
            R.id.media_schedule -> if (f !is ScheduleFragment) {
                Log.d("CLICKED", "SCHEDULE")
                if (f !is ScheduleFragment) {
                    switchFragment(R.id.rframe, ScheduleFragment())
                }
            }
            R.id.menu_message -> if (f !is MessagesTabFragment) {
                Log.d("CLICKED", "MESSAGE")
                if (f !is MessagesTabFragment) {
                    switchFragment(R.id.rframe, MessagesTabFragment())
                }
            }
        }
        return true
    }
    protected fun switchFragment(main_frame: Int, fragment: Fragment?) {
        val fragmentTransaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(main_frame, fragment!!)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            finishAffinity()
            //additional code
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}