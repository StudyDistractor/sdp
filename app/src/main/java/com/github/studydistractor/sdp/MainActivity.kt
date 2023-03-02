package com.github.studydistractor.sdp

import android.media.Image
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.navigation.NavigationView

// Credit: https://www.geeksforgeeks.org/navigation-drawer-in-android/
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the drawer
        drawerLayout = findViewById<DrawerLayout>(R.id.navigation_drawer)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        // Enable the drawer
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // Enable action bar hamburger
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Add the default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<TextFragment>(R.id.fragment_container_view)
            }
        }

        // Add listeners for the nav drawer
        val navView = findViewById<NavigationView>(R.id.navigation_view)
        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_text -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<TextFragment>(R.id.fragment_container_view)
                    }
                    true
                }
                R.id.nav_image -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<ImageFragment>(R.id.fragment_container_view)
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Toggle navigation bar
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        } else super.onOptionsItemSelected(item)
    }
}