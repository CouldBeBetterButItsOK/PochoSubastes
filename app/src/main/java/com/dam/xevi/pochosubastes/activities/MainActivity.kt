package com.dam.xevi.pochosubastes.activities

import android.os.Build
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.dam.xevi.pochosubastes.R
import com.dam.xevi.pochosubastes.databinding.ActivityMainBinding
import com.dam.xevi.pochosubastes.fragments.AboutUsFragment
import com.dam.xevi.pochosubastes.fragments.HomeFragment
import com.dam.xevi.pochosubastes.fragments.SubastesFragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationView: NavigationView
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_container, HomeFragment())
                .commit()
        }
        setSupportActionBar(binding.toolBar)
        drawerLayout = binding.main
        var toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.toolBar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView = binding.navigationView
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                   navigateToFragment(HomeFragment())
                    true
                }
                R.id.aboutUs -> {
                    navigateToFragment(AboutUsFragment())
                    true
                }
                R.id.subastes -> {
                    navigateToFragment(SubastesFragment())
                    true
                }
                else -> false
            }
        }
    }
    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_container, fragment)
            .commit()
        drawerLayout.closeDrawer(GravityCompat.START)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_view -> {

                true
            }
            R.id.configMenu -> {
                onClickSettingsMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    private fun onClickSettingsMenu() {
        val popupMenu = PopupMenu(this, findViewById(R.id.configMenu))
        popupMenu.menuInflater.inflate(R.menu.settings_menu, popupMenu.menu)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true)
        }
        popupMenu.show()
    }


}