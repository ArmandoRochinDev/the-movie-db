package com.armandorochin.themoviedb.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.armandorochin.themoviedb.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(){

    private lateinit var binding: ActivityHomeBinding

    private val homeViewModel: HomeViewModel by viewModels()
    private var adapter: DiscoveryAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLayout.toolbar)

        setupRecycler()
    }

    private fun setupRecycler() {
        adapter = DiscoveryAdapter()
        binding.rvMovies.layoutManager = GridLayoutManager(this, 2)
        binding.rvMovies.adapter = adapter
        homeViewModel.getDiscoveryLiveData().observe(this){ movies ->
            lifecycleScope.launch {
                adapter?.submitData(movies)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter = null
    }
}