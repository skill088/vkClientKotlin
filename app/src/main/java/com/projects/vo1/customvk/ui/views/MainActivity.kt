package com.projects.vo1.customvk.ui.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.projects.vo1.customvk.R
import com.projects.vo1.customvk.data.device.services.DialogsService
import com.projects.vo1.customvk.ui.dialogs.FragmentDialogs
import com.projects.vo1.customvk.ui.friends.FragmentFriends
import com.projects.vo1.customvk.ui.profile.FragmentProfile
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        fab.visibility = View.INVISIBLE

        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        supportActionBar?.title = resources.getString(R.string.menu_frineds)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, FragmentFriends.newInstance(), "FragmentFriends")
            .addToBackStack("FragmentFriends")
            .commit()

        startService(Intent(this, DialogsService::class.java))
    }


    override fun onBackPressed() {
        when {
            drawer_layout.isDrawerOpen(GravityCompat.START) -> drawer_layout.closeDrawer(
                GravityCompat.START
            )
            supportFragmentManager.backStackEntryCount != 0 -> supportFragmentManager.popBackStack()
            else -> super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.menu_profile -> {
                toolbar.visibility = View.GONE
                supportActionBar?.title = resources.getString(R.string.menu_profile)
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        FragmentProfile.newInstance(),
                        "FragmentProfile"
                    )
                    .commit()
            }
            R.id.menu_friends -> {
                if (supportFragmentManager.findFragmentByTag("FragmentProfile") != null) {
                    setSupportActionBar(toolbar)
                    val toggle = ActionBarDrawerToggle(
                        this,
                        drawer_layout,
                        toolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close
                    )
                    drawer_layout.addDrawerListener(toggle)
                    toggle.syncState()
                    toolbar.visibility = View.VISIBLE
                }
                supportActionBar?.title = resources.getString(R.string.menu_frineds)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.fragment_container,
                            FragmentFriends.newInstance(),
                            "FragmentFriends"
                        )
                        .addToBackStack("FragmentFriends")
                        .commit()
//                }
            }
            R.id.menu_conversations -> {
                if (supportFragmentManager.findFragmentByTag("FragmentProfile") != null) {
                    setSupportActionBar(toolbar)
                    val toggle = ActionBarDrawerToggle(
                        this,
                        drawer_layout,
                        toolbar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close
                    )
                    drawer_layout.addDrawerListener(toggle)
                    toggle.syncState()
                    supportActionBar?.title = resources.getString(R.string.menu_frineds)
                    toolbar.visibility = View.VISIBLE
                }
                    supportActionBar?.title = resources.getString(R.string.menu_conversations)
                    supportFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.fragment_container,
                            FragmentDialogs.newInstance(),
                            "FragmentDialogs"
                        )
                        .addToBackStack("FragmentDialogs")
                        .commit()
            }
            R.id.menu_account_logout -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDestroy() {
        stopService(Intent(this, DialogsService::class.java))
        super.onDestroy()
    }

    companion object {

        fun getIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}