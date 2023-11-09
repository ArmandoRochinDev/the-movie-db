package com.armandorochin.themoviedb.ui.screens.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.armandorochin.themoviedb.R
import com.armandorochin.themoviedb.databinding.ActivityMainBinding
import com.armandorochin.themoviedb.ui.screens.about.AboutFragment
import com.armandorochin.themoviedb.ui.screens.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    private var keep = true
    private val delay = 700L

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSplashscreen(splashScreen)
        setupToolbar()

        if (savedInstanceState == null){
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<HomeFragment>(R.id.fragmentContainer)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_about) {

            // Do something
            //addFragmentToBackstack()
            addFragmentToBackstack(AboutFragment())
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.layoutToolbar.toolbar)
        supportActionBar?.title = "TMDb - ShowCase App"

    }

    private fun setupSplashscreen(splashScreen: androidx.core.splashscreen.SplashScreen) {
        splashScreen.setKeepOnScreenCondition{ keep }
        Handler(Looper.getMainLooper()).postDelayed({
            keep = false
        }, delay)
    }
    fun addFragmentToBackstack(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(fragment.tag)
        transaction.commit()
    }
}