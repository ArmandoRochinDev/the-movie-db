package com.armandorochin.themoviedb.ui.screens.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.armandorochin.themoviedb.R
import com.armandorochin.themoviedb.databinding.ActivityHomeBinding
import com.armandorochin.themoviedb.ui.screens.discovery.DiscoveryMoviesFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    private var keep = true
    private val delay = 700L

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSplashscreen(splashScreen)

        if (savedInstanceState == null){
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<DiscoveryMoviesFragment>(R.id.fragmentContainer)
            }
        }
    }

    private fun setupSplashscreen(splashScreen: androidx.core.splashscreen.SplashScreen) {
        splashScreen.setKeepOnScreenCondition{ keep }
        Handler(Looper.getMainLooper()).postDelayed({
            keep = false
        }, delay)
    }

    fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }
}