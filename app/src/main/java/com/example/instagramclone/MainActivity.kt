package com.example.instagramclone


import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import com.example.instagramclone.Fragments.HomeFragment
import com.example.instagramclone.Fragments.NotificationFragment
import com.example.instagramclone.Fragments.profileFragment
import com.example.instagramclone.Fragments.searchFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    internal var Fragment:Fragment?=null

private val Onnavigationbar=BottomNavigationView.OnNavigationItemSelectedListener {

    when(it.itemId) {
        R.id.navigation_home -> {

            movefragment(HomeFragment())
            return@OnNavigationItemSelectedListener true
        }
        R.id.navigatio_search -> {

            movefragment(searchFragment())
            return@OnNavigationItemSelectedListener true

        }
        R.id.navigation_heart -> {

         movefragment(NotificationFragment())
            return@OnNavigationItemSelectedListener true
        }
        R.id.navigation_add -> {

            return@OnNavigationItemSelectedListener true
        }
        R.id.navigation_profile -> {

            movefragment(profileFragment())

            return@OnNavigationItemSelectedListener true
        }
        R.id.text_notifications -> {


            return@OnNavigationItemSelectedListener true
        }
    }


    false

    }





lateinit var Textview:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(
            R.id.mainframe,
           HomeFragment()
        ).commit()
nav_view.setOnNavigationItemSelectedListener (Onnavigationbar)

    }

    private fun movefragment(framgment:Fragment){
        Fragment=framgment
        if (Fragment != null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.mainframe,
                Fragment!!
            ).commit()
        }


    }
}
